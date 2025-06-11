package com.mskart.services;

import com.mskart.entities.KartEntity;
import com.mskart.repository.KartRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class KartServices {
    @Autowired
    KartRepository kartRepository;

    //consultar los carros segun filtros
    public List<KartEntity> getAllKarts() {
        return kartRepository.findAll();
    }

    public KartEntity getKartById(Long id) {return kartRepository.findById(id).get();}

    public List<KartEntity> getAllKartsByStatus(String status) {
        if (status == null || status.isEmpty()) {
            throw new IllegalArgumentException("El status no puede estar vacío");
        }
        return kartRepository.findKartEntityByStatus(status);
    }
    //guardar karts
    public KartEntity saveKart(KartEntity kart) {
        return kartRepository.save(kart);
    }

    //actualizar un kart
    public KartEntity updateKart(KartEntity kart) {
        return kartRepository.save(kart);
    }

    //delete
    public Boolean deleteKartById(Long id) throws Exception {
        try {
            kartRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
