package com.example.amazon2.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    @NotEmpty(message = "name cannot be empty")
    @Size(min = 3, message = "name must be more than 3")
    @Column(columnDefinition = "varchar(20) not null unique")
    private String name ;
    @NotNull(message = "price cannot be empty")
    @Positive(message = "price must be positive")
    @Column(columnDefinition = "int not null")
    private Integer price ;
    @NotNull(message = "category ID cannot be empty")
    @Column(columnDefinition = "int not null")
    private Integer categoryId;

}
