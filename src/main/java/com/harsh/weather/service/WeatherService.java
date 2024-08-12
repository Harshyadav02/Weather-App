package com.harsh.weather.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    @Value("${weather.api.uri}")
    private String apiUrl;

    @Value("${weather.api.key}")
    private String apiKey;

    @Autowired
    RestTemplate restTemplate;

    public Map<String, Object> getWeatherData(String city) {
        // map too store final data
        Map<String, Object> weatherData = new HashMap<>();
        try {

            // seting up url of weather data
            String url = String.format("%s?q=%s&appid=%s", apiUrl, city, apiKey);

            // retirving data from api
            
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            
            // creating map from the retrived data
            if (response != null) {
                
                Map<String, Object> main = (Map<String, Object>) response.get("main");
                
                Map<String, Object> wind = (Map<String, Object>) response.get("wind");

                // adding the data to final map
                weatherData.put("temp_max", main.get("temp_max"));
                weatherData.put("temp_min", main.get("temp_min"));
                weatherData.put("humidity", main.get("humidity"));
                weatherData.put("pressure", main.get("pressure"));
                weatherData.put("tempCel", main.get("temp"));
                weatherData.put("tempFah", (Double) main.get("temp") * 9 / 5 + 32);

                weatherData.put("wind", wind.get("deg"));
                weatherData.put("wind_speed",wind.get("speed"));
                weatherData.put("location", response.get("name"));

                
                List<Map<String,Object>> weatherList = (List<Map<String,Object>>)(response.get("weather"));
                weatherData.put("Desc",weatherList.get(0).get("description"));
                weatherData.put("icon",weatherList.get(0).get("icon"));
                return weatherData;
            }
            else{
                weatherData.put("error","No data found for city\t"+city);
                return weatherData;
            }

        } catch (Exception e) {

            weatherData.put("error","somthing went wrong");
            return weatherData;
        }
    }
}
