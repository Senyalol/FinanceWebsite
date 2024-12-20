package com.FinanceBack.FinanceBack.servises;

import com.FinanceBack.FinanceBack.DTO.UserDTO.CreateUserDTO;
import com.FinanceBack.FinanceBack.DTO.UserDTO.ShortUserInfoDTO;
import com.FinanceBack.FinanceBack.DTO.UserDTO.UpdateUserDTO;
import com.FinanceBack.FinanceBack.Enities.User;
import com.FinanceBack.FinanceBack.repositorises.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class UserServise { // Исправлено имя класса

    private final UserRepository userRepository;

    @Autowired
    public UserServise(UserRepository userRepository) { // Исправлено имя класса
        this.userRepository = userRepository;
    }

    public List<ShortUserInfoDTO> getUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(this::convertToShortUserInfoDTO)
                .toList();
    }

    public ShortUserInfoDTO getUserById(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found with ID " + id));
        return convertToShortUserInfoDTO(user);
    }

    public void createUser(CreateUserDTO createUserDTO) {
        User user = new User();
        user.setUsername(createUserDTO.getUsername());
        user.setEmail(createUserDTO.getEmail());
        user.setPassword(createUserDTO.getPassword());
        user.setCreatedAt(LocalDateTime.now().toInstant(java.time.ZoneOffset.UTC)); // Установка текущей даты
        user.setImg(createUserDTO.getImg());
        userRepository.save(user);
    }

    public void updateUser(int id, UpdateUserDTO updateUserDTO) {
        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User with ID " + id + " not found"));

        // Обновляем поля только при наличии новых значений в DTO
        if (updateUserDTO.getUsername() != null) {
            userToUpdate.setUsername(updateUserDTO.getUsername());
        }
        if (updateUserDTO.getEmail() != null) {
            userToUpdate.setEmail(updateUserDTO.getEmail());
        }
        if (updateUserDTO.getPassword() != null) {
            userToUpdate.setPassword(updateUserDTO.getPassword());
        }
        if (updateUserDTO.getImg() != null) {
            userToUpdate.setImg(updateUserDTO.getImg());
        }

        userRepository.save(userToUpdate);
    }

    public void deleteUser(int id) {
        User userToDelete = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User with ID " + id + " not found"));

        userRepository.delete(userToDelete);
    }

    private User convertDTOToUser(UpdateUserDTO updateUserDTO, User user) {
        if (updateUserDTO.getUsername() != null) {
            user.setUsername(updateUserDTO.getUsername());
        }
        if (updateUserDTO.getEmail() != null) {
            user.setEmail(updateUserDTO.getEmail());
        }
        if (updateUserDTO.getPassword() != null) {
            user.setPassword(updateUserDTO.getPassword());
        }
        if (updateUserDTO.getImg() != null) {
            user.setImg(updateUserDTO.getImg());
        }
        return user;
    }

    private ShortUserInfoDTO convertToShortUserInfoDTO(User user) {
        ShortUserInfoDTO userDTO = new ShortUserInfoDTO();
        userDTO.setUser_id(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setCreatedAt(convertToLocalDateTime(user.getCreatedAt())); // Преобразуйте в LocalDateTime
        userDTO.setImg(user.getImg());
        return userDTO;
    }

    private LocalDateTime convertToLocalDateTime(java.time.Instant instant) {
        return instant != null ? LocalDateTime.ofInstant(instant, java.time.ZoneId.systemDefault()) : null;
    }
}