package com.motrechko.clientconnect.exception;

import com.motrechko.clientconnect.model.Role;

public class AccountRoleException extends RuntimeException {
    public AccountRoleException(Long id, Role role) {
        super("The account with the ID" + id + "has no rights to this action. Role: " + role);
    }
}
