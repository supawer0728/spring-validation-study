package org.parfait.study.springvalidation.error;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class ErrorResponse<T> {
    public static final ErrorResponse<Object> UNKNOWN = new ErrorResponse<>(ErrorCode.UNKNOWN);

    ErrorCode code;
    T optional;

    public ErrorResponse(ErrorCode code) {
        this.code = code;
        this.optional = null;
    }

    enum ErrorCode {
        UNKNOWN,
        NO_AUTHENTICATION,
        FORBIDDEN,
        INVALID_PARAMETERS
    }
}
