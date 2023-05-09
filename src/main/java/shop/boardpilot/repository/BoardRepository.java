package shop.boardpilot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import shop.boardpilot.model.entity.BoardEntity;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    Page<BoardEntity> findAllByOrderByCreatedDateDesc(Pageable pageable);
    @Query("select b from board b where b.title like %:keyword%  or b.nickName like %:keyword% order by b.createdDate desc")
    Page<BoardEntity> findAllByKeyword(String keyword, Pageable pageable);
    Optional<BoardEntity> findById(int id);
    Optional<BoardEntity> findByIdAndPassword(int id, String password);
    int removeByIdAndPassword(int id, String password);
}
