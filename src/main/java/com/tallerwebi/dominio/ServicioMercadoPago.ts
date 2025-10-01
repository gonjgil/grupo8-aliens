import { MercadoPagoConfig, Preference } from 'mercadopago';

const client = new MercadoPagoConfig({
  accessToken: 'TU_ACCESS_TOKEN',   // generar el access token de produccion en https://www.mercadopago.com.ar/developers/panel/credentials
                                    // despues modificar esto para usar variables de entorno con el access token de produccion
});

const preference = new Preference(client);

export type Item = {
  id: string;
  title: string;
  quantity: number;
  unit_price: number;
};

export async function crearPreferencia(items: Item[]) {
  try {
    const body = { items };
    const response = await preference.create({ body });
    return response.init_point;
  } catch (error: any) {
    throw new Error(error.message);
  }
}