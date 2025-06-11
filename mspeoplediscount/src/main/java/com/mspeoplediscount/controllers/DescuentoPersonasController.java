package com.mspeoplediscount.controllers;

import com.mspeoplediscount.entities.DescuentoPersonas;
import com.mspeoplediscount.services.DescuentoPersonasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/peoples")
public class DescuentoPersonasController {
    @Autowired
    private DescuentoPersonasService descuentoPersonasService;


    @GetMapping
    public List<DescuentoPersonas> getAllDescuentos() {
        return descuentoPersonasService.getAllDescuentos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DescuentoPersonas> getDescuentoById(@PathVariable Long id) {
        try {
            DescuentoPersonas descuento = descuentoPersonasService.getDescuentoById(id);
            return ResponseEntity.ok(descuento);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/por-personas/{numPersonas}")
    public ResponseEntity<DescuentoPersonas> getDescuentoByNumPersonas(@PathVariable Integer numPersonas) {
        DescuentoPersonas descuento = descuentoPersonasService.getDescuentoByNumPersonas(numPersonas);
        if (descuento != null) {
            return ResponseEntity.ok(descuento);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public DescuentoPersonas createDescuento(@RequestBody DescuentoPersonas descuento) {
        return descuentoPersonasService.createDescuento(descuento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DescuentoPersonas> updateDescuento(@PathVariable Long id, @RequestBody DescuentoPersonas descuentoDetails) {
        try {
            DescuentoPersonas updatedDescuento = descuentoPersonasService.updateDescuento(id, descuentoDetails);
            return ResponseEntity.ok(updatedDescuento);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDescuento(@PathVariable Long id) {
        try {
            descuentoPersonasService.deleteDescuento(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/inicializar")
    public ResponseEntity<?> inicializar() {
        descuentoPersonasService.inicializarDescuentos();
        return ResponseEntity.ok("Descuentos inicializados");
    }
}
