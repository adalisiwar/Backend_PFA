package com.example.api_gateway.controller;

import com.example.api_gateway.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/gateway")
public class GatewayController {

    @Value("${app.name}")
    private String appName;

    @Value("${app.version}")
    private String appVersion;

    @Value("${app.description}")
    private String appDescription;

    @GetMapping("/health")
    public ResponseEntity<ApiResponse<Map<String, String>>> health() {
        log.info("Health check request received");
        
        Map<String, String> healthInfo = new HashMap<>();
        healthInfo.put("status", "UP");
        healthInfo.put("service", appName);
        healthInfo.put("version", appVersion);
        healthInfo.put("timestamp", System.currentTimeMillis() + "");

        return ResponseEntity.ok(
                ApiResponse.success(healthInfo, "Gateway is healthy")
        );
    }


    @GetMapping("/info")
    public ResponseEntity<ApiResponse<Map<String, String>>> info() {
        log.info("Gateway info request received");
        
        Map<String, String> info = new HashMap<>();
        info.put("name", appName);
        info.put("version", appVersion);
        info.put("description", appDescription);
        info.put("environment", "production");

        return ResponseEntity.ok(
                ApiResponse.success(info, "Gateway information")
        );
    }

    @GetMapping("/ready")
    public ResponseEntity<ApiResponse<Map<String, Boolean>>> ready() {
        log.info("Ready check request received");
        
        Map<String, Boolean> readyStatus = new HashMap<>();
        readyStatus.put("ready", true);
        readyStatus.put("acceptingConnections", true);

        return ResponseEntity.ok(
                ApiResponse.success(readyStatus, "Gateway is ready")
        );
    }
}
