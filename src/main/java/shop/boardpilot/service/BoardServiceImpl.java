package shop.boardpilot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import shop.boardpilot.exception.ResourceNotFoundException;
import shop.boardpilot.model.dto.request.BoardRequestDto;
import shop.boardpilot.model.dto.response.BoardResponseDto;
import shop.boardpilot.model.entity.BoardEntity;
import shop.boardpilot.repository.BoardRepository;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    private final PostService postService;

    @Override
    public Page<BoardResponseDto> getBoardByKeyword(BoardRequestDto boardDto, Pageable pageable) {
        if(StringUtils.isEmpty(boardDto.getKeyword())) {
            return boardRepository.findAllByOrderByCreatedDateDesc(pageable).map(this::toResponse);
        } else {
            return boardRepository.findAllByKeyword(boardDto.getKeyword(), pageable).map(this::toResponse);
        }
    }

    @Override
    public BoardResponseDto getBoardById(int id) {
        return boardRepository
            .findById(id)
            .map(this::toResponse)
            .orElseThrow(() -> new ResourceNotFoundException("게시판을 찾을 수 없습니다."));
    }

    @Override
    public BoardEntity get(int id) {
        return boardRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("게시판을 찾을 수 없습니다."));
    }

    @Override
    public BoardResponseDto getBoardByIdAndPassword(BoardRequestDto boardDto) {
        return boardRepository
            .findByIdAndPassword(boardDto.getId(), boardDto.getPassword())
            .map(this::toResponse)
            .orElseThrow(() -> new ResourceNotFoundException("비밀번호가 틀립니다. 다시 입력하세요."));
    }

    @Override
    public BoardResponseDto create(BoardRequestDto boardDto) {
        BoardEntity entity = this.toRequest(boardDto, true);
        boardRepository.save(entity);
        return new BoardResponseDto(entity.getId());
    }

    @Override
    public BoardResponseDto update(BoardRequestDto boardDto) {
        BoardEntity entity = this.toRequest(boardDto, false);
        boardRepository.save(entity);
        return new BoardResponseDto(entity.getId());
    }

    @Transactional
    @Override
    public boolean delete(BoardRequestDto boardDto) {
        this.getBoardByIdAndPassword(boardDto);
        postService.deleteByBoardId(boardDto.getId());
        return boardRepository.removeByIdAndPassword(boardDto.getId(), boardDto.getPassword()) > 0;
    }

    private BoardEntity toRequest(BoardRequestDto boardDto, boolean isAdd) {
        BoardEntity entity = new BoardEntity();
        if(!isAdd) entity = this.get(boardDto.getId());
        if (!StringUtils.isEmpty(boardDto.getNickName())) entity.setNickName(boardDto.getNickName());
        if (!StringUtils.isEmpty(boardDto.getPassword())) entity.setPassword(boardDto.getPassword());
        entity.setTitle(boardDto.getTitle());
        return entity;
    }

    private BoardResponseDto toResponse (BoardEntity entity) {
        BoardResponseDto response = new BoardResponseDto();
        response.setId(entity.getId());
        response.setPassword(entity.getPassword());
        response.setNickName(entity.getNickName());
        response.setTitle(entity.getTitle());
        response.setCreatedDate(entity.getCreatedDate());
        response.setUpdatedDate(entity.getUpdatedDate());
        return response;
    }
}
