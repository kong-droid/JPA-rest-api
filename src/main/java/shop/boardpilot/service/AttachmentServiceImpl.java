package shop.boardpilot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.boardpilot.exception.InternalServerException;
import shop.boardpilot.exception.ResourceNotFoundException;
import shop.boardpilot.model.dto.request.AttachmentRequestDto;
import shop.boardpilot.model.dto.response.AttachmentResponseDto;
import shop.boardpilot.model.entity.AttachmentEntity;
import shop.boardpilot.repository.AttachmentRepository;
import shop.boardpilot.utils.FileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository attachmentRepository;

    @Value("${file.upload-path}")
    private String uploadPath;

    @Override
    public List<AttachmentResponseDto> getAllByTable(String tableName, int tableId) {
        return attachmentRepository
            .findAllByTable(tableName, tableId)
            .stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public ResponseEntity<InputStreamResource> download(int id) throws FileNotFoundException {
        AttachmentEntity entity = this.get(id);
        String fullPath = entity.getPath() + "/" + entity.getUuidFileName();
        String originalFileName = FileUtil.transUtf8FileName(fullPath);
        File file = new File(fullPath);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity
            .ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + originalFileName + "\"")
            .contentLength(file.length())
            .contentType(MediaType.parseMediaType("application/octet-stream;"))
            .body(resource);
    }

    @Override
    public AttachmentEntity get(int id) {
        return attachmentRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("파일 정보를 찾을 수 없습니다."));
    }

    @Override
    public List<AttachmentResponseDto> create(AttachmentRequestDto attachmentDto) {
        List<AttachmentResponseDto> toResponse = new ArrayList<>();
        attachmentDto.getFiles().forEach(file -> {
            try {
                String calPath = FileUtil.calcPath(uploadPath);
                String saveName = FileUtil.uploadFile(calPath, file.getOriginalFilename(), file.getBytes());
                AttachmentEntity entity = new AttachmentEntity();
                entity.setOriginalFileName(file.getOriginalFilename());
                entity.setUuidFileName(saveName.substring(saveName.lastIndexOf("/") + 1));
                entity.setExtension(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1));
                entity.setSize(file.getBytes().length);
                entity.setPath(calPath);

                attachmentRepository.save(entity);

                AttachmentResponseDto response = new AttachmentResponseDto();
                response.setId(entity.getId());
                response.setOriginalFileName(entity.getOriginalFileName());
                response.setFullPath(entity.getPath() + "/" + entity.getUuidFileName());
                toResponse.add(response);
            } catch (IOException e) {
                throw new RuntimeException("파일첨부 작업을 실패했습니다.");
            }
        });
        return toResponse;
    }

    @Override
    public boolean update(AttachmentRequestDto attachmentDto) {
        attachmentDto.getIds().forEach(item -> {
            AttachmentEntity entity = this.get(item);
            entity.setTableName(attachmentDto.getTableName().toUpperCase());
            entity.setTableId(attachmentDto.getTableId());
            attachmentRepository.save(entity);
        });
        return true;
    };

    @Override
    @Transactional
    public boolean delete(int id) {
        this.get(id);
        return attachmentRepository.removeById(id) > 0;
    }

    @Override
    @Transactional
    public boolean deleteByTable(String tableName, int tableId) {
        return attachmentRepository.removeByTableNameAndTableId(tableName, tableId) > 0;
    }

    private AttachmentResponseDto toResponse (AttachmentEntity entity) {
        AttachmentResponseDto response = new AttachmentResponseDto();
        response.setId(entity.getId());
        response.setOriginalFileName(entity.getOriginalFileName());
        response.setFullPath(entity.getPath() + "/" + entity.getUuidFileName());
        return response;
    }


}
