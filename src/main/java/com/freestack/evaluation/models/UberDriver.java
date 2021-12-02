package com.freestack.evaluation.models;

import javax.persistence.*;

@Entity
public class UberDriver {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "driver_id", nullable = false)
    private Long id;
    private Boolean avalable=true;
    private String firstName;
    private String lastName;

    public UberDriver() {
    }

    public Boolean getAvalable() {
        return avalable;
    }

    public void setAvalable(Boolean avalable) {
        this.avalable = avalable;
    }

    public UberDriver(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }


    public String getLastName() {
        return lastName;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UberDriver{" +
                "id=" + id +
                ", avalable=" + avalable +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
