package shop.boardpilot.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.boardpilot.model.dto.request.PostRequestDto;
import shop.boardpilot.model.dto.response.PostResponseDto;
import shop.boardpilot.model.entity.PostEntity;

public interface PostService {
    Page<PostResponseDto> getPostByKeyword(PostRequestDto postDto, Pageable pageable);
    PostResponseDto getPostById(int id);
    PostEntity get(int id);
    PostResponseDto getPostByIdAndPassword(PostRequestDto postDto);
    PostResponseDto create(PostRequestDto postDto);
    PostResponseDto update(PostRequestDto postDto);
    boolean delete(PostRequestDto postDto);
    boolean deleteByBoardId(int boardId);
}
