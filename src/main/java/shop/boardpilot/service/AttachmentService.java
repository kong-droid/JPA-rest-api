package shop.boardpilot.service;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import shop.boardpilot.model.dto.request.AttachmentRequestDto;
import shop.boardpilot.model.dto.response.AttachmentResponseDto;
import shop.boardpilot.model.entity.AttachmentEntity;

import java.io.FileNotFoundException;
import java.util.List;

public interface AttachmentService {
    List<AttachmentResponseDto> getAllByTable(String tableName, int tableId);
    AttachmentEntity get(int id);
    ResponseEntity<InputStreamResource> download(int id) throws FileNotFoundException;
    List<AttachmentResponseDto> create(AttachmentRequestDto attachmentDto);
    boolean update(AttachmentRequestDto attachmentDto);
    boolean delete(int id);
    boolean deleteByTable(String tableName, int tableId);
}
