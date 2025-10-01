import express from 'express';
import cors from 'cors';
import pagosRouter from './infraestructura/RutaPagos';

const app = express();
const PORT = process.env.PORT || 3000; // puerto por defecto 3000

app.use(cors());
app.use(express.json());

// endpoint para crear pagos /api/pagos
app.use('/api', pagosRouter);

app.listen(PORT, () => {
  console.log(`Servidor escuchando en puerto ${PORT}`);
});