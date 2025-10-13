package com.tallerwebi.dominio;

import java.util.List;

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
    public void toggleLike(Usuario usuario, Long obraId) {
        Obra obra = this.repositorioObra.obtenerPorId(obraId);
        if (usuario != null && obra != null) {
            if (obra.getUsuariosQueDieronLike().contains(usuario)) {
                obra.quitarLike(usuario);
            } else {
                obra.darLike(usuario);
            }
            repositorioObra.guardar(obra);
        }
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