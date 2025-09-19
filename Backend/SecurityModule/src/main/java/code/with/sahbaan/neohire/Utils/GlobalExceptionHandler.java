package code.with.sahbaan.neohire.Utils;

import code.with.sahbaan.neohire.ResponseDTO.BaseResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public void handleResourceNotFound(HttpServletResponse response, Exception ex) throws IOException {
        writeErrorResponse(response, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    private void writeErrorResponse(HttpServletResponse response, String message, int status) throws IOException {
        response.setContentType("application/json");
        BaseResponse<?> baseResponse = new BaseResponse<>();
        baseResponse.setResponseMessage(message);
        response.setStatus(status);
        new ObjectMapper().writeValue(response.getOutputStream(), baseResponse);
    }

}

