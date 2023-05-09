package shop.boardpilot.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BoardResponseDto {
    private int id;
    private String nickName;
    private String password;
    private String title;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public BoardResponseDto(int id) {
        this.id = id;
    }
    public BoardResponseDto() {}
}
