-- Datos simplificados para pruebas
INSERT INTO Usuario (email, password, rol, activo)
SELECT 'test@unlam.edu.ar', 'test', 'ADMIN', true
WHERE NOT EXISTS (
    SELECT 1 FROM Usuario WHERE email = 'test@unlam.edu.ar'
);

-- Algunos artistas de muestra
INSERT INTO Artista (id, nombre, biografia) VALUES
(1, 'Leonardo Da Vinci', 'Maestro renacentista italiano. Pintor, inventor, anatomista.'),
(2, 'Vincent van Gogh', 'Postimpresionista holandés conocido por su uso expresivo del color.'),
(3, 'Pablo Picasso', 'Artista español, cofundador del cubismo.');

-- Obras de muestra (con URLs de imágenes de ejemplo)
INSERT INTO Obra(id, titulo, autor, imagenUrl, descripcion, stock, precio, artista) VALUES
(1, 'La Dama del Armiño', 'Leonardo Da Vinci', 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/f9/Lady_with_an_Ermine_-_Leonardo_da_Vinci_-_Google_Art_Project.jpg/800px-Lady_with_an_Ermine_-_Leonardo_da_Vinci_-_Google_Art_Project.jpg', 'Retrato magistral que demuestra la técnica innovadora de Leonardo', 1, 2500000.00, 1),
(2, 'Noche Estrellada sobre el Ródano', 'Vincent van Gogh', 'https://upload.wikimedia.org/wikipedia/commons/thumb/9/94/Starry_Night_Over_the_Rhone.jpg/800px-Starry_Night_Over_the_Rhone.jpg', 'Obra nocturna que captura la belleza del cielo estrellado', 2, 850000.00, 2),
(3, 'La Noche Estrellada', 'Vincent van Gogh', 'https://upload.wikimedia.org/wikipedia/commons/thumb/e/ea/Van_Gogh_-_Starry_Night_-_Google_Art_Project.jpg/800px-Van_Gogh_-_Starry_Night_-_Google_Art_Project.jpg', 'Una de las obras más famosas de Van Gogh', 1, 950000.00, 2),
(4, 'Las Señoritas de Avignon', 'Pablo Picasso', 'https://upload.wikimedia.org/wikipedia/en/thumb/4/4c/Les_Demoiselles_d%27Avignon.jpg/800px-Les_Demoiselles_d%27Avignon.jpg', 'Obra que marca el inicio del cubismo', 1, 1200000.00, 3),
(5, 'Guernica', 'Pablo Picasso', 'https://upload.wikimedia.org/wikipedia/en/thumb/7/74/PicassoGuernica.jpg/800px-PicassoGuernica.jpg', 'Famosa obra contra la guerra', 1, 1500000.00, 3);