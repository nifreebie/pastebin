package nifreebie.pastebin.model.dto;

import lombok.*;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PasteDTO implements Serializable {
    private String text;
    private Language language;
    private boolean isPrivate;
    private OffsetDateTime expireAt;
}
