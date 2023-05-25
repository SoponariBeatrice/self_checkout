package com.example.selfcheckout2.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@Data
@AllArgsConstructor
public class ScheduleData {
    private LocalTime startTime;
    private LocalTime endTime;
}
