const BTN_EDITAR = document.getElementById("btn-editar");
const NOMBRE = document.getElementById("nombre-artista");
const BIO = document.getElementById("bio-artista");



// Función para simular edición
function habilitarEdicion() {
  const NUEVO_NOMBRE = prompt("Editar nombre:", NOMBRE.innerText);
  const NUEVA_BIO = prompt("Editar biografía:", BIO.innerText);

  if (NUEVO_NOMBRE) {
    NOMBRE.innerText = NUEVO_NOMBRE;
  }
  if (NUEVA_BIO) {
    BIO.innerText = NUEVA_BIO;

  }
}
BTN_EDITAR.addEventListener("click", habilitarEdicion);