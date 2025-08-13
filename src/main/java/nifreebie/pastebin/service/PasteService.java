package nifreebie.pastebin.service;

import nifreebie.pastebin.model.dto.PasteDTO;
import nifreebie.pastebin.model.request.CreatePasteRequest;

import java.util.List;

public interface PasteService {
    String createPaste(CreatePasteRequest request);

    PasteDTO getPasteByDisplayUrl(String displayUrl, String password);

    List<PasteDTO> getMyPastes();
}
