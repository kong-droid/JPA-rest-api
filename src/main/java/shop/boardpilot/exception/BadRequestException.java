package shop.boardpilot.exception;

import shop.boardpilot.constant.CommonCode;

public class BadRequestException extends DefaultNestedRuntimeExcepiton {
    public BadRequestException(String reason) {
        super(CommonCode.BAD_REQUEST_CODE, reason);
    }
}