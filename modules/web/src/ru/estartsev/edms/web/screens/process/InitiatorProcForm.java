package ru.estartsev.edms.web.screens.process;

import com.haulmont.bpm.entity.ProcActor;
import com.haulmont.bpm.entity.ProcInstance;
import com.haulmont.bpm.entity.ProcTask;
import com.haulmont.bpm.gui.form.ProcForm;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.WindowParam;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.components.Window;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.security.entity.User;
import org.slf4j.Logger;
import ru.estartsev.edms.entity.OutgoingDocument;
import ru.estartsev.edms.entity.Worker;
import ru.estartsev.edms.service.entityServices.OutgoingDocumentService;

import javax.inject.Inject;
import java.util.*;

@UiController("InitiatorProcForm")
@UiDescriptor("initiator-proc-form.xml")
@DialogMode(forceDialog = true)
public class InitiatorProcForm extends Screen implements ProcForm {

    private static final String PROCESS_CODE = "documentApproval";

    @Inject
    private DataManager dataManager;

    @WindowParam
    private ProcInstance procInstance;

    @Inject
    LookupField<Worker> workerLookupField;

    @Inject
    CollectionContainer<ProcActor> procActorDc;

    @Inject
    OutgoingDocumentService outgoingDocumentService;

    @Inject
    Button addActorButton;

    @Inject
    Logger log;

    @Inject
    Table<ProcActor> procActorsTable;


    @Subscribe
    public void onInit(InitEvent event) {
        List<Worker> workerList = dataManager.load(Worker.class)
                .query("select e from edms_Worker e")
                .view("worker-view-with-image")
                .list();
        workerLookupField.setOptionsList(workerList);
        procActorDc.setItems(procInstance.getProcActors());
    }

    @Subscribe("addActorButton")
    public void addActorToProcess(Button.ClickEvent event) {
        if (workerLookupField.getValue() != null) {
            User reconciler = workerLookupField.getValue().getUser();
            ProcActor reconcilerProcActor = outgoingDocumentService.createProcActor("reconciling", procInstance, reconciler);
            Set<ProcActor> procActorSet = procInstance.getProcActors();
            procActorSet.add(reconcilerProcActor);
        }
    }

    @Override
    public String getComment() {
        return null;
    }

    @Override
    public Map<String, Object> getFormResult() {
        Map<String, Object> res = new HashMap<>();
        return res;
    }

    public void onWindowCommit() {
            close(WINDOW_COMMIT_AND_CLOSE_ACTION);
    }

    public void onWindowClose() {
        closeWithDefaultAction();
    }
}