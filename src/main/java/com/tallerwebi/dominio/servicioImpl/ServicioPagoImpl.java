package com.tallerwebi.dominio.servicioImpl;

import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
import com.tallerwebi.config.MercadoPagoConfig;
import com.tallerwebi.dominio.ServicioPago;
import com.tallerwebi.dominio.entidades.Carrito;
import com.tallerwebi.dominio.entidades.ItemCarrito;
import com.tallerwebi.dominio.entidades.Pago;
import com.tallerwebi.dominio.enums.EstadoPago;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ServicioPagoImpl implements ServicioPago {

    private PaymentClient paymentClient;

    @Autowired
    public ServicioPagoImpl(PaymentClient paymentClient) {
        this.paymentClient = paymentClient;
    }

    @Override
    public Pago consultarEstadoDePago(Long pagoId) {
//        EstadoPago estadoPago = null;
        try {
            Payment pagoRealizado = paymentClient.get(pagoId);

            if ("approved".equals(pagoRealizado.getStatus())) {
      //          estadoPago = EstadoPago.APROBADO;
                return new Pago(true, pagoRealizado.getId(), EstadoPago.APROBADO,pagoRealizado.getPaymentMethodId() );
//                        pagoRealizado.getDateApproved().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
            } else {
                return new Pago(false, pagoRealizado.getId(), EstadoPago.RECHAZADO, pagoRealizado.getPaymentMethodId());
            }

        } catch (MPApiException | MPException e) {
            return new Pago(false, null, EstadoPago.ERROR, null);        }
    }

    @Override
    public Preference crearPreferenciaDePago(Carrito carrito) throws MPException, MPApiException {
        PreferenceClient client = new PreferenceClient();

        List<PreferenceItemRequest> items = new ArrayList<>();
        for (ItemCarrito itemCarrito : carrito.getItems()) {
            PreferenceItemRequest item = PreferenceItemRequest.builder()
                    .title(itemCarrito.getObra().getTitulo())
                    .quantity(itemCarrito.getCantidad())
                    .unitPrice(BigDecimal.valueOf(itemCarrito.getPrecioUnitario()))
                    .currencyId("ARS")
                    .build();
            items.add(item);
        }

        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                .success(MercadoPagoConfig.getSuccessUrl())
                .failure(MercadoPagoConfig.getFailureUrl())
                .pending(MercadoPagoConfig.getPendingUrl())
                .build();

        PreferenceRequest request = PreferenceRequest.builder()
                .items(items)
                .backUrls(backUrls)
                .autoReturn("approved")
                .metadata(Map.of(
                        "usuarioId", carrito.getUsuario().getId(),
                        "carritoId", carrito.getId()
                ))
                .build();

        return client.create(request);
    }

}


