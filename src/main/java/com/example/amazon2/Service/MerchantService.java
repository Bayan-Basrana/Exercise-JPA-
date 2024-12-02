package com.example.amazon2.Service;

import com.example.amazon2.Model.Merchant;
import com.example.amazon2.Repositroy.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantService {
    private final MerchantRepository merchantRepository;

    public List<Merchant> getMerchant (){
        return merchantRepository.findAll();
    }

    public void addMerchant (Merchant merchant){
merchantRepository.save(merchant);
    }



    public Boolean update ( Integer merchantId , Merchant merchant){
        Merchant oldMerchant = merchantRepository.getById(merchantId );

        if (oldMerchant==null){
            return false;
        }
        oldMerchant.setName(oldMerchant.getName());
        return true;
    }


    public Boolean delete (Integer merchantId){
        Merchant merchant = merchantRepository.getById(merchantId );
        if (merchant==null){
            return false;
        }
        merchantRepository.delete(merchant);
        return true;
    }




}
