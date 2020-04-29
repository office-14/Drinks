export interface Order {
	"id": number,
    "status_code": string,
    "status_name": string,
    "order_number": string,
    "total_price": number,
    "comment": string,
	"drinks": Array<object>
    "became_ready"?: boolean,
    "created"?: Date
}