package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import modelsJson.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;


@Validated
@RestController
@RequestMapping("/")
public class TickerController {
    /*
    замечаний нет
    */
    @PostMapping("/json")
    public Object add(@RequestParam(value = "action") String action, @RequestBody AddRequestJSON addRequestBody) throws JsonProcessingException {
        // объект с данными добавляемого алерта (имя тикера, параметры алерта)
        Add addJson = addRequestBody.getAdd();
        // получаем id пользователя со списком тикеров
        Info infoJson = addRequestBody.getInfo();
        // получаем список тикеров с алертами из запроса
        HashMap<String,TickersItem> tickersJson = infoJson.getTickers();

        // добавляемый алерт из запроса
        var newAlert = new AlertsItem(addJson.getTimeFrame(), addJson.getPercent());

        if (tickersJson.containsKey(addJson.getName())) {
            // если тикер найден
            TickersItem ticker = tickersJson.get(addJson.getName());
            List<AlertsItem> alertsList = ticker.getAlerts();
            if (alertsList.contains(newAlert)) {
                // если алерт со значением timeframe найден то он обновляется
                for (AlertsItem alert: alertsList) {
                    if (alert.getTimeFrame() == newAlert.getTimeFrame()) {
                        alert.setPercent(newAlert.getPercent());
                    }
                }
            } else if (!alertsList.contains(newAlert)) {
                // если алерт не найден -> добавляем его
                alertsList.add(newAlert);
            }
        } else {
            //если тикер не найден -> создание нового тикера с алертом
            tickersJson.put(
                addJson.getName(), new TickersItem(
                            addJson.getName(), Arrays.asList(newAlert)
                    )
            );
        }
        //добавляем обновленный список тикеров в ноду info, юзерид уже есть
        infoJson.setTickers(tickersJson);
        //создаем тело ответа
        AddResponseJSON responseBody = new AddResponseJSON();
        //добавляем в тело ответа ноду инфо, uuid из запроса
        responseBody.setInfo(infoJson);
        responseBody.setUuid(addRequestBody.getUuid());
        //устанавливаем текущую дату-время
        responseBody.setLastUpdate(
                LocalDateTime.now().
                        truncatedTo(ChronoUnit.SECONDS). //обрезаем миллисекунды
                        format(DateTimeFormatter.ISO_DATE_TIME)
        );

        //создаем экземпляр objectMapper"а, конвертируем POJO в json
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(responseBody);

        //HashMap<String,String> headers = new HashMap<>() {{ put("content-type", "application/json"); }};
        //добавляем хэдер, статус код и тело ответа
        return ResponseEntity.ok().header("content-type","application/json").body(json);
        //return new ResponseEntity<>(responseBody, headers,HttpStatus.OK);
    }
}