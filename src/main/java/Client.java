/*import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class Client {
    public static void main(String[] args) {
        final String sensorName = "Sensor123";

        registerSensor(sensorName);

        Random random = new Random();

        double minTemperature = 0.0;
        double maxTemperature = 45.0;
        for (int i = 0; i < 500; i++) {
            System.out.println(i);
            sendMeasurement(random.nextDouble() * maxTemperature,
                    random.nextBoolean(), sensorName);
        }
    }

    private static void registerSensor(String sensorName) {
        final String url = "http://localhost:8080/sensors/registration";

        Map<String, Object> jsonData = new HashMap<>();
        jsonData.put("name", sensorName);

        makePostRequestWithJSONData(url, jsonData);
    }

    private static void sendMeasurement(double value, boolean raining, String sensorName) {
        final String url = "http://localhost:8080/measurements/add";

        Map<String, Object> jsonData = new HashMap<>();
        jsonData.put("value", value);
        jsonData.put("raining", raining);
        jsonData.put("sensor", Map.of("name", sensorName));
        Map<String, String> tmpMap = new HashMap<>();
        tmpMap.put("name", sensorName);
        jsonData.put("sensor", tmpMap);

        makePostRequestWithJSONData(url, jsonData);
    }

    private static void makePostRequestWithJSONData(String url, Map<String, Object> jsonData) {
        final RestTemplate restTemplate = new RestTemplate();

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> request = new HttpEntity<>(jsonData, headers);

        try {
            restTemplate.postForObject(url, request, String.class);

            System.out.println("Измерение успешно отправлено на сервер!");
        } catch (HttpClientErrorException e) {
            System.out.println("ОШИБКА!");
            System.out.println(e.getMessage());
        }
    }
}*/


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;


public class Client {
    public static void main(String[] args) {
        final String sensorName = "MySensor2";

       // sensorRegistration(sensorName);
       // add1000Measurements(sensorName);
        getMeasurements();
    }

    private static void sensorRegistration(String sensorName) {
        final String url = "http://localhost:8080/sensors/registration";

        Map<String, Object> jsonToSend = new HashMap<>();

        jsonToSend.put("name", sensorName);

        String response = makePostRequest(jsonToSend, url);
        System.out.println(response);
    }

    private static void add1000Measurements(String sensorName) {
        final String url = "http://localhost:8080/measurements/add";

        Map<String, Object> jsonToSend;
        Random random = new Random();
        double min = -20;
        double max = 45;
        double nextDouble;
        double temperature;
        boolean raining;


        for (int i = 0; i < 10; i++) {
            jsonToSend = new HashMap<>();

            nextDouble = random.nextDouble();
            temperature = min + (nextDouble * (max - min));
            BigDecimal bd = new BigDecimal(Double.toString(temperature));
            bd = bd.setScale(1, RoundingMode.DOWN);

            raining = random.nextBoolean();

            jsonToSend.put("value", bd);
            jsonToSend.put("raining", raining);
            jsonToSend.put("sensor", Map.of("name", sensorName));

            String response = makePostRequest(jsonToSend, url);
            System.out.println(response);
        }

    }

    private static String makePostRequest(Map<String, Object> jsonToSend, String url) {
        RestTemplate restTemplate = new RestTemplate();

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> request = new HttpEntity<>(jsonToSend, headers);

        return restTemplate.postForObject(url, request, String.class);
    }
    private static String getMeasurements() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/measurements";

        return restTemplate.getForObject(url, String.class);
    }

}
