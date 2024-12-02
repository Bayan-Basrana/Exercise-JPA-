package com.example.amazon2.Service;

import com.example.amazon2.Model.Product;
import com.example.amazon2.Repositroy.CategoryRepository;
import com.example.amazon2.Repositroy.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public List<Product> getProduct (){
        return productRepository.findAll();
    }


    public Boolean addProduct (Product product){
        for (int i = 0; i < categoryRepository.findAll().size(); i++) {
            if(product.getCategoryId().equals(categoryRepository.findAll().get(i).getCategoryId())){
                productRepository.save(product);
                return true;
            }
        }return false;
    }

public Integer updateProduct (Integer productId , Product product){
        Product oldProduct =productRepository.getById(productId);
        if(oldProduct==null){
            return 1;
        }
    for (int i = 0; i < categoryRepository.findAll().size(); i++) {
        if(product.getCategoryId().equals(categoryRepository.findAll().get(i).getCategoryId())){
            oldProduct.setName(product.getName());
            oldProduct.setPrice(product.getPrice());
            productRepository.save(oldProduct);
            return 2;
        }
    }return 3;
}


public Boolean deleteProduct (Integer productId ){
        Product product =productRepository.getById(productId);
        if (product==null){
            return false;
        }
        productRepository.delete(product);
        return true;
}



public List<Product> getProductByCategory (Integer categoryId){
        List<Product> productByCategory =new ArrayList<>();
    for (Product p :productRepository.findAll()){
        if(p.getCategoryId().equals(categoryId)){
            productByCategory.add(p);
        }
    }return productByCategory;

}


    //4-extra endpoints-Compere product
public List<Product> productCompere (Integer[] productIds){
        List<Product> productCompere = new ArrayList<>();
    for (Product p :productRepository.findAll()) {
        for (Integer s : productIds){
            if (s.equals(p.getProductId())){
                productCompere.add(p);
            }
        }
    }return productCompere;
    }

}
