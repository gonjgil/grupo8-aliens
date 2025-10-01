"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var express_1 = require("express");
var ControladorPagos_1 = require("../presentacion/ControladorPagos");
var router = (0, express_1.Router)();
// Define la ruta POST /pagos que llama al controlador crearPago
router.post('/pagos', ControladorPagos_1.crearPago);
exports.default = router;
