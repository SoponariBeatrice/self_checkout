package com.example.selfcheckout2.service;

import com.example.selfcheckout2.data.Schedule;
import com.example.selfcheckout2.data.ScheduleData;
import com.example.selfcheckout2.data.Supermarket;
import com.example.selfcheckout2.data.SupermarketData;
import com.example.selfcheckout2.repository.SupermarketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupermarketServiceImpl implements SupermarketService{
    @Autowired
    SupermarketRepository repository;

    @Autowired
    ScheduleServiceImpl scheduleService;
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
            ScheduleData scheduleData = scheduleService.getScheduleById(s.getId());
            supermarketDataList.add(new SupermarketData(s.name, s.collaborationStartDate, s.emailContact, s.address,s.account, s.dataFormat, s.latitude, s.longitude,scheduleData));
        }
        return supermarketDataList;
    }

    @Override
    public List<SupermarketData> getSupermarketsByKeyword(String keyword) {
        List<Supermarket> supermarkets = repository.findAll();
        List<SupermarketData> supermarketDataList = new ArrayList<>();
        keyword = keyword.toLowerCase();
        for (Supermarket supermarket : supermarkets) {
            if (supermarket.getName().toLowerCase().contains(keyword) || supermarket.getAddress().toLowerCase().contains(keyword))
            {
                ScheduleData scheduleData = scheduleService.getScheduleById(supermarket.getId());
                supermarketDataList.add(new SupermarketData(supermarket.name, supermarket.collaborationStartDate, supermarket.emailContact, supermarket.address,supermarket.account, supermarket.dataFormat, supermarket.latitude, supermarket.longitude, scheduleData));
            }
        }
        return supermarketDataList;
    }

    @Override
    public SupermarketData getSupermarketByAdministrator(String administratorEmail) {
        Supermarket supermarket = repository.findSupermarketByEmailContact(administratorEmail);
        ScheduleData scheduleData = scheduleService.getScheduleById(supermarket.getId());
        return new SupermarketData(supermarket.name, supermarket.collaborationStartDate, supermarket.emailContact, supermarket.address, supermarket.account, supermarket.dataFormat, supermarket.latitude, supermarket.longitude, scheduleData, supermarket.getId());
    }

    @Override
    public SupermarketData updateSupermarket(SupermarketData supermarketData, Long id) {
        Supermarket supermarket = repository.getById(id);
        supermarket.setAccount(supermarketData.getAccount());
        supermarket.setAddress(supermarketData.getAddress());
        supermarket.setName(supermarketData.getName());
        //Schedule schedule = new Schedule(supermarketData.getScheduleData().getStartTime(), supermarketData.getScheduleData().getEndTime());
        //supermarket.setSchedule(schedule);
        ScheduleData scheduleData = scheduleService.getScheduleById(supermarket.getId());

        if(supermarketData.getScheduleData() != null)
        {
            scheduleData.setStartTime(supermarketData.getScheduleData().getStartTime());
            scheduleData.setEndTime(supermarketData.getScheduleData().getEndTime());
            scheduleService.updateSchedule(scheduleData, supermarket.getId());
        }


        Supermarket updatedSupermarket = repository.save(supermarket);
        return new SupermarketData(updatedSupermarket.name, updatedSupermarket.collaborationStartDate, updatedSupermarket.emailContact, updatedSupermarket.address, updatedSupermarket.account, updatedSupermarket.dataFormat, updatedSupermarket.latitude, updatedSupermarket.longitude, supermarketData.getScheduleData(), updatedSupermarket.getId());
    }


}
