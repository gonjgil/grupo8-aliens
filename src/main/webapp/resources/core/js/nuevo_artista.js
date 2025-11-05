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

// Mostrar preview de imagen elegida
  const inputFoto = document.getElementById("fotoPerfil");
  const preview = document.getElementById("foto-preview");

  if (inputFoto && preview) {
    inputFoto.addEventListener("change", function() {
      const file = this.files[0];
      if (file) {
        const reader = new FileReader();
        reader.onload = function(e) {
          preview.setAttribute("src", e.target.result);
        };
        reader.readAsDataURL(file);
      } else {
        // Si se deselecciona, vuelve al estado vacío
        preview.setAttribute("src", "/images/html5up/defaultProfilePic.jpg");
      }
    });
  }

});