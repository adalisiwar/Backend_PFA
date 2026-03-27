package com.fooddelivery.admin_service.client;

import com.fooddelivery.admin_service.dto.UserDTO;
import com.fooddelivery.admin_service.dto.RatingDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "user-service", url = "http://localhost:8081")
public interface UserClient {

    @GetMapping("/api/users/id/{id}")
    UserDTO getUserById(@PathVariable("id") Long id,
                        @RequestHeader("Authorization") String token);

    @GetMapping("/api/users/email/{email}")
    UserDTO getUserByEmail(@PathVariable("email") String email,
                           @RequestHeader("Authorization") String token);

@DeleteMapping(
            value = "/api/users/{id}"
    )
    void deleteUser(@PathVariable("id") Long id,
                    @RequestHeader("Authorization") String token);

    @GetMapping("/api/users/{userId}/ratings")
    List<RatingDTO> getRatingsByUser(@PathVariable("userId") Long userId,
                                     @RequestHeader("Authorization") String token);

    @GetMapping("/api/users")
    List<UserDTO> getAllUsers(@RequestHeader("Authorization") String token);

    @PostMapping("/api/users")
    UserDTO createUser(@RequestBody UserDTO userDTO, @RequestHeader("Authorization") String token);

    @PutMapping("/api/users/{id}")
    UserDTO updateUser(@PathVariable("id") Long id, @RequestBody UserDTO userDTO, @RequestHeader("Authorization") String token);

}