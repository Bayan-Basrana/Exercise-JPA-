package com.example.amazon2.Service;

import com.example.amazon2.Model.Category;
import com.example.amazon2.Repositroy.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getCategory(){
        return categoryRepository.findAll();
    }

    public void addCategory (Category category){
        categoryRepository.save(category);
    }

    public Boolean update (Integer categoryId , Category category){
        Category oldCategory = categoryRepository.getById(categoryId);

        if (oldCategory==null){
            return false;
        }
        oldCategory.setName(category.getName());
        categoryRepository.save(oldCategory);
        return true;
    }


    public Boolean delete (Integer categoryId){
        Category category = categoryRepository.getById(categoryId);
        if (category==null){
            return false;
        }
        categoryRepository.delete(category);
        return true;
    }

   public Boolean  getCategoryID (Integer categoryId){
    if(categoryRepository.findById(categoryId).isEmpty()){
        return false;
    }
    return true;
   }




}
