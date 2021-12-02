package com.freestack.evaluation.models;

import javax.persistence.*;
import java.time.Instant;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "booking_id", nullable = false)
    private Long id;
    private Instant endOfTheBooking=Instant.now();
    private Integer score;
    private Instant startOfTheBooking=Instant.now();
    @ManyToOne
    @JoinColumn(name = "driver_id")
    private UberDriver driver;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UberUser user;

    public Booking() {
    }

    public Booking(UberDriver driver, UberUser user) {
        this.driver = driver;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getEndOfTheBooking() {
        return endOfTheBooking;
    }

    public void setEndOfTheBooking(Instant endOfTheBooking) {
        this.endOfTheBooking = endOfTheBooking;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Instant getStartOfTheBooking() {
        return startOfTheBooking;
    }

    public void setStartOfTheBooking(Instant startOfTheBooking) {
        this.startOfTheBooking = startOfTheBooking;
    }

    public UberDriver getDriver() {
        return driver;
    }

    public void setDriver(UberDriver driver) {
        this.driver = driver;
    }

    public UberUser getUser() {
        return user;
    }

    public void setUser(UberUser user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", endOfTheBooking=" + endOfTheBooking +
                ", score=" + score +
                ", startOfTheBooking=" + startOfTheBooking +
                ", driver=" + driver +
                ", user=" + user +
                '}';
    }
}
