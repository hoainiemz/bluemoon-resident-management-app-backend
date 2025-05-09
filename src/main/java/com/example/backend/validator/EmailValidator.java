package com.example.backend.validator;

import com.example.backend.model.Validation;
import com.example.backend.model.enums.ValidationState;
import com.example.backend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailValidator {
    @Autowired
    private AccountService accountService;

    public Validation emailSignupCheck(String value) {
        if (value == null || value.isEmpty()) {
            return new Validation(ValidationState.ERROR, "Địa chỉ email không được bỏ trống!");
        }
        if (value.length() > 255) {
            return new Validation(ValidationState.ERROR, "Địa chỉ email có độ dài không được quá 255 ký tự!");
        }
        if (!org.apache.commons.validator.routines.EmailValidator.getInstance().isValid(value)) {
            return new Validation(ValidationState.ERROR, "Email không đúng định dạng!");
        }
        if (accountService.checkAccountExistByEmail(value)) {
            return new Validation(ValidationState.ERROR, "Địa chỉ email đã được sử dụng cho tài khoản khác!");
        }
        return new Validation(ValidationState.OK, "OK!");
    }

    public Validation emailCheck(String value) {
        if (value == null || value.isEmpty()) {
            return new Validation(ValidationState.ERROR, "Địa chỉ email không được bỏ trống!");
        }
        if (value.length() > 255) {
            return new Validation(ValidationState.ERROR, "Địa chỉ email có độ dài không được quá 255 ký tự!");
        }
        if (!org.apache.commons.validator.routines.EmailValidator.getInstance().isValid(value)) {
            return new Validation(ValidationState.ERROR, "Email không đúng định dạng!");
        }
        return new Validation(ValidationState.OK, "OK!");
    }
}
