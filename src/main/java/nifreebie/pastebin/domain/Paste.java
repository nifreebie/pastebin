package nifreebie.pastebin.domain;

import jakarta.persistence.*;
import lombok.*;
import nifreebie.pastebin.model.dto.Language;
import org.springframework.data.annotation.CreatedDate;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pastes")
public class Paste {
    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String displayUrl;

    @Column(nullable = false)
    private String minioKey;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Language language;

    @Column(nullable = false)
    private Boolean isPrivate;

    private String password;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    private OffsetDateTime expiryDate;
}
