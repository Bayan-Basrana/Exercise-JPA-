package com.example.amazon2.Controller;

import com.example.amazon2.ApiResponse.ApiResponse;
import com.example.amazon2.Model.Merchant;
import com.example.amazon2.Service.MerchantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/merchant")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;
    @GetMapping("/get")
    public ResponseEntity getMerchant (){
        return ResponseEntity.status(200).body(merchantService.getMerchant());}

    @PostMapping("/add")
    public ResponseEntity addMerchant (@RequestBody @Valid Merchant merchant , Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        merchantService.addMerchant(merchant);
        return ResponseEntity.status(200).body(new ApiResponse("added successfully"));

    }


    @PutMapping("/update/{merchantId}")
    public ResponseEntity updateMerchant (@PathVariable Integer merchantId, @RequestBody @Valid Merchant merchant , Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        Boolean isUpdated= merchantService.update(merchantId,merchant);
        if (isUpdated){
            return ResponseEntity.status(200).body(new ApiResponse("updated successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("merchant Id not found"));
    }

    @DeleteMapping("/delete/{merchantId}")
    public ResponseEntity deleteMerchant (@PathVariable Integer merchantId ){
        Boolean isDeleted   =merchantService.delete(merchantId);
        if (isDeleted){
            return ResponseEntity.status(200).body("deleted successfully");
        }
        return ResponseEntity.status(400).body("merchant Id not found");
    }


}
