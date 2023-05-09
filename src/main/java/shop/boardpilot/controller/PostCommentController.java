package shop.boardpilot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import shop.boardpilot.model.dto.request.PostCommentRequestDto;
import shop.boardpilot.model.dto.response.PageResponseDto;
import shop.boardpilot.model.dto.response.PostCommentResponseDto;
import shop.boardpilot.service.PostCommentService;

import javax.validation.constraints.Positive;
import java.util.concurrent.Callable;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/post-comment")
public class PostCommentController {

    private final PostCommentService commentService;

    @GetMapping(value = "/read/{postId}")
    public Callable<PageResponseDto<PostCommentResponseDto>> readComment(@PathVariable @Positive int postId, Pageable pageable) {
        return () -> new PageResponseDto<>(commentService.getPostCommentByPostId(postId, pageable));
    }

    @PostMapping(value = "/read/check-password")
    public Callable<PostCommentResponseDto> checkPassword(@RequestBody PostCommentRequestDto replyDto) {
        return () -> commentService.getPostCommentByIdAndPassword(replyDto);
    }

    @PostMapping(value = "/register")
    public Callable<PostCommentResponseDto> registComment(@RequestBody PostCommentRequestDto replyDto) {
        return () -> commentService.create(replyDto);
    }

    @PostMapping(value = "/modify")
    public Callable<PostCommentResponseDto> modifyComment(@RequestBody PostCommentRequestDto replyDto) {
        return () -> commentService.update(replyDto);
    }

    @PostMapping(value = "/remove")
    public Callable<Boolean> removeComment(@RequestBody PostCommentRequestDto replyDto) {
        return () -> commentService.delete(replyDto);
    }
}
