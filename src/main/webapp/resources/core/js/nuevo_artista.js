document.addEventListener("DOMContentLoaded", function() {
  const btnCancelar = document.getElementById("btn-cancelar");
  if (btnCancelar) {
      btnCancelar.addEventListener("click", function() {
          const confirmar = confirm("¿Seguro que querés cancelar? Se perderán los datos no guardados.");
          if (confirmar) {
              window.history.back();
          }
      });
  }
});