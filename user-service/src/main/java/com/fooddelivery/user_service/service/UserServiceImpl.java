package com.fooddelivery.user_service.service;

import com.fooddelivery.user_service.dto.AdminCreateUserDTO;
import com.fooddelivery.user_service.dto.UserDTO;
import com.fooddelivery.user_service.dto.UserRegisterDTO;
import com.fooddelivery.user_service.exception.UserNotFoundException;
import com.fooddelivery.user_service.model.User;
import com.fooddelivery.user_service.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // ─── Mapper ───────────────────────────────────────────────────────────────

    private UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        return dto;
    }

    // ─── Public self-registration ─────────────────────────────────────────────

    @Override
    public UserDTO registerUser(UserRegisterDTO registerDTO) {
        if (userRepository.findByEmail(registerDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use: " + registerDTO.getEmail());
        }

        User user = new User();
        user.setFullName(registerDTO.getFullName());
        user.setEmail(registerDTO.getEmail());
        user.setUsername(registerDTO.getEmail()); // username = email — satisfies NOT NULL
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setRole("ROLE_USER"); // self-registration cannot escalate role

        return toDTO(userRepository.save(user));
    }

    // ─── Admin-initiated creation ─────────────────────────────────────────────

    @Override
    public UserDTO createUser(AdminCreateUserDTO dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use: " + dto.getEmail());
        }

        String rawPassword = (dto.getPassword() != null && !dto.getPassword().isBlank())
                ? dto.getPassword()
                : UUID.randomUUID().toString();

        User user = new User();
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getEmail()); // username = email — satisfies NOT NULL
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole(dto.getRole());

        return toDTO(userRepository.save(user));
    }

    // ─── Read ─────────────────────────────────────────────────────────────────

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
        return toDTO(user);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
        return toDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ─── Update (never touches password or username) ──────────────────────────

    @Override
    public UserDTO updateUser(Long id, UserDTO updatedDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));

        if (updatedDTO.getFullName() != null) user.setFullName(updatedDTO.getFullName());
        if (updatedDTO.getRole()     != null) user.setRole(updatedDTO.getRole());

        if (updatedDTO.getEmail() != null && !updatedDTO.getEmail().equals(user.getEmail())) {
            if (userRepository.findByEmail(updatedDTO.getEmail()).isPresent()) {
                throw new IllegalArgumentException("Email already in use: " + updatedDTO.getEmail());
            }
            user.setEmail(updatedDTO.getEmail());
            user.setUsername(updatedDTO.getEmail()); // keep username in sync with email
        }

        return toDTO(userRepository.save(user));
    }

    // ─── Delete ───────────────────────────────────────────────────────────────

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }
}