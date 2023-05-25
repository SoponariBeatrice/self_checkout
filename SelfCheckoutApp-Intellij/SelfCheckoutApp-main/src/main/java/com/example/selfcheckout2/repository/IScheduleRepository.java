package com.example.selfcheckout2.repository;

import com.example.selfcheckout2.data.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IScheduleRepository extends JpaRepository<Schedule, Long> {
    @Override
    Schedule getById(Long id);
}
