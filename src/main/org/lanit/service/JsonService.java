package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class JsonService {

    private static JsonService jsonService;

    public static JsonService getUserService() {
        if (jsonService == null) {
            JsonService jsonService = new JsonService();
        }
        return jsonService;
    }

    public Object add(AddRequestJSON addRequestBody) throws JsonProcessingException {

        // объект с данными добавляемого алерта (имя тикера, параметры алерта)
        Add addJson = addRequestBody.getAdd();
        // получаем id пользователя со списком тикеров
        Info infoJson = addRequestBody.getInfo();
        // получаем список тикеров с алертами из запроса
        HashMap<String, TickersItem> tickersJson = infoJson.getTickers();

        // добавляемый алерт из запроса
        var newAlert = new AlertsItem(addJson.getTimeFrame(), addJson.getPercent());

        if (tickersJson.containsKey(addJson.getName())) {
            // если тикер найден
            TickersItem ticker = tickersJson.get(addJson.getName());
            List<AlertsItem> alertsList = ticker.getAlerts();
            if (alertsList.contains(newAlert)) {
                // если алерт со значением timeframe найден то он обновляется
                for (AlertsItem alert : alertsList) {
                    if (alert.getTimeFrame() == newAlert.getTimeFrame()) {
                        alert.setPercent(newAlert.getPercent());
                    }
                }
            } else if (!alertsList.contains(newAlert)) {
                // если алерт не найден -> добавляем его
                alertsList.add(newAlert);
            }
            // обновляем список алертов в тикере
            ticker.setAlerts(alertsList);
            // обновляем тикер в списоке тикерове
            tickersJson.replace(addJson.getName(), ticker);
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
        String jsonResponse = objectMapper.writeValueAsString(responseBody);

        //добавляем хэдер, статус код и тело ответа
        return ResponseEntity.ok()
                .header("content-type", "application/json")
                .body(jsonResponse);

    }

    public Object delete(DeleteRequestJSON deleteRequestBody) throws JsonProcessingException {

        // объект с данными удаляемого алерта (имя тикера, номер алерта)
        Delete deleteJson = deleteRequestBody.getDelete();
        // получаем id пользователя со списком тикеров
        Info infoJson = deleteRequestBody.getInfo();
        // получаем список тикеров с алертами из запроса
        HashMap<String, TickersItem> tickersJson = infoJson.getTickers();

        // добавляемый алерт из запроса// var newAlert = new AlertsItem(addJson.getTimeFrame(), addJson.getPercent());
        if (tickersJson.containsKey(deleteJson.getTickerName())) {
            // если тикер найден
            TickersItem ticker = tickersJson.get(deleteJson.getTickerName());
            List<AlertsItem> alertsList = ticker.getAlerts();
            // если размер списка алертов больше или равен индексы из запроса на удаление то он удалится
            if ((alertsList.size() - 1) >= deleteJson.getAlertIndex()) {
                // удаляем алерт по индексу из запроса
                alertsList.remove(deleteJson.getAlertIndex());
                // обновляем список алертов у тикера
                ticker.setAlerts(alertsList);
                // обновляем список тикеров
                tickersJson.replace(deleteJson.getTickerName(), ticker);
            } else if ((alertsList.size() - 1) < deleteJson.getAlertIndex()) {
                // если размер списка алертов меньше индекса из запроса то возвращаем сообщение об ошибке
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .header("content-type", "application/json")
                        .body(new ErrorResponseJSON("error", "Передан некорректный индекс"));

            }
            // если тикер с именем из запроса не найден то отправляем сообщение об ошибке
        } else if (!tickersJson.containsKey(deleteJson.getTickerName())) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .header("content-type", "application/json")
                    .body(new ErrorResponseJSON("error", "Передан некорректный тикер"));
        }

        //добавляем обновленный список тикеров в ноду info, юзерид уже есть
        infoJson.setTickers(tickersJson);
        //создаем тело ответа
        DeleteResponseJSON responseBody = new DeleteResponseJSON();
        //добавляем в тело ответа ноду инфо, uuid из запроса
        responseBody.setInfo(infoJson);
        responseBody.setUuid(deleteRequestBody.getUuid());
        //устанавливаем текущую дату-время
        responseBody.setLastUpdate(
                LocalDateTime.now().
                        truncatedTo(ChronoUnit.SECONDS). //обрезаем миллисекунды
                        format(DateTimeFormatter.ISO_DATE_TIME)
        );

        //создаем экземпляр objectMapper"а, конвертируем POJO в json
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(responseBody);

        //добавляем хэдер, статус код и тело ответа
        return ResponseEntity.ok()
                .header("content-type", "application/json")
                .body(jsonResponse);

    }
}