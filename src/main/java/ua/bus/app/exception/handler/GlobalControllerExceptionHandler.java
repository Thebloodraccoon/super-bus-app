package ua.bus.app.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.bus.app.model.dto.ErrorDTO;


@ControllerAdvice(annotations = {Controller.class})
public class GlobalControllerExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleUnHandled(Exception e){
        String message = e.getMessage();
        ErrorDTO error = new ErrorDTO();
        error.setMessage(message);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
