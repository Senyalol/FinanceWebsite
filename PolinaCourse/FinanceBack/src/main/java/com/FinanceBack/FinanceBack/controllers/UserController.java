package com.FinanceBack.FinanceBack.controllers;

import com.FinanceBack.FinanceBack.DTO.UserDTO.CreateUserDTO;
import com.FinanceBack.FinanceBack.DTO.UserDTO.ShortUserInfoDTO;
import com.FinanceBack.FinanceBack.DTO.UserDTO.UpdateUserDTO;
import com.FinanceBack.FinanceBack.servises.UserServise; // Исправлено имя класса
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity; // Импорт для ResponseEntity
import jakarta.validation.Valid; // Импорт для аннотации Valid
import org.springframework.http.HttpStatus; // Импорт для HttpStatus
import java.util.NoSuchElementException; // Импорт для NoSuchElementException
import java.util.List; // Импорт для List

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserServise userService; // Исправлено имя класса

    @Autowired
    public UserController(UserServise userService) { // Исправлено имя класса
        this.userService = userService;
    }

    @GetMapping
    public List<ShortUserInfoDTO> getAllUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public ShortUserInfoDTO getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody CreateUserDTO createUserDTO) {
        try {
            userService.createUser(createUserDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Пользователь успешно создан");
        } catch (Exception e) {
            e.printStackTrace(); // Логируем ошибку
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при создании пользователя");
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable("id") int id, @Valid @RequestBody UpdateUserDTO updateUserDTO) {
        try {
            userService.updateUser(id, updateUserDTO);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            // Логируйте ошибку
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") int id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            // Логируйте ошибку
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}