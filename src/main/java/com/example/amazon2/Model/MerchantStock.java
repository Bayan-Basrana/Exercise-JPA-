package com.example.amazon2.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class MerchantStock {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer stockId ;
    @NotNull(message = "product ID cannot be empty")
    @Column(columnDefinition = "int not null")
    private Integer productId ;
    @NotNull(message = "merchant ID cannot be empty")
    @Column(columnDefinition = "int not null")
    private Integer merchantId ;
    @NotNull(message = "stock cannot be empty")
    @Max(value = 10,message = "stock must be 10 or more ")
    @Column(columnDefinition = "int not null")
    private Integer stock;


}
