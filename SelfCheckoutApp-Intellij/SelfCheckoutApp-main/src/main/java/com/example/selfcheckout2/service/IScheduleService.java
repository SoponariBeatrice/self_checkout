package com.example.selfcheckout2.service;

import com.example.selfcheckout2.data.ScheduleData;
import org.springframework.stereotype.Service;

@Service
public interface IScheduleService {
    ScheduleData getScheduleById(final Long scheduleId);
}
