package com.msfee.services;


import com.msfee.entities.Tarifa;
import com.msfee.repository.TarifaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TarifaService {
    @Autowired
    private TarifaRepository tarifaRepository;

    public List<Tarifa> getAllTarifas() {
        return tarifaRepository.findAll();
    }

    public Optional<Tarifa> getTarifaById(Long id) {
        return tarifaRepository.findById(id);
    }

    public Tarifa createTarifa(Tarifa tarifa) {
        return tarifaRepository.save(tarifa);
    }

    public Tarifa updateTarifa(Long id, Tarifa tarifaDetails) {
        Tarifa tarifa = tarifaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarifa no encontrada con id: " + id));

        tarifa.setDescripcion(tarifaDetails.getDescripcion());
        tarifa.setVueltas(tarifaDetails.getVueltas());
        tarifa.setMinutosMaximos(tarifaDetails.getMinutosMaximos());
        tarifa.setPrecio(tarifaDetails.getPrecio());
        tarifa.setDuracionTotal(tarifaDetails.getDuracionTotal());

        return tarifaRepository.save(tarifa);
    }

    public void deleteTarifa(Long id) {
        Tarifa tarifa = tarifaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarifa no encontrada con id: " + id));

        tarifaRepository.delete(tarifa);
    }

    //Método para inicializar tarifas por defecto
    public void inicializarTarifas() {
        if (tarifaRepository.count() == 0) {
            Tarifa tarifa1 = new Tarifa("10 vueltas o máx 10 min", 10, 10, 15000.0, 30);
            Tarifa tarifa2 = new Tarifa("15 vueltas o máx 15 min", 15, 15, 20000.0, 35);
            Tarifa tarifa3 = new Tarifa("20 vueltas o máx 20 min", 20, 20, 25000.0, 40);

            tarifaRepository.save(tarifa1);
            tarifaRepository.save(tarifa2);
            tarifaRepository.save(tarifa3);
        }
    }
}