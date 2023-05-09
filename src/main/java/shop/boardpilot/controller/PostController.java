package shop.boardpilot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import shop.boardpilot.model.dto.request.PostRequestDto;
import shop.boardpilot.model.dto.response.BoardResponseDto;
import shop.boardpilot.model.dto.response.PageResponseDto;
import shop.boardpilot.model.dto.response.PostResponseDto;
import shop.boardpilot.service.BoardService;
import shop.boardpilot.service.PostService;

import javax.validation.constraints.Positive;
import java.util.concurrent.Callable;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final BoardService boardService;

    @PostMapping(value = "/read")
    public Callable<PageResponseDto<PostResponseDto>> readPosts(@RequestBody PostRequestDto postDto, Pageable pageable) {
        BoardResponseDto boardDto = boardService.getBoardById(postDto.getBoardId());
        return () -> new PageResponseDto<>(postService.getPostByKeyword(postDto, pageable), boardDto.getTitle());
    }

    @PostMapping(value = "/read/check-password")
    public Callable<PostResponseDto> checkPassword(@RequestBody PostRequestDto postDto) {
        return () -> postService.getPostByIdAndPassword(postDto);
    }

    @GetMapping(value = "/read/{id}")
    public Callable<PostResponseDto> readPost(@PathVariable @Positive int id) {
        return () -> postService.getPostById(id);
    }

    @PostMapping(value = "/register")
    public Callable<PostResponseDto> registPost(@RequestBody PostRequestDto postDto) {
        return () -> postService.create(postDto);
    }

    @PostMapping(value = "/modify")
    public Callable<PostResponseDto> modifyPost(@RequestBody PostRequestDto postDto) {
        return () -> postService.update(postDto);
    }

    @PostMapping(value = "/remove")
    public Callable<Boolean> removPost(@RequestBody PostRequestDto postDto) {
        return () -> postService.delete(postDto);
    }

}
