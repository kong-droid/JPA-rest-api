package shop.boardpilot.exception;

import shop.boardpilot.constant.CommonCode;

public class InternalServerException extends DefaultNestedRuntimeExcepiton {
    public InternalServerException(String reason) {
        super(CommonCode.INTERNAL_SERVER_ERROR_CODE, reason);
    }

}