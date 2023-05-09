package shop.boardpilot.model.dto.request;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostRequestDto {
    private int id;
    private Integer boardId;
    private String nickName;
    private String password;
    private String title;
    private String content;
    private String keyword;
}
