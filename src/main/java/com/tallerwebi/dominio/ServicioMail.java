package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.CompraHecha;
import com.tallerwebi.dominio.entidades.ItemCompra;
import com.tallerwebi.dominio.entidades.Usuario;

import java.util.List;

public interface ServicioMail {
    void enviarMail(String para, String asunto, String cuerpo);
    void enviarMailConfirmacionCompra(Usuario usuario, CompraHecha compra, List<ItemCompra> items);

}
