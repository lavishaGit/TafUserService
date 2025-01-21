package com.tfa.UsersMS.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTOResponse {

private Long id;
    private String username;
    private String email;
    private String phone;
    private LocalDateTime createdAt;

 private LocalDateTime updatedAt;
}
