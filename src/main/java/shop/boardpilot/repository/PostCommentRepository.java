package shop.boardpilot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.boardpilot.model.entity.PostCommentEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostCommentRepository extends JpaRepository<PostCommentEntity, Long> {
    Page<PostCommentEntity> findAllByPostIdOrderByCreatedDateDesc(int postId, Pageable pageable);
    List<PostCommentEntity> findAllByPostIdOrderByCreatedDateDesc(int postId);
    Optional<PostCommentEntity> findByIdAndPassword(int id, String password);
    Optional<PostCommentEntity> findById(int id);
    int removeById(int id);
    int removeAllByPostId(int postId);
}
