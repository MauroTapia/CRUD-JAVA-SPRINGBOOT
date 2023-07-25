package ProyectoFinal.ClinicaOdontologica.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<ErrorDetails> tratamientoResourceNotFoundException(){
        return ResponseEntity.notFound().build(); //no existe paciente o odontologo con ese id:
    }
    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<ErrorDetails> tratamientoBadRequestException(BadRequestException bre, HttpServletRequest request){
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),bre.getMessage(),request.getRequestURI(),HttpStatus.BAD_REQUEST);
        return ResponseEntity.badRequest().body(errorDetails);

    }
}
