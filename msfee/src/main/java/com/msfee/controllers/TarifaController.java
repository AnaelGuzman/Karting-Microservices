package com.msfee.controllers;

import com.msfee.entities.Tarifa;
import com.msfee.services.TarifaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fees")
public class TarifaController {
    @Autowired
    private TarifaService tarifaService;

    @GetMapping
    public List<Tarifa> getAllTarifas() {
        return tarifaService.getAllTarifas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarifa> getTarifaById(@PathVariable Long id) {
        return tarifaService.getTarifaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Tarifa createTarifa(@RequestBody Tarifa tarifa) {
        return tarifaService.createTarifa(tarifa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarifa> updateTarifa(@PathVariable Long id, @RequestBody Tarifa tarifaDetails) {
        try {
            Tarifa updatedTarifa = tarifaService.updateTarifa(id, tarifaDetails);
            return ResponseEntity.ok(updatedTarifa);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTarifa(@PathVariable Long id) {
        try {
            tarifaService.deleteTarifa(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/iniciar/tarifas")
    public void inicializarTarifas() {
        tarifaService.inicializarTarifas();
    }
}
