package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidades.Artista;
import com.tallerwebi.dominio.entidades.Obra;
import com.tallerwebi.dominio.ServicioBusqueda;
import com.tallerwebi.dominio.excepcion.NoSeEncontraronResultadosException;
import com.tallerwebi.presentacion.dto.ObraDto;
import com.tallerwebi.presentacion.dto.PerfilArtistaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class ControladorBusqueda {
    private ServicioBusqueda servicioBusqueda;

    @Autowired
    public ControladorBusqueda(ServicioBusqueda servicioBusqueda) {
        this.servicioBusqueda = servicioBusqueda;
    }

    @GetMapping("/busqueda")
    public ResponseEntity<Map<String, Object>> buscar(@RequestParam("query") String query) {
        Map<String, Object> resultado = new HashMap<>();

        try {
            List<Obra> obras = servicioBusqueda.buscarObrasPorString(query);
            List<ObraDto> obrasDto = new ArrayList<>();
            for (Obra obra : obras) {
                obrasDto.add(new ObraDto(obra));
            }
            resultado.put("obras", obrasDto);
        } catch (NoSeEncontraronResultadosException e) {
            resultado.put("obras", Collections.emptyList());
        }

        try {
            List<Artista> artistas = servicioBusqueda.buscarArtistaPorNombre(query);
            List<PerfilArtistaDTO> perfilArtistasDto = new ArrayList<>();
            for (Artista artista : artistas) {
                perfilArtistasDto.add(new PerfilArtistaDTO(artista));
            }
            resultado.put("artistas", perfilArtistasDto);
        } catch (NoSeEncontraronResultadosException e) {
            resultado.put("artistas", Collections.emptyList());
        }

        // Si no se encontró nada en ninguna categoría
        if (((List<?>) resultado.get("obras")).isEmpty() && ((List<?>) resultado.get("artistas")).isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(resultado);
    }
}
