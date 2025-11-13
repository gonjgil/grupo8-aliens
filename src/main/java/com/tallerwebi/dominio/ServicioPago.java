package com.tallerwebi.dominio;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import com.tallerwebi.dominio.entidades.Carrito;
import com.tallerwebi.dominio.entidades.Pago;
import com.tallerwebi.presentacion.dto.PagoDto;
import com.tallerwebi.dominio.entidades.Usuario;

public interface ServicioPago {
    Pago consultarEstadoDePago(Long pagoid);
    Preference crearPreferenciaDePago(Carrito carrito) throws MPException, MPApiException;
}
