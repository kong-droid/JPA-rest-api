package shop.boardpilot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import shop.boardpilot.model.dto.request.BoardRequestDto;
import shop.boardpilot.model.dto.response.BoardResponseDto;
import shop.boardpilot.model.dto.response.PageResponseDto;
import shop.boardpilot.service.BoardService;
import javax.validation.constraints.Positive;
import java.util.concurrent.Callable;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @PostMapping(value = "/read")
    public Callable<PageResponseDto<BoardResponseDto>> readBoards(@RequestBody BoardRequestDto boardDto, Pageable pageable) {
        return () -> new PageResponseDto<>(boardService.getBoardByKeyword(boardDto, pageable));
    }

    @GetMapping(value = "/read/{id}")
    public Callable<BoardResponseDto> readBoard(@PathVariable @Positive int id) {
        return () -> boardService.getBoardById(id);
    }

    @PostMapping(value = "/read/check-password")
    public Callable<BoardResponseDto> checkPassword(@RequestBody BoardRequestDto boardDto) {
        return () -> boardService.getBoardByIdAndPassword(boardDto);
    }

    @PostMapping(value = "/register")
    public Callable<BoardResponseDto> registBoard(@RequestBody BoardRequestDto boardDto) {
        return () -> boardService.create(boardDto);
    }

    @PostMapping(value = "/modify")
    public Callable<BoardResponseDto> modifyBoard(@RequestBody BoardRequestDto boardDto) {
        return () -> boardService.update(boardDto);
    }

    @PostMapping(value = "/remove")
    public Callable<Boolean> removeBoard(@RequestBody BoardRequestDto boardDto) {
        return () -> boardService.delete(boardDto);
    }

}
