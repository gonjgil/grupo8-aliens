// Datos de ejemplo - En una aplicación real vendría de una API/Backend
const orders = [
    {
        id: '000123',
        date: '03/11/2025',
        items: [
            { name: 'Camiseta Alien', quantity: 2, price: 10000 },
            { name: 'Gorro Edición Especial', quantity: 1, price: 8000 }
        ],
        total: 28000,
        payment: {
            method: 'Mercado Pago',
            status: 'pending',
            date: '03/11/2025',
            transactionId: 'MP-123456789'
        },
        shipping: {
            address: 'Calle Falsa 123, Buenos Aires',
            method: 'Envío estándar',
            status: 'Preparando envío',
            tracking: 'ENV123456'
        }
    },
    // ... más órdenes
];

document.addEventListener('DOMContentLoaded', function() {
    const modal = document.getElementById('orderDetail');

    // Agregar event listener a todas las tarjetas de orden
    document.querySelectorAll('.order-card').forEach(card => {
        card.addEventListener('click', function() {
            const orderId = this.dataset.orderId;
            const order = orders.find(o => o.id === orderId);
            updateModalContent(order);
        });
    });

    function updateModalContent(order) {
        // Actualizar título del modal
        modal.querySelector('.modal-title').textContent = `Detalle de Orden #${order.id}`;

        // Actualizar contenido del modal
        const modalBody = modal.querySelector('.modal-body');

        // Generar HTML para los productos
        const productsHTML = order.items.map(item => `
            <tr>
                <td>${item.name}</td>
                <td class="text-center">${item.quantity}</td>
                <td class="text-end">$${item.price.toLocaleString()}</td>
                <td class="text-end">$${(item.quantity * item.price).toLocaleString()}</td>
            </tr>
        `).join('');

        modalBody.innerHTML = `
            <div class="row g-3">
                <div class="col-md-12">
                    <div class="table-responsive">
                        <table class="table table-products">
                            <thead class="table-light">
                                <tr>
                                    <th>Producto</th>
                                    <th class="text-center">Cantidad</th>
                                    <th class="text-end">Precio unitario</th>
                                    <th class="text-end">Subtotal</th>
                                </tr>
                            </thead>
                            <tbody>
                                ${productsHTML}
                            </tbody>
                            <tfoot>
                                <tr>
                                    <th colspan="3" class="text-end">Total</th>
                                    <th class="text-end">$${order.total.toLocaleString()}</th>
                                </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="summary-box">
                        <h6 class="mb-1">💳 Información de pago</h6>
                        <div class="small-muted mb-2">Método: <strong>${order.payment.method}</strong></div>
                        <div class="mb-1">Estado del pago: <span class="status-dot status-${order.payment.status}"></span><strong>${order.payment.status}</strong></div>
                        <div class="small-muted">Fecha de pago: ${order.payment.date}</div>
                        <div class="small-muted">ID de transacción: ${order.payment.transactionId}</div>
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="summary-box">
                        <h6 class="mb-1">🚚 Envío</h6>
                        <div class="small-muted mb-1">Dirección: ${order.shipping.address}</div>
                        <div class="small-muted mb-1">Método: ${order.shipping.method}</div>
                        <div>Estado: <strong>${order.shipping.status}</strong></div>
                        <div class="small-muted">Número de seguimiento: ${order.shipping.tracking}</div>
                    </div>
                </div>
            </div>
        `;
    }

    // Handle status filter
    document.querySelectorAll('.dropdown-item').forEach(item => {
        item.addEventListener('click', function(e) {
            e.preventDefault();
            const status = this.textContent.toLowerCase();
            filterOrders(status);
        });
    });

    function filterOrders(status) {
        // Implement order filtering logic here
        console.log('Filtering by status:', status);
    }
});
// This file contains the JavaScript functionality for the purchase order.
// It handles the toggling between client and admin views, as well as the demo actions for updating the order status.

const toggleAdmin = document.getElementById('toggleAdmin');
const clientView = document.getElementById('clientView');
const adminView = document.getElementById('adminView');
const clientPendingActions = document.getElementById('clientPendingActions');

toggleAdmin.addEventListener('change', e => {
    if (e.target.checked) {
        clientView.classList.add('d-none');
        adminView.classList.remove('d-none');
    } else {
        clientView.classList.remove('d-none');
        adminView.classList.add('d-none');
    }
});

// // Demo: admin actions update UI state
// document.getElementById('markApproved').addEventListener('click', () => {
//     document.getElementById('adminOrderState').textContent = 'Aprobada';
//     document.getElementById('adminOrderState').className = 'badge bg-success';
//     document.getElementById('adminPaymentState').textContent = 'Aprobado';
//     document.getElementById('clientPaymentStatus').innerHTML = '<span class="status-dot status-paid"></span><strong>Aprobado</strong>';
//     clientPendingActions.style.display = 'none';
// });
//
// document.getElementById('markRejected').addEventListener('click', () => {
//     document.getElementById('adminOrderState').textContent = 'Rechazada';
//     document.getElementById('adminOrderState').className = 'badge bg-danger';
//     document.getElementById('adminPaymentState').textContent = 'Fallido';
//     document.getElementById('clientPaymentStatus').innerHTML = '<span class="status-dot status-rej"></span><strong>Rechazado</strong>';
// });
//
// document.getElementById('updateShipment').addEventListener('click', () => {
//     const ship = document.getElementById('clientShipStatus');
//     ship.textContent = (ship.textContent === 'Preparando envío') ? 'En camino' : 'Entregado';
//     alert('Estado de envío actualizado: ' + ship.textContent);
// });