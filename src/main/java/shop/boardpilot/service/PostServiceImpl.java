package shop.boardpilot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import shop.boardpilot.model.dto.request.PostRequestDto;
import shop.boardpilot.model.dto.response.PostResponseDto;
import shop.boardpilot.model.entity.PostEntity;
import shop.boardpilot.repository.PostRepository;
import site.kongdroid.common.constant.CommonCode;
import site.kongdroid.common.exception.ResourceNotFoundException;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final PostCommentService commentService;
    private final AttachmentService attachmentService;

    @Override
    public Page<PostResponseDto> getPostByKeyword(PostRequestDto postDto, Pageable pageable) {
        if(StringUtils.isEmpty(postDto.getKeyword())) {
            return postRepository.findAllByBoardIdOrderByCreatedDateDesc(postDto.getBoardId(), pageable).map(this::toResponse);
        } else {
            return postRepository.findAllByKeyword(postDto.getKeyword(), pageable).map(this::toResponse);
        }
    }

    @Override
    public PostResponseDto getPostById(int id) {
        PostResponseDto response =
            postRepository.findById(id)
            .map(this::toResponse)
            .orElseThrow(() -> new ResourceNotFoundException("게시글을 찾을 수 없습니다."));

        // 조회수 등록
        postRepository.save(this.toRequestByViewCount(response));

        // 게시글 조회 후 id값 기준으로 댓글 조회
        response.setComment(commentService.getPostCommentByPostId(id));

        // 파일 첨부 조회
        response.setAttachment(attachmentService.getAllByTable(CommonCode.POST_TABLE, id));

        return response;
    }

    @Override
    public PostEntity get(int id) {
        return postRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("게시글을 찾을 수 없습니다."));
    }

    @Override
    public PostResponseDto getPostByIdAndPassword(PostRequestDto postDto) {
        return postRepository
                .findByIdAndPassword(postDto.getId(), postDto.getPassword())
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("비밀번호가 틀립니다. 다시 입력하세요."));
    }

    @Override
    public PostResponseDto create(PostRequestDto postDto) {
        PostEntity entity = this.toRequest(postDto, true);
        postRepository.save(entity);
        return new PostResponseDto(entity.getId(), entity.getBoardId());
    }

    @Override
    public PostResponseDto update(PostRequestDto postDto) {
        PostEntity entity = this.toRequest(postDto, false);
        postRepository.save(entity);
        return new PostResponseDto(entity.getId(), entity.getBoardId());
    }


    @Transactional
    @Override
    public boolean delete(PostRequestDto postDto) {
        this.getPostByIdAndPassword(postDto);
        commentService.deleteByPostId(postDto.getId());
        attachmentService.deleteByTable(CommonCode.POST_TABLE, postDto.getId());
        return postRepository.removeByIdAndPassword(postDto.getId(), postDto.getPassword()) > 0;
    }

    @Transactional
    @Override
    public boolean deleteByBoardId(int boardId) {
        List<PostEntity> list = postRepository.findAllByBoardId(boardId);
        if(!CollectionUtils.isEmpty(list)) {
            list.forEach(item -> {
                commentService.deleteByPostId(item.getId());
                attachmentService.deleteByTable(CommonCode.POST_TABLE, item.getId());
            });
        }
        return postRepository.removeAllByBoardId(boardId) > 0;
    }
    private PostEntity toRequest(PostRequestDto postDto, boolean isAdd) {
        PostEntity entity = new PostEntity();
        if(!isAdd) entity = this.get(postDto.getId());
        if(postDto.getBoardId() != null) entity.setBoardId(postDto.getBoardId());
        if(!StringUtils.isEmpty(postDto.getNickName())) entity.setNickName(postDto.getNickName());
        if(!StringUtils.isEmpty(postDto.getPassword())) entity.setPassword(postDto.getPassword());
        entity.setTitle(postDto.getTitle());
        entity.setContent(postDto.getContent());
        return entity;
    }

    // add view count
    private PostEntity toRequestByViewCount(PostResponseDto postDto) {
        PostEntity entity = new PostEntity();
        entity.setId(postDto.getId());
        entity.setBoardId(postDto.getBoardId());
        entity.setNickName(postDto.getNickName());
        entity.setPassword(postDto.getPassword());
        entity.setTitle(postDto.getTitle());
        entity.setContent(postDto.getContent());
        entity.setViewCount(postDto.getViewCount() + 1);
        entity.setCreatedDate(postDto.getCreatedDate());
        postDto.setViewCount(postDto.getViewCount() + 1);
        return entity;
    }

    private PostResponseDto toResponse (PostEntity entity) {
        PostResponseDto response = new PostResponseDto();
        response.setId(entity.getId());
        response.setBoardId(entity.getBoardId());
        response.setPassword(entity.getPassword());
        response.setNickName(entity.getNickName());
        response.setTitle(entity.getTitle());
        response.setContent(entity.getContent());
        response.setViewCount(entity.getViewCount());
        response.setCreatedDate(entity.getCreatedDate());
        response.setUpdatedDate(entity.getUpdatedDate());
        return response;
    }
}
