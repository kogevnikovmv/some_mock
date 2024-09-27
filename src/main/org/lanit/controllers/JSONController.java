package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.AddRequestJSON;
import models.DeleteRequestJSON;
import models.ErrorResponseJSON;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.*;
import service.JsonService;

import java.io.IOException;


@RestController
@RequestMapping("/")
public class JSONController {


    public static JsonService jsonService = new JsonService();
    public static ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/json")
    public Object add(@RequestParam(value = "action") String action,
                          @RequestBody String json) throws IOException {

        // если параметр пути = add
        try {
            if (action.equals("add")) {
                // json -> object
                AddRequestJSON addRequestBody = objectMapper.readValue(json, AddRequestJSON.class);
                // возвращаем результат операции
                return jsonService.add(addRequestBody);
            }
            // если параметр пути = delete
            else if (action.equals("delete")) {
                // json -> object
                DeleteRequestJSON deleteRequestBody = objectMapper.readValue(json, DeleteRequestJSON.class);
                // возвращаем результат операции
                return jsonService.delete(deleteRequestBody);
            }
            // если параметр пути равен чему-либо, кроме add\delete, возвращаем сообщение об ошибке
            else {
                return ResponseEntity
                        .badRequest()
                        .header("content-type", "application/json")
                        .body(new ErrorResponseJSON("error", "Передан некорректный action - " + action));
            }
        } catch (IOException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("content-type", "application/json")
                    .body(new ErrorResponseJSON("error", "При обработке Вашего запроса произошла неизвестная ошибка"));
        }
    }
}