package com.msspecialdaydiscount.entities;

import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;


@Entity
@Table(name = "special_fee")
public class SpecialDayDiscountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    private String type;
    private LocalDate date;
    private Long idUserBirthday;
    private Double discount;
    private Double price;

    public SpecialDayDiscountEntity(String type, LocalDate date, Double price, Double discount) {
        this.type = type;
        this.date = date;
        this.idUserBirthday = null;
        this.price = price;
        this.discount = discount;
    }
    public SpecialDayDiscountEntity() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public Long getIdUserBirthday() {
        return idUserBirthday;
    }
    public void setIdUserBirthday(Long idUserBirthday) {
        this.idUserBirthday = idUserBirthday;
    }
}
