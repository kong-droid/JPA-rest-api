package shop.boardpilot.model.dto.request;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttachmentRequestDto {
    private List<Integer> ids;
    private String tableName;
    private Integer tableId;
    private List<MultipartFile> files;
}
