document.addEventListener("DOMContentLoaded", function() {
  const btnCancelar = document.getElementById("btn-cancelar");

  // Funcionalidad botón Cancelar
  btnCancelar.addEventListener("click", function() {
      const artistaId = btnCancelar.dataset.id;
      window.location.href = `/spring/perfilArtista/ver/${artistaId}`;
    });
});