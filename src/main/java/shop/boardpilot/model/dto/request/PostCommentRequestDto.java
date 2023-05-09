package shop.boardpilot.model.dto.request;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostCommentRequestDto {
    private int id;
    private Integer postId;
    private String nickName;
    private String password;
    private String comment;
}