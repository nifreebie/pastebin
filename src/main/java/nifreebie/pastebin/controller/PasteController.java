package nifreebie.pastebin.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nifreebie.pastebin.model.dto.PasteDTO;
import nifreebie.pastebin.model.request.CreatePasteRequest;
import nifreebie.pastebin.service.PasteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pastes")
@RequiredArgsConstructor
public class PasteController {
    private final PasteService pasteService;

    @PostMapping
    public ResponseEntity<String> createPaste(@RequestBody @Valid CreatePasteRequest request) {
        String displayUrl = pasteService.createPaste(request);
        return new ResponseEntity<>(displayUrl, HttpStatus.CREATED);
    }

    @GetMapping("/{displayUrl}")
    public ResponseEntity<PasteDTO> getPaste(
            @PathVariable String displayUrl,
            @RequestHeader(value = "X-Paste-Password", required = false) String password) {
        PasteDTO response = pasteService.getPasteByDisplayUrl(displayUrl, password);
        return ResponseEntity.ok(response);
    }
}
