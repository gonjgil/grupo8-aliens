-- Script SQL para agregar formatos de obras
-- Este script debe ejecutarse después de que las obras estén insertadas en la base de datos

-- Primero, creamos la tabla formato_obra si no existe
CREATE TABLE IF NOT EXISTS formato_obra (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    obra_id BIGINT NOT NULL,
    formato VARCHAR(50) NOT NULL,
    precio DOUBLE NOT NULL,
    stock INT NOT NULL,
    disponible BOOLEAN NOT NULL DEFAULT TRUE,
    FOREIGN KEY (obra_id) REFERENCES obra(id),
    UNIQUE KEY unique_obra_formato (obra_id, formato)
);

-- Agregar columna formato a ItemCarrito si no existe
ALTER TABLE ItemCarrito ADD COLUMN IF NOT EXISTS formato VARCHAR(50) NOT NULL DEFAULT 'DIGITAL';

-- Limpiar formatos existentes (opcional, para reinicializar)
-- DELETE FROM formato_obra;

-- Insertar formatos para las primeras 10 obras (ajustar según las obras que tengas)
-- Los precios se calculan basándose en multiplicadores del precio base de cada obra

-- Obra ID 1 - Paisaje Abstracto ($150 base)
INSERT INTO formato_obra (obra_id, formato, precio, stock, disponible) VALUES
(1, 'DIGITAL', 150.00, 999, true),
(1, 'DIGITAL_NFT', 450.00, 10, true),
(1, 'IMPRESION_CANVAS', 240.00, 50, true),
(1, 'IMPRESION_PREMIUM', 360.00, 30, true),
(1, 'ORIGINAL', 750.00, 1, true),
(1, 'ORIGINAL_FIRMADO', 1050.00, 1, true),
(1, 'LICENCIA_COMERCIAL', 1500.00, 5, true);

-- Obra ID 2 - Retrato Moderno ($200 base)
INSERT INTO formato_obra (obra_id, formato, precio, stock, disponible) VALUES
(2, 'DIGITAL', 200.00, 999, true),
(2, 'DIGITAL_NFT', 600.00, 10, true),
(2, 'IMPRESION_CANVAS', 320.00, 50, true),
(2, 'IMPRESION_PREMIUM', 480.00, 30, true),
(2, 'ORIGINAL', 1000.00, 1, true),
(2, 'ORIGINAL_FIRMADO', 1400.00, 1, true),
(2, 'LICENCIA_COMERCIAL', 2000.00, 5, true);

-- Obra ID 3 - Montañas al Amanecer ($250 base)
INSERT INTO formato_obra (obra_id, formato, precio, stock, disponible) VALUES
(3, 'DIGITAL', 250.00, 999, true),
(3, 'DIGITAL_NFT', 750.00, 10, true),
(3, 'IMPRESION_CANVAS', 400.00, 50, true),
(3, 'IMPRESION_PREMIUM', 600.00, 30, true),
(3, 'ORIGINAL', 1250.00, 1, true),
(3, 'ORIGINAL_FIRMADO', 1750.00, 1, true),
(3, 'LICENCIA_COMERCIAL', 2500.00, 5, true);

-- Obras 4-10: Insertar automáticamente usando el precio base de cada obra
-- Nota: Estos INSERTs asumen que las obras con ID 4-10 existen
-- Si tu base de datos tiene diferentes IDs, ajusta según corresponda

INSERT INTO formato_obra (obra_id, formato, precio, stock, disponible) 
SELECT 
    o.id as obra_id,
    'DIGITAL' as formato,
    o.precio as precio,
    999 as stock,
    true as disponible
FROM obra o 
WHERE o.id BETWEEN 4 AND 20
AND NOT EXISTS (SELECT 1 FROM formato_obra fo WHERE fo.obra_id = o.id AND fo.formato = 'DIGITAL');

INSERT INTO formato_obra (obra_id, formato, precio, stock, disponible) 
SELECT 
    o.id as obra_id,
    'DIGITAL_NFT' as formato,
    o.precio * 3 as precio,
    10 as stock,
    true as disponible
FROM obra o 
WHERE o.id BETWEEN 4 AND 20
AND NOT EXISTS (SELECT 1 FROM formato_obra fo WHERE fo.obra_id = o.id AND fo.formato = 'DIGITAL_NFT');

INSERT INTO formato_obra (obra_id, formato, precio, stock, disponible) 
SELECT 
    o.id as obra_id,
    'IMPRESION_CANVAS' as formato,
    o.precio * 1.6 as precio,
    50 as stock,
    true as disponible
FROM obra o 
WHERE o.id BETWEEN 4 AND 20
AND NOT EXISTS (SELECT 1 FROM formato_obra fo WHERE fo.obra_id = o.id AND fo.formato = 'IMPRESION_CANVAS');

INSERT INTO formato_obra (obra_id, formato, precio, stock, disponible) 
SELECT 
    o.id as obra_id,
    'IMPRESION_PREMIUM' as formato,
    o.precio * 2.4 as precio,
    30 as stock,
    true as disponible
FROM obra o 
WHERE o.id BETWEEN 4 AND 20
AND NOT EXISTS (SELECT 1 FROM formato_obra fo WHERE fo.obra_id = o.id AND fo.formato = 'IMPRESION_PREMIUM');

INSERT INTO formato_obra (obra_id, formato, precio, stock, disponible) 
SELECT 
    o.id as obra_id,
    'ORIGINAL' as formato,
    o.precio * 5 as precio,
    1 as stock,
    true as disponible
FROM obra o 
WHERE o.id BETWEEN 4 AND 20
AND NOT EXISTS (SELECT 1 FROM formato_obra fo WHERE fo.obra_id = o.id AND fo.formato = 'ORIGINAL');

INSERT INTO formato_obra (obra_id, formato, precio, stock, disponible) 
SELECT 
    o.id as obra_id,
    'ORIGINAL_FIRMADO' as formato,
    o.precio * 7 as precio,
    1 as stock,
    true as disponible
FROM obra o 
WHERE o.id BETWEEN 4 AND 20
AND NOT EXISTS (SELECT 1 FROM formato_obra fo WHERE fo.obra_id = o.id AND fo.formato = 'ORIGINAL_FIRMADO');

INSERT INTO formato_obra (obra_id, formato, precio, stock, disponible) 
SELECT 
    o.id as obra_id,
    'LICENCIA_COMERCIAL' as formato,
    o.precio * 10 as precio,
    5 as stock,
    true as disponible
FROM obra o 
WHERE o.id BETWEEN 4 AND 20
AND NOT EXISTS (SELECT 1 FROM formato_obra fo WHERE fo.obra_id = o.id AND fo.formato = 'LICENCIA_COMERCIAL');

-- Verificar los datos insertados
-- SELECT o.titulo, fo.formato, fo.precio, fo.stock 
-- FROM obra o 
-- JOIN formato_obra fo ON o.id = fo.obra_id 
-- ORDER BY o.id, fo.formato;

INSERT INTO formato_obra (obra_id, formato, precio, stock, disponible) VALUES
(2, 'ORIGINAL', 18000.00, 1, true),
(2, 'ORIGINAL_FIRMADO', 28000.00, 1, true),
(2, 'DIGITAL', 4000.00, 999, true),
(2, 'DIGITAL_NFT', 12000.00, 10, true),
(2, 'IMPRESION_CANVAS', 6500.00, 50, true),
(2, 'IMPRESION_PREMIUM', 9500.00, 30, true);

INSERT INTO formato_obra (obra_id, formato, precio, stock, disponible) VALUES
(3, 'ORIGINAL', 30000.00, 1, true),
(3, 'ORIGINAL_FIRMADO', 45000.00, 1, true),
(3, 'DIGITAL', 6000.00, 999, true),
(3, 'DIGITAL_NFT', 18000.00, 10, true),
(3, 'IMPRESION_CANVAS', 9000.00, 50, true),
(3, 'IMPRESION_PREMIUM', 14000.00, 30, true),
(3, 'LICENCIA_COMERCIAL', 75000.00, 3, true);