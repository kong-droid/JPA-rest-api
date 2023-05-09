package shop.boardpilot.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostResponseDto {
    private int id;
    private int boardId;
    private String nickName;
    private String password;
    private String title;
    private String content;
    private int viewCount;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private List<PostCommentResponseDto> comment;
    private List<AttachmentResponseDto> attachment;
    public PostResponseDto(int id, int boardId) {
        this.id = id;
        this.boardId = boardId;
    }
    public PostResponseDto() {}
}
