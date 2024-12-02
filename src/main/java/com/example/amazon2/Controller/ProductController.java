package com.example.amazon2.Controller;

import com.example.amazon2.ApiResponse.ApiResponse;
import com.example.amazon2.Model.Product;
import com.example.amazon2.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/get")
    public ResponseEntity getProduct (){
        return ResponseEntity.status(200).body(productService.getProduct());
    }

    @PostMapping("/add")
public ResponseEntity addProduct (@RequestBody @Valid Product product , Errors errors){
    if (errors.hasErrors()){
        return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
    }
    Boolean isAdded = productService.addProduct(product);
    if (isAdded){
        return ResponseEntity.status(200).body(new ApiResponse("added successfully"));
}
    return ResponseEntity.status(400).body("category id not found ");
}
@PutMapping("/update/{productId}")
public ResponseEntity updateProduct (@PathVariable Integer productId , @RequestBody @Valid Product product ,Errors errors){
    if (errors.hasErrors()){
        return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
    }
    int result = productService.updateProduct(productId,product);
    if (result==1){
        return ResponseEntity.status(400).body(new ApiResponse("productId not found"));
    }
    if (result==2){
        return ResponseEntity.status(200).body(new ApiResponse("product update successfully"));
}
    return ResponseEntity.status(400).body("Category id not found ");
}



@DeleteMapping("/delete/{productId}")
public ResponseEntity deleteProduct (@PathVariable Integer productId ){
      Boolean isDeleted = productService.deleteProduct(productId) ;
      if (isDeleted){
          return ResponseEntity.status(200).body("deleted successfully");
      }
      else return ResponseEntity.status(400).body(new ApiResponse("product Id not found"));
}

@GetMapping("/productByCategory/{categoryId}")
public ResponseEntity productByCategory (@PathVariable Integer categoryId){
    List<Product> productByCategory = productService.getProductByCategory(categoryId);
    if(productByCategory!= null){
        return ResponseEntity.status(200).body(productByCategory);
    }return ResponseEntity.status(400).body(new ApiResponse("no product by this category "));
}

    //4-extra endpoints-Compere product
    @GetMapping("/productCompere")
public ResponseEntity productCompere (@RequestBody Integer[] productIds){
    List<Product> productCompere =productService.productCompere(productIds);
if (productCompere!=null){
    return ResponseEntity.status(200).body(productCompere);
}return ResponseEntity.status(400).body(new ApiResponse("no product by this IDs "));

    }



}



