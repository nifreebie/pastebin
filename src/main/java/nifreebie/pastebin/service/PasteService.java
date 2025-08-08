package nifreebie.pastebin.service;

import nifreebie.pastebin.model.dto.PasteDTO;
import nifreebie.pastebin.model.request.CreatePasteRequest;

public interface PasteService {
    String createPaste(CreatePasteRequest request);

    PasteDTO getPasteByDisplayUrl(String displayUrl, String password);
}
