package com.msfrecuencydiscount.controllers;

import com.msfrecuencydiscount.entities.DescuentoFrecuente;
import com.msfrecuencydiscount.models.Usuario;
import com.msfrecuencydiscount.services.DescuentoFrecuenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/descuentos-frecuentes")
public class DescuentoFrecuenteController {

    @Autowired
    private DescuentoFrecuenteService descuentoFrecuenteService;

    // Obtener todos los descuentos frecuentes
    @GetMapping
    public ResponseEntity<List<DescuentoFrecuente>> getAllDescuentosFrecuentes() {
        try {
            List<DescuentoFrecuente> descuentos = descuentoFrecuenteService.getAllDiscountFrecuente();
            return ResponseEntity.ok(descuentos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtener descuento por ID
    @GetMapping("/{id}")
    public ResponseEntity<DescuentoFrecuente> getDescuentoFrecuenteById(@PathVariable Long id) {
        try {
            DescuentoFrecuente descuento = descuentoFrecuenteService.getDiscountFrecuenteById(id);
            return ResponseEntity.ok(descuento);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtener descuento por categoría de usuario FALTA PROBAR****
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<DescuentoFrecuente> getDescuentoPorUsuario(@PathVariable Long usuarioId) {
        try {
            DescuentoFrecuente descuento = descuentoFrecuenteService.getDescuentoByCategogy(usuarioId);
            if (descuento == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(descuento);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Inicializar descuentos por defecto
    @PostMapping("/inicializar")
    public void inicializarDescuentos() {
        descuentoFrecuenteService.inicializarDescuentos();
    }

    // Eliminar todos los descuentos
    @DeleteMapping
    public ResponseEntity<Map<String, String>> deleteAllDescuentos() {
        try {
            descuentoFrecuenteService.deleteAllDiscountFrecuente();
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Todos los descuentos han sido eliminados");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Error al eliminar descuentos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}