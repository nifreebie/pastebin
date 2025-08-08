package nifreebie.pastebin.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nifreebie.pastebin.model.dto.Language;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePasteRequest {
    @NotBlank
    private String text;
    @NotNull
    private Language language;
    @NotNull
    private boolean isPrivate;
    private Long expireIntervalSeconds;
    private String password;
}
