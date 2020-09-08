package ru.estartsev.edms.web.screens.worker;

import com.haulmont.cuba.gui.screen.*;
import ru.estartsev.edms.entity.Worker;

@UiController("edms_Worker.browse")
@UiDescriptor("worker-browse.xml")
@LookupComponent("workersTable")
@LoadDataBeforeShow
public class WorkerBrowse extends StandardLookup<Worker> {
}