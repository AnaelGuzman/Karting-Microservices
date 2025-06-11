package com.msrack.services;

import com.msrack.entities.Rack;
import com.msrack.model.Reserva;
import com.msrack.repository.RackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Service
public class RackService {
    @Autowired
    RackRepository rackRepository;
    @Autowired
    RestTemplate restTemplate;

    public List<Rack> getAllEvents(){
        return rackRepository.findAll();
    }
    public Rack getEventById(Long id){
        return rackRepository.findById(id).get();
    }
    public Rack createEvent(Rack event){
        return rackRepository.save(event);
    }
    public Rack updateEvent(Rack event){
        return rackRepository.save(event);
    }
    public void deleteEvent(Long id){
        rackRepository.deleteById(id);
    }
    //Colocar todas las reservas en el rack
    public Rack crearEventosByIdReservation(Long idReservacion){
        Reserva reserva = restTemplate.getForObject("http://msreservation/api/v1/reservas/" + idReservacion, Reserva.class);
        Rack evento = new Rack();
        evento.setFechaEvento(reserva.getFechaReserva());
        evento.setHoraInicio(reserva.getHoraInicio());
        evento.setHoraFin(reserva.getHoraFin());
        evento.setIdReserva(reserva.getId());
        evento.setMensaje("Reserva Asigada");
        return rackRepository.save(evento);
    }
}
