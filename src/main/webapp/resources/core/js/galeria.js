function scrollCarousel(containerId, direction) {
  const track = document.getElementById(containerId + '-track');
  const item = track.querySelector('.carousel-item');
  if (item) {
    const itemWidth = item.offsetWidth + 20; // 20px es el margen
    track.scrollBy({
      left: direction * itemWidth,
      behavior: 'smooth',
    });
  }
}