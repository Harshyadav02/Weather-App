package com.harsh.weather.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    private static final long CACHE_TTL = 5; // expire time of data from cache in min

    @Value("${weather.api.uri}")
    private String apiUrl;

    @Value("${weather.api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    public Map<String, Object> getWeatherData(String city) {
        Map<String, Object> weatherData = new HashMap<>();
        try {
            String cacheKey = "weather:" + city;

            // Check if data is already in cache
            Map<String, Object> cacheData = (Map<String, Object>) redisTemplate.opsForValue().get(cacheKey);

            if (cacheData != null) {
                return cacheData;
            }

            // Retrieve data from API
            String url = String.format("%s?q=%s&appid=%s", apiUrl, city, apiKey);
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response != null) {
                Map<String, Object> main = (Map<String, Object>) response.get("main");
                Map<String, Object> wind = (Map<String, Object>) response.get("wind");

                weatherData.put("temp_max", main.get("temp_max"));
                weatherData.put("temp_min", main.get("temp_min"));
                weatherData.put("humidity", main.get("humidity"));
                weatherData.put("pressure", main.get("pressure"));
                weatherData.put("tempCel", main.get("temp"));
                weatherData.put("tempFah", (Double) main.get("temp") * 9 / 5 + 32);
                weatherData.put("wind", wind.get("deg"));
                weatherData.put("wind_speed", wind.get("speed"));
                weatherData.put("location", response.get("name"));

                List<Map<String, Object>> weatherList = (List<Map<String, Object>>) (response.get("weather"));
                weatherData.put("Desc", weatherList.get(0).get("description"));
                weatherData.put("icon", weatherList.get(0).get("icon"));

                // Store the retrieved data in Redis
                redisTemplate.opsForValue().set(cacheKey, weatherData, CACHE_TTL, TimeUnit.MINUTES);
                return weatherData;
            } else {
                weatherData.put("error", "No data found for city\t" + city);
                return weatherData;
            }

        } catch (Exception e) {
            weatherData.put("error", "Something went wrong");
            return weatherData;
        }
    }
}
