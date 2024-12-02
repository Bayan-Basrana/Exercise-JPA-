package com.example.amazon2.Service;

import com.example.amazon2.Model.MerchantStock;
import com.example.amazon2.Repositroy.MerchantRepository;
import com.example.amazon2.Repositroy.MerchantStockRepository;
import com.example.amazon2.Repositroy.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MerchantStockService {
    private final MerchantStockRepository merchantStockRepository;
    private final ProductRepository productRepository;
    private final MerchantRepository merchantRepository;

public List<MerchantStock> getMerchantStock (){
    return merchantStockRepository.findAll();
}


public Integer addMerchantStock (MerchantStock merchantStock){
    for (int i = 0; i < merchantRepository.findAll().size(); i++) {
        if (!merchantStock.getMerchantId().equals(merchantRepository.findAll().get(i).getMerchantId())){
            return 1;
        }
    }
    for (int i = 0; i < productRepository.findAll().size(); i++) {
        if (!merchantStock.getProductId().equals(productRepository.findAll().get(i).getProductId())){
            return 2;
        }
    }
    merchantStockRepository.save(merchantStock);
    return 3;

}

    public int updateMerchantStock (Integer stockId, MerchantStock merchantStock){
        MerchantStock oldStock =merchantStockRepository.getById(stockId);
        if(oldStock==null){
            return 1;
        }
        for (int i = 0; i < merchantRepository.findAll().size(); i++) {
            if (!merchantStock.getMerchantId().equals(merchantRepository.findAll().get(i).getMerchantId())){
                return 2;
            }
        }
        for (int i = 0; i < productRepository.findAll().size(); i++) {
            if (!merchantStock.getProductId().equals(productRepository.findAll().get(i).getProductId())){
                return 3;
            }
        }

                oldStock.setStock(merchantStock.getStock());
                merchantStockRepository.save(oldStock);
                return 4;
    }


    public Boolean deleteMerchantStock (Integer stockId){
        MerchantStock merchantStock= merchantStockRepository.getById(stockId);
        if (merchantStock==null){
            return false;
        }
        merchantStockRepository.delete(merchantStock);
        return true;
    }

//11- endpoint where merchant can add more stocks of product to a merchant Stock
    public Integer changStock (Integer productId ,Integer merchantId ,Integer amount){
        for (MerchantStock m:merchantStockRepository.findAll()){
            if (amount<=0){
                return 1;
            }
if (m.getProductId().equals(productId) &&  m.getMerchantId().equals(merchantId)) {
m.setStock(m.getStock()+amount);
merchantStockRepository.save(m);
return 2;
}}
        return 3;
    }


    //5-extra endpoints-display the products with low stock for a specific merchant.
public List<MerchantStock> lowStock (Integer merchantId){
    List<MerchantStock> lowStock = new ArrayList<>();
    for (MerchantStock m :merchantStockRepository.findAll()){
        if (m.getMerchantId().equals(merchantId)){
            if (m.getStock()<=3){
                lowStock.add(m);
            }
        }
    }return lowStock;
}



}
