package com.tallerwebi.dominio.servicioImpl;

import java.util.List;

import com.tallerwebi.dominio.repositorios.RepositorioObra;
import com.tallerwebi.dominio.ServicioLike;
import com.tallerwebi.dominio.entidades.Obra;
import com.tallerwebi.dominio.entidades.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("servicioLike")
@Transactional
public class ServicioLikeImpl implements ServicioLike {

    private final RepositorioObra repositorioObra;

    @Autowired
    public ServicioLikeImpl(RepositorioObra repositorioObra) {
        this.repositorioObra = repositorioObra;
    }

    @Override
    public boolean toggleLike(Usuario usuario, Long obraId) {
        boolean resultado = false;
        Obra obra = this.repositorioObra.obtenerPorId(obraId);
        if (usuario != null && obra != null) {
            if (obra.getUsuariosQueDieronLike().contains(usuario)) {
                obra.quitarLike(usuario);
                resultado = false;
            } else {
                obra.darLike(usuario);
                resultado = true;
            }
            repositorioObra.guardar(obra);
        }
        return resultado;
    }

    @Override
    public Integer contarLikes(Long obraId) {
        return 0;
    }

    @Override
    public List<Obra> obtenerObrasLikeadasPorUsuario(Usuario usuario) {
        return null;
    }
}