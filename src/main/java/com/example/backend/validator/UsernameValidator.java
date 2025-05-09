package com.example.backend.validator;

import com.example.backend.model.Validation;
import com.example.backend.model.enums.ValidationState;
import com.example.backend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsernameValidator {
    @Autowired
    AccountService accountService;

    public Validation usernameCheck(String value) {
        if (value == null || value.isEmpty()) {
            return new Validation(ValidationState.ERROR, "Tên tài khoản không được bỏ trống!");
        }
        if (value.length() > 255) {
            return new Validation(ValidationState.ERROR, "Tên tài khoản có độ dài không được quá 255 ký tự!");
        }
        if (accountService.checkAccountExistByUsername(value)) {
            return new Validation(ValidationState.ERROR, "Tên tài khoản đã được sử dụng!");
        }
        return new Validation(ValidationState.OK, "OK!");
    }
}
