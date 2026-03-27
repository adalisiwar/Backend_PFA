package com.fooddelivery.user_service.service;

import com.fooddelivery.user_service.dto.AdminCreateUserDTO;
import com.fooddelivery.user_service.dto.UserDTO;
import com.fooddelivery.user_service.dto.UserRegisterDTO;

import java.util.List;

public interface UserService {

    UserDTO registerUser(UserRegisterDTO registerDTO);       // public self-registration

    UserDTO createUser(AdminCreateUserDTO dto);              // admin-initiated creation

    UserDTO getUserById(Long id);

    UserDTO getUserByEmail(String email);

    List<UserDTO> getAllUsers();

    UserDTO updateUser(Long id, UserDTO updatedDTO);

    void deleteUser(Long id);
}