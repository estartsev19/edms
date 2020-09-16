package ru.estartsev.edms.service.entityServices;

import com.haulmont.bpm.entity.ProcActor;
import com.haulmont.bpm.entity.ProcDefinition;
import com.haulmont.bpm.entity.ProcInstance;
import com.haulmont.bpm.entity.ProcRole;
import com.haulmont.bpm.service.BpmEntitiesService;
import com.haulmont.cuba.core.global.*;

import com.haulmont.cuba.security.entity.User;
import org.springframework.stereotype.Service;
import ru.estartsev.edms.core.EntityCodeCreator;
import ru.estartsev.edms.entity.DocumentType;
import ru.estartsev.edms.entity.Worker;

import javax.inject.Inject;
import java.text.DecimalFormat;
import java.time.LocalDate;

@Service(OutgoingDocumentService.NAME)
public class OutgoingDocumentServiceBean implements OutgoingDocumentService {

    private static final String PROCESS_CODE = "documentApproval";

    @Inject
    DataManager dm;

    @Inject
    Metadata metadata;

    @Inject
    EntityCodeCreator codeCreator;

    @Inject
    UserSessionSource userSessionSource;

    @Inject
    BpmEntitiesService bpmEntitiesService;

    @Override
    public Worker getCurrentWorker() {
        User currentUser = userSessionSource.getUserSession().getUser();
        return dm.load(Worker.class)
                .query("SELECT e FROM edms_Worker e where e.user = :currentUser")
                .parameter("currentUser", currentUser)
                .one();

    }

    @Override
    public String setRegNumberFromTemplate(String template, LocalDate date, int numberOfDigits) {
        int number = Integer.valueOf(codeCreator.createCode("", "outgoingDocumentSequence"));
        String zeroString = "";
        for (int i = 0; i < numberOfDigits; i++){
            zeroString = zeroString.concat("0");
        }
        DecimalFormat df = new DecimalFormat(zeroString);
        String str = df.format(number);
        if (template.contains("DD")) {
            int dayNum = date.getDayOfMonth();
            String day = "";
            if (dayNum < 10) {
                day = day.concat("0" + dayNum);
            } else {
                day = day.concat(String.valueOf(dayNum));
            }
            template = template.replace("DD", day);
        }
        if (template.contains("MM")) {
            int monthNum = date.getMonthValue();
            String month = "";
            if (monthNum < 10) {
                month = month.concat("0" + monthNum);
            } else {
                month = month.concat(String.valueOf(monthNum));
            }
            template = template.replace("MM", month);
        }
        if (template.contains("YYYY")){
            String year = String.valueOf(date.getYear());
            template = template.replace("YYYY", year);
            template = template.concat(str);
            return template;
        }
        if (template.contains("YY")){
            int yearNum = date.getYear() % 100;
            String year = String.valueOf(yearNum);
            template = template.replace("YY", year);
            template = template.concat(str);
            return template;
        }
        template = template.concat(str);
        return template;
    }

    @Override
    public String setTitleForNewDocument(DocumentType documentType, String regNumber, LocalDate date,
                                         String destination, String theme){
        return documentType.getTitle() + " № " + regNumber + " от " +
                date.getDayOfMonth() + "." + date.getMonthValue() + "." + date.getYear()
                + " в " + destination + ", " + theme;
    }

    @Override
    public ProcActor createProcActor(String procRoleCode, ProcInstance procInstance, User user) {
        ProcActor initiatorProcActor = metadata.create(ProcActor.class);
        initiatorProcActor.setUser(user);
        ProcRole initiatorProcRole = bpmEntitiesService.findProcRole(PROCESS_CODE, procRoleCode, View.MINIMAL);
        initiatorProcActor.setProcRole(initiatorProcRole);
        initiatorProcActor.setProcInstance(procInstance);
        return initiatorProcActor;
    }

}