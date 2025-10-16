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
INSERT INTO Obra(id, titulo, autor, imagenUrl, descripcion, precio, artista) VALUES
-- J. Doe (Abstracto/Paisajismo)
(null, 'Paisaje Abstracto', 'J. Doe', 'https://picsum.photos/400/600?seed=1', 'Una hermosa representación abstracta de la naturaleza', 150.00, 1),
(null, 'Retrato Moderno', 'J. Doe', 'https://picsum.photos/400/600?seed=2', 'Un retrato contemporáneo con técnicas modernas', 200.00, 1),
(null, 'Montañas al Amanecer', 'J. Doe', 'https://picsum.photos/400/600?seed=6', 'Paisaje montañoso durante el amanecer', 250.00, 1),

-- María García (Urbano/Nocturno)
(null, 'Ciudad Nocturna', 'María García', 'https://picsum.photos/400/600?seed=3', 'Vista nocturna de una ciudad moderna', 300.00, 2),
(null, 'Luces de Neón', 'María García', 'https://picsum.photos/400/600?seed=12', 'Reflejo de luces urbanas en la lluvia', 280.00, 2),
(null, 'Skyline al Atardecer', 'María García', 'https://picsum.photos/400/600?seed=23', 'Silueta urbana contra el cielo dorado', 320.00, 2),

-- Carlos López (Naturalismo/Botánico)
(null, 'Flores Silvestres', 'Carlos López', 'https://picsum.photos/400/600?seed=4', 'Composición de flores en estado natural',  120.00, 3),
(null, 'Bosque Primaveral', 'Carlos López', 'https://picsum.photos/400/600?seed=13', 'Verde intenso de un bosque en primavera', 190.00, 3),

-- Ana Martínez (Abstracto/Minimalismo)
(null, 'Abstracto Azul', 'Ana Martínez', 'https://picsum.photos/400/600?seed=5', 'Obra abstracta en tonos azules', 180.00, 4),
(null, 'Formas Geométricas', 'Ana Martínez', 'https://picsum.photos/400/600?seed=14', 'Composición minimalista con formas puras', 220.00, 4),
(null, 'Equilibrio', 'Ana Martínez', 'https://picsum.photos/400/600?seed=24', 'Balance perfecto entre líneas y espacios', 195.00, 4),

-- Luis Fernández (Claroscuro/Experimental)
(null, 'Reflexiones', 'Luis Fernández', 'https://picsum.photos/400/600?seed=7', 'Juego de luces y sombras', 175.00, 5),
(null, 'Contraste', 'Luis Fernández', 'https://picsum.photos/400/600?seed=15', 'Dramático juego de claroscuros', 210.00, 5),

-- Sofía Romano (Digital/Contemporáneo)
(null, 'Arte Digital', 'Sofía Romano', 'https://picsum.photos/400/600?seed=8', 'Creación digital contemporánea', 220.00, 6),
(null, 'Píxeles y Sueños', 'Sofía Romano', 'https://picsum.photos/400/600?seed=16', 'Fusión entre lo digital y lo onírico', 265.00, 6),
(null, 'Realidad Virtual', 'Sofía Romano', 'https://picsum.photos/400/600?seed=25', 'Exploración de mundos virtuales', 290.00, 6),

-- Diego Morales (Surrealismo)
(null, 'Sueños Líquidos', 'Diego Morales', 'https://picsum.photos/400/600?seed=9', 'Paisaje onírico con elementos flotantes', 340.00, 7),
(null, 'Tiempo Fragmentado', 'Diego Morales', 'https://picsum.photos/400/600?seed=17', 'Relojes derretidos en un espacio imposible', 385.00, 7),
(null, 'La Puerta Dimensional', 'Diego Morales', 'https://picsum.photos/400/600?seed=26', 'Portal entre realidades alternativas', 420.00, 7),

-- Isabella Chen (Asiático/Zen)
(null, 'Jardín Zen', 'Isabella Chen', 'https://picsum.photos/400/600?seed=10', 'Serenidad en piedras y agua', 160.00, 8),
(null, 'Bambú al Viento', 'Isabella Chen', 'https://picsum.photos/400/600?seed=18', 'Elegancia natural del bambú', 145.00, 8),
(null, 'Meditación', 'Isabella Chen', 'https://picsum.photos/400/600?seed=27', 'Momento de paz interior', 170.00, 8),

-- Roberto Silva (Marino/Acuático)
(null, 'Océano Profundo', 'Roberto Silva', 'https://picsum.photos/400/600?seed=11', 'Misterios del fondo marino', 275.00, 9),
(null, 'Olas Doradas', 'Roberto Silva', 'https://picsum.photos/400/600?seed=19', 'Atardecer sobre el mar', 245.00, 9),
(null, 'Coral Vibrante', 'Roberto Silva', 'https://picsum.photos/400/600?seed=28', 'Vida colorida del arrecife', 230.00, 9),

-- Elena Volkov (Clásico/Renacentista)
(null, 'Dama del Renacimiento', 'Elena Volkov', 'https://picsum.photos/400/600?seed=20', 'Retrato al estilo clásico', 450.00, 10),
(null, 'Naturaleza Muerta', 'Elena Volkov', 'https://picsum.photos/400/600?seed=29', 'Composición tradicional con frutas', 380.00, 10),
(null, 'Ángeles Barrocos', 'Elena Volkov', 'https://picsum.photos/400/600?seed=39', 'Querubines en técnica clásica', 520.00, 10),

-- Kenji Nakamura (Manga/Anime)
(null, 'Sakura Dreams', 'Kenji Nakamura', 'https://picsum.photos/400/600?seed=21', 'Estilo anime con cerezos en flor', 195.00, 11),
(null, 'Guerrero Digital', 'Kenji Nakamura', 'https://picsum.photos/400/600?seed=30', 'Personaje de videojuego en acción', 240.00, 11),
(null, 'Gato Místico', 'Kenji Nakamura', 'https://picsum.photos/400/600?seed=40', 'Criatura fantástica estilo kawaii', 185.00, 11),

-- Pierre Dubois (Impresionismo)
(null, 'Jardín de Monet', 'Pierre Dubois', 'https://picsum.photos/400/600?seed=22', 'Homenaje al maestro impresionista', 365.00, 12),
(null, 'Catedral en Bruma', 'Pierre Dubois', 'https://picsum.photos/400/600?seed=31', 'Arquitectura difuminada por la niebla', 395.00, 12),
(null, 'Bailarinas en Ensayo', 'Pierre Dubois', 'https://picsum.photos/400/600?seed=41', 'Movimiento capturado en pinceladas', 425.00, 12),

-- Ahmed Hassan (Geometría Islámica)
(null, 'Mandala Dorado', 'Ahmed Hassan', 'https://picsum.photos/400/600?seed=32', 'Patrones geométricos sagrados', 310.00, 13),
(null, 'Caligrafía Árabe', 'Ahmed Hassan', 'https://picsum.photos/400/600?seed=42', 'Belleza de la escritura tradicional', 285.00, 13),

-- Frida Esperanza (Folclore/Tradicional)
(null, 'Danza Folklórica', 'Frida Esperanza', 'https://picsum.photos/400/600?seed=33', 'Celebración de tradiciones ancestrales', 205.00, 14),
(null, 'Máscara Ceremonial', 'Frida Esperanza', 'https://picsum.photos/400/600?seed=43', 'Arte ritual precolombino', 330.00, 14),
(null, 'Tejidos Sagrados', 'Frida Esperanza', 'https://picsum.photos/400/600?seed=47', 'Textiles con símbolos ancestrales', 270.00, 14),

-- Viktor Petrov (Industrial/Steampunk)
(null, 'Máquina del Tiempo', 'Viktor Petrov', 'https://picsum.photos/400/600?seed=34', 'Engranajes y vapor en armonía', 355.00, 15),
(null, 'Ciudad Mecánica', 'Viktor Petrov', 'https://picsum.photos/400/600?seed=44', 'Metrópolis de cobre y acero', 390.00, 15),

-- Amélie Laurent (Romántico/Pastoral)
(null, 'Picnic Campestre', 'Amélie Laurent', 'https://picsum.photos/400/600?seed=35', 'Escena bucólica en el campo', 225.00, 16),
(null, 'Cottage de Ensueño', 'Amélie Laurent', 'https://picsum.photos/400/600?seed=45', 'Casa rural entre flores', 265.00, 16),
(null, 'Atardecer Provenzal', 'Amélie Laurent', 'https://picsum.photos/400/600?seed=48', 'Campos de lavanda al ocaso', 295.00, 16),

-- Jackson Rivers (Graffiti/Street Art)
(null, 'Mural Urbano', 'Jackson Rivers', 'https://picsum.photos/400/600?seed=36', 'Arte callejero vibrante', 180.00, 17),
(null, 'Rebeldía en Color', 'Jackson Rivers', 'https://picsum.photos/400/600?seed=46', 'Expresión urbana contemporánea', 160.00, 17),

-- Yuki Tanaka (Sci-Fi/Futurismo)
(null, 'Nave Espacial', 'Yuki Tanaka', 'https://picsum.photos/400/600?seed=37', 'Exploración intergaláctica', 315.00, 18),
(null, 'Cyborg Awakening', 'Yuki Tanaka', 'https://picsum.photos/400/600?seed=49', 'Fusión entre humano y máquina', 385.00, 18),

-- Leonardo Rossi (Arquitectura/Monumentos)
(null, 'Basílica al Alba', 'Leonardo Rossi', 'https://picsum.photos/400/600?seed=38', 'Majestuosidad arquitectónica', 440.00, 19),
(null, 'Escalera Infinita', 'Leonardo Rossi', 'https://picsum.photos/400/600?seed=50', 'Perspectiva imposible de Escher', 375.00, 19),

-- Emma Thompson (Acuarela/Delicado)
(null, 'Mariposas Etéreas', 'Emma Thompson', 'https://picsum.photos/400/600?seed=51', 'Delicadeza en acuarela', 155.00, 20),
(null, 'Pétalos al Viento', 'Emma Thompson', 'https://picsum.photos/400/600?seed=52', 'Suavidad primaveral', 140.00, 20);

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
