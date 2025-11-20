-- ============================================
-- SETUP COMPLETO DE LA BASE DE DATOS
-- Carga de artistas, usuarios y likes
-- ============================================

-- 1. CREAR USUARIOS PARA ARTISTAS 2-50
-- Email pattern: firstname@unlam.edu.ar
-- Password: firstname (encriptado con BCrypt)
-- Rol: ARTISTA, Estado: activo

INSERT INTO Usuario (email, password, nombre, rol, activo) VALUES
('sofia@unlam.edu.ar', '$2b$10$slYQmyNdGzin7olVN3/Adu8bLPU0w6JjN4CJvDt5D3/9w7gJMJvne', 'Sofia', 'ARTISTA', true),
('carmen@unlam.edu.ar', '$2b$10$slYQmyNdGzin7olVN3/Adu8bLPU0w6JjN4CJvDt5D3/9w7gJMJvne', 'Carmen', 'ARTISTA', true),
('ana@unlam.edu.ar', '$2b$10$slYQmyNdGzin7olVN3/Adu8bLPU0w6JjN4CJvDt5D3/9w7gJMJvne', 'Ana', 'ARTISTA', true),
('maria@unlam.edu.ar', '$2b$10$slYQmyNdGzin7olVN3/Adu8bLPU0w6JjN4CJvDt5D3/9w7gJMJvne', 'Maria', 'ARTISTA', true),
('valeria@unlam.edu.ar', '$2b$10$slYQmyNdGzin7olVN3/Adu8bLPU0w6JjN4CJvDt5D3/9w7gJMJvne', 'Valeria', 'ARTISTA', true),
('isabel@unlam.edu.ar', '$2b$10$slYQmyNdGzin7olVN3/Adu8bLPU0w6JjN4CJvDt5D3/9w7gJMJvne', 'Isabel', 'ARTISTA', true),
('lucia@unlam.edu.ar', '$2b$10$slYQmyNdGzin7olVN3/Adu8bLPU0w6JjN4CJvDt5D3/9w7gJMJvne', 'Lucia', 'ARTISTA', true),
('amanda@unlam.edu.ar', '$2b$10$slYQmyNdGzin7olVN3/Adu8bLPU0w6JjN4CJvDt5D3/9w7gJMJvne', 'Amanda', 'ARTISTA', true),
('natalia@unlam.edu.ar', '$2b$10$slYQmyNdGzin7olVN3/Adu8bLPU0w6JjN4CJvDt5D3/9w7gJMJvne', 'Natalia', 'ARTISTA', true),
('gabriela@unlam.edu.ar', '$2b$10$slYQmyNdGzin7olVN3/Adu8bLPU0w6JjN4CJvDt5D3/9w7gJMJvne', 'Gabriela', 'ARTISTA', true),
('patricia@unlam.edu.ar', '$2b$10$slYQmyNdGzin7olVN3/Adu8bLPU0w6JjN4CJvDt5D3/9w7gJMJvne', 'Patricia', 'ARTISTA', true),
('monica@unlam.edu.ar', '$2b$10$slYQmyNdGzin7olVN3/Adu8bLPU0w6JjN4CJvDt5D3/9w7gJMJvne', 'Monica', 'ARTISTA', true),
('daniela@unlam.edu.ar', '$2b$10$slYQmyNdGzin7olVN3/Adu8bLPU0w6JjN4CJvDt5D3/9w7gJMJvne', 'Daniela', 'ARTISTA', true),
('cristina@unlam.edu.ar', '$2b$10$slYQmyNdGzin7olVN3/Adu8bLPU0w6JjN4CJvDt5D3/9w7gJMJvne', 'Cristina', 'ARTISTA', true),
('veronica@unlam.edu.ar', '$2b$10$slYQmyNdGzin7olVN3/Adu8bLPU0w6JjN4CJvDt5D3/9w7gJMJvne', 'Veronica', 'ARTISTA', true),
('adriana@unlam.edu.ar', '$2b$10$slYQmyNdGzin7olVN3/Adu8bLPU0w6JjN4CJvDt5D3/9w7gJMJvne', 'Adriana', 'ARTISTA', true),
('lorena@unlam.edu.ar', '$2b$10$slYQmyNdGzin7olVN3/Adu8bLPU0w6JjN4CJvDt5D3/9w7gJMJvne', 'Lorena', 'ARTISTA', true),
('fernanda@unlam.edu.ar', '$2b$10$slYQmyNdGzin7olVN3/Adu8bLPU0w6JjN4CJvDt5D3/9w7gJMJvne', 'Fernanda', 'ARTISTA', true),
('alejandra@unlam.edu.ar', '$2b$10$slYQmyNdGzin7olVN3/Adu8bLPU0w6JjN4CJvDt5D3/9w7gJMJvne', 'Alejandra', 'ARTISTA', true),
('camila@unlam.edu.ar', '$2b$10$slYQmyNdGzin7olVN3/Adu8bLPU0w6JjN4CJvDt5D3/9w7gJMJvne', 'Camila', 'ARTISTA', true),
('roxana@unlam.edu.ar', '$2b$10$slYQmyNdGzin7olVN3/Adu8bLPU0w6JjN4CJvDt5D3/9w7gJMJvne', 'Roxana', 'ARTISTA', true),
('silvia@unlam.edu.ar', '$2b$10$slYQmyNdGzin7olVN3/Adu8bLPU0w6JjN4CJvDt5D3/9w7gJMJvne', 'Silvia', 'ARTISTA', true),
('carolina@unlam.edu.ar', '$2b$10$slYQmyNdGzin7olVN3/Adu8bLPU0w6JjN4CJvDt5D3/9w7gJMJvne', 'Carolina', 'ARTISTA', true),
('mariana@unlam.edu.ar', '$2b$10$slYQmyNdGzin7olVN3/Adu8bLPU0w6JjN4CJvDt5D3/9w7gJMJvne', 'Mariana', 'ARTISTA', true),
('tomas@unlam.edu.ar', '$2b$10$slYQmyNdGzin7olVN3/Adu8bLPU0w6JjN4CJvDt5D3/9w7gJMJvne', 'Tomas', 'ARTISTA', true),
('martin@unlam.edu.ar', '$2b$10$slYQmyNdGzin7olVN3/Adu8bLPU0w6JjN4CJvDt5D3/9w7gJMJvne', 'Martin', 'ARTISTA', true),
('diego@unlam.edu.ar', '$2b$10$slYQmyNdGzin7olVN3/Adu8bLPU0w6JjN4CJvDt5D3/9w7gJMJvne', 'Diego', 'ARTISTA', true),
('luis@unlam.edu.ar', '$2b$10$slYQmyNdGzin7olVN3/Adu8bLPU0w6JjN4CJvDt5D3/9w7gJMJvne', 'Luis', 'ARTISTA', true),
('carlos@unlam.edu.ar', '$2b$10$slYQmyNdGzin7olVN3/Adu8bLPU0w6JjN4CJvDt5D3/9w7gJMJvne', 'Carlos', 'ARTISTA', true),
('andres@unlam.edu.ar', '$2b$10$slYQmyNdGzin7olVN3/Adu8bLPU0w6JjN4CJvDt5D3/9w7gJMJvne', 'Andres', 'ARTISTA', true),
('francisco@unlam.edu.ar', '$2b$10$slYQmyNdGzin7olVN3/Adu8bLPU0w6JjN4CJvDt5D3/9w7gJMJvne', 'Francisco', 'ARTISTA', true),
('roberto@unlam.edu.ar', '$2b$10$slYQmyNdGzin7olVN3/Adu8bLPU0w6JjN4CJvDt5D3/9w7gJMJvne', 'Roberto', 'ARTISTA', true),
('pedro@unlam.edu.ar', '$2b$10$slYQmyNdGzin7olVN3/Adu8bLPU0w6JjN4CJvDt5D3/9w7gJMJvne', 'Pedro', 'ARTISTA', true),
('sebastian@unlam.edu.ar', '$2b$10$slYQmyNdGzin7olVN3/Adu8bLPU0w6JjN4CJvDt5D3/9w7gJMJvne', 'Sebastian', 'ARTISTA', true),
('emilio@unlam.edu.ar', '$2b$10$slYQmyNdGzin7olVN3/Adu8bLPU0w6JjN4CJvDt5D3/9w7gJMJvne', 'Emilio', 'ARTISTA', true),
('joaquin@unlam.edu.ar', '$2b$10$slYQmyNdGzin7olVN3/Adu8bLPU0w6JjN4CJvDt5D3/9w7gJMJvne', 'Joaquin', 'ARTISTA', true),
('alejandro@unlam.edu.ar', '$2b$10$slYQmyNdGzin7olVN3/Adu8bLPU0w6JjN4CJvDt5D3/9w7gJMJvne', 'Alejandro', 'ARTISTA', true),
('raul@unlam.edu.ar', '$2b$10$slYQmyNdGzin7olVN3/Adu8bLPU0w6JjN4CJvDt5D3/9w7gJMJvne', 'Raul', 'ARTISTA', true),
('nicolas@unlam.edu.ar', '$2b$10$slYQmyNdGzin7olVN3/Adu8bLPU0w6JjN4CJvDt5D3/9w7gJMJvne', 'Nicolas', 'ARTISTA', true),
('mauricio@unlam.edu.ar', '$2b$10$slYQmyNdGzin7olVN3/Adu8bLPU0w6JjN4CJvDt5D3/9w7gJMJvne', 'Mauricio', 'ARTISTA', true),
('gustavo@unlam.edu.ar', '$2b$10$slYQmyNdGzin7olVN3/Adu8bLPU0w6JjN4CJvDt5D3/9w7gJMJvne', 'Gustavo', 'ARTISTA', true),
('oscar@unlam.edu.ar', '$2b$10$slYQmyNdGzin7olVN3/Adu8bLPU0w6JjN4CJvDt5D3/9w7gJMJvne', 'Oscar', 'ARTISTA', true),
('ricardo@unlam.edu.ar', '$2b$10$slYQmyNdGzin7olVN3/Adu8bLPU0w6JjN4CJvDt5D3/9w7gJMJvne', 'Ricardo', 'ARTISTA', true),
('ivan@unlam.edu.ar', '$2b$10$slYQmyNdGzin7olVN3/Adu8bLPU0w6JjN4CJvDt5D3/9w7gJMJvne', 'Ivan', 'ARTISTA', true),
('felipe@unlam.edu.ar', '$2b$10$slYQmyNdGzin7olVN3/Adu8bLPU0w6JjN4CJvDt5D3/9w7gJMJvne', 'Felipe', 'ARTISTA', true),
('manuel@unlam.edu.ar', '$2b$10$slYQmyNdGzin7olVN3/Adu8bLPU0w6JjN4CJvDt5D3/9w7gJMJvne', 'Manuel', 'ARTISTA', true),
('hector@unlam.edu.ar', '$2b$10$slYQmyNdGzin7olVN3/Adu8bLPU0w6JjN4CJvDt5D3/9w7gJMJvne', 'Hector', 'ARTISTA', true),
('pablo@unlam.edu.ar', '$2b$10$slYQmyNdGzin7olVN3/Adu8bLPU0w6JjN4CJvDt5D3/9w7gJMJvne', 'Pablo', 'ARTISTA', true),
('alvaro@unlam.edu.ar', '$2b$10$slYQmyNdGzin7olVN3/Adu8bLPU0w6JjN4CJvDt5D3/9w7gJMJvne', 'Alvaro', 'ARTISTA', true);

-- 2. VINCULAR USUARIOS CON ARTISTAS (2-50)
-- Usando subqueries para encontrar el ID del usuario por email
UPDATE Artista SET usuario_id = (SELECT id FROM Usuario WHERE email = 'sofia@unlam.edu.ar') WHERE id = 2;
UPDATE Artista SET usuario_id = (SELECT id FROM Usuario WHERE email = 'carmen@unlam.edu.ar') WHERE id = 3;
UPDATE Artista SET usuario_id = (SELECT id FROM Usuario WHERE email = 'ana@unlam.edu.ar') WHERE id = 4;
UPDATE Artista SET usuario_id = (SELECT id FROM Usuario WHERE email = 'maria@unlam.edu.ar') WHERE id = 5;
UPDATE Artista SET usuario_id = (SELECT id FROM Usuario WHERE email = 'valeria@unlam.edu.ar') WHERE id = 6;
UPDATE Artista SET usuario_id = (SELECT id FROM Usuario WHERE email = 'isabel@unlam.edu.ar') WHERE id = 7;
UPDATE Artista SET usuario_id = (SELECT id FROM Usuario WHERE email = 'lucia@unlam.edu.ar') WHERE id = 8;
UPDATE Artista SET usuario_id = (SELECT id FROM Usuario WHERE email = 'amanda@unlam.edu.ar') WHERE id = 9;
UPDATE Artista SET usuario_id = (SELECT id FROM Usuario WHERE email = 'natalia@unlam.edu.ar') WHERE id = 10;
UPDATE Artista SET usuario_id = (SELECT id FROM Usuario WHERE email = 'gabriela@unlam.edu.ar') WHERE id = 11;
UPDATE Artista SET usuario_id = (SELECT id FROM Usuario WHERE email = 'patricia@unlam.edu.ar') WHERE id = 12;
UPDATE Artista SET usuario_id = (SELECT id FROM Usuario WHERE email = 'monica@unlam.edu.ar') WHERE id = 13;
UPDATE Artista SET usuario_id = (SELECT id FROM Usuario WHERE email = 'daniela@unlam.edu.ar') WHERE id = 14;
UPDATE Artista SET usuario_id = (SELECT id FROM Usuario WHERE email = 'cristina@unlam.edu.ar') WHERE id = 15;
UPDATE Artista SET usuario_id = (SELECT id FROM Usuario WHERE email = 'veronica@unlam.edu.ar') WHERE id = 16;
UPDATE Artista SET usuario_id = (SELECT id FROM Usuario WHERE email = 'adriana@unlam.edu.ar') WHERE id = 17;
UPDATE Artista SET usuario_id = (SELECT id FROM Usuario WHERE email = 'lorena@unlam.edu.ar') WHERE id = 18;
UPDATE Artista SET usuario_id = (SELECT id FROM Usuario WHERE email = 'fernanda@unlam.edu.ar') WHERE id = 19;
UPDATE Artista SET usuario_id = (SELECT id FROM Usuario WHERE email = 'alejandra@unlam.edu.ar') WHERE id = 20;
UPDATE Artista SET usuario_id = (SELECT id FROM Usuario WHERE email = 'camila@unlam.edu.ar') WHERE id = 21;
UPDATE Artista SET usuario_id = (SELECT id FROM Usuario WHERE email = 'roxana@unlam.edu.ar') WHERE id = 22;
UPDATE Artista SET usuario_id = (SELECT id FROM Usuario WHERE email = 'silvia@unlam.edu.ar') WHERE id = 23;
UPDATE Artista SET usuario_id = (SELECT id FROM Usuario WHERE email = 'carolina@unlam.edu.ar') WHERE id = 24;
UPDATE Artista SET usuario_id = (SELECT id FROM Usuario WHERE email = 'mariana@unlam.edu.ar') WHERE id = 25;
UPDATE Artista SET usuario_id = (SELECT id FROM Usuario WHERE email = 'tomas@unlam.edu.ar') WHERE id = 26;
UPDATE Artista SET usuario_id = (SELECT id FROM Usuario WHERE email = 'martin@unlam.edu.ar') WHERE id = 27;
UPDATE Artista SET usuario_id = (SELECT id FROM Usuario WHERE email = 'diego@unlam.edu.ar') WHERE id = 28;
UPDATE Artista SET usuario_id = (SELECT id FROM Usuario WHERE email = 'luis@unlam.edu.ar') WHERE id = 29;
UPDATE Artista SET usuario_id = (SELECT id FROM Usuario WHERE email = 'carlos@unlam.edu.ar') WHERE id = 30;
UPDATE Artista SET usuario_id = (SELECT id FROM Usuario WHERE email = 'andres@unlam.edu.ar') WHERE id = 31;
UPDATE Artista SET usuario_id = (SELECT id FROM Usuario WHERE email = 'francisco@unlam.edu.ar') WHERE id = 32;
UPDATE Artista SET usuario_id = (SELECT id FROM Usuario WHERE email = 'roberto@unlam.edu.ar') WHERE id = 33;
UPDATE Artista SET usuario_id = (SELECT id FROM Usuario WHERE email = 'pedro@unlam.edu.ar') WHERE id = 34;
UPDATE Artista SET usuario_id = (SELECT id FROM Usuario WHERE email = 'sebastian@unlam.edu.ar') WHERE id = 35;
UPDATE Artista SET usuario_id = (SELECT id FROM Usuario WHERE email = 'emilio@unlam.edu.ar') WHERE id = 36;
UPDATE Artista SET usuario_id = (SELECT id FROM Usuario WHERE email = 'joaquin@unlam.edu.ar') WHERE id = 37;
UPDATE Artista SET usuario_id = (SELECT id FROM Usuario WHERE email = 'alejandro@unlam.edu.ar') WHERE id = 38;
UPDATE Artista SET usuario_id = (SELECT id FROM Usuario WHERE email = 'raul@unlam.edu.ar') WHERE id = 39;
UPDATE Artista SET usuario_id = (SELECT id FROM Usuario WHERE email = 'nicolas@unlam.edu.ar') WHERE id = 40;
UPDATE Artista SET usuario_id = (SELECT id FROM Usuario WHERE email = 'mauricio@unlam.edu.ar') WHERE id = 41;
UPDATE Artista SET usuario_id = (SELECT id FROM Usuario WHERE email = 'gustavo@unlam.edu.ar') WHERE id = 42;
UPDATE Artista SET usuario_id = (SELECT id FROM Usuario WHERE email = 'oscar@unlam.edu.ar') WHERE id = 43;
UPDATE Artista SET usuario_id = (SELECT id FROM Usuario WHERE email = 'ricardo@unlam.edu.ar') WHERE id = 44;
UPDATE Artista SET usuario_id = (SELECT id FROM Usuario WHERE email = 'ivan@unlam.edu.ar') WHERE id = 45;
UPDATE Artista SET usuario_id = (SELECT id FROM Usuario WHERE email = 'felipe@unlam.edu.ar') WHERE id = 46;
UPDATE Artista SET usuario_id = (SELECT id FROM Usuario WHERE email = 'manuel@unlam.edu.ar') WHERE id = 47;
UPDATE Artista SET usuario_id = (SELECT id FROM Usuario WHERE email = 'hector@unlam.edu.ar') WHERE id = 48;
UPDATE Artista SET usuario_id = (SELECT id FROM Usuario WHERE email = 'pablo@unlam.edu.ar') WHERE id = 49;
UPDATE Artista SET usuario_id = (SELECT id FROM Usuario WHERE email = 'alvaro@unlam.edu.ar') WHERE id = 50;

-- 3. AGREGAR LIKES A LAS OBRAS DE ANA HERRERA (artista_id = 4)
-- 10 likes a la obra 7 (Espacios Íntimos)
-- 3 likes a la obra 8 (Líneas de Fuga)

INSERT INTO obra_likes (obra_id, usuario_id) 
SELECT 7, id FROM Usuario WHERE email IN (
  'sofia@unlam.edu.ar', 'carmen@unlam.edu.ar', 'maria@unlam.edu.ar', 
  'valeria@unlam.edu.ar', 'isabel@unlam.edu.ar', 'lucia@unlam.edu.ar',
  'amanda@unlam.edu.ar', 'natalia@unlam.edu.ar', 'gabriela@unlam.edu.ar',
  'patricia@unlam.edu.ar'
);

INSERT INTO obra_likes (obra_id, usuario_id) 
SELECT 8, id FROM Usuario WHERE email IN (
  'monica@unlam.edu.ar', 'daniela@unlam.edu.ar', 'cristina@unlam.edu.ar'
);
