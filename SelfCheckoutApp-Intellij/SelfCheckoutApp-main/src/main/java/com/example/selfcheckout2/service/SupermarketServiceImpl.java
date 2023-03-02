package com.example.selfcheckout2.service;

import com.example.selfcheckout2.data.Supermarket;
import com.example.selfcheckout2.data.SupermarketData;
import com.example.selfcheckout2.repository.SupermarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SupermarketServiceImpl implements SupermarketService{
    @Autowired
    SupermarketRepository repository;

    @Override
    public void saveSupermarket(SupermarketData supermarket) {
        Supermarket newSupermarket = new Supermarket(supermarket.getName(), supermarket.getCollaborationStartDate(), supermarket.getEmailContact(),supermarket.getAddress(), supermarket.getAccount(), supermarket.getDataFormat());
        repository.save(newSupermarket);
    }

    @Override
    public SupermarketData getSupermarketById(Long supermarketId) {
        return null;
    }

    @Override
    public List<SupermarketData> getAll() {
        List<Supermarket> supermarkets = repository.findAll();
        List<SupermarketData> supermarketDataList = new ArrayList<>();
        for (Supermarket s : supermarkets
             ) {
            supermarketDataList.add(new SupermarketData(s.name, s.collaborationStartDate, s.emailContact, s.address,s.account, s.dataFormat));
        }
        return supermarketDataList;
    }
}
