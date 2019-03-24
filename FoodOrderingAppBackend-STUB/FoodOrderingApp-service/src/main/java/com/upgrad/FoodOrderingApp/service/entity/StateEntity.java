package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;

@Entity
@Table(name = "states")
public class StateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "state_name")
    private String stateName;

    public StateEntity() {
    }

    public StateEntity(String stateName) {
        this.stateName = stateName;
    }

    public int getId() {
        return id;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
}
