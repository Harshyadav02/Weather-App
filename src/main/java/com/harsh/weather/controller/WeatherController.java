package com.harsh.weather.controller;

import java.util.Map;

// @RestController
// public class WeatherController {
    
// }

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.harsh.weather.service.WeatherService;

@Controller
public class WeatherController {

    @Autowired
    private WeatherService weatherService;
    
    @GetMapping("")
    public String retriveCityInfo(){
        return "weather";
    }
    
    @PostMapping("")
    public String CityInfo(@RequestParam("cityInfo")String cityInfo,Model model){
        Map<String,Object> response  = weatherService.getWeatherData(cityInfo);
        model.addAttribute("response", response);
        model.addAttribute("cityInfo", cityInfo.toUpperCase());
        return "weather";
    }
}
