package com.son.todolist.common.security;

import com.son.todolist.project.ProjectUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class ProjectSecurity implements AuthorizationManager<RequestAuthorizationContext> {

    @Autowired
    private ProjectUserRepository projectUserRepository;

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {


        return null;
    }

    private boolean isBelongUser(Long projectId ) {
        return true;
    }
}
