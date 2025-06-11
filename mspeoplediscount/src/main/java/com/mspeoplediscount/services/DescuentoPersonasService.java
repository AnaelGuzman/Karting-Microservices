package com.mspeoplediscount.services;

import com.mspeoplediscount.entities.DescuentoPersonas;
import com.mspeoplediscount.repository.DescuentoPersonasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DescuentoPersonasService {
    @Autowired
    private DescuentoPersonasRepository descuentoPersonasRepository;

    public List<DescuentoPersonas> getAllDescuentos() {
        return descuentoPersonasRepository.findAll();
    }

    public DescuentoPersonas getDescuentoById(Long id) {
        return descuentoPersonasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Descuento no encontrado con id: " + id));
    }

    public DescuentoPersonas createDescuento(DescuentoPersonas descuento) {
        return descuentoPersonasRepository.save(descuento);
    }
    public DescuentoPersonas updateDescuento(Long id, DescuentoPersonas descuentoDetails) {
        DescuentoPersonas descuento = getDescuentoById(id);

        descuento.setMinPersonas(descuentoDetails.getMinPersonas());
        descuento.setMaxPersonas(descuentoDetails.getMaxPersonas());
        descuento.setPorcentajeDescuento(descuentoDetails.getPorcentajeDescuento());

        return descuentoPersonasRepository.save(descuento);
    }
    public void deleteDescuento(Long id) {
        DescuentoPersonas descuento = getDescuentoById(id);
        descuentoPersonasRepository.delete(descuento);
    }
    public DescuentoPersonas getDescuentoByNumPersonas(Integer numPersonas) {
        return descuentoPersonasRepository.findByMinPersonasLessThanEqualAndMaxPersonasGreaterThanEqual(numPersonas, numPersonas);
    }
    // Método para inicializar descuentos por defecto
    public void inicializarDescuentos() {
        if (descuentoPersonasRepository.count() == 0) {
            DescuentoPersonas desc1 = new DescuentoPersonas(1, 2, 0.0);
            DescuentoPersonas desc2 = new DescuentoPersonas(3, 5, 10.0);
            DescuentoPersonas desc3 = new DescuentoPersonas(6, 10, 20.0);
            DescuentoPersonas desc4 = new DescuentoPersonas(11, 15, 30.0);

            descuentoPersonasRepository.save(desc1);
            descuentoPersonasRepository.save(desc2);
            descuentoPersonasRepository.save(desc3);
            descuentoPersonasRepository.save(desc4);
        }
    }
}