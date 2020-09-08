package ru.estartsev.edms.service.entityServices;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.DataManager;

import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.security.entity.User;
import org.springframework.stereotype.Service;
import ru.estartsev.edms.entity.Worker;

import javax.inject.Inject;

@Service(OutgoingDocumentService.NAME)
public class OutgoingDocumentServiceBean implements OutgoingDocumentService {

    @Inject
    DataManager dm;

    @Override
    public Worker getCurrentWorker() {
        User currentUser = AppBeans.get(UserSessionSource.class).getUserSession().getUser();
        return dm.load(Worker.class)
                .query("SELECT e FROM edms_Worker e where e.user = :currentUser")
                .parameter("currentUser", currentUser)
                .one();

    }

}