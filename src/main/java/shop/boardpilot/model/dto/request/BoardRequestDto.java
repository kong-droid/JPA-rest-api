package shop.boardpilot.model.dto.request;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BoardRequestDto {
    private int id;
    private String nickName;
    private String password;
    private String title;
    private String keyword;
}
