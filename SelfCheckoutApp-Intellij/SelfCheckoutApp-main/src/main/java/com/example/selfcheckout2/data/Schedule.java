package com.example.selfcheckout2.data;

import lombok.Data;


import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "schedule")
@Data
public class Schedule {

    private LocalTime startTime;
    private LocalTime endTime;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Supermarket supermarket;

    public Schedule(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Schedule() {

    }
}
