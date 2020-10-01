package ru.estartsev.edms.service.entityServices;

import com.haulmont.bpm.entity.ProcActor;
import com.haulmont.bpm.entity.ProcInstance;
import com.haulmont.bpm.entity.ProcRole;
import com.haulmont.bpm.service.BpmEntitiesService;
import com.haulmont.cuba.core.global.*;

import com.haulmont.cuba.security.entity.User;
import org.springframework.stereotype.Service;
import ru.estartsev.edms.entity.DocumentType;
import ru.estartsev.edms.entity.OutgoingDocument;
import ru.estartsev.edms.entity.Worker;
import ru.estartsev.edms.service.EntityCodeCreateService;

import javax.inject.Inject;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.UUID;

@Service(OutgoingDocumentService.NAME)
public class OutgoingDocumentServiceBean implements OutgoingDocumentService {

    private static final String PROCESS_CODE = "documentApproval";
    private static final String day = "DD";
    private static final String month = "MM";
    private static final String yearFourDigits = "YYYY";
    private static final String yearTwoDigits = "YY";

    @Inject
    private DataManager dm;

    @Inject
    private Metadata metadata;

    @Inject
    private EntityCodeCreateService entityCodeCreateService;

    @Inject
    private UserSessionSource userSessionSource;

    @Inject
    private BpmEntitiesService bpmEntitiesService;

    /**
     * Метод возвращает значение текущего работника
     *
     * @return возвращает значение текущего работника
     */
    @Override
    public Worker getCurrentWorker() {
        User currentUser = userSessionSource.getUserSession().getUser();
        return dm.load(Worker.class)
                .query("SELECT e FROM edms_Worker e where e.user = :currentUser")
                .parameter("currentUser", currentUser)
                .one();
    }

    /**
     * Метод позволяет получить редактируемый исходящий документ
     *
     * @param id - ID исходящего документа
     * @return возвращает редактируемый в настоящий момент исходящий документ
     */
    @Override
    public OutgoingDocument getCurrentOutgoingDocument(UUID id) {
        return dm.load(OutgoingDocument.class)
                .query("select e from edms_OutgoingDocument e where e.id = :id")
                .parameter("id", id)
                .view("outgoingDocument-editView")
                .one();
    }

    /**
     * Метод создает рег. номер сущности "Исходящий документ"
     *
     * @param template       - макрос для определения формата номера
     * @param date           - дата регистрации
     * @param numberOfDigits - количество цифр в номере
     * @return возвращает регистрационный номер
     */
    @Override
    public String setRegNumberFromTemplate(String template, LocalDate date, int numberOfDigits) {
        int number = Integer.parseInt(entityCodeCreateService.createCode("", "outgoingDocumentSequence"));
        String zeroString = "";
        for (int i = 0; i < numberOfDigits; i++) {
            zeroString = zeroString.concat("0");
        }
        DecimalFormat df = new DecimalFormat(zeroString);
        String str = df.format(number);
        if (template.contains(day)) {
            template = template.replace(day, createDayOrMonthString(date.getDayOfMonth()));
        }
        if (template.contains(month)) {
            template = template.replace(month, createDayOrMonthString(date.getMonthValue()));
        }
        if (template.contains(yearFourDigits)) {
            String year = String.valueOf(date.getYear());
            template = template.replace(yearFourDigits, year);
            template = template.concat(str);
            return template;
        }
        if (template.contains(yearTwoDigits)) {
            int yearNum = date.getYear() % 100;
            String year = String.valueOf(yearNum);
            template = template.replace(yearTwoDigits, year);
            template = template.concat(str);
            return template;
        }
        template = template.concat(str);
        return template;
    }

    /**
     * Метод создает наименование сущности "Исходящий документ"
     *
     * @param documentType - вид документа
     * @param regNumber    - рег. номер
     * @param date         - дата регистрации
     * @param destination  - адресат
     * @param theme        - тема
     * @return возвращает наименование сущности
     */
    @Override
    public String setTitleForNewDocument(DocumentType documentType, String regNumber, LocalDate date,
                                         String destination, String theme) {
        if (regNumber == null) {
            String template = "%s %s, %s";
            return String.format(template, documentType.getTitle(), destination, theme);
        }
        String template = "%s № %s от %02d.%02d.%d в %s, %s";
        return String.format(template, documentType.getTitle(), regNumber, date.getDayOfMonth(),
                date.getMonthValue(), date.getYear(), destination, theme);
    }

    /**
     * Метод присваивает процессным ролям пользователей
     *
     * @param procRoleCode - код процессной роли
     * @param procInstance - экземпляр процесса
     * @param user         - пользователь
     * @return возвращает созданного участника процесса
     */
    @Override
    public ProcActor createProcActor(String procRoleCode, ProcInstance procInstance, User user) {
        ProcActor procActor = metadata.create(ProcActor.class);
        procActor.setUser(user);
        ProcRole initiatorProcRole = bpmEntitiesService.findProcRole(PROCESS_CODE, procRoleCode, View.MINIMAL);
        procActor.setProcRole(initiatorProcRole);
        procActor.setProcInstance(procInstance);
        return procActor;
    }

    private String createDayOrMonthString(int number) {
        String str = "";
        if (number < 10) {
            return str.concat("0" + number);
        } else {
            return str.concat(String.valueOf(number));
        }
    }
}