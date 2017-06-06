package fr.ebiz.computerDatabase.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by ebiz on 06/06/17.
 */

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    /**
     * @param message
     *          to print
     */
    public NotFoundException(String message) {
        super(message);
    }
}
