package shop.boardpilot.model.dto.response;

import lombok.Data;

@Data
public class SuccessResponseDto<T> {
    private final int code = 200;
    private final String message = "success";
    private Object data;

    public  SuccessResponseDto(Object data) {
        this.data = data;
    }
}
