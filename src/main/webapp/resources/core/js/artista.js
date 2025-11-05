// Despliegue formulario editar artista
document.addEventListener('DOMContentLoaded', function() {
  const btnEditar = document.getElementById('btn-editar-perfil');

  if (btnEditar) {
    btnEditar.addEventListener('click', function() {
      const artistaId = this.dataset.artistaId; // Obtiene el ID del atributo data-artista-id
      if (artistaId) {
        window.location.href = '/spring/perfilArtista/ver/' + artistaId + '/editar'; // Redirige el navegador a la URL de edición
      } else {
        console.error("No se pudo obtener el ID del artista para la edición.");
      }
    });
  }

  // Funcionalidad botón Cancelar (formulario edición)
  const btnCancelar = document.getElementById("btn-cancelar");
  if (btnCancelar) {
    btnCancelar.addEventListener("click", function() {
      const artistaId = btnCancelar.dataset.id;
      window.location.href = `/spring/perfilArtista/ver/${artistaId}`;
    });
  }

  // Mostrar preview de imagen elegida en formulario de edición
  const inputFoto = document.getElementById("fotoPerfil");
  const preview = document.getElementById("foto-preview");
  const form = document.getElementById("form-editar-artista");
  let ultimaImgSeleccionada = null;

  if (inputFoto && preview) {
    inputFoto.addEventListener("change", function() {
      const file = this.files[0];
      if (file) {
        const reader = new FileReader();
        reader.addEventListener("load", function() {
          preview.setAttribute("src", this.result);
          ultimaImgSeleccionada = file;
        });
        reader.readAsDataURL(file);
      } else if (ultimaImgSeleccionada) {
        const reader = new FileReader();
        reader.onload = function(e) {
          preview.setAttribute("src", e.target.result);
        };
        reader.readAsDataURL(ultimaImgSeleccionada);
      }
    });
  }

  if (form) {
    form.addEventListener("submit", function(evt) {
      if (inputFoto.files.length === 0 && ultimaImgSeleccionada) {
        try {
          const dataTransfer = new DataTransfer();
          dataTransfer.items.add(ultimaImgSeleccionada);
          inputFoto.files = dataTransfer.files;
        } catch (err) {
          evt.preventDefault();
          alert("Tu navegador no soporta restaurar la imagen seleccionada automáticamente. Volvé a elegir la imagen antes de guardar.");
        }
      }
    });
  }
});
