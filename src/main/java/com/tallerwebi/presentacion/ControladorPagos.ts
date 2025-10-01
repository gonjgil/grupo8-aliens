import { Request, Response } from 'express';
import { crearPreferencia, Item } from '../dominio/ServicioMercadoPago';

export async function crearPago(req: Request, res: Response) {
  try {
    const items: Item[] = req.body.items;
    if (!items || !Array.isArray(items) || items.length === 0) {
      return res.status(400).json({ error: 'Debes enviar un array de items.' });
    }
    const initPoint = await crearPreferencia(items);
    res.json({ init_point: initPoint });
  } catch (error: any) {
    res.status(500).json({ error: error.message });
  }
}