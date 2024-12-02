package com.example.amazon2.Controller;

import com.example.amazon2.ApiResponse.ApiResponse;
import com.example.amazon2.Model.MerchantStock;
import com.example.amazon2.Repositroy.MerchantStockRepository;
import com.example.amazon2.Service.MerchantStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/merchantStock")
@RequiredArgsConstructor

public class MerchantStockController {

    private final MerchantStockService merchantStockService;
    private final MerchantStockRepository merchantStockRepository;

    @GetMapping("/get")
    public ResponseEntity getMerchantStock (){
        return ResponseEntity.status(200).body(merchantStockService.getMerchantStock());
    }
    @PostMapping("/add")
    public ResponseEntity addMerchantStock (@RequestBody @Valid MerchantStock merchantStock , Errors errors ){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        Integer result = merchantStockService.addMerchantStock(merchantStock);
        if (result==1){
            return ResponseEntity.status(400).body(new ApiResponse(" merchantId not found"));}
        if (result==2){
            return ResponseEntity.status(400).body(new ApiResponse("ProductId not found"));}

        return ResponseEntity.status(200).body(new ApiResponse("added successfully"));

    }
    @PutMapping("/update/{stockId}")
    public ResponseEntity updateMerchantStock (@PathVariable Integer stockId , @RequestBody @Valid MerchantStock merchantStock , Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        int result= merchantStockService.updateMerchantStock(stockId, merchantStock);
        if (result==1){
            return ResponseEntity.status(400).body(new ApiResponse("stockId not found"));
        }
        if(result==2){
            return ResponseEntity.status(400).body(new ApiResponse("merchant Id not found"));}
        if (result==3){
            return ResponseEntity.status(400).body(new ApiResponse("productId not found"));}
        return ResponseEntity.status(200).body(new ApiResponse("updated successfully "));


    }
    @DeleteMapping("/delete/{stockId}")
    public ResponseEntity deleteMerchantStock (@PathVariable Integer stockId ){
        Boolean isDeleted   =merchantStockService.deleteMerchantStock(stockId);
        if (isDeleted){
            return ResponseEntity.status(200).body("deleted successfully");
        }
        return ResponseEntity.status(400).body("not found");
    }
//11- endpoint where merchant can add more stocks of product to a merchant Stock
    @PutMapping("/changStock/{productId}/{merchantId}/{amount}")
public ResponseEntity changStock (@PathVariable Integer productId ,@PathVariable Integer merchantId ,@PathVariable Integer amount){
    int result =merchantStockService.changStock(productId ,merchantId ,amount);
    if (result==2){
        return ResponseEntity.status(200).body(new ApiResponse(" chang Stock successfully"));
    }
    if(result==1){
        return ResponseEntity.status(400).body(new ApiResponse("amount less than 0"));
    } return ResponseEntity.status(400).body("not found");
}
    //5-extra endpoints-display the products with low stock for a specific merchant.
    @GetMapping("/lowStock/{merchantId}")
    public ResponseEntity lowStock (@PathVariable Integer merchantId){
        List<MerchantStock> lowStock =merchantStockService.lowStock(merchantId);
        if (lowStock!= null){
            return ResponseEntity.status(200).body(lowStock);
        }return ResponseEntity.status(400).body("there is no low stock product");
    }

}
