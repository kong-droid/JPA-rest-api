package shop.boardpilot.model.dto.response;

import lombok.Data;


@Data
public class AttachmentResponseDto {
    private int id;
    private String originalFileName;
    private String fullPath;
}
