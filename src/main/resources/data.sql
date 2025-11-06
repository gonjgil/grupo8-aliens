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
(7, 'Georgia Keeffe', 'Artista estadounidense conocida por sus pinturas de flores ampliadas, paisajes de Nuevo México y rascacielos de Nueva York.'),
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

INSERT INTO Obra(id, titulo, autor, imagenUrl, descripcion, stock, artista) VALUES
(null, 'La Dama del Armiño', 'Leonardo Da Vinci', '/images/obras/artwork_001.jpg', 'Retrato magistral que demuestra la técnica innovadora de Leonardo en el sfumato y la expresión psicológica', 1, 1),
(null, 'Autorretrato con Sombrero', 'Leonardo Da Vinci', '/images/obras/artwork_002.jpg', 'Introspectivo autorretrato que revela la personalidad del genio renacentista en sus últimos años', 1, 1),
(null, 'Noche Estrellada sobre el Ródano', 'Vincent van Gogh', '/images/obras/artwork_003.jpg', 'Obra nocturna que captura la belleza del cielo estrellado con la técnica única de pinceladas expresivas', 2, 2),
(null, 'Campo de Trigo con Cipreses', 'Vincent van Gogh', '/images/obras/artwork_004.jpg', 'Paisaje vibrante que muestra la conexión espiritual del artista con la naturaleza mediterránea', 1, 2),
(null, 'Los Girasoles del Jardín', 'Vincent van Gogh', '/images/obras/artwork_005.jpg', 'Naturaleza muerta que simboliza la devoción y la alegría a través del color amarillo radiante', 3, 2),
(null, 'Retrato Geométrico Azul', 'Pablo Picasso', '/images/obras/artwork_006.jpg', 'Exploración cubista de la forma humana durante el innovador período azul del artista', 2, 3),
(null, 'Naturaleza Muerta con Guitarra', 'Pablo Picasso', '/images/obras/artwork_007.jpg', 'Deconstrucción cubista de objetos cotidianos que revolucionó la perspectiva artística', 1, 3),
(null, 'Mujer en Sillón', 'Pablo Picasso', '/images/obras/artwork_008.jpg', 'Retrato que ejemplifica la síntesis entre realismo y abstracción geométrica', 1, 3),
(null, 'Nenúfares en Estanque', 'Claude Monet', '/images/obras/artwork_009.jpg', 'Obra serena que captura la luz reflejada en el agua con técnica impresionista pura', 4, 4),
(null, 'Catedral al Amanecer', 'Claude Monet', '/images/obras/artwork_010.jpg', 'Estudio de luz arquitectural que muestra los cambios cromáticos durante las horas matutinas', 2, 4),
(null, 'Jardín de Primavera', 'Claude Monet', '/images/obras/artwork_011.jpg', 'Celebración colorista de la naturaleza en su máximo esplendor estacional', 3, 4),
(null, 'Autorretrato con Mariposas', 'Frida Kahlo', '/images/obras/artwork_012.jpg', 'Introspección personal que incorpora simbolismo mexicano y elementos autobiográficos', 1, 5),
(null, 'Naturaleza Viva Mexicana', 'Frida Kahlo', '/images/obras/artwork_013.jpg', 'Bodegón vibrante que celebra la biodiversidad y cultura gastronómica de México', 2, 5),
(null, 'Paisaje Onírico', 'Salvador Dalí', '/images/obras/artwork_014.jpg', 'Exploración del subconsciente a través de imágenes imposibles y técnica hiperrealista', 1, 6),
(null, 'Metamorfosis del Tiempo', 'Salvador Dalí', '/images/obras/artwork_015.jpg', 'Reflexión sobre la relatividad temporal mediante simbolismo surrealista característico', 1, 6),
(null, 'Flor Ampliada Rosa', 'Georgia Keeffe', '/images/obras/artwork_016.jpg', 'Interpretación íntima de formas naturales con sensualidad y precisión técnica', 3, 7),
(null, 'Paisaje de Nuevo México', 'Georgia Keeffe', '/images/obras/artwork_017.jpg', 'Vista del desierto americano que captura la espiritualidad del paisaje árido', 2, 7),
(null, 'Repetición en Color', 'Andy Warhol', '/images/obras/artwork_018.jpg', 'Exploración de la cultura de masas mediante repetición e iconografía comercial', 5, 8),
(null, 'Retrato Serigráfico', 'Andy Warhol', '/images/obras/artwork_019.jpg', 'Técnica de serigrafía aplicada al retrato contemporáneo con colores saturados', 4, 8),
(null, 'Composición de Goteo', 'Jackson Pollock', '/images/obras/artwork_020.jpg', 'Técnica de drip painting que expresa emoción pura a través del movimiento gestual', 1, 9),
(null, 'Ritmo en Azul', 'Jackson Pollock', '/images/obras/artwork_021.jpg', 'Exploración del automatismo y la expresión subconsciente mediante pintura de acción', 1, 9),
(null, 'Composición Sinestésica', 'Wassily Kandinsky', '/images/obras/artwork_022.jpg', 'Traducción visual de sensaciones musicales mediante formas y colores abstractos', 2, 10),
(null, 'Formas en Movimiento', 'Wassily Kandinsky', '/images/obras/artwork_023.jpg', 'Exploración de la espiritualidad del arte a través de la abstracción geométrica', 1, 10),
(null, 'Fusión Cultural', 'Isabella Chen', '/images/obras/artwork_024.jpg', 'Síntesis entre tradición pictórica china y conceptos artísticos occidentales contemporáneos', 4, 11),
(null, 'Caligrafía Moderna', 'Isabella Chen', '/images/obras/artwork_025.jpg', 'Reinterpretación contemporánea de la caligrafía tradicional china con materiales modernos', 3, 11),
(null, 'Luces de la Ciudad', 'Marcus Thompson', '/images/obras/artwork_026.jpg', 'Captura fotográfica de la vida urbana nocturna en metrópolis contemporáneas', 8, 12),
(null, 'Arquitectura Reflejada', 'Marcus Thompson', '/images/obras/artwork_027.jpg', 'Estudio de geometrías urbanas y reflejos en superficies de vidrio modernas', 6, 12),
(null, 'Mundo Virtual', 'Yuki Nakamura', '/images/obras/artwork_028.jpg', 'Creación digital que explora realidades alternativas inspiradas en cultura cyberpunk', 12, 13),
(null, 'Avatar Futurista', 'Yuki Nakamura', '/images/obras/artwork_029.jpg', 'Diseño de personaje que fusiona estética anime con elementos tecnológicos avanzados', 15, 13),
(null, 'Renacer Ecológico', 'Amara Okafor', '/images/obras/artwork_030.jpg', 'Escultura de materiales reciclados que aborda crisis medioambiental global', 1, 14),
(null, 'Equilibrio Natural', 'Amara Okafor', '/images/obras/artwork_031.jpg', 'Instalación que representa la harmonía entre desarrollo humano y conservación natural', 1, 14),
(null, 'Mural Ancestral', 'Diego Mendoza', '/images/obras/artwork_032.jpg', 'Fusión de iconografía precolombina con expresión urbana contemporánea mexicana', 2, 15),
(null, 'Códice Urbano', 'Diego Mendoza', '/images/obras/artwork_033.jpg', 'Reinterpretación de códices antiguos en contexto de ciudad moderna', 1, 15),
(null, 'Invierno Ártico', 'Svetlana Petrov', '/images/obras/artwork_034.jpg', 'Paisaje invernal que captura la melancolía y belleza de las regiones nórdicas', 3, 16),
(null, 'Retrato Siberiano', 'Svetlana Petrov', '/images/obras/artwork_035.jpg', 'Estudio de carácter humano enfrentado a la dureza del clima extremo', 2, 16),
(null, 'Geometría Sagrada', 'Ahmed Al-Rashid', '/images/obras/artwork_036.jpg', 'Instalación multimedia que combina caligrafía árabe con tecnología contemporánea', 1, 17),
(null, 'Luz de Mezquita', 'Ahmed Al-Rashid', '/images/obras/artwork_037.jpg', 'Estudio de iluminación inspirado en arquitectura islámica tradicional', 2, 17),
(null, 'Nuevo Impresionismo', 'Marie Dubois', '/images/obras/artwork_038.jpg', 'Reinterpretación feminista de los maestros impresionistas franceses clásicos', 4, 18),
(null, 'Jardín Contemporáneo', 'Marie Dubois', '/images/obras/artwork_039.jpg', 'Visión moderna del jardín francés con perspectiva de género actualizada', 3, 18),
(null, 'Miniatura Mumbai', 'Raj Patel', '/images/obras/artwork_040.jpg', 'Técnica mogol aplicada a escenas urbanas de la India contemporánea', 5, 19),
(null, 'Mercado de Delhi', 'Raj Patel', '/images/obras/artwork_041.jpg', 'Detallado estudio de la vida comercial en mercados tradicionales indios', 4, 19),
(null, 'Mármol Contemporáneo', 'Elena Rossi', '/images/obras/artwork_042.jpg', 'Escultura que dialoga entre tradición renacentista italiana y sensibilidad moderna', 1, 20),
(null, 'Forma Clásica', 'Elena Rossi', '/images/obras/artwork_043.jpg', 'Interpretación contemporánea de ideales estéticos clásicos en mármol de Carrara', 1, 20),
(null, 'Glaciar en Retroceso', 'Benjamin Carter', '/images/obras/artwork_044.jpg', 'Documentación fotográfica del impacto del cambio climático en paisajes árticos', 7, 21),
(null, 'Bosque Primordial', 'Benjamin Carter', '/images/obras/artwork_045.jpg', 'Captura de ecosistemas vírgenes que enfatizan la necesidad de conservación', 6, 21),
(null, 'Textil Bereber', 'Fatima Benali', '/images/obras/artwork_046.jpg', 'Tejido tradicional marroquí actualizado con patrones y colores contemporáneos', 8, 22),
(null, 'Alfombra Narrativa', 'Fatima Benali', '/images/obras/artwork_047.jpg', 'Tapiz que narra historias ancestrales del pueblo bereber con técnicas modernas', 5, 22),
(null, 'Cerámica Wabi-Sabi', 'Hiroshi Tanaka', '/images/obras/artwork_048.jpg', 'Pieza cerámica que abraza la imperfección como principio estético japonés', 10, 23),
(null, 'Cuenco de Té Contemporáneo', 'Hiroshi Tanaka', '/images/obras/artwork_049.jpg', 'Cerámica funcional que moderniza la ceremonia del té tradicional japonesa', 12, 23),
(null, 'Instalación Amazónica', 'Lucia Santos', '/images/obras/artwork_050.jpg', 'Experiencia inmersiva que recrea la biodiversidad de la selva tropical brasileña', 1, 24),
(null, 'Sonidos de la Selva', 'Lucia Santos', '/images/obras/artwork_051.jpg', 'Instalación audiovisual que traduce la comunicación entre especies amazónicas', 1, 24),
(null, 'Fiordo Noruego', 'Olaf Eriksen', '/images/obras/artwork_052.jpg', 'Paisaje nórdico que explora la relación espiritual entre humano y naturaleza ártica', 3, 25),
(null, 'Aurora Boreal', 'Olaf Eriksen', '/images/obras/artwork_053.jpg', 'Captura pictórica del fenómeno lumínico natural del norte de Europa', 2, 25),
(null, 'Identidad Migratoria', 'Priya Sharma', '/images/obras/artwork_054.jpg', 'Instalación interactiva sobre la experiencia de inmigración y identidad cultural', 1, 26),
(null, 'Raíces Digitales', 'Priya Sharma', '/images/obras/artwork_055.jpg', 'Exploración multimedia de la preservación cultural en la era digital', 1, 26),
(null, 'Venecia Contemporánea', 'Alessandro Bianchi', '/images/obras/artwork_056.jpg', 'Veduta moderna que actualiza la tradición pictórica veneciana del siglo XVIII', 4, 27),
(null, 'Canal al Atardecer', 'Alessandro Bianchi', '/images/obras/artwork_057.jpg', 'Estudio de luz sobre arquitectura veneciana con técnicas contemporáneas', 3, 27),
(null, 'Escultura Ancestral', 'Kofi Asante', '/images/obras/artwork_058.jpg', 'Talla en madera africana que narra historias tradicionales del pueblo akan', 1, 28),
(null, 'Máscara Ceremonial', 'Kofi Asante', '/images/obras/artwork_059.jpg', 'Escultura ritual que conecta tradiciones espirituales con expresión artística moderna', 1, 28),
(null, 'Instalación Lumínica', 'Ingrid Larsson', '/images/obras/artwork_060.jpg', 'Obra de LED y vidrio que explora sostenibilidad energética a través del arte', 1, 29),
(null, 'Cristal Sostenible', 'Ingrid Larsson', '/images/obras/artwork_061.jpg', 'Escultura en vidrio reciclado que simboliza la economía circular', 2, 29),
(null, 'Aguafuerte Social', 'Rafael Guerrero', '/images/obras/artwork_062.jpg', 'Grabado tradicional español que aborda problemáticas sociales contemporáneas', 8, 30),
(null, 'Serie Urbana', 'Rafael Guerrero', '/images/obras/artwork_063.jpg', 'Conjunto de grabados que documentan transformaciones urbanas en España', 6, 30),
(null, 'Retrato de Identidad', 'Aisha Williams', '/images/obras/artwork_064.jpg', 'Fotografía que explora la complejidad de la identidad racial en América contemporánea', 9, 31),
(null, 'Herencia Cultural', 'Aisha Williams', '/images/obras/artwork_065.jpg', 'Serie fotográfica que celebra la diversidad y riqueza de la cultura afroamericana', 7, 31),
(null, 'Escultura Cinética', 'Dmitri Volkov', '/images/obras/artwork_066.jpg', 'Obra mecánica inspirada en la estética de la era espacial soviética', 1, 32),
(null, 'Cosmos en Movimiento', 'Dmitri Volkov', '/images/obras/artwork_067.jpg', 'Instalación cinética que simula movimientos planetarios con precisión mecánica', 1, 32),
(null, 'Contrastes Colombianos', 'Carmen Delgado', '/images/obras/artwork_068.jpg', 'Pintura que refleja la riqueza cultural y las tensiones sociales de Colombia', 3, 33),
(null, 'Biodiversidad Tropical', 'Carmen Delgado', '/images/obras/artwork_069.jpg', 'Celebración pictórica de la excepcional biodiversidad del trópico sudamericano', 4, 33),
(null, 'Outback Abstracto', 'James Mitchell', '/images/obras/artwork_070.jpg', 'Pintura abstracta que incorpora arena y pigmentos del desierto australiano', 2, 34),
(null, 'Tierra Roja', 'James Mitchell', '/images/obras/artwork_071.jpg', 'Exploración de texturas y colores del paisaje árido del centro de Australia', 3, 34),
(null, 'Caligrafía del Exilio', 'Noor Al-Zahra', '/images/obras/artwork_072.jpg', 'Instalación conceptual sobre memoria y desplazamiento forzado en Oriente Medio', 1, 35),
(null, 'Cartas Perdidas', 'Noor Al-Zahra', '/images/obras/artwork_073.jpg', 'Obra textual que explora la comunicación interrumpida por conflictos geopolíticos', 2, 35),
(null, 'Narrativa Nórdica', 'Erik Nielsen', '/images/obras/artwork_074.jpg', 'Ilustración que combina mitología escandinava con estética contemporánea', 10, 36),
(null, 'Saga Moderna', 'Erik Nielsen', '/images/obras/artwork_075.jpg', 'Reinterpretación gráfica de historias tradicionales danesas para audiencias actuales', 8, 36),
(null, 'Tapiz Preincaico', 'Catalina Ruiz', '/images/obras/artwork_076.jpg', 'Textil que moderniza técnicas ancestrales peruanas con diseños contemporáneos', 5, 37),
(null, 'Colores de los Andes', 'Catalina Ruiz', '/images/obras/artwork_077.jpg', 'Tapiz que captura la paleta cromática de los paisajes andinos peruanos', 4, 37),
(null, 'Vida Cotidiana Magrebí', 'Hassan Ouni', '/images/obras/artwork_078.jpg', 'Fotografía poética que documenta la cotidianidad en el norte de África', 8, 38),
(null, 'Mercado de Túnez', 'Hassan Ouni', '/images/obras/artwork_079.jpg', 'Captura fotográfica de la actividad comercial en medinas tradicionales', 6, 38),
(null, 'Iconografía Moderna', 'Anastasia Popov', '/images/obras/artwork_080.jpg', 'Reinterpretación contemporánea de la tradición iconográfica ortodoxa búlgara', 3, 39),
(null, 'Santos Contemporáneos', 'Anastasia Popov', '/images/obras/artwork_081.jpg', 'Pintura que actualiza hagiografía tradicional con sensibilidad moderna', 2, 39),
(null, 'Graffiti Caligráfico', 'Tony Chang', '/images/obras/artwork_082.jpg', 'Fusión entre caligrafía china tradicional y arte urbano taiwanés contemporáneo', 6, 40),
(null, 'Caracteres Urbanos', 'Tony Chang', '/images/obras/artwork_083.jpg', 'Exploración de escritura china en contextos de arte callejero moderno', 5, 40),
(null, 'Cerámica Pampeana', 'Isabella Martinez', '/images/obras/artwork_084.jpg', 'Escultura cerámica inspirada en la vastedad de la llanura argentina', 4, 41),
(null, 'Espíritu Gaucho', 'Isabella Martinez', '/images/obras/artwork_085.jpg', 'Obra que captura la esencia cultural de la tradición rural argentina', 3, 41),
(null, 'Pigmentos Africanos', 'Kwame Osei', '/images/obras/artwork_086.jpg', 'Pintura que utiliza pigmentos naturales para narrar mitología tradicional ghanesa', 5, 42),
(null, 'Anansi Stories', 'Kwame Osei', '/images/obras/artwork_087.jpg', 'Serie pictórica basada en historias tradicionales de la cultura akan', 4, 42),
(null, 'Fragilidad Humana', 'Helena Kozlova', '/images/obras/artwork_088.jpg', 'Instalación de vidrio soplado que explora la vulnerabilidad de la condición humana', 1, 43),
(null, 'Transparencias', 'Helena Kozlova', '/images/obras/artwork_089.jpg', 'Escultura en vidrio que juega con luz y transparencia como metáfora existencial', 2, 43),
(null, 'Escultura Sami', 'Samuel Andersson', '/images/obras/artwork_090.jpg', 'Talla en madera que combina tradición sami con diseño escandinavo contemporáneo', 2, 44),
(null, 'Reno del Ártico', 'Samuel Andersson', '/images/obras/artwork_091.jpg', 'Escultura que honra la relación ancestral entre pueblo sami y renos', 1, 44),
(null, 'Jeroglíficos Modernos', 'Yasmin Farouk', '/images/obras/artwork_092.jpg', 'Reinterpretación artística de escritura jeroglífica egipcia en contexto contemporáneo', 6, 45),
(null, 'Papiro Digital', 'Yasmin Farouk', '/images/obras/artwork_093.jpg', 'Fusión entre tradición escriba egipcia y tecnologías de comunicación actuales', 5, 45),
(null, 'Luz Mediterránea', 'Marco Santini', '/images/obras/artwork_094.jpg', 'Paisaje siciliano que captura la calidad lumínica única del Mediterráneo', 4, 46),
(null, 'Volcán Etna', 'Marco Santini', '/images/obras/artwork_095.jpg', 'Estudio pictórico del paisaje volcánico siciliano y su impacto cultural', 3, 46),
(null, 'Ukiyo-e Contemporáneo', 'Keiko Yamamoto', '/images/obras/artwork_096.jpg', 'Xilografía que moderniza la tradición del mundo flotante japonés', 8, 47),
(null, 'Estampas Urbanas', 'Keiko Yamamoto', '/images/obras/artwork_097.jpg', 'Grabado que aplica técnicas tradicionales japonesas a escenas urbanas modernas', 6, 47),
(null, 'Reconciliación', 'Thabo Mthembu', '/images/obras/artwork_098.jpg', 'Escultura en metal reciclado que simboliza esperanza y unidad en Sudáfrica post-apartheid', 1, 48),
(null, 'Ubuntu', 'Thabo Mthembu', '/images/obras/artwork_099.jpg', 'Instalación que explora filosofía africana de interconexión humana universal', 1, 48),
(null, 'Bordado Ancestral', 'Zara Hassan', '/images/obras/artwork_100.jpg', 'Textil que preserva técnicas tradicionales de bordado pakistaní en instalación contemporánea', 3, 49);

INSERT INTO formato_obra(id, obra_id, formato, precio, stock, disponible) VALUES
(null, 1, 'ORIGINAL', 2500000.00, 1, true),
(null, 1, 'ORIGINAL_FIRMADO', 3000000.00, 1, true),
(null, 1, 'IMPRESION_CANVAS', 85000.00, 10, true),
(null, 1, 'IMPRESION_PREMIUM', 120000.00, 5, true),

(null, 2, 'ORIGINAL', 2200000.00, 1, true),
(null, 2, 'DIGITAL', 22000.00, 999, true),
(null, 2, 'IMPRESION_CANVAS', 75000.00, 8, true),

(null, 3, 'ORIGINAL', 1800000.00, 2, true),
(null, 3, 'ORIGINAL_FIRMADO', 2200000.00, 1, true),
(null, 3, 'IMPRESION_CANVAS', 95000.00, 15, true),
(null, 3, 'IMPRESION_PREMIUM', 135000.00, 8, true),
(null, 3, 'DIGITAL', 28000.00, 999, true),

(null, 4, 'ORIGINAL', 1650000.00, 1, true),
(null, 4, 'DIGITAL', 26000.00, 999, true),

(null, 5, 'ORIGINAL', 1750000.00, 3, true),
(null, 5, 'ORIGINAL_FIRMADO', 2100000.00, 2, true),
(null, 5, 'IMPRESION_CANVAS', 92000.00, 20, true),

(null, 6, 'ORIGINAL', 2100000.00, 2, true),
(null, 6, 'IMPRESION_CANVAS', 105000.00, 12, true),
(null, 6, 'IMPRESION_PREMIUM', 145000.00, 6, true),
(null, 6, 'DIGITAL', 32000.00, 999, true),

(null, 7, 'ORIGINAL', 1950000.00, 1, true),

(null, 8, 'ORIGINAL', 2000000.00, 1, true),
(null, 8, 'ORIGINAL_FIRMADO', 2500000.00, 1, true),
(null, 8, 'IMPRESION_CANVAS', 102000.00, 8, true),
(null, 8, 'DIGITAL', 31000.00, 999, true),

(null, 9, 'ORIGINAL', 1500000.00, 4, true),
(null, 9, 'IMPRESION_CANVAS', 78000.00, 25, true),
(null, 9, 'DIGITAL', 24000.00, 999, true),

(null, 10, 'ORIGINAL', 1450000.00, 2, true),
(null, 10, 'ORIGINAL_FIRMADO', 1800000.00, 1, true),
(null, 10, 'IMPRESION_CANVAS', 75000.00, 18, true),
(null, 10, 'IMPRESION_PREMIUM', 110000.00, 10, true),

(null, 11, 'ORIGINAL', 1600000.00, 3, true),
(null, 11, 'DIGITAL', 25000.00, 999, true),

(null, 12, 'ORIGINAL', 1400000.00, 1, true),
(null, 12, 'ORIGINAL_FIRMADO', 1750000.00, 1, true),
(null, 12, 'IMPRESION_CANVAS', 95000.00, 8, true),
(null, 12, 'DIGITAL', 35000.00, 999, true),

(null, 13, 'ORIGINAL', 890000.00, 2, true),
(null, 13, 'IMPRESION_CANVAS', 68000.00, 12, true),
(null, 13, 'DIGITAL', 28000.00, 999, true),

(null, 14, 'ORIGINAL', 1350000.00, 1, true),
(null, 14, 'ORIGINAL_FIRMADO', 1680000.00, 1, true),
(null, 14, 'IMPRESION_CANVAS', 88000.00, 6, true),
(null, 14, 'IMPRESION_PREMIUM', 125000.00, 3, true),
(null, 14, 'DIGITAL', 42000.00, 999, true),

(null, 15, 'ORIGINAL', 1500000.00, 1, true),
(null, 15, 'DIGITAL', 45000.00, 999, true),

(null, 16, 'ORIGINAL', 480000.00, 3, true),
(null, 16, 'IMPRESION_CANVAS', 35000.00, 15, true),
(null, 16, 'DIGITAL', 15000.00, 999, true),

(null, 17, 'ORIGINAL', 620000.00, 2, true),
(null, 17, 'IMPRESION_CANVAS', 42000.00, 12, true),
(null, 17, 'IMPRESION_PREMIUM', 58000.00, 8, true),
(null, 17, 'DIGITAL', 18000.00, 999, true),

(null, 18, 'ORIGINAL', 320000.00, 5, true),
(null, 18, 'DIGITAL', 12000.00, 999, true),
(null, 18, 'DIGITAL_NFT', 45000.00, 50, true),

(null, 19, 'ORIGINAL', 380000.00, 4, true),
(null, 19, 'IMPRESION_CANVAS', 32000.00, 20, true),
(null, 19, 'DIGITAL', 14000.00, 999, true),
(null, 19, 'DIGITAL_NFT', 52000.00, 40, true),

(null, 20, 'ORIGINAL', 1800000.00, 1, true),
(null, 20, 'ORIGINAL_FIRMADO', 2250000.00, 1, true),
(null, 20, 'IMPRESION_CANVAS', 125000.00, 3, true),

(null, 21, 'DIGITAL', 35000.00, 999, true),
(null, 21, 'DIGITAL_NFT', 125000.00, 10, true),
(null, 21, 'IMPRESION_CANVAS', 65000.00, 15, true),
(null, 21, 'IMPRESION_PREMIUM', 85000.00, 8, true),

(null, 22, 'DIGITAL', 28000.00, 999, true),
(null, 22, 'DIGITAL_NFT', 95000.00, 15, true),

(null, 23, 'ORIGINAL', 140000.00, 5, true),
(null, 23, 'IMPRESION_CANVAS', 18000.00, 30, true),
(null, 23, 'DIGITAL', 8000.00, 999, true),

(null, 24, 'ORIGINAL', 165000.00, 4, true),
(null, 24, 'IMPRESION_CANVAS', 22000.00, 25, true),
(null, 24, 'DIGITAL', 9500.00, 999, true),

(null, 25, 'ORIGINAL', 180000.00, 3, true),
(null, 25, 'DIGITAL', 8500.00, 999, true),

(null, 26, 'ORIGINAL', 240000.00, 2, true),
(null, 26, 'DIGITAL', 12000.00, 999, true),

(null, 27, 'ORIGINAL', 220000.00, 3, true),
(null, 27, 'IMPRESION_CANVAS', 28000.00, 15, true),
(null, 27, 'DIGITAL', 11000.00, 999, true),

(null, 28, 'ORIGINAL', 340000.00, 2, true),
(null, 28, 'DIGITAL', 15000.00, 999, true),

(null, 29, 'ORIGINAL', 520000.00, 1, true),
(null, 29, 'DIGITAL', 22000.00, 999, true),
(null, 29, 'DIGITAL_NFT', 75000.00, 25, true),

(null, 30, 'ORIGINAL', 160000.00, 4, true),
(null, 30, 'IMPRESION_CANVAS', 20000.00, 20, true),
(null, 30, 'DIGITAL', 9000.00, 999, true),

(null, 31, 'ORIGINAL', 200000.00, 3, true),
(null, 31, 'IMPRESION_CANVAS', 25000.00, 15, true),

(null, 32, 'ORIGINAL', 140000.00, 5, true),
(null, 32, 'DIGITAL', 8000.00, 999, true),

(null, 33, 'ORIGINAL', 165000.00, 4, true),
(null, 33, 'IMPRESION_CANVAS', 22000.00, 18, true),
(null, 33, 'DIGITAL', 9500.00, 999, true),

(null, 34, 'ORIGINAL', 95000.00, 8, true),
(null, 34, 'DIGITAL', 6000.00, 999, true),

(null, 35, 'ORIGINAL', 110000.00, 6, true),
(null, 35, 'IMPRESION_CANVAS', 15000.00, 25, true),
(null, 35, 'DIGITAL', 7000.00, 999, true),

(null, 36, 'ORIGINAL', 85000.00, 12, true),
(null, 36, 'DIGITAL', 5500.00, 999, true),

(null, 37, 'ORIGINAL', 75000.00, 15, true),
(null, 37, 'IMPRESION_CANVAS', 12000.00, 30, true),
(null, 37, 'DIGITAL', 5000.00, 999, true),

(null, 38, 'ORIGINAL', 450000.00, 1, true),
(null, 38, 'DIGITAL', 18000.00, 999, true),

(null, 39, 'ORIGINAL', 380000.00, 1, true),
(null, 39, 'IMPRESION_CANVAS', 38000.00, 10, true),
(null, 39, 'DIGITAL', 16000.00, 999, true),

(null, 40, 'ORIGINAL', 220000.00, 2, true),
(null, 40, 'DIGITAL', 11000.00, 999, true),

(null, 41, 'ORIGINAL', 290000.00, 1, true),
(null, 41, 'IMPRESION_CANVAS', 32000.00, 12, true),
(null, 41, 'DIGITAL', 14000.00, 999, true),

(null, 42, 'ORIGINAL', 180000.00, 3, true),

(null, 43, 'ORIGINAL', 240000.00, 2, true),
(null, 43, 'IMPRESION_CANVAS', 28000.00, 15, true),
(null, 43, 'DIGITAL', 12000.00, 999, true),

(null, 44, 'ORIGINAL', 520000.00, 1, true),
(null, 44, 'DIGITAL', 22000.00, 999, true),

(null, 45, 'ORIGINAL', 340000.00, 2, true),
(null, 45, 'IMPRESION_CANVAS', 35000.00, 10, true),
(null, 45, 'DIGITAL', 15000.00, 999, true),

(null, 46, 'ORIGINAL', 160000.00, 4, true),
(null, 46, 'DIGITAL', 8500.00, 999, true),

(null, 47, 'ORIGINAL', 200000.00, 3, true),
(null, 47, 'IMPRESION_CANVAS', 24000.00, 18, true),
(null, 47, 'DIGITAL', 10000.00, 999, true),

(null, 48, 'ORIGINAL', 140000.00, 5, true),
(null, 48, 'DIGITAL', 8000.00, 999, true),

(null, 49, 'ORIGINAL', 165000.00, 4, true),
(null, 49, 'IMPRESION_CANVAS', 20000.00, 22, true),
(null, 49, 'IMPRESION_PREMIUM', 28000.00, 12, true),
(null, 49, 'DIGITAL', 9500.00, 999, true),

(null, 50, 'ORIGINAL', 180000.00, 3, true),
(null, 50, 'DIGITAL', 9000.00, 999, true),

(null, 51, 'ORIGINAL', 95000.00, 8, true),
(null, 51, 'DIGITAL', 6000.00, 999, true),

(null, 52, 'ORIGINAL', 110000.00, 6, true),
(null, 52, 'IMPRESION_CANVAS', 14000.00, 25, true),
(null, 52, 'DIGITAL', 7000.00, 999, true),

(null, 53, 'ORIGINAL', 85000.00, 12, true),
(null, 53, 'DIGITAL', 5500.00, 999, true),

(null, 55, 'ORIGINAL', 88000.00, 10, true),
(null, 55, 'IMPRESION_CANVAS', 12000.00, 35, true),
(null, 55, 'DIGITAL', 5800.00, 999, true),

(null, 56, 'ORIGINAL', 65000.00, 12, true),
(null, 56, 'DIGITAL', 4500.00, 999, true),

(null, 57, 'ORIGINAL', 890000.00, 1, true),
(null, 57, 'IMPRESION_CANVAS', 85000.00, 5, true),
(null, 57, 'DIGITAL', 35000.00, 999, true),

(null, 58, 'ORIGINAL', 720000.00, 1, true),
(null, 58, 'DIGITAL', 28000.00, 999, true),

(null, 59, 'ORIGINAL', 210000.00, 3, true),
(null, 59, 'IMPRESION_CANVAS', 26000.00, 18, true),
(null, 59, 'DIGITAL', 11000.00, 999, true),

(null, 60, 'ORIGINAL', 280000.00, 2, true),
(null, 60, 'DIGITAL', 14000.00, 999, true),

(null, 61, 'ORIGINAL', 650000.00, 1, true),
(null, 61, 'IMPRESION_CANVAS', 65000.00, 8, true),
(null, 61, 'DIGITAL', 25000.00, 999, true),

(null, 62, 'ORIGINAL', 580000.00, 1, true),
(null, 62, 'DIGITAL', 23000.00, 999, true),

(null, 63, 'ORIGINAL', 190000.00, 4, true),
(null, 63, 'IMPRESION_CANVAS', 22000.00, 20, true),
(null, 63, 'DIGITAL', 9500.00, 999, true),

(null, 64, 'ORIGINAL', 220000.00, 3, true),
(null, 64, 'DIGITAL', 11000.00, 999, true),

(null, 65, 'ORIGINAL', 420000.00, 1, true),
(null, 65, 'IMPRESION_CANVAS', 42000.00, 10, true),
(null, 65, 'IMPRESION_PREMIUM', 58000.00, 6, true),
(null, 65, 'DIGITAL', 18000.00, 999, true),

(null, 66, 'ORIGINAL', 380000.00, 1, true),
(null, 66, 'DIGITAL', 16000.00, 999, true),

(null, 67, 'ORIGINAL', 220000.00, 2, true),
(null, 67, 'IMPRESION_CANVAS', 26000.00, 15, true),
(null, 67, 'DIGITAL', 11000.00, 999, true),

(null, 68, 'ORIGINAL', 290000.00, 1, true),
(null, 68, 'DIGITAL', 14000.00, 999, true),

(null, 69, 'ORIGINAL', 180000.00, 3, true),
(null, 69, 'IMPRESION_CANVAS', 22000.00, 18, true),
(null, 69, 'DIGITAL', 9000.00, 999, true),

(null, 70, 'ORIGINAL', 240000.00, 2, true),
(null, 70, 'DIGITAL', 12000.00, 999, true),

(null, 71, 'ORIGINAL', 780000.00, 1, true),
(null, 71, 'IMPRESION_CANVAS', 75000.00, 6, true),
(null, 71, 'IMPRESION_PREMIUM', 95000.00, 3, true),
(null, 71, 'DIGITAL', 32000.00, 999, true),

(null, 72, 'ORIGINAL', 540000.00, 2, true),
(null, 72, 'DIGITAL', 22000.00, 999, true),

(null, 73, 'ORIGINAL', 75000.00, 8, true),
(null, 73, 'IMPRESION_CANVAS', 10000.00, 40, true),
(null, 73, 'DIGITAL', 5000.00, 999, true),

(null, 75, 'ORIGINAL', 110000.00, 9, true),
(null, 75, 'IMPRESION_CANVAS', 14000.00, 35, true),
(null, 75, 'DIGITAL', 7000.00, 999, true),

(null, 76, 'ORIGINAL', 125000.00, 7, true),
(null, 76, 'DIGITAL', 8000.00, 999, true),

(null, 77, 'ORIGINAL', 920000.00, 1, true),
(null, 77, 'IMPRESION_CANVAS', 88000.00, 5, true),
(null, 77, 'DIGITAL', 38000.00, 999, true),

(null, 78, 'ORIGINAL', 1100000.00, 1, true),
(null, 78, 'DIGITAL', 45000.00, 999, true),

(null, 79, 'ORIGINAL', 180000.00, 3, true),
(null, 79, 'IMPRESION_CANVAS', 22000.00, 20, true),
(null, 79, 'DIGITAL', 9000.00, 999, true),

(null, 80, 'ORIGINAL', 160000.00, 4, true),
(null, 80, 'DIGITAL', 8500.00, 999, true),

(null, 81, 'ORIGINAL', 340000.00, 2, true),
(null, 81, 'IMPRESION_CANVAS', 35000.00, 12, true),
(null, 81, 'IMPRESION_PREMIUM', 48000.00, 8, true),
(null, 81, 'DIGITAL', 15000.00, 999, true),

(null, 82, 'ORIGINAL', 290000.00, 3, true),
(null, 82, 'DIGITAL', 14000.00, 999, true),

(null, 83, 'ORIGINAL', 680000.00, 1, true),
(null, 83, 'IMPRESION_CANVAS', 65000.00, 8, true),
(null, 83, 'DIGITAL', 28000.00, 999, true),

(null, 84, 'ORIGINAL', 450000.00, 2, true),
(null, 84, 'DIGITAL', 18000.00, 999, true),

(null, 85, 'ORIGINAL', 85000.00, 10, true),
(null, 85, 'IMPRESION_CANVAS', 12000.00, 40, true),
(null, 85, 'DIGITAL', 5500.00, 999, true),

(null, 86, 'ORIGINAL', 95000.00, 8, true),
(null, 86, 'DIGITAL', 6000.00, 999, true),

(null, 87, 'ORIGINAL', 280000.00, 4, true),
(null, 87, 'IMPRESION_CANVAS', 32000.00, 16, true),
(null, 87, 'DIGITAL', 14000.00, 999, true),

(null, 88, 'ORIGINAL', 190000.00, 3, true),
(null, 88, 'DIGITAL', 9500.00, 999, true),

(null, 89, 'ORIGINAL', 95000.00, 8, true),
(null, 89, 'IMPRESION_CANVAS', 13000.00, 35, true),
(null, 89, 'DIGITAL', 6000.00, 999, true),

(null, 90, 'ORIGINAL', 180000.00, 5, true),
(null, 90, 'DIGITAL', 9000.00, 999, true),

(null, 91, 'ORIGINAL', 88000.00, 10, true),
(null, 91, 'IMPRESION_CANVAS', 12000.00, 38, true),
(null, 91, 'DIGITAL', 5800.00, 999, true),

(null, 92, 'ORIGINAL', 65000.00, 12, true),
(null, 92, 'DIGITAL', 4500.00, 999, true),

(null, 93, 'ORIGINAL', 890000.00, 1, true),
(null, 93, 'IMPRESION_CANVAS', 85000.00, 6, true),
(null, 93, 'IMPRESION_PREMIUM', 115000.00, 3, true),
(null, 93, 'DIGITAL', 35000.00, 999, true),

(null, 95, 'ORIGINAL', 210000.00, 3, true),
(null, 95, 'IMPRESION_CANVAS', 26000.00, 18, true),
(null, 95, 'DIGITAL', 11000.00, 999, true),

(null, 96, 'ORIGINAL', 280000.00, 2, true),
(null, 96, 'DIGITAL', 14000.00, 999, true),

(null, 97, 'ORIGINAL', 140000.00, 8, true),
(null, 97, 'IMPRESION_CANVAS', 18000.00, 32, true),
(null, 97, 'DIGITAL', 8000.00, 999, true),

(null, 98, 'ORIGINAL', 165000.00, 6, true),
(null, 98, 'DIGITAL', 9500.00, 999, true),

(null, 99, 'ORIGINAL', 420000.00, 1, true),
(null, 99, 'IMPRESION_CANVAS', 42000.00, 10, true),
(null, 99, 'DIGITAL', 18000.00, 999, true),

(null, 100, 'ORIGINAL', 380000.00, 1, true),
(null, 100, 'DIGITAL', 16000.00, 999, true);


-- Asignar categorías a las obras (las obras se numeran desde 1)
INSERT INTO obra_categorias (obra_id, categoria) VALUES
-- Leonardo Da Vinci (1-2)
(1, 'PINTURA'), (1, 'RETRATO'),
(2, 'PINTURA'), (2, 'RETRATO'),

-- Van Gogh (3-5)
(3, 'PINTURA'), (3, 'MODERNO'),
(4, 'PINTURA'), (4, 'MODERNO'),
(5, 'PINTURA'), (5, 'MODERNO'),

-- Picasso (6-8)
(6, 'PINTURA'), (6, 'ABSTRACTO'),
(7, 'PINTURA'), (7, 'ABSTRACTO'),
(8, 'PINTURA'), (8, 'ABSTRACTO'),

-- Monet (9-11)
(9, 'PINTURA'), (9, 'MODERNO'),
(10, 'PINTURA'), (10, 'MODERNO'),
(11, 'PINTURA'), (11, 'MODERNO'),

-- Frida Kahlo (12-13)
(12, 'PINTURA'), (12, 'RETRATO'),
(13, 'PINTURA'), (13, 'SURREALISMO'),

-- Salvador Dalí (14-15)
(14, 'PINTURA'), (14, 'SURREALISMO'),
(15, 'PINTURA'), (15, 'SURREALISMO'),

-- Georgia O’Keeffe (16-17)
(16, 'PINTURA'), (16, 'ABSTRACTO'),
(17, 'PINTURA'), (17, 'ABSTRACTO'),

-- Andy Warhol (18-19)
(18, 'ARTE_MIXTO'), (18, 'MODERNO'),
(19, 'ARTE_MIXTO'), (19, 'MODERNO'),

-- Jackson Pollock (20-21)
(20, 'PINTURA'), (20, 'ABSTRACTO'),
(21, 'PINTURA'), (21, 'ABSTRACTO'),

-- Kandinsky (22-23)
(22, 'PINTURA'), (22, 'ABSTRACTO'), (22, 'COSMICO'),
(23, 'PINTURA'), (23, 'ABSTRACTO'), (23, 'COSMICO'),

-- Isabella Chen (24-25)
(24, 'PINTURA'), (24, 'ARTE_MIXTO'),
(25, 'PINTURA'), (25, 'ARTE_MIXTO'),

-- Marcus Thompson (26-27)
(26, 'FOTOGRAFIA'),
(27, 'FOTOGRAFIA'),

-- Yuki Nakamura (28-29)
(28, 'ARTE_DIGITAL'),
(29, 'ARTE_DIGITAL'),

-- Amara Okafor (30-31)
(30, 'ESCULTURA'),
(31, 'ARTE_MIXTO'),

-- Diego Mendoza (32-33)
(32, 'ARTE_MIXTO'),
(33, 'ARTE_MIXTO'),

-- Svetlana Petrov (34-35)
(34, 'PINTURA'),
(35, 'PINTURA'),

-- Ahmed Al-Rashid (36-37)
(36, 'ARTE_MIXTO'),
(37, 'ARTE_MIXTO'),

-- Marie Dubois (38-39)
(38, 'PINTURA'), (38, 'MODERNO'),
(39, 'PINTURA'), (39, 'MODERNO'),

-- Raj Patel (40-41)
(40, 'ARTE_MIXTO'),
(41, 'ARTE_MIXTO'),

-- Elena Rossi (42-43)
(42, 'ESCULTURA'),
(43, 'ESCULTURA'),

-- Benjamin Carter (44-45)
(44, 'FOTOGRAFIA'),
(45, 'FOTOGRAFIA'),

-- Fatima Benali (46-47)
(46, 'ARTE_TEXTIL'),
(47, 'ARTE_TEXTIL'),

-- Hiroshi Tanaka (48-49)
(48, 'ARTE_CERAMICO'),
(49, 'ARTE_CERAMICO'),

-- Lucia Santos (50-51)
(50, 'ARTE_MIXTO'),
(51, 'ARTE_MIXTO'),

-- Olaf Eriksen (52-53)
(52, 'PINTURA'),
(53, 'PINTURA'),

-- Priya Sharma (54-55)
(54, 'ARTE_MIXTO'),
(55, 'ARTE_MIXTO'),

-- Alessandro Bianchi (56-57)
(56, 'PINTURA'),
(57, 'PINTURA'),

-- Kofi Asante (58-59)
(58, 'ESCULTURA'),
(59, 'ESCULTURA'),

-- Ingrid Larsson (60-61)
(60, 'ARTE_MIXTO'),
(61, 'ARTE_MIXTO'),

-- Rafael Guerrero (62-63)
(62, 'DIBUJO'),
(63, 'DIBUJO'),

-- Aisha Williams (64-65)
(64, 'FOTOGRAFIA'),
(65, 'FOTOGRAFIA'),

-- Dmitri Volkov (66-67)
(66, 'ESCULTURA'),
(67, 'ESCULTURA'),

-- Carmen Delgado (68-69)
(68, 'PINTURA'),
(69, 'PINTURA'),

-- James Mitchell (70-71)
(70, 'PINTURA'), (70, 'ABSTRACTO'),
(71, 'PINTURA'), (71, 'ABSTRACTO'),

-- Noor Al-Zahra (72-73)
(72, 'ARTE_MIXTO'),
(73, 'ARTE_MIXTO'),

-- Erik Nielsen (74-75)
(74, 'DIBUJO'),
(75, 'DIBUJO'),

-- Catalina Ruiz (76-77)
(76, 'ARTE_TEXTIL'),
(77, 'ARTE_TEXTIL'),

-- Hassan Ouni (78-79)
(78, 'FOTOGRAFIA'),
(79, 'FOTOGRAFIA'),

-- Anastasia Popov (80-81)
(80, 'PINTURA'),
(81, 'PINTURA'),

-- Tony Chang (82-83)
(82, 'ARTE_MIXTO'),
(83, 'ARTE_MIXTO'),

-- Isabella Martinez (84-85)
(84, 'ESCULTURA'),
(85, 'ESCULTURA'),

-- Kwame Osei (86-87)
(86, 'PINTURA'),
(87, 'PINTURA'),

-- Helena Kozlova (88-89)
(88, 'ESCULTURA'),
(89, 'ESCULTURA'),

-- Samuel Andersson (90-91)
(90, 'ESCULTURA'),
(91, 'ESCULTURA'),

-- Yasmin Farouk (92-93)
(92, 'PINTURA'),
(93, 'PINTURA'),

-- Marco Santini (94-95)
(94, 'PINTURA'),
(95, 'PINTURA'),

-- Keiko Yamamoto (96-97)
(96, 'DIBUJO'),
(97, 'DIBUJO'),

-- Thabo Mthembu (98-99)
(98, 'ESCULTURA'),
(99, 'ARTE_MIXTO'),

-- Zara Hassan (100)
(100, 'ARTE_TEXTIL');