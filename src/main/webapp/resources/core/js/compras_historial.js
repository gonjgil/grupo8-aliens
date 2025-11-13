document.addEventListener('DOMContentLoaded', () => {
    const modal = document.getElementById('compraDetail');
    const modalInstance = new bootstrap.Modal(modal);
    console.log("Modal encontrado:", modal);



    document.querySelectorAll('.order-card').forEach(card => {
        card.addEventListener('click', async () => {
            const compraId = card.dataset.orderId;

            try {
                const response = await fetch(`/spring/compras/detalle/${compraId}`);

                if (response.status === 401) {
                    window.location.href = "/login";
                    return;
                }
                if (!response.ok) throw new Error('Error al obtener la compra');

                const compra = await response.json();

                document.getElementById('compra-id').textContent = compra.id;
                document.getElementById('compra-fecha').textContent = compra.fechaYHora;
//                document.getElementById('compra-estado').textContent = compra.estado;
                document.getElementById('compra-total').textContent = `$${compra.precioFinal.toLocaleString()}`;
                document.getElementById('compra-total-footer').textContent = `$${compra.precioFinal.toLocaleString()}`;

                // Productos
                const tbody = document.getElementById('compra-items');
                tbody.innerHTML = '';
                for (const item of compra.items) {
                    const row = document.createElement('tr');
                    row.innerHTML = `
                        <td>${item.obraTitulo}</td>
                        <td class="text-center">${item.formatoNombre}</td>
                        <td class="text-end">${item.cantidad}</td>
                        <td class="text-end">$${item.obraPrecio.toLocaleString()}</td>
                        <td class="text-end">$${item.subtotal.toLocaleString()}</td>
                    `;
                    tbody.appendChild(row);
                }

                modalInstance.show();
            } catch (error) {
                console.error('Error al cargar el detalle:', error);
                alert('No se pudo cargar el detalle de la compra.');
            }
        });
    });
});


            //     <div class="col-md-6">
            //         <div class="summary-box">
            //             <h6 class="mb-1">💳 Información de pago</h6>
            //             <div class="small-muted mb-2">Método: <strong>${order.payment.method}</strong></div>
            //             <div class="mb-1">Estado del pago: <span class="status-dot status-${order.payment.status}"></span><strong>${order.payment.status}</strong></div>
            //             <div class="small-muted">Fecha de pago: ${order.payment.date}</div>
            //             <div class="small-muted">ID de transacción: ${order.payment.transactionId}</div>
            //         </div>
            //     </div>
            //
            //     <div class="col-md-6">
            //         <div class="summary-box">
            //             <h6 class="mb-1">🚚 Envío</h6>
            //             <div class="small-muted mb-1">Dirección: ${order.shipping.address}</div>
            //             <div class="small-muted mb-1">Método: ${order.shipping.method}</div>
            //             <div>Estado: <strong>${order.shipping.status}</strong></div>
            //             <div class="small-muted">Número de seguimiento: ${order.shipping.tracking}</div>
            //         </div>
            //     </div>
            // </div>
    //     `;
    // }

    // Handle status filter
//     document.querySelectorAll('.dropdown-item').forEach(item => {
//         item.addEventListener('click', function(e) {
//             e.preventDefault();
//             const status = this.textContent.toLowerCase();
//             filterOrders(status);
//         });
//     });
//
//     function filterOrders(status) {
//         // Implement order filtering logic here
//         console.log('Filtering by status:', status);
//     }
// });
// This file contains the JavaScript functionality for the purchase order.
// It handles the toggling between client and admin views, as well as the demo actions for updating the order status.

// const toggleAdmin = document.getElementById('toggleAdmin');
// const clientView = document.getElementById('clientView');
// const adminView = document.getElementById('adminView');
// const clientPendingActions = document.getElementById('clientPendingActions');
//
// toggleAdmin.addEventListener('change', e => {
//     if (e.target.checked) {
//         clientView.classList.add('d-none');
//         adminView.classList.remove('d-none');
//     } else {
//         clientView.classList.remove('d-none');
//         adminView.classList.add('d-none');
//     }
// });

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