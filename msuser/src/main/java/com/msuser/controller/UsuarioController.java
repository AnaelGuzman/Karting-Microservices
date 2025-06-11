package com.msuser.controller;

import com.msuser.entities.Usuario;
import com.msuser.models.DescuentoFrecuencia;
import com.msuser.services.UsuarioService;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // GET - Obtener todos los usuarios
    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        try {
            List<Usuario> usuarios = usuarioService.getAllUsuarios();
            if (usuarios.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET - Obtener usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        try {
            Optional<Usuario> usuario = usuarioService.getUsuarioById(id);
            return usuario.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET - Obtener usuario por RUT
    @GetMapping("/rut/{rut}")
    public ResponseEntity<Usuario> getUsuarioByRut(@PathVariable String rut) {
        try {
            Optional<Usuario> usuario = usuarioService.getUsuarioByRut(rut);
            return usuario.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // POST - Crear nuevo usuario
    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) {
        try {
            // Validaciones básicas
            if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            if (usuario.getRut() == null || usuario.getRut().trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            Usuario nuevoUsuario = usuarioService.createUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // PUT - Actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuarioDetails) {
        try {
            Usuario usuarioActualizado = usuarioService.updateUsuario(id, usuarioDetails);
            return ResponseEntity.ok(usuarioActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // DELETE - Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        try {
            usuarioService.deleteUsuario(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // POST - Registrar visita del usuario
    @PostMapping("/{id}/visitas")
    public ResponseEntity<Map<String, String>> registrarVisita(@PathVariable Long id) {
        try {
            usuarioService.registrarVisita(id);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Visita registrada correctamente");
            response.put("usuarioId", id.toString());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Usuario no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET - Obtener categoría de cliente frecuente
    @GetMapping("/{id}/categoria-frecuente")
    public ResponseEntity<Map<String, String>> obtenerCategoriaClienteFrecuente(@PathVariable Long id) {
        try {
            String categoria = usuarioService.obtenerCategoriaClienteFrecuente(id);
            Map<String, String> response = new HashMap<>();
            response.put("categoria", categoria);
            response.put("usuarioId", id.toString());
            return ResponseEntity.ok(response);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // POST - Agregar usuario a reserva **PROBAR
    @PostMapping("/{usuarioId}/reserva/{reservaId}")
    public ResponseEntity<Usuario> addReservationParticipant(@PathVariable Long usuarioId, @PathVariable Long reservaId) {
        try {
            Usuario usuario = usuarioService.addReservationParticipant(usuarioId, reservaId);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // DELETE - Remover usuario de reserva **PROBAR
    @DeleteMapping("/{usuarioId}/reserva")
    public ResponseEntity<Usuario> removeReservationParticipant(@PathVariable Long usuarioId) {
        try {
            Usuario usuario = usuarioService.removeReservationParticipant(usuarioId);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET - Obtener usuarios por reserva ***PROBAR
    @GetMapping("/reserva/{reservaId}")
    public ResponseEntity<List<Usuario>> getAllUserByReservation(@PathVariable Long reservaId) {
        try {
            List<Usuario> usuarios = usuarioService.getAllUserByReservation(reservaId);
            if (usuarios.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // POST - Aplicar descuento grupal
    @PostMapping("/{usuarioId}/descuento-grupal/{cantidadParticipante}")
    public ResponseEntity<Usuario> aplicarDescuentoGrupal(@PathVariable Long usuarioId, @PathVariable Integer cantidadParticipante) {
        try {
            Usuario usuario = usuarioService.anadirDescuentoGrupal(usuarioId, cantidadParticipante);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            throw new NotFoundException(e.getMessage());
        }
    }
    @GetMapping("/mostrar/usuario/{idUsuario}")
    public ResponseEntity<DescuentoFrecuencia> mostrarDescuentoFrecuenciaByUsuario(@PathVariable Long idUsuario) throws Exception {
        DescuentoFrecuencia descuento = usuarioService.mostrarDescuentoFrecuenciaByUsuario(idUsuario);
        return ResponseEntity.ok(descuento);
    }

    // POST - Aplicar descuento por frecuencia
    @PostMapping("/{usuarioId}/descuento-frecuencia")
    public ResponseEntity<Usuario> aplicarDescuentoFrecuencia(@PathVariable Long usuarioId) {
            Usuario usuario = usuarioService.anadirDescuentoFrecuencia(usuarioId);
            return ResponseEntity.ok(usuario);
    }
}