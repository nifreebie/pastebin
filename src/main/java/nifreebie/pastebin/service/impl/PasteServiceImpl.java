package nifreebie.pastebin.service.impl;

import lombok.RequiredArgsConstructor;
import nifreebie.pastebin.domain.Paste;
import nifreebie.pastebin.domain.User;
import nifreebie.pastebin.model.dto.PasteDTO;
import nifreebie.pastebin.model.request.CreatePasteRequest;
import nifreebie.pastebin.repository.PastesRepository;
import nifreebie.pastebin.repository.UserRepository;
import nifreebie.pastebin.service.HashService;
import nifreebie.pastebin.service.MinioService;
import nifreebie.pastebin.service.PasteService;
import nifreebie.pastebin.util.AccessDeniedException;
import nifreebie.pastebin.util.NotFoundException;
import nifreebie.pastebin.util.PasswordUtil;
import nifreebie.pastebin.util.PasteIsExpiredException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasteServiceImpl implements PasteService {
    private final PastesRepository pastesRepository;
    private final MinioService minioService;
    private final HashService hashService;
    private final UserRepository userRepository;

    @Override
    public String createPaste(CreatePasteRequest request) {
        Paste paste = new Paste();

        paste.setId(UUID.randomUUID());
        paste.setDisplayUrl(hashService.getUniqueHash());
        paste.setLanguage(request.getLanguage());
        paste.setIsPrivate(request.isPrivate());
        paste.setPassword(request.getPassword() != null ? PasswordUtil.hashPassword(request.getPassword()) : null);
        paste.setDateCreated(OffsetDateTime.now());
        paste.setExpiryDate(request.getExpireIntervalSeconds() != null && request.getExpireIntervalSeconds() > 0 ?
                OffsetDateTime.now().plusSeconds(request.getExpireIntervalSeconds()) : null);
        String minioKey = "pastes/" + paste.getDisplayUrl() + ".txt";
        minioService.uploadFile(minioKey, request.getText());
        paste.setMinioKey(minioKey);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName()).orElseThrow(NotFoundException::new);
        paste.setOwner(user);

        return pastesRepository.save(paste).getDisplayUrl();
    }

    @Cacheable(value = "pastes", key = "#displayUrl")
    @Override
    public PasteDTO getPasteByDisplayUrl(String displayUrl, String password) {
        Paste paste = pastesRepository.findByDisplayUrl(displayUrl).orElseThrow(
                () -> new NotFoundException("Не существует пасты с урлом: " + displayUrl));
        if (paste.getExpiryDate() != null && paste.getExpiryDate().isBefore(OffsetDateTime.now())) {
            throw new PasteIsExpiredException("Паста уже не доступна");
        }

        if(paste.getPassword() != null) {
            if(password == null) {
                throw new AccessDeniedException();
            } else if (!PasswordUtil.verifyPassword(password, paste.getPassword())) {
                throw new AccessDeniedException("Неверный пароль");
            }
        }

        String text = minioService.downloadFile(paste.getMinioKey());

        return PasteDTO.builder()
                .text(text)
                .language(paste.getLanguage())
                .isPrivate(paste.getIsPrivate())
                .expireAt(paste.getExpiryDate())
                .displayUrl(paste.getDisplayUrl())
                .build();
    }

    @Override
    public List<PasteDTO> getMyPastes() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return pastesRepository.findByOwnerUsername(username).stream()
                .map(paste -> PasteDTO.builder()
                        .text(null)
                        .language(paste.getLanguage())
                        .isPrivate(paste.getIsPrivate())
                        .expireAt(paste.getExpiryDate())
                        .displayUrl(paste.getDisplayUrl())
                        .build())
                .toList();
    }
}
