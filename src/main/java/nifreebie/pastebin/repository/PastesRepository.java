package nifreebie.pastebin.repository;

import nifreebie.pastebin.domain.Paste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PastesRepository extends JpaRepository<Paste, UUID> {
    Optional<Paste> findByDisplayUrl(String displayUrl);

    boolean existsByDisplayUrl(String displayUrl);

    @Query(value = "SELECT minio_key FROM pastes WHERE expiry_date < NOW()", nativeQuery = true)
    List<String> findMinioKeysOfExpiredPastes();

    @Modifying
    @Query(value = "DELETE FROM pastes WHERE expiry_date < NOW()", nativeQuery = true)
    void deleteExpiredPastes();
}
