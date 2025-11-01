package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidades.Obra;
import com.tallerwebi.dominio.ServicioGaleria;
import com.tallerwebi.dominio.ServicioLike;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.excepcion.NoExisteLaObra;
import com.tallerwebi.presentacion.dto.ObraDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class ControladorLikes {

    private final ServicioGaleria servicioGaleria;
    private final ServicioLike servicioLike;

    public ControladorLikes(ServicioGaleria servicioGaleria, ServicioLike servicioLike) {
        this.servicioGaleria = servicioGaleria;
        this.servicioLike = servicioLike;
    }

    @PostMapping("obra/{id}/like")
    public ResponseEntity<?> toggleLike(@PathVariable Long id, HttpServletRequest request) {
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogueado");

        if (usuario == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Debe estar logueado para dar o quitar like."));
        }

        try {
            boolean liked = servicioLike.toggleLike(usuario, id);
            Obra obra = servicioGaleria.obtenerPorId(id);
            ObraDto obraDto = new ObraDto(obra);

            return ResponseEntity.ok(Map.of(
                    "liked", liked,
                    "obraId", id,
                    "likesCount", obra.getCantidadLikes()
            ));
        } catch (NoExisteLaObra e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "No existe la obra solicitada."));
        }
    }
}
