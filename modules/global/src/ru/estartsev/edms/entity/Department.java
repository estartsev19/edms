package ru.estartsev.edms.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NamePattern("%s|title")
@Table(name = "EDMS_DEPARTMENT")
@Entity(name = "edms_Department")
public class Department extends StandardEntity {
    private static final long serialVersionUID = -2371588003508488002L;

    @NotNull
    @Column(name = "CODE", nullable = false)
    private String code;

    @NotNull
    @Column(name = "NAME", nullable = false)
    private String title;

    @OnDeleteInverse(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LEAD_DEPARTMENT_ID")
    private Department leadDepartment;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPARTMENT_MANAGER_ID")
    private Worker departmentManager;

    public Worker getDepartmentManager() {
        return departmentManager;
    }

    public void setDepartmentManager(Worker departmentManager) {
        this.departmentManager = departmentManager;
    }

    public Department getLeadDepartment() {
        return leadDepartment;
    }

    public void setLeadDepartment(Department leadDepartment) {
        this.leadDepartment = leadDepartment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}