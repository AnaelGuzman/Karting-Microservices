package com.msspecialdaydiscount.controllers;

import com.msspecialdaydiscount.entities.SpecialDayDiscountEntity;
import com.msspecialdaydiscount.services.SpecialDayDiscountServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/special")
public class SpecialDayDiscountController {

    private final SpecialDayDiscountServices discountServices;

    @Autowired
    public SpecialDayDiscountController(SpecialDayDiscountServices discountServices) {
        this.discountServices = discountServices;
    }

    // Endpoint para crear descuento general
    @PostMapping("/general")
    public ResponseEntity<SpecialDayDiscountEntity> createGeneralDiscount(
            @RequestParam String type,
            @RequestParam Integer discount,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        SpecialDayDiscountEntity newDiscount = discountServices.createSpecialDayDiscountFee(type, discount, date);
        return ResponseEntity.status(HttpStatus.CREATED).body(newDiscount);
    }

    // Endpoint para crear descuento por cumpleaños
    @PostMapping("/birthday")
    public ResponseEntity<SpecialDayDiscountEntity> createBirthdayDiscount(
            @RequestParam Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate reservationDate) {

        SpecialDayDiscountEntity newDiscount = discountServices.createBirthdayDiscountFee(userId, reservationDate);
        return ResponseEntity.status(HttpStatus.CREATED).body(newDiscount);
    }

    // Endpoint para crear descuento de fin de semana
    @PostMapping("/weekend")
    public ResponseEntity<SpecialDayDiscountEntity> createWeekendDiscount(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate reservationDate,
            @RequestParam Integer discount) {

        SpecialDayDiscountEntity newDiscount = discountServices.createSpecialWeekendDiscount(reservationDate, discount);
        return ResponseEntity.status(HttpStatus.CREATED).body(newDiscount);
    }

    // Obtener todos los descuentos especiales
    @GetMapping
    public ResponseEntity<List<SpecialDayDiscountEntity>> getAllSpecialDiscounts() {
        List<SpecialDayDiscountEntity> discounts = discountServices.getallSpecialDayDiscounts();
        return ResponseEntity.ok(discounts);
    }

    // Obtener descuento de fin de semana activo
    @GetMapping("/weekend")
    public ResponseEntity<SpecialDayDiscountEntity> getActiveWeekendDiscount() {
        Optional<SpecialDayDiscountEntity> discount = discountServices.getSpecialDayDiscountsWeekend();
        return discount.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Obtener descuento de cumpleaños por usuario
    @GetMapping("/birthday/{userId}")
    public ResponseEntity<SpecialDayDiscountEntity> getBirthdayDiscountByUser(@PathVariable Long userId) {
        Optional<SpecialDayDiscountEntity> discount = discountServices.getSpecialDayDiscountsUser(userId);
        return discount.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Obtener descuento por tipo
    @GetMapping("/type/{type}")
    public ResponseEntity<SpecialDayDiscountEntity> getDiscountByType(@PathVariable String type) {
        Optional<SpecialDayDiscountEntity> discount = discountServices.getSpecialDayDiscountsOther(type);
        return discount.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Eliminar descuento específico
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable Long id) {
        discountServices.deleteSpecialDayDiscount(id);
        return ResponseEntity.noContent().build();
    }

    // Eliminar todos los descuentos
    @DeleteMapping
    public ResponseEntity<Void> deleteAllDiscounts() {
        discountServices.deleteAllSpecialDayDiscount();
        return ResponseEntity.noContent().build();
    }
}