package shop.boardpilot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shop.boardpilot.model.entity.PostEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
    Page<PostEntity> findAllByBoardIdOrderByCreatedDateDesc(int boardId, Pageable pageable);
    @Query("select p from post p where p.title like %:keyword% or p.nickName like %:keyword% or p.content like %:keyword% order by p.createdDate desc")
    Page<PostEntity> findAllByKeyword(String keyword, Pageable pageable);
    List<PostEntity> findAllByBoardId(int boardId);
    Optional<PostEntity> findById(int id);
    Optional<PostEntity> findByIdAndPassword(int id, String password);
    int removeByIdAndPassword(int id, String password);
    int removeAllByBoardId(int boardId);
}
