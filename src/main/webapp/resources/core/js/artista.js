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
// Formulario emergente solicitud de obra por parte de un artista
document.addEventListener('DOMContentLoaded', () => {
    const modal = document.getElementById('obp-modal');
    const backdrop = document.getElementById('obp-backdrop');
    const fab = document.getElementById('obp-fab');
    const closeBtn = document.getElementById('obp-close');
    const steps = Array.from(document.querySelectorAll('.obp-step'));
    const nextBtn = document.getElementById('obp-next');
    const prevBtn = document.getElementById('obp-prev');
    const submitBtn = document.getElementById('obp-submit');
    const cancelBtn = document.getElementById('obp-cancel');
    const progressBar = document.getElementById('obp-progress');
    const obraForm = document.getElementById('obp-form');
    const filesInput = document.getElementById('obp-files');
    const filesError = document.getElementById('obp-files-error');
    const deliverySelect = document.getElementById('obp-delivery-method');
    const addressField = document.getElementById('obp-address-field');
    const formAlert = document.getElementById('obp-form-alert');
    const thumbs = document.getElementById('obp-thumbs');
    const modalBody = modal.querySelector('.obp-modal-body');

    const MAX_IMAGES = 3;
    let current = 0;

    function openModal() {
        modal.classList.remove('hidden');
        modal.classList.add('flex');
        modal.setAttribute('aria-hidden', 'false');
        // reset state on open
        obraForm.reset();
        thumbs.innerHTML = '';
        formAlert.innerHTML = '';
        filesError.classList.add('hidden');
        current = 0;
        showStep(current);
        // focus first input
        setTimeout(() => document.getElementById('obp-nombre')?.focus(), 100);
    }

    function closeModal() {
        modal.classList.add('hidden');
        modal.classList.remove('flex');
        modal.setAttribute('aria-hidden', 'true');
    }

    // open/close handlers
    fab.addEventListener('click', openModal);
    closeBtn.addEventListener('click', closeModal);
    backdrop.addEventListener('click', closeModal);
    cancelBtn.addEventListener('click', closeModal);
    document.addEventListener('keydown', (e) => { if (e.key === 'Escape') closeModal(); });

    function showStep(i) {
        steps.forEach((s, idx) => s.classList.toggle('hidden', idx !== i));
        prevBtn.disabled = i === 0;
        nextBtn.classList.toggle('hidden', i === steps.length - 1);
        submitBtn.classList.toggle('hidden', i !== steps.length - 1);
        progressBar.style.width = `${((i + 1) / steps.length) * 100}%`;
    }

    function validateCurrentStep() {
        const stepEl = steps[current];
        const requiredEls = Array.from(stepEl.querySelectorAll('[required]'));
        let valid = true;
        filesError.classList.add('hidden');
        filesError.textContent = '';

        requiredEls.forEach(el => {
            if (!el.checkValidity()) {
                valid = false;
                // show neighbor error paragraph if present
                const err = el.closest('div')?.querySelector('p.text-red-600');
                if (err) err.classList.remove('hidden');
                el.classList.add('border-red-400');
            } else {
                const err = el.closest('div')?.querySelector('p.text-red-600');
                if (err) err.classList.add('hidden');
                el.classList.remove('border-red-400');
            }
        });

        if (current === steps.length - 1 && filesInput.files.length > MAX_IMAGES) {
            filesError.classList.remove('hidden');
            filesError.textContent = `Máximo ${MAX_IMAGES} imágenes permitidas.`;
            valid = false;
        }

        return valid;
    }

    // file handling: only PNG/JPG, max MAX_IMAGES, show thumbs
    filesInput.addEventListener('change', () => {
        thumbs.innerHTML = '';
        const files = Array.from(filesInput.files || []);
        if (!files.length) { filesError.classList.add('hidden'); return; }

        const allowed = files.filter(f => ['image/png', 'image/jpeg'].includes(f.type));
        const messages = [];
        if (allowed.length !== files.length) messages.push('Se ignoraron archivos no permitidos (sólo PNG/JPG).');
        const finalFiles = allowed.slice(0, MAX_IMAGES);
        if (allowed.length > MAX_IMAGES) messages.push(`Se tomarán las primeras ${MAX_IMAGES} imágenes.`);

        // reassign trimmed files to input
        const dt = new DataTransfer();
        finalFiles.forEach(f => dt.items.add(f));
        filesInput.files = dt.files;

        // thumbnails
        finalFiles.forEach(f => {
            const reader = new FileReader();
            reader.onload = e => {
                const wrap = document.createElement('div');
                wrap.className = 'obp-thumb w-20 h-20 rounded-md overflow-hidden border bg-white flex items-center justify-center';
                const img = document.createElement('img');
                img.src = e.target.result;
                img.alt = f.name;
                img.className = 'object-cover w-full h-full';
                wrap.appendChild(img);
                thumbs.appendChild(wrap);
            };
            reader.readAsDataURL(f);
        });

        if (messages.length) {
            filesError.classList.remove('hidden');
            filesError.textContent = messages.join(' ');
            setTimeout(() => filesError.classList.add('hidden'), 3500);
        } else {
            filesError.classList.add('hidden');
        }
    });

    nextBtn.addEventListener('click', () => {
        formAlert.innerHTML = '';
        if (validateCurrentStep()) {
            current = Math.min(current + 1, steps.length - 1);
            showStep(current);
            modalBody.scrollTop = 0;
        }
    });

    prevBtn.addEventListener('click', () => {
        formAlert.innerHTML = '';
        current = Math.max(current - 1, 0);
        showStep(current);
        modalBody.scrollTop = 0;
    });

    deliverySelect.addEventListener('change', () => {
        if (deliverySelect.value === 'Envio') addressField.classList.remove('hidden');
        else { addressField.classList.add('hidden'); document.getElementById('obp-address').value = ''; }
    });

    obraForm.addEventListener('submit', (e) => {
        e.preventDefault();
        formAlert.innerHTML = '';
        if (!validateCurrentStep()) {
            formAlert.innerHTML = `<div class="text-sm text-red-600">Por favor corregí los errores del formulario.</div>`;
            return;
        }

        formAlert.innerHTML = `<div class="text-sm text-green-600">Tu solicitud fue enviada. Te contactaremos pronto.</div>`;
        obraForm.reset();
        thumbs.innerHTML = '';
        filesError.classList.add('hidden');
        current = 0;
        showStep(current);
        setTimeout(closeModal, 1400);
    });

    // init
    showStep(current);
});