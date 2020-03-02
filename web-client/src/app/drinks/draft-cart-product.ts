import { Addin } from './addin';
import { Size } from './size';

export interface DraftCartProduct {
	size: Size,
	addins: Addin[],
	qty: number,
	drink_id: number
}