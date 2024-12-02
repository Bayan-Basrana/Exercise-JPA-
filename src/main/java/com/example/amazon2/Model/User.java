package com.example.amazon2.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class User {
@Id
@GeneratedValue
    private Integer userId ;
    @NotEmpty(message = "name cannot be empty")
    @Size(min = 5, message = "username must be more than 5")
    @Column(columnDefinition = "varchar(20) not null")
    private String username ;
    @NotEmpty(message = "password cannot be empty")
    @Size(min = 7, message = "password must be more than 6")
    @Pattern(regexp = "^[A-Za-z0-9]+$",message = " password must have characters and digits only")
    @Column(columnDefinition = "varchar(20) not null")
    private String password;
    @Email(message = "enter a valid email")
    @NotEmpty(message = "email cannot be empty")
    @Column(columnDefinition = "varchar(50) not null")
    private String email;
    @NotEmpty(message = "role cannot be empty")
    @Pattern(regexp = "Admin|Customer")
    @Column(columnDefinition = "varchar(10) not null")
    private String role;
    @NotNull(message = "balance cannot be empty")
    @Positive(message = "balance must be Positive")
    @Column(columnDefinition = "int not null")
    private Integer balance;
    @PositiveOrZero(message = "rewardPoints must be Positive Or Zero ")
    @Column(columnDefinition = "int")
    private Integer rewardPoints=0;
    @Column(columnDefinition = "varchar(5)")
    private String uniqueCode;
}
