package com.tallerwebi.dominio;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import com.tallerwebi.dominio.entidades.Carrito;
import com.tallerwebi.dominio.entidades.ItemCarrito;
import com.tallerwebi.dominio.entidades.Obra;
import com.tallerwebi.dominio.entidades.Usuario;
import org.junit.jupiter.api.Test;

public class CarritoTest {

    @Test
    public void queAlAgregarUnItemSeAgregueCorrectamente() {
        Usuario usuario = new Usuario();
        Carrito carrito = new Carrito(usuario);
        Obra obra = new Obra();

        carrito.agregarItem(obra);

        assertThat(carrito.getItems().size(), is(equalTo(1)));
        ItemCarrito item = carrito.getItems().get(0);
        assertThat(item.getObra(), is(equalTo(obra)));
    }

    @Test
    public void queAlQuitarUnItemSeElimineCorrectamente() {
        Usuario usuario = new Usuario();
        Carrito carrito = new Carrito(usuario);
        Obra obra1 = new Obra();
        obra1.setId(1L);
        Obra obra2 = new Obra();
        obra2.setId(2L);

        carrito.agregarItem(obra1);
        carrito.agregarItem(obra2);

        carrito.disminuirCantidadDeItem(obra1);

        assertThat(carrito.getItems().size(), is(equalTo(1)));
        ItemCarrito item = carrito.getItems().get(0);
        assertThat(item.getObra(), is(equalTo(obra2)));
    }

    @Test
    public void queAlAgregarUnItemQueYaExisteSeActualiceLaCantidadDelItem() {
        Usuario usuario = new Usuario();
        Carrito carrito = new Carrito(usuario);
        Obra obra1 = new Obra();
        obra1.setId(1L);

        carrito.agregarItem(obra1);
        carrito.agregarItem(obra1);

        assertThat(carrito.getItems().size(), is(equalTo(1)));
        ItemCarrito item = carrito.getItems().get(0);
        assertThat(item.getObra(), is(equalTo(obra1)));
    }

    @Test
    public void queAlLimpiarElCarritoQuedeVacio() {
        Usuario usuario = new Usuario();
        Carrito carrito = new Carrito(usuario);
        Obra obra1 = new Obra();
        obra1.setId(1L);
        Obra obra2 = new Obra();
        obra2.setId(2L);
        
        carrito.agregarItem(obra1);
        carrito.agregarItem(obra2);

        carrito.limpiarCarrito();

        assertThat(carrito.getItems().size(), is(equalTo(0)));
    }

    @Test
    public void queAlAgregarItemsSeActualiceCorrectamenteElValorTotal() {
        Usuario usuario = new Usuario();
        Carrito carrito = new Carrito(usuario);
        Obra obra1 = new Obra();
        obra1.setId(1L);
        obra1.setPrecio(100.0);
        Obra obra2 = new Obra();
        obra2.setId(2L);
        obra2.setPrecio(3000.0);  
        
        carrito.agregarItem(obra1);
        carrito.agregarItem(obra1);
        carrito.agregarItem(obra2);

        assertThat(carrito.getItems().size(), is(equalTo(2)));
        assertThat(carrito.getTotal(), is(equalTo(3200.0)));
    }
    
@Test
    public void queAlAgregarItemsSeActualiceCorrectamenteLaCantidadTotalDeItems() {
        Usuario usuario = new Usuario();
        Carrito carrito = new Carrito(usuario);
        Obra obra1 = new Obra();
        obra1.setId(1L);
        Obra obra2 = new Obra();
        obra2.setId(2L);
        
        carrito.agregarItem(obra1);
        carrito.agregarItem(obra1);
        carrito.agregarItem(obra2);

        assertThat(carrito.getItems().size(), is(equalTo(2)));
        assertThat(carrito.getCantidadTotalItems(), is(equalTo(3)));
    }

}
