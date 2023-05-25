package com.example.selfcheckout2.service;

import com.example.selfcheckout2.data.Schedule;
import com.example.selfcheckout2.data.ScheduleData;
import com.example.selfcheckout2.repository.IScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleServiceImpl implements IScheduleService{
    @Autowired
    IScheduleRepository scheduleRepository;

    @Override
    public ScheduleData getScheduleById(Long scheduleId) {
        Schedule schedule = scheduleRepository.getById(scheduleId);
        return new ScheduleData(schedule.getStartTime(), schedule.getEndTime());
    }

    public ScheduleData updateSchedule(ScheduleData scheduleData, Long id)
    {
        Schedule schedule = scheduleRepository.getById(id);
        schedule.setStartTime(scheduleData.getStartTime());
        schedule.setEndTime(scheduleData.getEndTime());
        Schedule edited = scheduleRepository.save(schedule);

        return new ScheduleData(edited.getStartTime(), edited.getEndTime());
    }
}
