package shop.boardpilot.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostCommentResponseDto {
    private int id;
    private int postId;
    private String nickName;
    private String password;
    private String comment;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedDate;
    private List<AttachmentResponseDto> attachment;
    public PostCommentResponseDto(int id, int postId) {
        this.id = id;
        this.postId = postId;
    }
    public PostCommentResponseDto() {}
}
