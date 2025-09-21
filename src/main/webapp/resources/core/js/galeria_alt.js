      // Datos de ejemplo para las galerías
      const images = [
        {
          url: 'https://placehold.co/400x400/ef4444/FFF?text=Obra+1',
          title: 'Abstracta No. 1',
          author: 'J. Doe',
        },
        {
          url: 'https://placehold.co/400x400/10b981/FFF?text=Obra+2',
          title: 'Noche Cósmica',
          author: 'S. Smith',
        },
        {
          url: 'https://placehold.co/400x400/3b82f6/FFF?text=Obra+3',
          title: 'Retrato de un Sueño',
          author: 'A. García',
        },
        {
          url: 'https://placehold.co/400x400/f97316/FFF?text=Obra+4',
          title: 'Viaje al Infinito',
          author: 'L. Wang',
        },
        {
          url: 'https://placehold.co/400x400/d946ef/FFF?text=Obra+5',
          title: 'Horizonte Perdido',
          author: 'M. Khan',
        },
        {
          url: 'https://placehold.co/400x400/84cc16/FFF?text=Obra+6',
          title: 'El Alma del Mar',
          author: 'J. Doe',
        },
        {
          url: 'https://placehold.co/400x400/06b6d4/FFF?text=Obra+7',
          title: 'La Danza del Aire',
          author: 'S. Smith',
        },
        {
          url: 'https://placehold.co/400x400/ec4899/FFF?text=Obra+8',
          title: 'Ciudad Silenciosa',
          author: 'A. García',
        },
        {
          url: 'https://placehold.co/400x400/a855f7/FFF?text=Obra+9',
          title: 'Despertar Cromático',
          author: 'L. Wang',
        },
        {
          url: 'https://placehold.co/400x400/14b8a6/FFF?text=Obra+10',
          title: 'Geometría Natural',
          author: 'M. Khan',
        },
      ];

      function createCarousel(containerId, data) {
        const track = document.getElementById(containerId + '-track');
        data.forEach((item) => {
          const div = document.createElement('div');
          div.className =
            'carousel-item group cursor-pointer transition-transform duration-300 transform hover:scale-105';
          div.style.backgroundImage = `url(${item.url})`;
          div.innerHTML = `
                    <div class="image-overlay">
                        <p class="text-sm font-semibold text-white">${item.title}</p>
                        <p class="text-xs text-gray-300">por ${item.author}</p>
                    </div>
                `;
          track.appendChild(div);
        });
      }

      function scrollCarousel(containerId, direction) {
        const track = document.getElementById(containerId + '-track');
        const itemWidth =
          track.querySelector('.carousel-item').offsetWidth + 20; // 20px is the margin
        track.scrollBy({
          left: direction * itemWidth,
          behavior: 'smooth',
        });
      }

      // Crear los carruseles al cargar la página
      window.onload = function () {
        createCarousel('spotlight', images);
        createCarousel('author', images);
        createCarousel('theme', images);
      };