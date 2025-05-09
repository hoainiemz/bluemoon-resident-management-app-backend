package com.example.backend.validator;

import com.example.backend.model.Validation;
import com.example.backend.model.enums.ValidationState;
import com.example.backend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PhoneValidator {
    @Autowired
    AccountService accountService;

    public Validation phoneCheck(String value) {
        if (value == null || value.isEmpty()) {
            return new Validation(ValidationState.ERROR, "Số điện thoại không được để trống!");
        }
        if (value.length() > 255) {
            return new Validation(ValidationState.ERROR, "Số điện thoại có độ dài không được quá 255 ký tự!");
        }
        if (accountService.checkAccountExistByPhone(value)) {
            return new Validation(ValidationState.ERROR, "Số điện thoại đã được sử dụng cho tài khoản khác!");
        }
        return new Validation(ValidationState.OK, "OK!");
    }
}
