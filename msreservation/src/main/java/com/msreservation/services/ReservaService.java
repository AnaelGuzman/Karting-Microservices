package com.msreservation.services;

import com.msreservation.entities.Reserva;
import com.msreservation.models.DescuentoGrupo;
import com.msreservation.models.Usuario;
import com.msreservation.models.tarifaGeneral;
import com.msreservation.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservaService {
    @Autowired
    private ReservaRepository reservaRepository;
    @Autowired
    private RestTemplate restTemplate;

    public List<Reserva> getAllReservas() {
        return reservaRepository.findAll();
    }
    public Reserva getReservaById(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con id: " + id));
    }
    public Reserva createReserva(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    public void deleteReserva(Long id) {
        Reserva reserva = getReservaById(id);
        reservaRepository.delete(reserva);
    }

    public Reserva anadirTarifaGeneral(Long idReserva, Long idTarifa){
        Reserva reserva = getReservaById(idReserva);
        tarifaGeneral tarifaGeneral = restTemplate.getForObject("http://msfee/api/v1/fees/" + idTarifa, tarifaGeneral.class);
        reserva.setIdFee(idTarifa);
        reserva.setDecripcionFee(tarifaGeneral.getDescripcion());
        return reservaRepository.save(reserva);
    }
    public tarifaGeneral getTarifaEscogida(Long idReserva){
        Reserva reserva = getReservaById(idReserva);
        tarifaGeneral tarifa = restTemplate.getForObject("http://msfee/api/v1/fees/" + reserva.getIdFee(), tarifaGeneral.class);
        return tarifa;
    }
    public void aplicarTarifaUsuarios(Long idReserva){
        Usuario[] usuarios = restTemplate.getForObject("http://msuser/api/v1/usuarios/reserva/"+idReserva , Usuario[].class);
        tarifaGeneral tarifa = getTarifaEscogida(idReserva);
        for(Usuario usuario : usuarios){
            usuario.setTarifaPersonal(tarifa.getPrecio());
        }
    }
    //calcular el total de la reserva , ideal cuando todos los usuarios tengan su tarifa con descuento calculado
    public void calcularTotalReserva(Long idReserva){
        Double total = 0.0;
        Reserva reserva = reservaRepository.findById(idReserva).get();
        Usuario[] usuarios = restTemplate.getForObject("http://msuser/api/v1/usuarios/reserva/"+idReserva , Usuario[].class);
        // APROVECHAR SACAR INTERVALO DE PARTICIPANTE
        Integer participantes = 0;
        for(Usuario usuario : usuarios){
            total = total + usuario.getTarifaPersonal();
            participantes++;
        }
        DescuentoGrupo descuentoGrupo = restTemplate.getForObject("http://mspeoplediscount/api/v1/peoples/por-personas/"+ participantes , DescuentoGrupo.class);
        String intervalo = descuentoGrupo.getMinPersonas().toString() + "-" + descuentoGrupo.getMaxPersonas().toString()+ " Personas";
        reserva.setIntervaloParticipantes(intervalo);
        for(Usuario usuario : usuarios){
            total = total + usuario.getTarifaPersonal();
        }
        reserva.setParticiapantes(participantes);
        Double impuesto = total* 0.19;
        reserva.setImpuesto(impuesto);
        reserva.setPrecioTotal(total+impuesto);
    }
}
