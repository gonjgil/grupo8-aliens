-- Inserta password (test) hasheado
INSERT INTO Usuario (email, password, rol, activo)
SELECT 'test@unlam.edu.ar', '$2b$10$MOxaUF/Xx7Hqf7i62GHfquTdl0vHtHDh5ZeXeRmFFa5PmTDcvbcR.', 'ADMIN', true
WHERE NOT EXISTS (
    SELECT 1 FROM Usuario WHERE email = 'test@unlam.edu.ar'
);

-- Cambiar el charset y collation de la base de datos actual a utf8
ALTER DATABASE tallerwebi CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- Configurar la conexión actual a UTF-8 (por seguridad)
SET NAMES utf8mb4;
SET CHARACTER SET utf8mb4;
SET collation_connection = 'utf8mb4_unicode_ci';

-- Cambia las tablas actuales a utf8
SELECT CONCAT('ALTER TABLE `', table_name, '` CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;')
FROM information_schema.tables
WHERE table_schema = 'tallerwebi';