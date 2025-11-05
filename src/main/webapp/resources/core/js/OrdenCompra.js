class OrdenCompraManager {
  constructor() {
    this.setupEventListeners();
    this.estados = {
      PENDIENTE: { dot: 'status-pend', badge: 'bg-warning' },
      APROBADA: { dot: 'status-paid', badge: 'bg-success' },
      RECHAZADA: { dot: 'status-rej', badge: 'bg-danger' }
    };
  }

  setupEventListeners() {
    // Toggle entre vista cliente y admin
    const toggleAdmin = document.getElementById('toggleAdmin');
    if (toggleAdmin) {
      toggleAdmin.addEventListener('change', this.handleToggleView.bind(this));
    }

    // Acciones del administrador
    const markApproved = document.getElementById('markApproved');
    const markRejected = document.getElementById('markRejected');
    const updateShipment = document.getElementById('updateShipment');

    if (markApproved) {
      markApproved.addEventListener('click', () => this.updateOrderStatus('APROBADA'));
    }
    if (markRejected) {
      markRejected.addEventListener('click', () => this.updateOrderStatus('RECHAZADA'));
    }
    if (updateShipment) {
      updateShipment.addEventListener('click', this.updateShipmentStatus.bind(this));
    }
  }

  handleToggleView(e) {
    const clientView = document.getElementById('clientView');
    const adminView = document.getElementById('adminView');
    
    if (e.target.checked) {
      clientView?.classList.add('d-none');
      adminView?.classList.remove('d-none');
    } else {
      clientView?.classList.remove('d-none');
      adminView?.classList.add('d-none');
    }
  }

  async updateOrderStatus(newStatus) {
    try {
      const response = await fetch(`/api/orden/${document.getElementById('ordenId').value}/status`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          'X-CSRF-TOKEN': document.querySelector('meta[name="_csrf"]').content
        },
        body: JSON.stringify({ estado: newStatus })
      });

      if (!response.ok) throw new Error('Error al actualizar el estado');

      this.updateUIStatus(newStatus);
      
      if (newStatus === 'APROBADA') {
        const clientPendingActions = document.getElementById('clientPendingActions');
        if (clientPendingActions) {
          clientPendingActions.style.display = 'none';
        }
      }
    } catch (error) {
      console.error('Error:', error);
      alert('Error al actualizar el estado de la orden');
    }
  }

  async updateShipmentStatus() {
    const ship = document.getElementById('clientShipStatus');
    if (!ship) return;

    const currentStatus = ship.textContent;
    const newStatus = currentStatus === 'Preparando envío' ? 'En camino' : 'Entregado';

    try {
      const response = await fetch(`/api/orden/${document.getElementById('ordenId').value}/shipment`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          'X-CSRF-TOKEN': document.querySelector('meta[name="_csrf"]').content
        },
        body: JSON.stringify({ estado: newStatus })
      });

      if (!response.ok) throw new Error('Error al actualizar el estado del envío');

      ship.textContent = newStatus;
      this.showToast('Estado de envío actualizado: ' + newStatus);
    } catch (error) {
      console.error('Error:', error);
      alert('Error al actualizar el estado del envío');
    }
  }

  updateUIStatus(newStatus) {
    const adminOrderState = document.getElementById('adminOrderState');
    const adminPaymentState = document.getElementById('adminPaymentState');
    const clientPaymentStatus = document.getElementById('clientPaymentStatus');

    if (adminOrderState) {
      adminOrderState.textContent = newStatus;
      adminOrderState.className = `badge ${this.estados[newStatus].badge}`;
    }

    if (adminPaymentState) {
      adminPaymentState.textContent = newStatus === 'APROBADA' ? 'Aprobado' : 'Fallido';
    }

    if (clientPaymentStatus) {
      clientPaymentStatus.innerHTML = `
        <span class="status-dot ${this.estados[newStatus].dot}"></span>
        <strong>${newStatus}</strong>
      `;
    }
  }

  showToast(message) {
    const toast = document.createElement('div');
    toast.className = 'toast-notification';
    toast.textContent = message;
    document.body.appendChild(toast);
    setTimeout(() => toast.remove(), 3000);
  }
}

// Inicializar el manager cuando el DOM esté listo
document.addEventListener('DOMContentLoaded', () => {
  new OrdenCompraManager();
});