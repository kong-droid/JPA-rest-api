package shop.boardpilot.exception;


import shop.boardpilot.constant.CommonCode;

public class ResourceNotFoundException extends DefaultNestedRuntimeExcepiton {
    public ResourceNotFoundException(String reason) {
        super(CommonCode.RESOURCE_NOT_FOUND_CODE, reason);
    }
}