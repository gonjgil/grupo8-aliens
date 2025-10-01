import { Router } from 'express';
import { crearPago } from '../presentacion/ControladorPagos';

const router = Router();

// Define la ruta POST /pagos que llama al controlador crearPago
router.post('/pagos', crearPago);

export default router;