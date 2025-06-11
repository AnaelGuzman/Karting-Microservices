package com.msuser.services;

import com.msuser.entities.Usuario;
import com.msuser.models.DescuentoFrecuencia;
import com.msuser.models.DescuentoGrupal;
import com.msuser.models.Reserva;
import com.msuser.models.Tarifa;
import com.msuser.repository.UsuarioRepository;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RestTemplate restTemplate;

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> getUsuarioById(Long id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario>getUsuarioByRut(String rut){
        return usuarioRepository.findByRut(rut);
    }

    public Usuario createUsuario(Usuario usuario) {
        usuario.setFrecuenciaReserva(0);
        usuario.setTarifaPersonal(0.0);
        return usuarioRepository.save(usuario);
    }

    public Usuario updateUsuario(Long id, Usuario usuarioDetails) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        usuario.setNombre(usuarioDetails.getNombre());
        usuario.setApellido(usuarioDetails.getApellido());
        usuario.setEmail(usuarioDetails.getEmail());
        usuario.setTelefono(usuarioDetails.getTelefono());
        usuario.setFechaNacimiento(usuarioDetails.getFechaNacimiento());

        return usuarioRepository.save(usuario);
    }

    public void deleteUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        usuarioRepository.delete(usuario);
    }

    private boolean esMismoMes(LocalDate fecha1, LocalDate fecha2) {
        return fecha1.getYear() == fecha2.getYear() &&
                fecha1.getMonth() == fecha2.getMonth();
    }

    public void registrarVisita(Long usuarioId) {
        Usuario usuario = getUsuarioById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + usuarioId));

        // Resetear contador si es un nuevo mes
        LocalDate ahora = LocalDate.now();
        if (usuario.getFechaUltimaReserva() == null ||
                !esMismoMes(usuario.getFechaUltimaReserva(), ahora)) {
            usuario.setFrecuenciaReserva(1);
        } else {
            usuario.setFrecuenciaReserva(usuario.getFrecuenciaReserva() + 1);
        }

        usuario.setFechaUltimaReserva(ahora);
        usuarioRepository.save(usuario);
    }

    public String obtenerCategoriaClienteFrecuente(Long usuarioId) {
        Usuario usuario = getUsuarioById(usuarioId)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        int visitas = usuario.getFrecuenciaReserva();

        if (visitas >= 7) return "Muy frecuente";
        if (visitas >= 5) return "Frecuente";
        if (visitas >= 2) return "Regular";
        return "No frecuente";
    }

    //AGREGAR O ELIMINAR USUARIO A LA RESERVA
    public Usuario addReservationParticipant(Long usuarioId, Long reservaId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + usuarioId));
        registrarVisita(usuarioId);
        usuario.setReservationId(reservaId);
        return usuarioRepository.save(usuario);
    }

    public Usuario removeReservationParticipant(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + usuarioId));
        usuario.setReservationId(null);
        return usuarioRepository.save(usuario);
    }
    //TRAER USUARIOS QUE SON DE 1 RESERVA
    public List<Usuario> getAllUserByReservation(Long reservaId) {
        return usuarioRepository.findUserByReservationId(reservaId);
    }

    //SEGMENTO PARA ASOCIAR 1 DESCUENTO AL PARTICIPANTE

    //Seccion aplicar Descuento
    public Usuario anadirDescuentoGrupal(Long idUsuario, Integer cantidadParticipantes) {
        DescuentoGrupal descuentoGrupal = restTemplate.getForObject("http://mspeoplediscount/api/v1/peoples/por-personas/" + cantidadParticipantes, DescuentoGrupal.class);
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + idUsuario));
        Reserva reserva = restTemplate.getForObject("http://msreservation/api/v1/reservas/" + usuario.getReservationId(), Reserva.class);
        Tarifa tarifa = restTemplate.getForObject("http://msfee/api/v1/fees/" + reserva.getIdFee(), Tarifa.class);
        usuario.setDescuentoId(descuentoGrupal.getId());
        usuario.setNombreDescuento("DESCUENTO GRUPAL");
        usuario.setTarifaPersonal(tarifa.getPrecio() - tarifa.getPrecio()*descuentoGrupal.getPorcentajeDescuento()/100);
        return usuarioRepository.save(usuario);
    }
    public Usuario anadirDescuentoFrecuencia(Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + idUsuario));
        DescuentoFrecuencia descuentoFrecuencia = restTemplate.getForObject("http://msfrecuencydiscount/api/v1/descuentos-frecuentes/usuario/" + idUsuario, DescuentoFrecuencia.class);
        Reserva reserva = restTemplate.getForObject("http://msreservation/api/v1/reservas/" + usuario.getReservationId(), Reserva.class);
        Tarifa tarifa = restTemplate.getForObject("http://msfee/api/v1/fees/" + reserva.getIdFee(), Tarifa.class);
        usuario.setDescuentoId(descuentoFrecuencia.getId());
        usuario.setNombreDescuento("DESCUENTO FRECUENCIA");
        usuario.setTarifaPersonal(tarifa.getPrecio() - tarifa.getPrecio()*descuentoFrecuencia.getPorcentajeDescuento()/100);
        return usuarioRepository.save(usuario);
    }

    //Seccion Mostrar los descuentos
    public DescuentoFrecuencia mostrarDescuentoFrecuenciaByUsuario(Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + idUsuario));
        if (usuario.getDescuentoId() == null) {
            DescuentoFrecuencia descuentoFrecuencia= restTemplate.getForObject("http://msfrecuencydiscount/api/v1/descuentos-frecuentes/usuario/" + idUsuario, DescuentoFrecuencia.class);
            return descuentoFrecuencia;
        } else {
            DescuentoFrecuencia descuentoFrecuencia = restTemplate.getForObject("http://msfrecuencydiscount/api/v1/descuentos-frecuentes/" + usuario.getDescuentoId(), DescuentoFrecuencia.class);
            return descuentoFrecuencia;
        }
    }
}
