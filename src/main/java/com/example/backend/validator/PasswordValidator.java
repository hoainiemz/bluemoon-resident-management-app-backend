package com.example.backend.validator;

import com.example.backend.model.Validation;
import com.example.backend.model.enums.ValidationState;
import org.springframework.stereotype.Component;

@Component
public class PasswordValidator {
    public Validation passwordCheck(String value) {
        if (value == null || value.isEmpty()) {
            return new Validation(ValidationState.ERROR, "Mật khẩu không được bỏ trống!");
        }
        if (value.length() > 255) {
            return new Validation(ValidationState.ERROR, "Mật khẩu có độ dài không được quá 255 ký tự!");
        }
        return new Validation(ValidationState.OK, "OK!");
    }
}
