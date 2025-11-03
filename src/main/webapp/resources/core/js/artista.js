//despliegue formulario editar artista
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
  btnCancelar.addEventListener("click", function() {
      const artistaId = btnCancelar.dataset.id;
      window.location.href = `/spring/perfilArtista/ver/${artistaId}`;
    });
});