package com.mskart.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "kart")
public class KartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    @Column(unique = true, nullable = false)
    private String code;
    private String model;
    private String status;
    private Boolean maintenance;

    public KartEntity(Long id, String code, String model, String status, Boolean maintenance) {
        this.id = id;
        this.code = code;
        this.model = model;
        this.status = status;
        this.maintenance = maintenance;
    }

    public KartEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getMaintenance() {
        return maintenance;
    }

    public void setMaintenance(Boolean maintenance) {
        this.maintenance = maintenance;
    }
}