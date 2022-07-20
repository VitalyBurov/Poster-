package by.burov.user.controller.advices;

import by.burov.user.core.api.FieldValidationError;
import by.burov.user.core.errors.FieldValidationErrorDto;
import by.burov.user.core.errors.FieldValidationResultDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FieldValidationResultDto handle(ConstraintViolationException e) {
        List<FieldValidationError> errors = new ArrayList<>();
        String field = null;
        for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations()) {
            for (Path.Node node : constraintViolation.getPropertyPath()) {
                field = node.getName();
            }
            errors.add(new FieldValidationError(constraintViolation.getMessage(), field));
        }
        FieldValidationResultDto data = new FieldValidationResultDto("structured_errors",
                errors.stream()
                        .map(error -> new FieldValidationErrorDto(error.getField(), error.getMessage()))
                        .collect(Collectors.toList()));
        return data;
    }

//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    public List<Map<String, Object>> handle(IOException e){
//        List<Map<String, Object>> data = new ArrayList<>();
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("logref", "401");
//        map.put("message",e.getMessage());
//
//        data.add(map);
//
//        return data;
//    }

  /*  @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public List<Map<String, Object>> handle(HttpClientErrorException.Forbidden e){
        List<Map<String, Object>> data = new ArrayList<>();

        Map<String, Object> map = new HashMap<>();
        map.put("logref", "403");
        map.put("message",e.getMessage());

        data.add(map);

        return data;
    }*/
  @ExceptionHandler
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public List<Map<String, Object>> handle(RuntimeException e) {
      List<FieldValidationError> errors = new ArrayList<>();
      List<Map<String, Object>> data = new ArrayList<>();
      Map<String, Object> map = new HashMap<>();
      map.put("logref", "error");
      map.put("message", e.getMessage());
      data.add(map);
      return data;
  }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public List<Map<String, Object>> handle(IllegalStateException e) {
        List<FieldValidationError> errors = new ArrayList<>();
        List<Map<String, Object>> data = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("logref", "error");
        map.put("message", e.getMessage());
        data.add(map);
        return data;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public List<Map<String, Object>> handle(NullPointerException e) {
        List<FieldValidationError> errors = new ArrayList<>();
        List<Map<String, Object>> data = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("logref", "error");
        map.put("message", e.getMessage());
        data.add(map);
        return data;
    }


}

