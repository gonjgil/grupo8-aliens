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

ALTER TABLE ItemCarrito ADD COLUMN IF NOT EXISTS formato VARCHAR(50) NOT NULL DEFAULT 'DIGITAL';

DELETE FROM formato_obra;

INSERT INTO formato_obra (obra_id, formato, precio, stock, disponible) 
SELECT 
    o.id as obra_id,
    'DIGITAL' as formato,
    o.precio as precio,
    999 as stock,
    true as disponible
FROM obra o 
WHERE o.id BETWEEN 1 AND 100
AND NOT EXISTS (SELECT 1 FROM formato_obra fo WHERE fo.obra_id = o.id AND fo.formato = 'DIGITAL');

INSERT INTO formato_obra (obra_id, formato, precio, stock, disponible) 
SELECT 
    o.id as obra_id,
    'DIGITAL_NFT' as formato,
    o.precio * 2.5 as precio,
    5 as stock,
    true as disponible
FROM obra o 
WHERE o.id BETWEEN 1 AND 100
AND o.precio > 100000
AND NOT EXISTS (SELECT 1 FROM formato_obra fo WHERE fo.obra_id = o.id AND fo.formato = 'DIGITAL_NFT');

INSERT INTO formato_obra (obra_id, formato, precio, stock, disponible) 
SELECT 
    o.id as obra_id,
    'IMPRESION_CANVAS' as formato,
    o.precio * 1.4 as precio,
    50 as stock,
    true as disponible
FROM obra o 
WHERE o.id BETWEEN 1 AND 100
AND NOT EXISTS (SELECT 1 FROM formato_obra fo WHERE fo.obra_id = o.id AND fo.formato = 'IMPRESION_CANVAS');

INSERT INTO formato_obra (obra_id, formato, precio, stock, disponible) 
SELECT 
    o.id as obra_id,
    'IMPRESION_PREMIUM' as formato,
    o.precio * 1.8 as precio,
    25 as stock,
    true as disponible
FROM obra o 
WHERE o.id BETWEEN 1 AND 100
AND NOT EXISTS (SELECT 1 FROM formato_obra fo WHERE fo.obra_id = o.id AND fo.formato = 'IMPRESION_PREMIUM');

INSERT INTO formato_obra (obra_id, formato, precio, stock, disponible) 
SELECT 
    o.id as obra_id,
    'ORIGINAL' as formato,
    o.precio * 3.5 as precio,
    1 as stock,
    true as disponible
FROM obra o 
WHERE o.id BETWEEN 1 AND 100
AND o.precio > 200000
AND NOT EXISTS (SELECT 1 FROM formato_obra fo WHERE fo.obra_id = o.id AND fo.formato = 'ORIGINAL');

INSERT INTO formato_obra (obra_id, formato, precio, stock, disponible) 
SELECT 
    o.id as obra_id,
    'ORIGINAL_FIRMADO' as formato,
    o.precio * 4.5 as precio,
    1 as stock,
    true as disponible
FROM obra o 
WHERE o.id BETWEEN 1 AND 100
AND o.precio > 500000
AND NOT EXISTS (SELECT 1 FROM formato_obra fo WHERE fo.obra_id = o.id AND fo.formato = 'ORIGINAL_FIRMADO');

INSERT INTO formato_obra (obra_id, formato, precio, stock, disponible) 
SELECT 
    o.id as obra_id,
    'LICENCIA_COMERCIAL' as formato,
    o.precio * 8 as precio,
    3 as stock,
    true as disponible
FROM obra o 
WHERE o.id BETWEEN 1 AND 100
AND o.precio > 150000
AND NOT EXISTS (SELECT 1 FROM formato_obra fo WHERE fo.obra_id = o.id AND fo.formato = 'LICENCIA_COMERCIAL');

INSERT INTO formato_obra (obra_id, formato, precio, stock, disponible) VALUES
(30, 'ESCULTURA_REPLICA', 1800000.00, 2, true),
(31, 'ESCULTURA_REPLICA', 1520000.00, 2, true),
(42, 'ESCULTURA_REPLICA', 2720000.00, 1, true),
(43, 'ESCULTURA_REPLICA', 3000000.00, 1, true),
(58, 'ESCULTURA_REPLICA', 1680000.00, 2, true),
(59, 'ESCULTURA_REPLICA', 1520000.00, 2, true),
(88, 'ESCULTURA_REPLICA', 3400000.00, 1, true),
(89, 'ESCULTURA_REPLICA', 2480000.00, 1, true),
(90, 'ESCULTURA_REPLICA', 1920000.00, 2, true),
(91, 'ESCULTURA_REPLICA', 2080000.00, 1, true),
(66, 'ESCULTURA_REPLICA', 3680000.00, 1, true),
(67, 'ESCULTURA_REPLICA', 4400000.00, 1, true),
(98, 'ESCULTURA_REPLICA', 2320000.00, 1, true),
(99, 'ESCULTURA_REPLICA', 2600000.00, 1, true);

INSERT INTO formato_obra (obra_id, formato, precio, stock, disponible) VALUES
(48, 'CERAMICA_LIMITADA', 264000.00, 5, true),
(49, 'CERAMICA_LIMITADA', 195000.00, 8, true),
(84, 'CERAMICA_LIMITADA', 570000.00, 3, true),
(85, 'CERAMICA_LIMITADA', 660000.00, 2, true);

INSERT INTO formato_obra (obra_id, formato, precio, stock, disponible) VALUES
(46, 'TEXTIL_ARTESANAL', 285000.00, 4, true),
(47, 'TEXTIL_ARTESANAL', 540000.00, 2, true),
(76, 'TEXTIL_ARTESANAL', 660000.00, 2, true),
(77, 'TEXTIL_ARTESANAL', 780000.00, 2, true),
(100, 'TEXTIL_ARTESANAL', 960000.00, 1, true);

INSERT INTO formato_obra (obra_id, formato, precio, stock, disponible) VALUES
(36, 'INSTALACION_ITINERANTE', 4160000.00, 1, true),
(50, 'INSTALACION_ITINERANTE', 7120000.00, 1, true),
(51, 'INSTALACION_ITINERANTE', 5760000.00, 1, true),
(54, 'INSTALACION_ITINERANTE', 5200000.00, 1, true),
(55, 'INSTALACION_ITINERANTE', 4640000.00, 1, true),
(60, 'INSTALACION_ITINERANTE', 6240000.00, 1, true),
(61, 'INSTALACION_ITINERANTE', 4320000.00, 1, true),
(72, 'INSTALACION_ITINERANTE', 5440000.00, 1, true);