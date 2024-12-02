package com.example.amazon2.Controller;

import com.example.amazon2.ApiResponse.ApiResponse;
import com.example.amazon2.Model.Category;
import com.example.amazon2.Service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;


    @GetMapping("/get")
    public ResponseEntity getCategory (){
        return ResponseEntity.status(200).body(categoryService.getCategory());
    }

    @PostMapping("/add")
    public ResponseEntity addCategory (@RequestBody @Valid Category category, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        categoryService.addCategory(category);
        return ResponseEntity.status(200).body(new ApiResponse("Category added successfully"));
    }
    @PutMapping("/update/{categoryId}")
    public ResponseEntity updateCategory (@PathVariable Integer categoryId ,@RequestBody @Valid Category category , Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        Boolean isUpdated = categoryService.update(categoryId,category);

        if (isUpdated){
            return ResponseEntity.status(200).body(new ApiResponse("updated successfully"));
        }return  ResponseEntity.status(400).body("category Id not found");
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity deleteCategory (@PathVariable Integer categoryId){
        Boolean isDeleted =categoryService.delete(categoryId);
        if (isDeleted){
            return ResponseEntity.status(200).body(new ApiResponse("category deleted successfully"));
        }else return  ResponseEntity.status(400).body("category Id not found");
    }


}
