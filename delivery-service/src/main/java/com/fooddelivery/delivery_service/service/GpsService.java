package com.fooddelivery.delivery_service.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GpsService {

    private static final Logger logger = LoggerFactory.getLogger(GpsService.class);

    public Map<String, String> getRoute(String from, String to) {
        logger.warn("Using hardcoded GPS route data. Replace with actual GPS API for production. From: {} To: {}", from, to);

        Map<String, String> route = new HashMap<>();
        route.put("distance", "4.2 km");
        route.put("duration", "12 min");
        route.put("path", "mock-polyline");
        return route;
    }
}