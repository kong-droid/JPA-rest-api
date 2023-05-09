package shop.boardpilot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shop.boardpilot.model.entity.AttachmentEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttachmentRepository extends JpaRepository<AttachmentEntity, Long> {
    @Query("select a from attachment as a where a.tableName=:tableName and a.tableId=:tableId order by a.createdDate desc")
    List<AttachmentEntity> findAllByTable(String tableName, int tableId);
    Optional<AttachmentEntity> findById(int id);
    int removeById(int id);
    int removeByTableNameAndTableId(String tableName, int tableId);
}
