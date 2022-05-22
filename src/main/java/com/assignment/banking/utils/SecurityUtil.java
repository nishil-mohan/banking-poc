package com.assignment.banking.utils;

import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {

    public String getLoggedInUserName() throws AuthorizationServiceException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            return currentUserName;
        }
        throw new AuthorizationServiceException("Anonymouse USer. Operation not permitted");
    }

    public void checkForLoggedInUserAccess(String userName) throws AuthorizationServiceException {
       String loggedInUser = getLoggedInUserName();
       if (!loggedInUser.equalsIgnoreCase(userName)){
           throw new AuthorizationServiceException("LoggedIn user has no access to other users account.");
       }
    }
}
