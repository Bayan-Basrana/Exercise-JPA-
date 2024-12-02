package com.example.amazon2.Service;

import com.example.amazon2.Model.Merchant;
import com.example.amazon2.Model.MerchantStock;
import com.example.amazon2.Model.Product;
import com.example.amazon2.Model.User;
import com.example.amazon2.Repositroy.MerchantStockRepository;
import com.example.amazon2.Repositroy.ProductRepository;
import com.example.amazon2.Repositroy.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
private final MerchantStockRepository merchantStockRepository;
private final ProductRepository productRepository;


private final Map<Integer,List<Integer>>  purchaseHistory =new HashMap<>();
private final Map<Integer,List<Integer>>  wishlist =new HashMap<>();
    public List<User> getUser (){
        return userRepository.findAll();
    }

    public void addUser (User user){
        userRepository.save(user);
    }



    public Boolean update ( Integer userId , User user){
        User old = userRepository.getById(userId );

        if (old==null){
            return false;
        }
        old.setUsername(user.getUsername());
        old.setPassword(user.getPassword());
        old.setEmail(user.getEmail());
        old.setRole(user.getRole());
        old.setBalance(user.getBalance());
userRepository.save(old);
        return true;
    }


    public Boolean delete (Integer userId){
        User user = userRepository.getById(userId );
        if (user==null){
            return false;
        }
        userRepository.delete(user);
        return true;
    }

    //12-Create endpoint where user can buy a product directly
    public Integer buyProduct(Integer userId,Integer productId , Integer merchantId ) {
        User user = userRepository.getById(userId );
        MerchantStock merchantStock= null;
        for (MerchantStock m :merchantStockRepository.findAll()) {
           if(m.getProductId().equals(productId) && m.getMerchantId().equals(merchantId)){
                merchantStock =m;}
        }
        if(user==null){
            return 1;
        }
        else {
            Product product = productRepository.getById(productId);
            if (product == null) {
                return 2;
            }

            if (merchantStock.getStock() <= 0) {
                return 3;
            }
            if (user.getBalance() < product.getPrice()) {
                return 4;
            }
            merchantStock.setStock(merchantStock.getStock() - 1);
            merchantStockRepository.save(merchantStock);
            user.setBalance(user.getBalance() - product.getPrice());
            Integer point = product.getPrice() / 10;
            user.setRewardPoints(user.getRewardPoints() + point);
            userRepository.save(user);
            purchaseHistory.putIfAbsent(userId,new ArrayList<>());
            purchaseHistory.get(userId).add(productId);
            return 5;

        }
    }

    public List<Product> getPurchaseHistory (Integer userId){
        List<Integer> productIds =purchaseHistory.getOrDefault(userId,new ArrayList<>());
        if(productIds.isEmpty()){
            return new ArrayList<>();
        }
        return  productRepository.findAllById(productIds); }

    //1-extra endpoints-display how much the RewardPoints for a user
    public Integer getRewardPoints (Integer userId){
        User user= userRepository.getById(userId);
        if (user!=null){
            return user.getRewardPoints();
        }return 0;
    }


    //1-extra endpoints-use the RewardPoints
public Integer useRewardPoints (Integer userId, Integer pointToUse){
    User user= userRepository.getById(userId);
    if (user!=null){
        if(user.getRewardPoints()<=pointToUse){
            return 1;
        }
        user.setRewardPoints(user.getRewardPoints()-pointToUse);
        double discount = pointToUse*0.1  ;
        user.setBalance((int) (user.getBalance()+discount));
return 2;
    }return 3;
}




//2-extra endpoints-display wishlist

    public List<Product> gwtWishlist (Integer userId){
        List<Integer> productIds =wishlist.getOrDefault(userId,new ArrayList<>());
        if(productIds.isEmpty()){
            return new ArrayList<>();
        }
        return productRepository.findAllById(productIds);
    }
//2-extra endpoints-add product to the wishlist

public Integer addToWishlist (Integer userId , Integer productId ){
        wishlist.putIfAbsent(userId,new ArrayList<>());
        List<Integer> userWishlist =wishlist.get(userId);
        if(userWishlist.contains(productId)){
            return 1;
        }
        userWishlist.add(productId);
        return 2;
}

public Integer returnProduct (Integer userId , Integer productId){
    User user = userRepository.getById(userId );
    Product product =productRepository.getById(productId);
    if(!purchaseHistory.containsKey(userId)) {
            return 1;
    }
        List<Integer> userPurchases =purchaseHistory.get(userId);
        if(!userPurchases.contains(productId)){
            return 2;
        }
   userPurchases.remove(productId);
    for (MerchantStock m :merchantStockRepository.findAll()) {
        if (m.getProductId().equals(productId)) {
m.setStock(m.getStock()+1);}
    }
    user.setBalance(user.getBalance()+product.getPrice());
    return 3;
}
}
