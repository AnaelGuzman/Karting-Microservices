package com.msrack.controllers;
import com.msrack.entities.Rack;
import com.msrack.services.RackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/racks")
public class RackController {

    @Autowired
    private RackService rackService;

    @GetMapping
    public ResponseEntity<List<Rack>> getAllEvents() {
        return ResponseEntity.ok(rackService.getAllEvents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rack> getEventById(@PathVariable Long id) {
        return ResponseEntity.ok(rackService.getEventById(id));
    }

    @PostMapping
    public ResponseEntity<Rack> createEvent(@RequestBody Rack event) {
        return ResponseEntity.ok(rackService.createEvent(event));
    }

    @PutMapping
    public ResponseEntity<Rack> updateEvent(@RequestBody Rack event) {
        return ResponseEntity.ok(rackService.updateEvent(event));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        rackService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/reserva/{idReservacion}")
    public ResponseEntity<Rack> crearEventoPorReserva(@PathVariable Long idReservacion) {
        return ResponseEntity.ok(rackService.crearEventosByIdReservation(idReservacion));
    }
}