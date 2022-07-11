package by.burov.event.controller.advices;

import by.burov.event.core.dto.FieldValidationErrorDto;
import by.burov.event.core.dto.FieldValidationResultDto;
import by.burov.event.core.exception.FieldValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FieldValidationResultDto handle(FieldValidationException e){
        List<FieldValidationErrorDto> errors = e.getErrors().stream().map(error -> new FieldValidationErrorDto(error.getField(), error.getMessage()))
                .collect(Collectors.toList());
        FieldValidationResultDto data = new FieldValidationResultDto(e.getLogref(),errors);
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
    public List<Map<String, Object>> handle(IllegalStateException e){
        List<Map<String, Object>> data = new ArrayList<>();

        Map<String, Object> map = new HashMap<>();
        map.put("logref", "500");
        map.put("message", e.getMessage());

        data.add(map);

        return data;
    }


}

