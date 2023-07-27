package shop.boardpilot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import shop.boardpilot.model.dto.request.AttachmentRequestDto;
import shop.boardpilot.model.dto.response.AttachmentResponseDto;
import shop.boardpilot.service.AttachmentService;

import javax.validation.constraints.Positive;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.Callable;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/attachment")
public class AttachmentController {

    private final AttachmentService attachmentService;

    @GetMapping("/download/{id}")
    public Callable<ResponseEntity<InputStreamResource>> download(@PathVariable @Positive int id) throws FileNotFoundException {
        return () -> attachmentService.download(id);
    }

    @PostMapping(value = "/register")
    public Callable<List<AttachmentResponseDto>> registFile(@ModelAttribute AttachmentRequestDto attachmentDto) {
        return () -> attachmentService.create(attachmentDto);
    }

    @PostMapping(value = "/modify")
    public Callable<Boolean> modifyFile(@RequestBody AttachmentRequestDto attachmentDto) {
        return () -> attachmentService.update(attachmentDto);
    }

    @PostMapping(value = "/remove/{id}")
    public Callable<Boolean> removeFile(@PathVariable @Positive int id) {
        return () -> attachmentService.delete(id);
    }

}
