package com.msfrecuencydiscount.services;

import com.msfrecuencydiscount.entities.DescuentoFrecuente;
import com.msfrecuencydiscount.models.DescuentoResponse;
import com.msfrecuencydiscount.models.Usuario;
import com.msfrecuencydiscount.repository.DescuentoFrecuenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class DescuentoFrecuenteService {

    @Autowired
    private DescuentoFrecuenteRepository descuentoFrecuenteRepository;
    @Autowired
    private  RestTemplate restTemplate;

    public List<DescuentoFrecuente> getAllDiscountFrecuente() {
        return descuentoFrecuenteRepository.findAll();
    }
    public DescuentoFrecuente getDiscountFrecuenteById(Long id) {
        return descuentoFrecuenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Descuento no encontrado con id: " + id));
    }
    public void deleteAllDiscountFrecuente(){
        descuentoFrecuenteRepository.deleteAll();
    }

    // Método corregido para obtener categoría del usuario
    private String obtenerCategoriaCliente(Long usuarioId) {
        try {
            // URL corregida según el controller
            String url = "http://msuser/api/v1/usuarios/" + usuarioId + "/categoria-frecuente";

            // Manejo correcto de la respuesta Map
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, String> responseBody = response.getBody();
                return responseBody.get("categoria");
            }
            return null;

        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("Usuario no encontrado con id: " + usuarioId);
        } catch (RestClientException e) {
            throw new RuntimeException("Error al comunicarse con el microservicio de usuarios", e);
        } catch (Exception e) {
            throw new RuntimeException("Error inesperado al obtener categoría de cliente", e);
        }
    }
    public DescuentoFrecuente getDescuentoByCategogy(Long usuarioId) {
        if (usuarioId == null) {
            throw new IllegalArgumentException("El ID de usuario no puede ser nulo");
        }

        String categoriaUsuario = obtenerCategoriaCliente(usuarioId);
        if (categoriaUsuario == null) {
            return null;
        }

        DescuentoFrecuente descuento = descuentoFrecuenteRepository.findByCategoria(categoriaUsuario);
        if (descuento == null) {
            throw new RuntimeException("No se encontró descuento para la categoría: " + categoriaUsuario);
        }

        return descuento;
    }

    public Usuario anadirDescuentoFrecuencia(Long idUsuario) {
        Usuario usuario = restTemplate.getForObject("http://msuser/api/v1/usuarios/" + idUsuario, Usuario.class);
        DescuentoFrecuente descuentoFrecuencia = getDescuentoByCategogy(idUsuario);
        usuario.setDescuentoId(descuentoFrecuencia.getId());
        usuario.setNombreDescuento("DESCUENTO FRECUENCIA");
        usuario.setTarifaPersonal(usuario.getTarifaPersonal() - usuario.getTarifaPersonal()*descuentoFrecuencia.getPorcentajeDescuento());
        return usuario;
    }

    // Método para inicializar descuentos por defecto
    public void inicializarDescuentos() {
        deleteAllDiscountFrecuente();
        if (descuentoFrecuenteRepository.count() == 0) {
            DescuentoFrecuente desc1 = new DescuentoFrecuente("Muy frecuente", 7, Integer.MAX_VALUE, 30.0);
            DescuentoFrecuente desc2 = new DescuentoFrecuente("Frecuente", 5, 6, 20.0);
            DescuentoFrecuente desc3 = new DescuentoFrecuente("Regular", 2, 4, 10.0);
            DescuentoFrecuente desc4 = new DescuentoFrecuente("No frecuente", 0, 1, 0.0);

            descuentoFrecuenteRepository.save(desc1);
            descuentoFrecuenteRepository.save(desc2);
            descuentoFrecuenteRepository.save(desc3);
            descuentoFrecuenteRepository.save(desc4);
        }
    }
}