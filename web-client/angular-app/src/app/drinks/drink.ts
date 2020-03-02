import { Size } from './size';

export interface Drink {
	id: number,
	name: string,
	description: string,
	photo_url: string,
	smallest_size_price: number,
	sizes: Size[]
}