package com.immooc.filter;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class PermsOrFilter extends AuthorizationFilter {
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        Subject subject = getSubject(servletRequest,servletResponse);
        String[] perms = (String[]) o;
        if(perms == null || perms.length == 0){
            return true;
        }
        boolean hasPerm = false;
        for(String perm : perms){
            try {
                subject.checkPermission(perm);
                hasPerm = true;
                break;
            } catch (AuthorizationException e) {
                e.printStackTrace();
            }
        }
        return hasPerm;
    }
}
