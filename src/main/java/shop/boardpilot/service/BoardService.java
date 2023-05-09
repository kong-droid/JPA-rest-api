package shop.boardpilot.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.boardpilot.model.dto.request.BoardRequestDto;
import shop.boardpilot.model.dto.response.BoardResponseDto;
import shop.boardpilot.model.entity.BoardEntity;

public interface BoardService {
    Page<BoardResponseDto> getBoardByKeyword(BoardRequestDto boardDto, Pageable pageable);
    BoardResponseDto getBoardById(int id);
    BoardEntity get(int id);
    BoardResponseDto getBoardByIdAndPassword(BoardRequestDto boardDto);
    BoardResponseDto create(BoardRequestDto boardDto);
    BoardResponseDto update(BoardRequestDto boardDto);
    boolean delete(BoardRequestDto boardDto);
}
