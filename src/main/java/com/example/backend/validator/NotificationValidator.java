package com.example.backend.validator;

import com.example.backend.model.Validation;
import com.example.backend.model.enums.ValidationState;
import org.springframework.stereotype.Component;

@Component
public class NotificationValidator {
    public Validation titleCheck(String value) {
        if (value == null || value.isEmpty()) {
            return new Validation(ValidationState.ERROR, "Tiêu đề không được để trống");
        }
        if (value.length() > 255) {
            return new Validation(ValidationState.ERROR, "Tiêu đề có độ dài không quá 255 ký tự");
        }
        return new Validation(ValidationState.OK, "OK!");
    }

    public Validation contentCheck(String value) {
        if (value == null || value.isEmpty()) {
            return new Validation(ValidationState.ERROR, "Nội dung không được để trống");
        }
        if (value.length() > 255) {
            return new Validation(ValidationState.ERROR, "Nội dung có độ dài không quá 255 ký tự");
        }
        return new Validation(ValidationState.OK, "OK!");
    }
}
