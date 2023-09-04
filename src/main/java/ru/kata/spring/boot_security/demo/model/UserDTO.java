package ru.kata.spring.boot_security.demo.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Slf4j
@Builder
@AllArgsConstructor
public class UserDTO {
    private Long id;
    @NotNull
    @Size(min = 3)
    private String username;
    private String lastName;
    @Min(0)
    private Integer age;
    @NotNull
    private String email;
    @NotNull
    @Min(8)
    private String password;
    @NotNull
    private String[] roles;
}
