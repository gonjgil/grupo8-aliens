INSERT INTO Usuario(id, email, password, rol, activo) VALUES(null, 'test@unlam.edu.ar', 'test', 'ADMIN', true);

-- Insertar artistas
INSERT INTO Artista (id, nombre, biografia) VALUES
(1, 'J. Doe', 'Artista contemporáneo especializado en paisajismo y retratos modernos.'),
(2, 'María García', 'Fotógrafa urbana que captura la esencia de la vida nocturna en las ciudades.'),
(3, 'Carlos López', 'Pintor naturalista enfocado en la flora y fauna de su entorno.'),
(4, 'Ana Martínez', 'Artista abstracta conocida por su uso del color y la forma.'),
(5, 'Luis Fernández', 'Explorador del claroscuro y las técnicas experimentales en pintura.'),
(6, 'Sofía Romano', 'Artista digital que fusiona tecnología y arte contemporáneo.'),
(7, 'Diego Morales', 'Pintor surrealista que crea mundos oníricos y fantásticos.'),
(8, 'Isabella Chen', 'Artista asiática que incorpora elementos zen y tradicionales en su obra.'),
(9, 'Roberto Silva', 'Fotógrafo marino que documenta la belleza del océano y sus criaturas.'),
(10, 'Elena Volkov', 'Pintora clásica inspirada en el Renacimiento y el Barroco.'),
(11, 'Kenji Nakamura', 'Ilustrador japonés especializado en manga y anime.'),
(12, 'Pierre Dubois', 'Pintor impresionista que rinde homenaje a los grandes maestros.'),
(13, 'Ahmed Hassan', 'Artista islámico que explora la geometría sagrada y la caligrafía.'),
(14, 'Frida Esperanza', 'Artista folclórica que celebra las tradiciones ancestrales a través de su arte.'),
(15, 'Viktor Petrov', 'Escultor steampunk que combina elementos industriales con arte creativo.'),
(16, 'Amélie Laurent', 'Pintora romántica que captura escenas pastorales y bucólicas.'),
(17, 'Jackson Rivers', 'Artista de graffiti conocido por sus murales urbanos vibrantes.'),
(18, 'Yuki Tanaka', 'Artista futurista que imagina mundos sci-fi a través del arte digital.'),
(19, 'Leonardo Rossi', 'Fotógrafo arquitectónico que destaca monumentos y estructuras icónicas.'),
(20, 'Emma Thompson', 'Pintora delicada especializada en acuarelas suaves y etéreas.');

-- Insert obras de arte con imágenes de ejemplo
INSERT INTO Obra(id, titulo, autor, imagenUrl, descripcion, stock, precio, artista) VALUES
-- J. Doe (Abstracto/Paisajismo)
(1, 'Paisaje Abstracto', 'J. Doe', 'https://picsum.photos/400/600?seed=1', 'Una hermosa representación abstracta de la naturaleza', 8, 150.00, 1),
(2, 'Retrato Moderno', 'J. Doe', 'https://picsum.photos/400/600?seed=2', 'Un retrato contemporáneo con técnicas modernas', 5, 200.00, 1),
(3, 'Montañas al Amanecer', 'J. Doe', 'https://picsum.photos/400/600?seed=6', 'Paisaje montañoso durante el amanecer', 3, 250.00, 1),

-- María García (Urbano/Nocturno)
(4, 'Ciudad Nocturna', 'María García', 'https://picsum.photos/400/600?seed=3', 'Vista nocturna de una ciudad moderna', 6, 300.00, 2),
(5, 'Luces de Neón', 'María García', 'https://picsum.photos/400/600?seed=12', 'Reflejo de luces urbanas en la lluvia', 4, 280.00, 2),
(6, 'Skyline al Atardecer', 'María García', 'https://picsum.photos/400/600?seed=23', 'Silueta urbana contra el cielo dorado', 2, 320.00, 2),

-- Carlos López (Naturalismo/Botánico)
(7, 'Flores Silvestres', 'Carlos López', 'https://picsum.photos/400/600?seed=4', 'Composición de flores en estado natural', 12, 120.00, 3),
(8, 'Bosque Primaveral', 'Carlos López', 'https://picsum.photos/400/600?seed=13', 'Verde intenso de un bosque en primavera', 7, 190.00, 3),

-- Ana Martínez (Abstracto/Minimalismo)
(9, 'Abstracto Azul', 'Ana Martínez', 'https://picsum.photos/400/600?seed=5', 'Obra abstracta en tonos azules', 9, 180.00, 4),
(10, 'Formas Geométricas', 'Ana Martínez', 'https://picsum.photos/400/600?seed=14', 'Composición minimalista con formas puras', 6, 220.00, 4),
(11, 'Equilibrio', 'Ana Martínez', 'https://picsum.photos/400/600?seed=24', 'Balance perfecto entre líneas y espacios', 4, 195.00, 4),

-- Luis Fernández (Claroscuro/Experimental)
(12, 'Reflexiones', 'Luis Fernández', 'https://picsum.photos/400/600?seed=7', 'Juego de luces y sombras', 8, 175.00, 5),
(13, 'Contraste', 'Luis Fernández', 'https://picsum.photos/400/600?seed=15', 'Dramático juego de claroscuros', 5, 210.00, 5),

-- Sofía Romano (Digital/Contemporáneo)
(14, 'Arte Digital', 'Sofía Romano', 'https://picsum.photos/400/600?seed=8', 'Creación digital contemporánea', 15, 220.00, 6),
(15, 'Píxeles y Sueños', 'Sofía Romano', 'https://picsum.photos/400/600?seed=16', 'Fusión entre lo digital y lo onírico', 10, 265.00, 6),
(16, 'Realidad Virtual', 'Sofía Romano', 'https://picsum.photos/400/600?seed=25', 'Exploración de mundos virtuales', 8, 290.00, 6),

-- Diego Morales (Surrealismo)
(17, 'Sueños Líquidos', 'Diego Morales', 'https://picsum.photos/400/600?seed=9', 'Paisaje onírico con elementos flotantes', 3, 340.00, 7),
(18, 'Tiempo Fragmentado', 'Diego Morales', 'https://picsum.photos/400/600?seed=17', 'Relojes derretidos en un espacio imposible', 2, 385.00, 7),
(19, 'La Puerta Dimensional', 'Diego Morales', 'https://picsum.photos/400/600?seed=26', 'Portal entre realidades alternativas', 1, 420.00, 7),

-- Isabella Chen (Asiático/Zen)
(20, 'Jardín Zen', 'Isabella Chen', 'https://picsum.photos/400/600?seed=10', 'Serenidad en piedras y agua', 7, 160.00, 8),
(21, 'Bambú al Viento', 'Isabella Chen', 'https://picsum.photos/400/600?seed=18', 'Elegancia natural del bambú', 9, 145.00, 8),
(22, 'Meditación', 'Isabella Chen', 'https://picsum.photos/400/600?seed=27', 'Momento de paz interior', 6, 170.00, 8),

-- Roberto Silva (Marino/Acuático)
(23, 'Océano Profundo', 'Roberto Silva', 'https://picsum.photos/400/600?seed=11', 'Misterios del fondo marino', 5, 275.00, 9),
(24, 'Olas Doradas', 'Roberto Silva', 'https://picsum.photos/400/600?seed=19', 'Atardecer sobre el mar', 8, 245.00, 9),
(25, 'Coral Vibrante', 'Roberto Silva', 'https://picsum.photos/400/600?seed=28', 'Vida colorida del arrecife', 10, 230.00, 9),

-- Elena Volkov (Clásico/Renacentista)
(26, 'Dama del Renacimiento', 'Elena Volkov', 'https://picsum.photos/400/600?seed=20', 'Retrato al estilo clásico', 2, 450.00, 10),
(27, 'Naturaleza Muerta', 'Elena Volkov', 'https://picsum.photos/400/600?seed=29', 'Composición tradicional con frutas', 4, 380.00, 10),
(28, 'Ángeles Barrocos', 'Elena Volkov', 'https://picsum.photos/400/600?seed=39', 'Querubines en técnica clásica', 1, 520.00, 10),

-- Kenji Nakamura (Manga/Anime)
(29, 'Sakura Dreams', 'Kenji Nakamura', 'https://picsum.photos/400/600?seed=21', 'Estilo anime con cerezos en flor', 20, 195.00, 11),
(30, 'Guerrero Digital', 'Kenji Nakamura', 'https://picsum.photos/400/600?seed=30', 'Personaje de videojuego en acción', 12, 240.00, 11),
(31, 'Gato Místico', 'Kenji Nakamura', 'https://picsum.photos/400/600?seed=40', 'Criatura fantástica estilo kawaii', 15, 185.00, 11),

-- Pierre Dubois (Impresionismo)
(32, 'Jardín de Monet', 'Pierre Dubois', 'https://picsum.photos/400/600?seed=22', 'Homenaje al maestro impresionista', 3, 365.00, 12),
(33, 'Catedral en Bruma', 'Pierre Dubois', 'https://picsum.photos/400/600?seed=31', 'Arquitectura difuminada por la niebla', 2, 395.00, 12),
(34, 'Bailarinas en Ensayo', 'Pierre Dubois', 'https://picsum.photos/400/600?seed=41', 'Movimiento capturado en pinceladas', 1, 425.00, 12),

-- Ahmed Hassan (Geometría Islámica)
(35, 'Mandala Dorado', 'Ahmed Hassan', 'https://picsum.photos/400/600?seed=32', 'Patrones geométricos sagrados', 4, 310.00, 13),
(36, 'Caligrafía Árabe', 'Ahmed Hassan', 'https://picsum.photos/400/600?seed=42', 'Belleza de la escritura tradicional', 6, 285.00, 13),

-- Frida Esperanza (Folclore/Tradicional)
(37, 'Danza Folklórica', 'Frida Esperanza', 'https://picsum.photos/400/600?seed=33', 'Celebración de tradiciones ancestrales', 8, 205.00, 14),
(38, 'Máscara Ceremonial', 'Frida Esperanza', 'https://picsum.photos/400/600?seed=43', 'Arte ritual precolombino', 3, 330.00, 14),
(39, 'Tejidos Sagrados', 'Frida Esperanza', 'https://picsum.photos/400/600?seed=47', 'Textiles con símbolos ancestrales', 5, 270.00, 14),

-- Viktor Petrov (Industrial/Steampunk)
(40, 'Máquina del Tiempo', 'Viktor Petrov', 'https://picsum.photos/400/600?seed=34', 'Engranajes y vapor en armonía', 2, 355.00, 15),
(41, 'Ciudad Mecánica', 'Viktor Petrov', 'https://picsum.photos/400/600?seed=44', 'Metrópolis de cobre y acero', 1, 390.00, 15),

-- Amélie Laurent (Romántico/Pastoral)
(42, 'Picnic Campestre', 'Amélie Laurent', 'https://picsum.photos/400/600?seed=35', 'Escena bucólica en el campo', 7, 225.00, 16),
(43, 'Cottage de Ensueño', 'Amélie Laurent', 'https://picsum.photos/400/600?seed=45', 'Casa rural entre flores', 4, 265.00, 16),
(44, 'Atardecer Provenzal', 'Amélie Laurent', 'https://picsum.photos/400/600?seed=48', 'Campos de lavanda al ocaso', 3, 295.00, 16),

-- Jackson Rivers (Graffiti/Street Art)
(45, 'Mural Urbano', 'Jackson Rivers', 'https://picsum.photos/400/600?seed=36', 'Arte callejero vibrante', 10, 180.00, 17),
(46, 'Rebeldía en Color', 'Jackson Rivers', 'https://picsum.photos/400/600?seed=46', 'Expresión urbana contemporánea', 12, 160.00, 17),

-- Yuki Tanaka (Sci-Fi/Futurismo)
(47, 'Nave Espacial', 'Yuki Tanaka', 'https://picsum.photos/400/600?seed=37', 'Exploración intergaláctica', 6, 315.00, 18),
(48, 'Cyborg Awakening', 'Yuki Tanaka', 'https://picsum.photos/400/600?seed=49', 'Fusión entre humano y máquina', 3, 385.00, 18),

-- Leonardo Rossi (Arquitectura/Monumentos)
(49, 'Basílica al Alba', 'Leonardo Rossi', 'https://picsum.photos/400/600?seed=38', 'Majestuosidad arquitectónica', 2, 440.00, 19),
(50, 'Escalera Infinita', 'Leonardo Rossi', 'https://picsum.photos/400/600?seed=50', 'Perspectiva imposible de Escher', 4, 375.00, 19),

-- Emma Thompson (Acuarela/Delicado)
(51, 'Mariposas Etéreas', 'Emma Thompson', 'https://picsum.photos/400/600?seed=51', 'Delicadeza en acuarela', 11, 155.00, 20),
(52, 'Pétalos al Viento', 'Emma Thompson', 'https://picsum.photos/400/600?seed=52', 'Suavidad primaveral', 14, 140.00, 20);

-- Asignar categorías a las obras (las obras se numeran desde 1)
INSERT INTO obra_categorias (obra_id, categoria) VALUES
-- J. Doe - Abstracto/Retrato
(1, 'ABSTRACTO'), (1, 'PINTURA'),
(2, 'RETRATO'), (2, 'MODERNO'),
(3, 'PINTURA'), (3, 'OTRO'),

-- María García - Moderno
(4, 'MODERNO'), (4, 'FOTOGRAFIA'),
(5, 'MODERNO'), (5, 'ARTE_DIGITAL'),
(6, 'MODERNO'), (6, 'FOTOGRAFIA'),

-- Carlos López - Naturaleza
(7, 'PINTURA'), (7, 'OTRO'),
(8, 'PINTURA'), (8, 'OTRO'),

-- Ana Martínez - Abstracto/Moderno  
(9, 'ABSTRACTO'), (9, 'MODERNO'),
(10, 'ABSTRACTO'), (10, 'MODERNO'),
(11, 'ABSTRACTO'), (11, 'MODERNO'),

-- Luis Fernández - Arte Mixto
(12, 'ARTE_MIXTO'), (12, 'MODERNO'),
(13, 'ARTE_MIXTO'), (13, 'MODERNO'),

-- Sofía Romano - Arte Digital
(14, 'ARTE_DIGITAL'), (14, 'MODERNO'),
(15, 'ARTE_DIGITAL'), (15, 'MODERNO'),
(16, 'ARTE_DIGITAL'), (16, 'MODERNO'),

-- Diego Morales - Surrealismo
(17, 'SURREALISMO'), (17, 'PINTURA'),
(18, 'SURREALISMO'), (18, 'PINTURA'),
(19, 'SURREALISMO'), (19, 'PINTURA'),

-- Isabella Chen - Dibujo/Otro
(20, 'DIBUJO'), (20, 'OTRO'),
(21, 'DIBUJO'), (21, 'OTRO'),
(22, 'DIBUJO'), (22, 'OTRO'),

-- Roberto Silva - Fotografia
(23, 'FOTOGRAFIA'), (23, 'MODERNO'),
(24, 'FOTOGRAFIA'), (24, 'MODERNO'),
(25, 'FOTOGRAFIA'), (25, 'MODERNO'),

-- Elena Volkov - Pintura/Retrato
(26, 'PINTURA'), (26, 'RETRATO'),
(27, 'PINTURA'), (27, 'OTRO'),
(28, 'PINTURA'), (28, 'RETRATO'),

-- Kenji Nakamura - Arte Digital/Dibujo
(29, 'ARTE_DIGITAL'), (29, 'DIBUJO'),
(30, 'ARTE_DIGITAL'), (30, 'DIBUJO'),
(31, 'ARTE_DIGITAL'), (31, 'DIBUJO'),

-- Pierre Dubois - Pintura
(32, 'PINTURA'), (32, 'MODERNO'),
(33, 'PINTURA'), (33, 'MODERNO'),
(34, 'PINTURA'), (34, 'RETRATO'),

-- Ahmed Hassan - Arte Mixto
(35, 'ARTE_MIXTO'), (35, 'OTRO'),
(36, 'ARTE_MIXTO'), (36, 'OTRO'),

-- Frida Esperanza - Arte Textil/Ceramico
(37, 'ARTE_TEXTIL'), (37, 'OTRO'),
(38, 'ARTE_CERAMICO'), (38, 'OTRO'),
(39, 'ARTE_TEXTIL'), (39, 'OTRO'),

-- Viktor Petrov - Escultura/Arte Mixto
(40, 'ESCULTURA'), (40, 'ARTE_MIXTO'),
(41, 'ESCULTURA'), (41, 'ARTE_MIXTO'),

-- Amélie Laurent - Pintura
(42, 'PINTURA'), (42, 'OTRO'),
(43, 'PINTURA'), (43, 'OTRO'),
(44, 'PINTURA'), (44, 'OTRO'),

-- Jackson Rivers - Arte Mixto
(45, 'ARTE_MIXTO'), (45, 'MODERNO'),
(46, 'ARTE_MIXTO'), (46, 'MODERNO'),

-- Yuki Tanaka - Arte Digital/Sci-Fi
(47, 'ARTE_DIGITAL'), (47, 'MODERNO'),
(48, 'ARTE_DIGITAL'), (48, 'MODERNO'),

-- Leonardo Rossi - Fotografia/Dibujo
(49, 'FOTOGRAFIA'), (49, 'MODERNO'),
(50, 'DIBUJO'), (50, 'OTRO'),

-- Emma Thompson - Pintura
(51, 'PINTURA'), (51, 'OTRO'),
(52, 'PINTURA'), (52, 'OTRO');
