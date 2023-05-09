package shop.boardpilot.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.boardpilot.model.dto.request.PostCommentRequestDto;
import shop.boardpilot.model.dto.response.PostCommentResponseDto;
import shop.boardpilot.model.entity.PostCommentEntity;

import java.util.List;

public interface PostCommentService {
    Page<PostCommentResponseDto> getPostCommentByPostId(int postId, Pageable pageable);
    PostCommentResponseDto getPostCommentByIdAndPassword(PostCommentRequestDto commentDto);
    List<PostCommentResponseDto> getPostCommentByPostId(int postId);
    PostCommentEntity get(int id);
    PostCommentResponseDto create(PostCommentRequestDto commentDto);
    PostCommentResponseDto update(PostCommentRequestDto commentDto);
    boolean delete(PostCommentRequestDto commentDto);
    boolean deleteByPostId(int postId);
}
