INSERT INTO Usuario (email, password, rol, activo)
SELECT 'test@unlam.edu.ar', 'test', 'ADMIN', true
WHERE NOT EXISTS (
    SELECT 1 FROM Usuario WHERE email = 'test@unlam.edu.ar'
);

INSERT INTO Artista (id, nombre, biografia) VALUES
(1, 'Leonardo Da Vinci', 'Maestro renacentista italiano. Pintor, inventor, anatomista. Sus obras incluyen La Mona Lisa y La Última Cena. Genio universal que revolucionó el arte.'),
(2, 'Vincent van Gogh', 'Postimpresionista holandés conocido por su uso expresivo del color y técnica única. Creó más de 2000 obras en una década de trabajo intenso.'),
(3, 'Pablo Picasso', 'Artista español, cofundador del cubismo. Una de las figuras más influyentes del arte del siglo XX. Período azul, rosa y cubista definen su evolución.'),
(4, 'Claude Monet', 'Fundador del impresionismo francés. Famoso por sus series de nenúfares y catedrales. Revolucionó la pintura al aire libre.'),
(5, 'Frida Kahlo', 'Pintora mexicana conocida por sus autorretratos y obras que incorporan elementos de la naturaleza y artefactos de México.'),

(6, 'Salvador Dalí', 'Surrealista español famoso por sus paisajes oníricos y su técnica hiperrealista. Sus relojes derretidos son iconos del arte moderno.'),
(7, 'Georgia O\'Keeffe', 'Artista estadounidense conocida por sus pinturas de flores ampliadas, paisajes de Nuevo México y rascacielos de Nueva York.'),
(8, 'Andy Warhol', 'Líder del movimiento artístico pop. Exploró la relación entre la expresión artística, la cultura pop y la publicidad.'),
(9, 'Jackson Pollock', 'Pintor estadounidense conocido por su técnica de goteo (drip painting) y su papel en el movimiento expresionista abstracto.'),
(10, 'Wassily Kandinsky', 'Pionero del arte abstracto. Teorizó sobre la sinestesia en el arte y la espiritualidad de la forma y el color.'),

(11, 'Isabella Chen', 'Artista chino-canadiense que fusiona técnicas tradicionales de pintura china con conceptos occidentales modernos.'),
(12, 'Marcus Thompson', 'Fotógrafo urbano británico especializado en capturar la vida nocturna y la arquitectura de ciudades metropolitanas.'),
(13, 'Yuki Nakamura', 'Artista digital japonés que crea mundos virtuales inspirados en el anime y la cultura cyberpunk contemporánea.'),
(14, 'Amara Okafor', 'Escultora nigeriana que trabaja con materiales reciclados para crear instalaciones que abordan temas medioambientales.'),
(15, 'Diego Mendoza', 'Muralista mexicano cuyas obras reflejan la cultura precolombina fusionada con elementos urbanos contemporáneos.'),

(16, 'Svetlana Petrov', 'Pintora rusa especializada en paisajes árticos y retratos que capturan la melancolía de los inviernos del norte.'),
(17, 'Ahmed Al-Rashid', 'Calígrafo y artista conceptual iraní que incorpora geometría islámica en instalaciones multimedia modernas.'),
(18, 'Marie Dubois', 'Artista francesa neoimpresionistra que reinterpreta los clásicos maestros con una perspectiva feminista contemporánea.'),
(19, 'Raj Patel', 'Artista indio que combina técnicas de miniatura mogol con temáticas urbanas de Mumbai y Delhi.'),
(20, 'Elena Rossi', 'Escultora italiana especializada en mármol que crea obras que dialogan entre lo clásico y lo moderno.'),

(21, 'Benjamin Carter', 'Fotógrafo de naturaleza estadounidense que documenta los efectos del cambio climático en paisajes naturales.'),
(22, 'Fatima Benali', 'Artista textil marroquí que preserva y moderniza técnicas tradicionales de tejido bereber.'),
(23, 'Hiroshi Tanaka', 'Ceramista japonés cuyas obras reflejan la filosofía wabi-sabi en formas contemporáneas.'),
(24, 'Lucia Santos', 'Artista brasileña que trabaja con instalaciones inmersivas inspiradas en la biodiversidad amazónica.'),
(25, 'Olaf Eriksen', 'Pintor noruego especializado en paisajes nórdicos que exploran la relación entre humano y naturaleza.'),

(26, 'Priya Sharma', 'Artista multimedia india que crea experiencias artísticas interactivas sobre migración y identidad cultural.'),
(27, 'Alessandro Bianchi', 'Pintor veneciano contemporáneo que reinterpreta la tradición del vedutismo con técnicas modernas.'),
(28, 'Kofi Asante', 'Escultor ghanés que trabaja con maderas africanas creando piezas que narran historias ancestrales.'),
(29, 'Ingrid Larsson', 'Artista sueca que utiliza luz LED y vidrio para crear instalaciones que exploran temas de sostenibilidad.'),
(30, 'Rafael Guerrero', 'Grabador español especializado en técnicas tradicionales de aguafuerte con temáticas sociales contemporáneas.'),

(31, 'Aisha Williams', 'Artista afroamericana que explora la identidad racial a través de retratos fotográficos y pintura.'),
(32, 'Dmitri Volkov', 'Artista ruso que crea esculturas cinéticas inspiradas en la era espacial soviética.'),
(33, 'Carmen Delgado', 'Pintora colombiana cuyas obras reflejan la riqueza cultural y los contrastes sociales de Latinoamérica.'),
(34, 'James Mitchell', 'Artista abstracto australiano que incorpora arena y pigmentos naturales del outback en sus obras.'),
(35, 'Noor Al-Zahra', 'Artista conceptual libanesa que utiliza caligrafía árabe en instalaciones sobre memoria y exilio.'),

(36, 'Erik Nielsen', 'Ilustrador danés especializado en arte narrativo que combina tradición nórdica con estética contemporánea.'),
(37, 'Catalina Ruiz', 'Artista textil peruana que moderniza técnicas preincaicas en tapices contemporáneos.'),
(38, 'Hassan Ouni', 'Fotógrafo tunecino que documenta la vida cotidiana en el norte de África con una perspectiva poética.'),
(39, 'Anastasia Popov', 'Pintora búlgara especializada en iconografía ortodoxa reinterpretada con sensibilidad moderna.'),
(40, 'Tony Chang', 'Artista taiwanés que fusiona caligrafía china tradicional con graffiti urbano contemporáneo.'),

(41, 'Isabella Martinez', 'Ceramista argentina que crea esculturas inspiradas en la pampa y la cultura gaucha.'),
(42, 'Kwame Osei', 'Pintor ghanés que utiliza pigmentos naturales para crear obras sobre mitología africana.'),
(43, 'Helena Kozlova', 'Artista checa que trabaja con vidrio soplado creando instalaciones sobre fragilidad humana.'),
(44, 'Samuel Andersson', 'Escultor en madera finlandés que combina tradición sami con diseño escandinavo moderno.'),
(45, 'Yasmin Farouk', 'Artista egipcia que explora jeroglíficos antiguos en contextos artísticos contemporáneos.'),

(46, 'Marco Santini', 'Pintor siciliano que captura la luz mediterránea en paisajes que dialogan con la tradición italiana.'),
(47, 'Keiko Yamamoto', 'Artista de grabado japonesa especializada en xilografía que moderniza el ukiyo-e tradicional.'),
(48, 'Thabo Mthembu', 'Escultor sudafricano que trabaja con metal reciclado creando obras sobre reconciliación y esperanza.'),
(49, 'Zara Hassan', 'Artista textil pakistaní que preserva técnicas de bordado tradicional en instalaciones contemporáneas.'),
(50, 'Gabriel Silva', 'Muralista venezolano que documenta la realidad social latinoamericana a través del arte urbano.');

INSERT INTO Obra(id, titulo, autor, imagenUrl, descripcion, stock, precio, artista) VALUES
(null, 'La Dama del Armiño', 'Leonardo Da Vinci', '/images/obras/artwork_001.jpg', 'Retrato magistral que demuestra la técnica innovadora de Leonardo en el sfumato y la expresión psicológica', 1, 2500000.00, 1),
(null, 'Autorretrato con Sombrero', 'Leonardo Da Vinci', '/images/obras/artwork_002.jpg', 'Introspectivo autorretrato que revela la personalidad del genio renacentista en sus últimos años', 1, 1800000.00, 1),

(null, 'Noche Estrellada sobre el Ródano', 'Vincent van Gogh', '/images/obras/artwork_003.jpg', 'Obra nocturna que captura la belleza del cielo estrellado con la técnica única de pinceladas expresivas', 2, 850000.00, 2),
(null, 'Campo de Trigo con Cipreses', 'Vincent van Gogh', '/images/obras/artwork_004.jpg', 'Paisaje vibrante que muestra la conexión espiritual del artista con la naturaleza mediterránea', 1, 920000.00, 2),
(null, 'Los Girasoles del Jardín', 'Vincent van Gogh', '/images/obras/artwork_005.jpg', 'Naturaleza muerta que simboliza la devoción y la alegría a través del color amarillo radiante', 3, 780000.00, 2),

(null, 'Retrato Geométrico Azul', 'Pablo Picasso', '/images/obras/artwork_006.jpg', 'Exploración cubista de la forma humana durante el innovador período azul del artista', 2, 1200000.00, 3),
(null, 'Naturaleza Muerta con Guitarra', 'Pablo Picasso', '/images/obras/artwork_007.jpg', 'Deconstrucción cubista de objetos cotidianos que revolucionó la perspectiva artística', 1, 950000.00, 3),
(null, 'Mujer en Sillón', 'Pablo Picasso', '/images/obras/artwork_008.jpg', 'Retrato que ejemplifica la síntesis entre realismo y abstracción geométrica', 1, 1100000.00, 3),

(null, 'Nenúfares en Estanque', 'Claude Monet', '/images/obras/artwork_009.jpg', 'Obra serena que captura la luz reflejada en el agua con técnica impresionista pura', 4, 650000.00, 4),
(null, 'Catedral al Amanecer', 'Claude Monet', '/images/obras/artwork_010.jpg', 'Estudio de luz arquitectural que muestra los cambios cromáticos durante las horas matutinas', 2, 720000.00, 4),
(null, 'Jardín de Primavera', 'Claude Monet', '/images/obras/artwork_011.jpg', 'Celebración colorista de la naturaleza en su máximo esplendor estacional', 3, 580000.00, 4),

(null, 'Autorretrato con Mariposas', 'Frida Kahlo', '/images/obras/artwork_012.jpg', 'Introspección personal que incorpora simbolismo mexicano y elementos autobiográficos', 1, 1400000.00, 5),
(null, 'Naturaleza Viva Mexicana', 'Frida Kahlo', '/images/obras/artwork_013.jpg', 'Bodegón vibrante que celebra la biodiversidad y cultura gastronómica de México', 2, 890000.00, 5),

(null, 'Paisaje Onírico', 'Salvador Dalí', '/images/obras/artwork_014.jpg', 'Exploración del subconsciente a través de imágenes imposibles y técnica hiperrealista', 1, 1350000.00, 6),
(null, 'Metamorfosis del Tiempo', 'Salvador Dalí', '/images/obras/artwork_015.jpg', 'Reflexión sobre la relatividad temporal mediante simbolismo surrealista característico', 1, 1500000.00, 6),

(null, 'Flor Ampliada Rosa', 'Georgia O\'Keeffe', '/images/obras/artwork_016.jpg', 'Interpretación íntima de formas naturales con sensualidad y precisión técnica', 3, 480000.00, 7),
(null, 'Paisaje de Nuevo México', 'Georgia O\'Keeffe', '/images/obras/artwork_017.jpg', 'Vista del desierto americano que captura la espiritualidad del paisaje árido', 2, 620000.00, 7),

(null, 'Repetición en Color', 'Andy Warhol', '/images/obras/artwork_018.jpg', 'Exploración de la cultura de masas mediante repetición e iconografía comercial', 5, 320000.00, 8),
(null, 'Retrato Serigráfico', 'Andy Warhol', '/images/obras/artwork_019.jpg', 'Técnica de serigrafía aplicada al retrato contemporáneo con colores saturados', 4, 380000.00, 8),

(null, 'Composición de Goteo', 'Jackson Pollock', '/images/obras/artwork_020.jpg', 'Técnica de drip painting que expresa emoción pura a través del movimiento gestual', 1, 1800000.00, 9),
(null, 'Ritmo en Azul', 'Jackson Pollock', '/images/obras/artwork_021.jpg', 'Exploración del automatismo y la expresión subconsciente mediante pintura de acción', 1, 1650000.00, 9),

(null, 'Composición Sinestésica', 'Wassily Kandinsky', '/images/obras/artwork_022.jpg', 'Traducción visual de sensaciones musicales mediante formas y colores abstractos', 2, 980000.00, 10),
(null, 'Formas en Movimiento', 'Wassily Kandinsky', '/images/obras/artwork_023.jpg', 'Exploración de la espiritualidad del arte a través de la abstracción geométrica', 1, 1100000.00, 10),

(null, 'Fusión Cultural', 'Isabella Chen', '/images/obras/artwork_024.jpg', 'Síntesis entre tradición pictórica china y conceptos artísticos occidentales contemporáneos', 4, 280000.00, 11),
(null, 'Caligrafía Moderna', 'Isabella Chen', '/images/obras/artwork_025.jpg', 'Reinterpretación contemporánea de la caligrafía tradicional china con materiales modernos', 3, 190000.00, 11),

(null, 'Luces de la Ciudad', 'Marcus Thompson', '/images/obras/artwork_026.jpg', 'Captura fotográfica de la vida urbana nocturna en metrópolis contemporáneas', 8, 95000.00, 12),
(null, 'Arquitectura Reflejada', 'Marcus Thompson', '/images/obras/artwork_027.jpg', 'Estudio de geometrías urbanas y reflejos en superficies de vidrio modernas', 6, 110000.00, 12),

(null, 'Mundo Virtual', 'Yuki Nakamura', '/images/obras/artwork_028.jpg', 'Creación digital que explora realidades alternativas inspiradas en cultura cyberpunk', 12, 85000.00, 13),
(null, 'Avatar Futurista', 'Yuki Nakamura', '/images/obras/artwork_029.jpg', 'Diseño de personaje que fusiona estética anime con elementos tecnológicos avanzados', 15, 75000.00, 13),

(null, 'Renacer Ecológico', 'Amara Okafor', '/images/obras/artwork_030.jpg', 'Escultura de materiales reciclados que aborda crisis medioambiental global', 1, 450000.00, 14),
(null, 'Equilibrio Natural', 'Amara Okafor', '/images/obras/artwork_031.jpg', 'Instalación que representa la harmonía entre desarrollo humano y conservación natural', 1, 380000.00, 14),

(null, 'Mural Ancestral', 'Diego Mendoza', '/images/obras/artwork_032.jpg', 'Fusión de iconografía precolombina con expresión urbana contemporánea mexicana', 2, 220000.00, 15),
(null, 'Códice Urbano', 'Diego Mendoza', '/images/obras/artwork_033.jpg', 'Reinterpretación de códices antiguos en contexto de ciudad moderna', 1, 290000.00, 15),

(null, 'Invierno Ártico', 'Svetlana Petrov', '/images/obras/artwork_034.jpg', 'Paisaje invernal que captura la melancolía y belleza de las regiones nórdicas', 3, 180000.00, 16),
(null, 'Retrato Siberiano', 'Svetlana Petrov', '/images/obras/artwork_035.jpg', 'Estudio de carácter humano enfrentado a la dureza del clima extremo', 2, 240000.00, 16),

(null, 'Geometría Sagrada', 'Ahmed Al-Rashid', '/images/obras/artwork_036.jpg', 'Instalación multimedia que combina caligrafía árabe con tecnología contemporánea', 1, 520000.00, 17),
(null, 'Luz de Mezquita', 'Ahmed Al-Rashid', '/images/obras/artwork_037.jpg', 'Estudio de iluminación inspirado en arquitectura islámica tradicional', 2, 340000.00, 17),

(null, 'Nuevo Impresionismo', 'Marie Dubois', '/images/obras/artwork_038.jpg', 'Reinterpretación feminista de los maestros impresionistas franceses clásicos', 4, 160000.00, 18),
(null, 'Jardín Contemporáneo', 'Marie Dubois', '/images/obras/artwork_039.jpg', 'Visión moderna del jardín francés con perspectiva de género actualizada', 3, 200000.00, 18),

(null, 'Miniatura Mumbai', 'Raj Patel', '/images/obras/artwork_040.jpg', 'Técnica mogol aplicada a escenas urbanas de la India contemporánea', 5, 140000.00, 19),
(null, 'Mercado de Delhi', 'Raj Patel', '/images/obras/artwork_041.jpg', 'Detallado estudio de la vida comercial en mercados tradicionales indios', 4, 165000.00, 19),

(null, 'Mármol Contemporáneo', 'Elena Rossi', '/images/obras/artwork_042.jpg', 'Escultura que dialoga entre tradición renacentista italiana y sensibilidad moderna', 1, 680000.00, 20),
(null, 'Forma Clásica', 'Elena Rossi', '/images/obras/artwork_043.jpg', 'Interpretación contemporánea de ideales estéticos clásicos en mármol de Carrara', 1, 750000.00, 20),

(null, 'Glaciar en Retroceso', 'Benjamin Carter', '/images/obras/artwork_044.jpg', 'Documentación fotográfica del impacto del cambio climático en paisajes árticos', 7, 120000.00, 21),
(null, 'Bosque Primordial', 'Benjamin Carter', '/images/obras/artwork_045.jpg', 'Captura de ecosistemas vírgenes que enfatizan la necesidad de conservación', 6, 135000.00, 21),

(null, 'Textil Bereber', 'Fatima Benali', '/images/obras/artwork_046.jpg', 'Tejido tradicional marroquí actualizado con patrones y colores contemporáneos', 8, 95000.00, 22),
(null, 'Alfombra Narrativa', 'Fatima Benali', '/images/obras/artwork_047.jpg', 'Tapiz que narra historias ancestrales del pueblo bereber con técnicas modernas', 5, 180000.00, 22),

(null, 'Cerámica Wabi-Sabi', 'Hiroshi Tanaka', '/images/obras/artwork_048.jpg', 'Pieza cerámica que abraza la imperfección como principio estético japonés', 10, 88000.00, 23),
(null, 'Cuenco de Té Contemporáneo', 'Hiroshi Tanaka', '/images/obras/artwork_049.jpg', 'Cerámica funcional que moderniza la ceremonia del té tradicional japonesa', 12, 65000.00, 23),

(null, 'Instalación Amazónica', 'Lucia Santos', '/images/obras/artwork_050.jpg', 'Experiencia inmersiva que recrea la biodiversidad de la selva tropical brasileña', 1, 890000.00, 24),
(null, 'Sonidos de la Selva', 'Lucia Santos', '/images/obras/artwork_051.jpg', 'Instalación audiovisual que traduce la comunicación entre especies amazónicas', 1, 720000.00, 24),

(null, 'Fiordo Noruego', 'Olaf Eriksen', '/images/obras/artwork_052.jpg', 'Paisaje nórdico que explora la relación espiritual entre humano y naturaleza ártica', 3, 210000.00, 25),
(null, 'Aurora Boreal', 'Olaf Eriksen', '/images/obras/artwork_053.jpg', 'Captura pictórica del fenómeno lumínico natural del norte de Europa', 2, 280000.00, 25),

(null, 'Identidad Migratoria', 'Priya Sharma', '/images/obras/artwork_054.jpg', 'Instalación interactiva sobre la experiencia de inmigración y identidad cultural', 1, 650000.00, 26),
(null, 'Raíces Digitales', 'Priya Sharma', '/images/obras/artwork_055.jpg', 'Exploración multimedia de la preservación cultural en la era digital', 1, 580000.00, 26),

(null, 'Venecia Contemporánea', 'Alessandro Bianchi', '/images/obras/artwork_056.jpg', 'Veduta moderna que actualiza la tradición pictórica veneciana del siglo XVIII', 4, 190000.00, 27),
(null, 'Canal al Atardecer', 'Alessandro Bianchi', '/images/obras/artwork_057.jpg', 'Estudio de luz sobre arquitectura veneciana con técnicas contemporáneas', 3, 220000.00, 27),

(null, 'Escultura Ancestral', 'Kofi Asante', '/images/obras/artwork_058.jpg', 'Talla en madera africana que narra historias tradicionales del pueblo akan', 1, 420000.00, 28),
(null, 'Máscara Ceremonial', 'Kofi Asante', '/images/obras/artwork_059.jpg', 'Escultura ritual que conecta tradiciones espirituales con expresión artística moderna', 1, 380000.00, 28),

(null, 'Instalación Lumínica', 'Ingrid Larsson', '/images/obras/artwork_060.jpg', 'Obra de LED y vidrio que explora sostenibilidad energética a través del arte', 1, 780000.00, 29),
(null, 'Cristal Sostenible', 'Ingrid Larsson', '/images/obras/artwork_061.jpg', 'Escultura en vidrio reciclado que simboliza la economía circular', 2, 540000.00, 29),

(null, 'Aguafuerte Social', 'Rafael Guerrero', '/images/obras/artwork_062.jpg', 'Grabado tradicional español que aborda problemáticas sociales contemporáneas', 8, 75000.00, 30),
(null, 'Serie Urbana', 'Rafael Guerrero', '/images/obras/artwork_063.jpg', 'Conjunto de grabados que documentan transformaciones urbanas en España', 6, 95000.00, 30),

(null, 'Retrato de Identidad', 'Aisha Williams', '/images/obras/artwork_064.jpg', 'Fotografía que explora la complejidad de la identidad racial en América contemporánea', 9, 110000.00, 31),
(null, 'Herencia Cultural', 'Aisha Williams', '/images/obras/artwork_065.jpg', 'Serie fotográfica que celebra la diversidad y riqueza de la cultura afroamericana', 7, 125000.00, 31),

(null, 'Escultura Cinética', 'Dmitri Volkov', '/images/obras/artwork_066.jpg', 'Obra mecánica inspirada en la estética de la era espacial soviética', 1, 920000.00, 32),
(null, 'Cosmos en Movimiento', 'Dmitri Volkov', '/images/obras/artwork_067.jpg', 'Instalación cinética que simula movimientos planetarios con precisión mecánica', 1, 1100000.00, 32),

(null, 'Contrastes Colombianos', 'Carmen Delgado', '/images/obras/artwork_068.jpg', 'Pintura que refleja la riqueza cultural y las tensiones sociales de Colombia', 3, 180000.00, 33),
(null, 'Biodiversidad Tropical', 'Carmen Delgado', '/images/obras/artwork_069.jpg', 'Celebración pictórica de la excepcional biodiversidad del trópico sudamericano', 4, 160000.00, 33),

(null, 'Outback Abstracto', 'James Mitchell', '/images/obras/artwork_070.jpg', 'Pintura abstracta que incorpora arena y pigmentos del desierto australiano', 2, 340000.00, 34),
(null, 'Tierra Roja', 'James Mitchell', '/images/obras/artwork_071.jpg', 'Exploración de texturas y colores del paisaje árido del centro de Australia', 3, 290000.00, 34),

(null, 'Caligrafía del Exilio', 'Noor Al-Zahra', '/images/obras/artwork_072.jpg', 'Instalación conceptual sobre memoria y desplazamiento forzado en Oriente Medio', 1, 680000.00, 35),
(null, 'Cartas Perdidas', 'Noor Al-Zahra', '/images/obras/artwork_073.jpg', 'Obra textual que explora la comunicación interrumpida por conflictos geopolíticos', 2, 450000.00, 35),

(null, 'Narrativa Nórdica', 'Erik Nielsen', '/images/obras/artwork_074.jpg', 'Ilustración que combina mitología escandinava con estética contemporánea', 10, 85000.00, 36),
(null, 'Saga Moderna', 'Erik Nielsen', '/images/obras/artwork_075.jpg', 'Reinterpretación gráfica de historias tradicionales danesas para audiencias actuales', 8, 95000.00, 36),

(null, 'Tapiz Preincaico', 'Catalina Ruiz', '/images/obras/artwork_076.jpg', 'Textil que moderniza técnicas ancestrales peruanas con diseños contemporáneos', 5, 220000.00, 37),
(null, 'Colores de los Andes', 'Catalina Ruiz', '/images/obras/artwork_077.jpg', 'Tapiz que captura la paleta cromática de los paisajes andinos peruanos', 4, 260000.00, 37),

(null, 'Vida Cotidiana Magrebí', 'Hassan Ouni', '/images/obras/artwork_078.jpg', 'Fotografía poética que documenta la cotidianidad en el norte de África', 8, 90000.00, 38),
(null, 'Mercado de Túnez', 'Hassan Ouni', '/images/obras/artwork_079.jpg', 'Captura fotográfica de la actividad comercial en medinas tradicionales', 6, 105000.00, 38),

(null, 'Iconografía Moderna', 'Anastasia Popov', '/images/obras/artwork_080.jpg', 'Reinterpretación contemporánea de la tradición iconográfica ortodoxa búlgara', 3, 280000.00, 39),
(null, 'Santos Contemporáneos', 'Anastasia Popov', '/images/obras/artwork_081.jpg', 'Pintura que actualiza hagiografía tradicional con sensibilidad moderna', 2, 320000.00, 39),

(null, 'Graffiti Caligráfico', 'Tony Chang', '/images/obras/artwork_082.jpg', 'Fusión entre caligrafía china tradicional y arte urbano taiwanés contemporáneo', 6, 140000.00, 40),
(null, 'Caracteres Urbanos', 'Tony Chang', '/images/obras/artwork_083.jpg', 'Exploración de escritura china en contextos de arte callejero moderno', 5, 165000.00, 40),

(null, 'Cerámica Pampeana', 'Isabella Martinez', '/images/obras/artwork_084.jpg', 'Escultura cerámica inspirada en la vastedad de la llanura argentina', 4, 190000.00, 41),
(null, 'Espíritu Gaucho', 'Isabella Martinez', '/images/obras/artwork_085.jpg', 'Obra que captura la esencia cultural de la tradición rural argentina', 3, 220000.00, 41),

(null, 'Pigmentos Africanos', 'Kwame Osei', '/images/obras/artwork_086.jpg', 'Pintura que utiliza pigmentos naturales para narrar mitología tradicional ghanesa', 5, 180000.00, 42),
(null, 'Anansi Stories', 'Kwame Osei', '/images/obras/artwork_087.jpg', 'Serie pictórica basada en historias tradicionales de la cultura akan', 4, 200000.00, 42),

(null, 'Fragilidad Humana', 'Helena Kozlova', '/images/obras/artwork_088.jpg', 'Instalación de vidrio soplado que explora la vulnerabilidad de la condición humana', 1, 850000.00, 43),
(null, 'Transparencias', 'Helena Kozlova', '/images/obras/artwork_089.jpg', 'Escultura en vidrio que juega con luz y transparencia como metáfora existencial', 2, 620000.00, 43),

(null, 'Escultura Sami', 'Samuel Andersson', '/images/obras/artwork_090.jpg', 'Talla en madera que combina tradición sami con diseño escandinavo contemporáneo', 2, 480000.00, 44),
(null, 'Reno del Ártico', 'Samuel Andersson', '/images/obras/artwork_091.jpg', 'Escultura que honra la relación ancestral entre pueblo sami y renos', 1, 520000.00, 44),

(null, 'Jeroglíficos Modernos', 'Yasmin Farouk', '/images/obras/artwork_092.jpg', 'Reinterpretación artística de escritura jeroglífica egipcia en contexto contemporáneo', 6, 150000.00, 45),
(null, 'Papiro Digital', 'Yasmin Farouk', '/images/obras/artwork_093.jpg', 'Fusión entre tradición escriba egipcia y tecnologías de comunicación actuales', 5, 175000.00, 45),

(null, 'Luz Mediterránea', 'Marco Santini', '/images/obras/artwork_094.jpg', 'Paisaje siciliano que captura la calidad lumínica única del Mediterráneo', 4, 210000.00, 46),
(null, 'Volcán Etna', 'Marco Santini', '/images/obras/artwork_095.jpg', 'Estudio pictórico del paisaje volcánico siciliano y su impacto cultural', 3, 240000.00, 46),

(null, 'Ukiyo-e Contemporáneo', 'Keiko Yamamoto', '/images/obras/artwork_096.jpg', 'Xilografía que moderniza la tradición del mundo flotante japonés', 8, 125000.00, 47),
(null, 'Estampas Urbanas', 'Keiko Yamamoto', '/images/obras/artwork_097.jpg', 'Grabado que aplica técnicas tradicionales japonesas a escenas urbanas modernas', 6, 140000.00, 47),

(null, 'Reconciliación', 'Thabo Mthembu', '/images/obras/artwork_098.jpg', 'Escultura en metal reciclado que simboliza esperanza y unidad en Sudáfrica post-apartheid', 1, 580000.00, 48),
(null, 'Ubuntu', 'Thabo Mthembu', '/images/obras/artwork_099.jpg', 'Instalación que explora filosofía africana de interconexión humana universal', 1, 650000.00, 48),

(null, 'Bordado Ancestral', 'Zara Hassan', '/images/obras/artwork_100.jpg', 'Textil que preserva técnicas tradicionales de bordado pakistaní en instalación contemporánea', 3, 320000.00, 49);

INSERT INTO obra_categorias (obra_id, categoria) VALUES
(1, 'PINTURA'), (1, 'RETRATO'), (1, 'CLASICO'),
(2, 'PINTURA'), (2, 'RETRATO'), (2, 'CLASICO'),
(3, 'PINTURA'), (3, 'POSTIMPRESIONISMO'), (3, 'PAISAJE'),
(4, 'PINTURA'), (4, 'POSTIMPRESIONISMO'), (4, 'PAISAJE'),
(5, 'PINTURA'), (5, 'POSTIMPRESIONISMO'), (5, 'NATURALEZA_MUERTA'),
(6, 'PINTURA'), (6, 'CUBISMO'), (6, 'RETRATO'),
(7, 'PINTURA'), (7, 'CUBISMO'), (7, 'NATURALEZA_MUERTA'),
(8, 'PINTURA'), (8, 'CUBISMO'), (8, 'RETRATO'),
(9, 'PINTURA'), (9, 'IMPRESIONISMO'), (9, 'PAISAJE'),
(10, 'PINTURA'), (10, 'IMPRESIONISMO'), (10, 'ARQUITECTURA'),

(11, 'PINTURA'), (11, 'IMPRESIONISMO'), (11, 'PAISAJE'),
(12, 'PINTURA'), (12, 'AUTORRETRATO'), (12, 'SURREALISMO'),
(13, 'PINTURA'), (13, 'NATURALEZA_MUERTA'), (13, 'MODERNO'),
(14, 'PINTURA'), (14, 'SURREALISMO'), (14, 'PAISAJE'),
(15, 'PINTURA'), (15, 'SURREALISMO'), (15, 'CONCEPTUAL'),
(16, 'PINTURA'), (16, 'MODERNO'), (16, 'NATURALEZA'),
(17, 'PINTURA'), (17, 'MODERNO'), (17, 'PAISAJE'),
(18, 'ARTE_DIGITAL'), (18, 'POP_ART'), (18, 'MODERNO'),
(19, 'ARTE_DIGITAL'), (19, 'POP_ART'), (19, 'RETRATO'),
(20, 'PINTURA'), (20, 'EXPRESIONISMO_ABSTRACTO'), (20, 'ABSTRACTO'),

(21, 'PINTURA'), (21, 'EXPRESIONISMO_ABSTRACTO'), (21, 'ABSTRACTO'),
(22, 'PINTURA'), (22, 'ABSTRACTO'), (22, 'MODERNO'),
(23, 'PINTURA'), (23, 'ABSTRACTO'), (23, 'GEOMETRICO'),
(24, 'PINTURA'), (24, 'FUSION_CULTURAL'), (24, 'MODERNO'),
(25, 'ARTE_MIXTO'), (25, 'CALIGRAFIA'), (25, 'TRADICION'),
(26, 'FOTOGRAFIA'), (26, 'URBANO'), (26, 'NOCTURNO'),
(27, 'FOTOGRAFIA'), (27, 'ARQUITECTURA'), (27, 'URBANO'),
(28, 'ARTE_DIGITAL'), (28, 'CYBERPUNK'), (28, 'FUTURISMO'),
(29, 'ARTE_DIGITAL'), (29, 'ANIME'), (29, 'PERSONAJE'),
(30, 'ESCULTURA'), (30, 'ECOLOGICO'), (30, 'RECICLADO'),

(31, 'INSTALACION'), (31, 'ECOLOGICO'), (31, 'CONCEPTUAL'),
(32, 'ARTE_MIXTO'), (32, 'MURAL'), (32, 'PRECOLOMBINO'),
(33, 'ARTE_MIXTO'), (33, 'MURAL'), (33, 'HISTORICO'),
(34, 'PINTURA'), (34, 'PAISAJE'), (34, 'NORDICO'),
(35, 'PINTURA'), (35, 'RETRATO'), (35, 'REGIONAL'),
(36, 'INSTALACION'), (36, 'MULTIMEDIA'), (36, 'GEOMETRIA_SAGRADA'),
(37, 'INSTALACION'), (37, 'ARQUITECTURA'), (37, 'ISLAMICO'),
(38, 'PINTURA'), (38, 'NEOIMPRESIONISMO'), (38, 'FEMINISTA'),
(39, 'PINTURA'), (39, 'PAISAJE'), (39, 'CONTEMPORANEO'),
(40, 'ARTE_MIXTO'), (40, 'MINIATURA'), (40, 'URBANO'),

(41, 'ARTE_MIXTO'), (41, 'ESCENA_URBANA'), (41, 'DETALLE'),
(42, 'ESCULTURA'), (42, 'MARMOL'), (42, 'CLASICO_MODERNO'),
(43, 'ESCULTURA'), (43, 'MARMOL'), (43, 'TRADICION'),
(44, 'FOTOGRAFIA'), (44, 'NATURALEZA'), (44, 'DOCUMENTAL'),
(45, 'FOTOGRAFIA'), (45, 'PAISAJE'), (45, 'CONSERVACION'),
(46, 'ARTE_TEXTIL'), (46, 'TRADICIONAL'), (46, 'BEREBER'),
(47, 'ARTE_TEXTIL'), (47, 'NARRATIVO'), (47, 'CULTURAL'),
(48, 'CERAMICA'), (48, 'WABI_SABI'), (48, 'FILOSOFICO'),
(49, 'CERAMICA'), (49, 'FUNCIONAL'), (49, 'TRADICION'),
(50, 'INSTALACION'), (50, 'INMERSIVA'), (50, 'BIODIVERSIDAD'),

(51, 'INSTALACION'), (51, 'AUDIOVISUAL'), (51, 'NATURALEZA'),
(52, 'PINTURA'), (52, 'PAISAJE'), (52, 'ESPIRITUAL'),
(53, 'PINTURA'), (53, 'FENOMENO_NATURAL'), (53, 'LUZ'),
(54, 'INSTALACION'), (54, 'INTERACTIVA'), (54, 'MIGRACION'),
(55, 'ARTE_DIGITAL'), (55, 'MULTIMEDIA'), (55, 'CULTURAL'),
(56, 'PINTURA'), (56, 'VEDUTA'), (56, 'VENECIANO'),
(57, 'PINTURA'), (57, 'ESTUDIO_LUZ'), (57, 'ARQUITECTURA'),
(58, 'ESCULTURA'), (58, 'MADERA'), (58, 'ANCESTRAL'),
(59, 'ESCULTURA'), (59, 'RITUAL'), (59, 'AFRICANO'),
(60, 'INSTALACION'), (60, 'LED'), (60, 'SOSTENIBILIDAD'),

(61, 'ESCULTURA'), (61, 'VIDRIO'), (61, 'RECICLADO'),
(62, 'GRABADO'), (62, 'AGUAFUERTE'), (62, 'SOCIAL'),
(63, 'GRABADO'), (63, 'SERIE'), (63, 'URBANO'),
(64, 'FOTOGRAFIA'), (64, 'RETRATO'), (64, 'IDENTIDAD'),
(65, 'FOTOGRAFIA'), (65, 'DOCUMENTAL'), (65, 'CULTURAL'),
(66, 'ESCULTURA'), (66, 'CINETICA'), (66, 'ESPACIAL'),
(67, 'INSTALACION'), (67, 'CINETICA'), (67, 'ASTRONOMICO'),
(68, 'PINTURA'), (68, 'SOCIAL'), (68, 'LATINOAMERICANO'),
(69, 'PINTURA'), (69, 'BIODIVERSIDAD'), (69, 'TROPICAL'),
(70, 'PINTURA'), (70, 'ABSTRACTO'), (70, 'TEXTURAL'),

(71, 'PINTURA'), (71, 'PAISAJE'), (71, 'AUSTRALIANO'),
(72, 'INSTALACION'), (72, 'CONCEPTUAL'), (72, 'EXILIO'),
(73, 'ARTE_TEXTUAL'), (73, 'CONCEPTUAL'), (73, 'COMUNICACION'),
(74, 'ILUSTRACION'), (74, 'NARRATIVO'), (74, 'NORDICO'),
(75, 'ILUSTRACION'), (75, 'MITOLOGIA'), (75, 'CONTEMPORANEO'),
(76, 'ARTE_TEXTIL'), (76, 'PREINCAICO'), (76, 'PERUANO'),
(77, 'ARTE_TEXTIL'), (77, 'PAISAJE'), (77, 'ANDINO'),
(78, 'FOTOGRAFIA'), (78, 'DOCUMENTAL'), (78, 'COTIDIANO'),
(79, 'FOTOGRAFIA'), (79, 'URBANO'), (79, 'TRADICIONAL'),
(80, 'PINTURA'), (80, 'ICONOGRAFIA'), (80, 'ORTODOXO'),

(81, 'PINTURA'), (81, 'HAGIOGRAFIA'), (81, 'RELIGIOSO'),
(82, 'ARTE_URBANO'), (82, 'CALIGRAFIA'), (82, 'FUSION'),
(83, 'ARTE_URBANO'), (83, 'ESCRITURA'), (83, 'CHINO'),
(84, 'CERAMICA'), (84, 'REGIONAL'), (84, 'ARGENTINO'),
(85, 'CERAMICA'), (85, 'CULTURAL'), (85, 'RURAL'),
(86, 'PINTURA'), (86, 'MITOLOGIA'), (86, 'AFRICANO'),
(87, 'PINTURA'), (87, 'NARRATIVO'), (87, 'AKAN'),
(88, 'ESCULTURA'), (88, 'VIDRIO'), (88, 'FILOSOFICO'),
(89, 'ESCULTURA'), (89, 'VIDRIO'), (89, 'METAFISICO'),
(90, 'ESCULTURA'), (90, 'MADERA'), (90, 'SAMI'),

-- Obras 91-100: Arte Final
(91, 'ESCULTURA'), (91, 'MADERA'), (91, 'ARTICO'),
(92, 'ARTE_MIXTO'), (92, 'JEROGLIFICOS'), (92, 'EGIPCIO'),
(93, 'ARTE_DIGITAL'), (93, 'COMUNICACION'), (93, 'HIBRIDO'),
(94, 'PINTURA'), (94, 'LUZ'), (94, 'MEDITERRANEO'),
(95, 'PINTURA'), (95, 'VOLCANICO'), (95, 'GEOLOGICO'),
(96, 'GRABADO'), (96, 'UKIYO_E'), (96, 'JAPONES'),
(97, 'GRABADO'), (97, 'URBANO'), (97, 'XILOGRAFIA'),
(98, 'ESCULTURA'), (98, 'METAL'), (98, 'SUDAFRICANO'),
(99, 'INSTALACION'), (99, 'UBUNTU'), (99, 'FILOSOFICO'),
(100, 'ARTE_TEXTIL'), (100, 'BORDADO'), (100, 'PAKISTANI');
