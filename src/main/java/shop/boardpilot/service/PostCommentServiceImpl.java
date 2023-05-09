package shop.boardpilot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import shop.boardpilot.constant.CommonCode;
import shop.boardpilot.exception.ResourceNotFoundException;
import shop.boardpilot.model.dto.request.PostCommentRequestDto;
import shop.boardpilot.model.dto.response.PostCommentResponseDto;
import shop.boardpilot.model.entity.PostCommentEntity;
import shop.boardpilot.repository.PostCommentRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostCommentServiceImpl implements PostCommentService {

    private final PostCommentRepository commentRepository;

    private final AttachmentService attachmentService;
    @Override
    public Page<PostCommentResponseDto> getPostCommentByPostId(int postId, Pageable pageable) {
        return commentRepository.findAllByPostIdOrderByCreatedDateDesc(postId, pageable).map(this::toResponse);
    }

    @Override
    public PostCommentEntity get(int id) {
        return commentRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("댓글을 찾을 수 없습니다."));
    }

    @Override
    public List<PostCommentResponseDto> getPostCommentByPostId(int postId) {
        return commentRepository
            .findAllByPostIdOrderByCreatedDateDesc(postId)
            .stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    @Override
    public PostCommentResponseDto getPostCommentByIdAndPassword(PostCommentRequestDto commentDto) {
        return commentRepository
            .findByIdAndPassword(commentDto.getId(), commentDto.getPassword())
            .map(this::toResponse)
            .orElseThrow(() -> new ResourceNotFoundException("비밀번호가 틀립니다. 다시 입력하세요."));
    }

    @Override
    public PostCommentResponseDto create(PostCommentRequestDto commentDto) {
        PostCommentEntity entity = this.toRequest(commentDto, true);
        commentRepository.save(entity);
        return new PostCommentResponseDto(entity.getId(), entity.getPostId());
    }

    @Override
    public PostCommentResponseDto update(PostCommentRequestDto commentDto) {
        PostCommentEntity entity = this.toRequest(commentDto, false);
        commentRepository.save(entity);
        return new PostCommentResponseDto(entity.getId(), entity.getPostId());
    }

    @Transactional
    @Override
    public boolean delete(PostCommentRequestDto commentDto) {
        this.getPostCommentByIdAndPassword(commentDto);
        if(commentRepository.removeById(commentDto.getId()) > 0) {
            attachmentService.deleteByTable(CommonCode.POST_COMMENT_TABLE, commentDto.getId());
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    @Override
    public boolean deleteByPostId(int postId) {
        List<PostCommentEntity> list = commentRepository.findAllByPostIdOrderByCreatedDateDesc(postId);
        if(!CollectionUtils.isEmpty(list)) {
            list.forEach(item -> {
                attachmentService.deleteByTable(CommonCode.POST_COMMENT_TABLE, item.getId());
            });
        }
        return commentRepository.removeAllByPostId(postId) > 0;
    };

    private PostCommentEntity toRequest(PostCommentRequestDto commentDto, boolean isAdd) {
        PostCommentEntity entity = new PostCommentEntity();
        if(!isAdd) entity = this.get(commentDto.getId());
        if (commentDto.getPostId() != null) entity.setPostId(commentDto.getPostId());
        if (commentDto.getNickName() != null) entity.setNickName(commentDto.getNickName());
        if (commentDto.getPassword() != null) entity.setPassword(commentDto.getPassword());
        entity.setComment(commentDto.getComment());
        return entity;
    }

    private PostCommentResponseDto toResponse (PostCommentEntity entity) {
        PostCommentResponseDto response = new PostCommentResponseDto();
        response.setId(entity.getId());
        response.setPostId(entity.getPostId());
        response.setPassword(entity.getPassword());
        response.setNickName(entity.getNickName());
        response.setComment(entity.getComment());
        response.setCreatedDate(entity.getCreatedDate());
        response.setUpdatedDate(entity.getUpdatedDate());
        response.setAttachment(attachmentService.getAllByTable(CommonCode.POST_COMMENT_TABLE, entity.getId()));
        return response;
    }
}
