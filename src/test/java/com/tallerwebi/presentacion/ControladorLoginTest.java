package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.presentacion.dto.DatosLogin;
import com.tallerwebi.presentacion.dto.UsuarioDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


public class ControladorLoginTest {

	private ControladorLogin controladorLogin;
	private Usuario usuarioMock;
	private UsuarioDto usuarioDtoMock;
	private DatosLogin datosLoginMock;
	private HttpServletRequest requestMock;
	private HttpSession sessionMock;
	private ServicioLogin servicioLoginMock;

	@BeforeEach
	public void init() {
		datosLoginMock = new DatosLogin("dami@unlam.com", "123");

		usuarioMock = mock(Usuario.class);
		when(usuarioMock.getEmail()).thenReturn("dami@unlam.com");

		usuarioDtoMock = mock(UsuarioDto.class);
		when(usuarioDtoMock.toUsuario()).thenReturn(usuarioMock);

		requestMock = mock(HttpServletRequest.class);
		sessionMock = mock(HttpSession.class);
		servicioLoginMock = mock(ServicioLogin.class);

		controladorLogin = new ControladorLogin(servicioLoginMock);
	}

	@Test
	public void loginConUsuarioYPasswordInorrectosDeberiaLlevarALoginNuevamente() {
		// preparacion
		when(servicioLoginMock.consultarUsuario(anyString(), anyString())).thenReturn(null);

		// ejecucion
		ModelAndView modelAndView = controladorLogin.validarLogin(datosLoginMock, requestMock);

		// validacion
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("login"));
		assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("Usuario o clave incorrecta"));
		verify(sessionMock, times(0)).setAttribute("ROL", "ADMIN");
	}

	@Test
	public void loginConUsuarioYPasswordCorrectosDeberiaLLevarAHome() {
		// preparacion
		Usuario usuarioEncontradoMock = mock(Usuario.class);
		when(usuarioEncontradoMock.getRol()).thenReturn("ADMIN");

		when(requestMock.getSession()).thenReturn(sessionMock);
		when(servicioLoginMock.consultarUsuario(anyString(), anyString())).thenReturn(usuarioEncontradoMock);

		// ejecucion
		ModelAndView modelAndView = controladorLogin.validarLogin(datosLoginMock, requestMock);

		// validacion
		// CAMBIO TEMPORARIO HASTA CONFIRMAR QUE PAGINA USAREMOS DE INICIO
		// assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/home"));
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/galeria"));
		verify(sessionMock, times(1)).setAttribute("ROL", usuarioEncontradoMock.getRol());
	}

	@Test
	public void registrameSiUsuarioNoExisteDeberiaCrearUsuarioYVolverAlLogin() throws UsuarioExistente {

		// ejecucion
		ModelAndView modelAndView = controladorLogin.registrarme(usuarioDtoMock);

		// validacion
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login"));
		verify(servicioLoginMock, times(1)).registrar(usuarioMock);
	}

	@Test
	public void registrarmeSiUsuarioExisteDeberiaVolverAFormularioYMostrarError() throws UsuarioExistente {
		// preparacion
		doThrow(UsuarioExistente.class).when(servicioLoginMock).registrar(usuarioMock);

		// ejecucion
		ModelAndView modelAndView = controladorLogin.registrarme(usuarioDtoMock);

		// validacion
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("nuevo-usuario"));
		assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("El usuario ya existe"));
	}

	@Test
	public void errorEnRegistrarmeDeberiaVolverAFormularioYMostrarError() throws UsuarioExistente {
		// preparacion
		doThrow(RuntimeException.class).when(servicioLoginMock).registrar(usuarioMock);

		// ejecucion
		ModelAndView modelAndView = controladorLogin.registrarme(usuarioDtoMock);

		// validacion
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("nuevo-usuario"));
		assertThat(modelAndView.getModel().get("error").toString(),
				equalToIgnoringCase("Error al registrar el nuevo usuario"));
	}

	@Test
	public void queAlCerrarSesionSeRedirijaAGaleriaYSeElimineLaSesion() {
		// preparacion
		when(requestMock.getSession()).thenReturn(sessionMock);

		// ejecucion
		ModelAndView modelAndView = controladorLogin.logout(requestMock);

		// validacion
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/galeria"));
		verify(sessionMock, times(1)).invalidate();
	}

	@Test
	public void queNoSePuedaAccederACierreDeSesionSiNoHaySesionIniciada() {
		// preparacion
		when(requestMock.getSession()).thenReturn(null);

		// ejecucion
		ModelAndView modelAndView = controladorLogin.logout(requestMock);

		// validacion
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/galeria"));
		verify(requestMock, times(1)).getSession();
	}
}
