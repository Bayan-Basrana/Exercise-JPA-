package com.example.amazon2.Controller;

import com.example.amazon2.ApiResponse.ApiResponse;
import com.example.amazon2.Model.Merchant;
import com.example.amazon2.Model.User;
import com.example.amazon2.Service.MerchantService;
import com.example.amazon2.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;
    @GetMapping("/get")
    public ResponseEntity getUser (){
        return ResponseEntity.status(200).body(userService.getUser());}

    @PostMapping("/add")
    public ResponseEntity addUser (@RequestBody @Valid User user , Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        userService.addUser(user);
        return ResponseEntity.status(200).body(new ApiResponse("added successfully"));

    }


    @PutMapping("/update/{userId}")
    public ResponseEntity updateUser (@PathVariable Integer userId, @RequestBody @Valid User user , Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        Boolean isUpdated= userService.update(userId,user);
        if (isUpdated){
            return ResponseEntity.status(200).body(new ApiResponse("updated successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("user Id not found"));
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity deleteMerchant (@PathVariable Integer userId ){
        Boolean isDeleted   =userService.delete(userId);
        if (isDeleted){
            return ResponseEntity.status(200).body("deleted successfully");
        }
        return ResponseEntity.status(400).body("user Id not found");
    }


@GetMapping("/getPurchaseHistory/{userId}")
    public ResponseEntity getPurchaseHistory (@PathVariable Integer userId){
        return ResponseEntity.status(200).body(userService.getPurchaseHistory(userId));
    }

//12-Create endpoint where user can buy a product directly
    @PutMapping ("/buy/{userId}/{productId}/{merchantId}")
    public ResponseEntity buyProduct(@PathVariable Integer userId, @PathVariable Integer productId, @PathVariable Integer merchantId) {
        Integer result = userService.buyProduct(userId, productId, merchantId);
        switch (result) {
            case 1: return ResponseEntity.status(400).body("User not found");
            case 2: return ResponseEntity.status(400).body("Product not found");
            case 3: return ResponseEntity.status(400).body("Product out of stock");
            case 4: return ResponseEntity.status(400).body("Insufficient balance");
            default: return ResponseEntity.ok("Purchase successful!");
        }

    }

    //1-extra endpoints-display how much the RewardPoints for a user
    @GetMapping("/getRewardPoints/{userId}")
    public ResponseEntity getRewardPoints (@PathVariable Integer userId){
        if (userService.getRewardPoints(userId)!=0){
            return ResponseEntity.status(200).body(userService.getRewardPoints(userId));
        }else return ResponseEntity.status(400).body(new ApiResponse("yore don't have any RewardPoints  "));
    }
    //1-extra endpoints-use the RewardPoints
    @PutMapping("/useRewardPoints/{userId}/{pointToUse}")
    public ResponseEntity useRewardPoints (@PathVariable Integer userId, @PathVariable Integer pointToUse){
        int result = userService.useRewardPoints(userId,pointToUse);
        if (result==1){
            return ResponseEntity.status(400).body(new ApiResponse("no enough RewardPoints."));
        }
        if (result==2){
            return ResponseEntity.status(200).body(new ApiResponse("you used the RewardPoints successfully"));
        }else return ResponseEntity.status(400).body(new ApiResponse("user id not found"));
    }



//2-extra endpoints-display wishlist

    @GetMapping("/getWishlist/{userId}")
    public ResponseEntity getWishlist (@PathVariable Integer userId){
        return ResponseEntity.status(200).body(userService.gwtWishlist(userId));
    }
//2-extra endpoints-add product to the wishlist

    @PostMapping("/addToWishlist/{userId}/{productId}")
    public ResponseEntity addToWishlist (@PathVariable Integer userId , @PathVariable Integer productId){
        Integer result = userService.addToWishlist(userId,productId);
        if (result==1){
            return ResponseEntity.status(400).body("Product already in the wishlist");
        }
        return ResponseEntity.status(200).body(new ApiResponse("product added successfully to wishlist"));
    }


    //3-extra endpoints-returnProduct
    @PutMapping("/return/{userId}/{productId}")
    public ResponseEntity returnProduct (@PathVariable Integer userId ,@PathVariable Integer productId ){
        int result = userService.returnProduct(userId,productId);
        if (result==1){
            return ResponseEntity.status(400).body(new ApiResponse("User has no purchase history"));
        }
        if (result==2){
            return ResponseEntity.status(400).body(new ApiResponse("not allow, because product isn't in PurchaseHistory"));
        }
        if (result==3){
            return ResponseEntity.status(200).body(new ApiResponse("product return successfully"));
        }

        return ResponseEntity.status(400).body(new ApiResponse("userId not found"));

    }




}


