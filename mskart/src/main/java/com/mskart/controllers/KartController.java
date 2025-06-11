package com.mskart.controllers;

import com.mskart.entities.KartEntity;
import com.mskart.services.KartServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/kart")
public class KartController {
    @Autowired
    KartServices kartServices;

    @GetMapping("/")
    public ResponseEntity<List<KartEntity>> listKart() {
        List<KartEntity> karts = kartServices.getAllKarts();
        return ResponseEntity.ok(karts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<KartEntity> getKartById(@PathVariable("id") Long id) {
        KartEntity kartEntity = kartServices.getKartById(id);
        return ResponseEntity.ok(kartEntity);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> listAllKartByStatus(@PathVariable("status") String status) {
        try {
            if (status == null || status.isEmpty()) {
                return ResponseEntity.badRequest().body("El status no puede estar vacío");
            }
            List<KartEntity> karts = kartServices.getAllKartsByStatus(status);
            if (karts.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(karts);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al procesar la solicitud: " + e.getMessage());
        }
    }

    @PostMapping("/")
    public ResponseEntity<KartEntity> addKart(@RequestBody KartEntity kart) {
        KartEntity kartNew = kartServices.saveKart(kart);
        return ResponseEntity.ok(kartNew);
    }

    @PutMapping("/{id}")
    public ResponseEntity<KartEntity> updateKart(@PathVariable("id") Long id,@RequestBody KartEntity kart) {
        if (!id.equals(kart.getId())) {
            return ResponseEntity.badRequest().build();
        }
        KartEntity kartUpdated = kartServices.updateKart(kart);
        return ResponseEntity.ok(kartUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<KartEntity> deleteKart(@PathVariable("id") Long id) throws Exception {
        var delete = kartServices.deleteKartById(id);
        return ResponseEntity.noContent().build();
    }
}