package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.ObjectMapper;
import modelsJson.AddRequestJSON;
import modelsJson.DeleteRequestJSON;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.*;
import service.JsonService;


@Validated
@RestController
@RequestMapping("/")
public class JSONController {
    /*
    замечаний нет
    */
    public static JsonService jsonService = new JsonService();
    public static ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/json")
    public Object add(@RequestParam(value = "action") String action,
                          @RequestBody String json) throws JsonProcessingException {

        if (action.equals("add")) {
            AddRequestJSON addRequestBody = objectMapper.readValue(json, AddRequestJSON.class);
            return jsonService.add(addRequestBody);}
        else if (action.equals("delete")) {
            DeleteRequestJSON deleteRequestBody = objectMapper.readValue(json, DeleteRequestJSON.class);
            return jsonService.delete(deleteRequestBody);}
        else {return ResponseEntity.badRequest()
                .header("content-type","application/json")
                .body("{\"Передан некорректный action\":\"" + action + "\"}");
        }
    }
}