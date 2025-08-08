package nifreebie.pastebin.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.GONE)
public class PasteIsExpiredException extends RuntimeException {
    public PasteIsExpiredException(String message) {
        super(message);
    }
}
