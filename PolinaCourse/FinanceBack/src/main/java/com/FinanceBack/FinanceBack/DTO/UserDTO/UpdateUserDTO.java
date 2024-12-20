package com.FinanceBack.FinanceBack.DTO.UserDTO;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UpdateUserDTO {

    private Integer user_id;
    private String username;
    private String email;
    private String password;
    private LocalDateTime createdAt;
    private String img;

}
