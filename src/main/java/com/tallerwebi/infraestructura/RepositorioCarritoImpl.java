package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Carrito;
import com.tallerwebi.dominio.RepositorioCarrito;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.enums.EstadoCarrito;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioCarritoImpl implements RepositorioCarrito {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioCarritoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(Carrito carrito) {

        this.sessionFactory.getCurrentSession().saveOrUpdate(carrito);
    }

    @Override
    public Carrito obtenerPorId(Long id) {
        return this.sessionFactory.getCurrentSession()
                .createQuery("FROM Carrito c LEFT JOIN FETCH c.items WHERE c.id = :id", Carrito.class)
                .setParameter("id", id)
                .uniqueResult();
    }

    @Override
    public Carrito obtenerCarritoActivoPorUsuario(Usuario usuario) {
        Long usuarioId = usuario.getId();
        return this.sessionFactory.getCurrentSession()
                .createQuery("FROM Carrito c LEFT JOIN FETCH c.items WHERE c.usuario = :usuarioId AND c.estado = :estado", Carrito.class)
                .setParameter("usuarioId", usuarioId)
                .setParameter("estado", EstadoCarrito.ACTIVO)
                .uniqueResult();
    }

    @Override
    public Carrito crearCarritoParaUsuario(Usuario usuario) {
        Carrito carrito = new Carrito(usuario);
        guardar(carrito);
        return carrito;
    }

    @Override
    public void eliminar(Carrito carrito) {
        this.sessionFactory.getCurrentSession().delete(carrito);
    }

    @Override
    public void actualizarEstado(Long carritoId, EstadoCarrito estado) {
        this.sessionFactory.getCurrentSession()
                .createQuery("UPDATE Carrito SET estado = :estado WHERE id = :id")
                .setParameter("estado", estado)
                .setParameter("id", carritoId)
                .executeUpdate();
    }
}