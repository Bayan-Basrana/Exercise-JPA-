package com.example.amazon2.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Category {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY )
private Integer categoryId ;
    @NotEmpty(message = "name cannot be empty")
    @Size(min = 3,message = "name must be more than 3")
    @Column(columnDefinition = "varchar(10) not null unique")
    private String name ;
}
