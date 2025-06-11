package com.msreservation.controllers;

import com.msreservation.entities.Reserva;
import com.msreservation.models.tarifaGeneral;
import com.msreservation.services.GeneratorVoucherService;
import com.msreservation.services.ReservaService;
import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;
    @Autowired
    private GeneratorVoucherService generatorVoucherService;

    @GetMapping
    public ResponseEntity<List<Reserva>> getAllReservas() {
        List<Reserva> reservas = reservaService.getAllReservas();
        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reserva> getReservaById(@PathVariable Long id) {
        Reserva reserva = reservaService.getReservaById(id);
        return ResponseEntity.ok(reserva);
    }

    @PostMapping
    public ResponseEntity<Reserva> createReserva(@RequestBody Reserva reserva) {
        Reserva nuevaReserva = reservaService.createReserva(reserva);
        return ResponseEntity.ok(nuevaReserva);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReserva(@PathVariable Long id) {
        reservaService.deleteReserva(id);
        return ResponseEntity.noContent().build();
    }

    // Nuevos endpoints agregados
    @PostMapping("/{idReserva}/tarifas/{idTarifa}")
    public ResponseEntity<Reserva> anadirTarifaGeneral(
            @PathVariable Long idReserva,
            @PathVariable Long idTarifa) {
        Reserva reservaActualizada = reservaService.anadirTarifaGeneral(idReserva, idTarifa);
        return ResponseEntity.ok(reservaActualizada);
    }

    @GetMapping("/{idReserva}/tarifa")
    public ResponseEntity<tarifaGeneral> getTarifaEscogida(@PathVariable Long idReserva) {
        tarifaGeneral tarifa = reservaService.getTarifaEscogida(idReserva);
        return ResponseEntity.ok(tarifa);
    }

    @PostMapping("/{idReserva}/aplicar-tarifa")
    public ResponseEntity<Void> aplicarTarifaUsuarios(@PathVariable Long idReserva) {
        reservaService.aplicarTarifaUsuarios(idReserva);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{idReserva}/calcular-total")
    public ResponseEntity<Reserva> calcularTotalReserva(@PathVariable Long idReserva) {
        reservaService.calcularTotalReserva(idReserva);
        Reserva reservaActualizada = reservaService.getReservaById(idReserva);
        return ResponseEntity.ok(reservaActualizada);
    }

    //VOUCHER
    @GetMapping("/comprobante/{idReserva}")
    public ResponseEntity<?> getReservationVoucherById(@PathVariable("idReserva") Long idReserva) {
        try {
            Reserva reserva = reservaService.getReservaById(idReserva);
            File vouche = generatorVoucherService.generarComprobante(reserva);
            InputStreamResource resource = new InputStreamResource(new FileInputStream(vouche));
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + vouche.getName())
                    .contentType(MediaType.APPLICATION_PDF)
                    .contentLength(vouche.length())
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al generar comprobante: " + e.getMessage());
        }
    }
}
