package shop.boardpilot.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.domain.Page;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageResponseDto<T> {
    private String boardTitle;
    private List<T> list;
    private long totalCount;
    private int totalPages;

    public PageResponseDto(Page<T> page, String boardTitle) {
        this.boardTitle = boardTitle;
        this.list = page.getContent();
        this.totalCount = page.getTotalElements();
        this.totalPages = page.getTotalPages();
    }

    public PageResponseDto(Page<T> page) {
        this.list = page.getContent();
        this.totalCount = page.getTotalElements();
        this.totalPages = page.getTotalPages();
    }
}
