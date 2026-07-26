/* ============================================
   STORE API - Common JavaScript
   ============================================ */

// Image Preview Handler
function previewImage(event, previewId) {
  const img = document.getElementById(previewId || 'imgPreview');
  if (img && event.target.files && event.target.files[0]) {
    const url = URL.createObjectURL(event.target.files[0]);
    img.src = url;
    img.classList.remove('d-none');
    img.style.animation = 'fadeIn 0.3s ease-out';
  }
}

// Delete Confirmation Modal
function initDeleteModal(modalId, nameSelector, btnSelector) {
  const modal = document.getElementById(modalId || 'deleteModal');
  if (!modal) return;

  modal.addEventListener('show.bs.modal', function (event) {
    const btn = event.relatedTarget;
    const name = btn.getAttribute('data-name');
    const id = btn.getAttribute('data-id');
    const url = btn.getAttribute('data-url');

    const nameEl = modal.querySelector(nameSelector || '#modalItemName');
    const confirmBtn = modal.querySelector(btnSelector || '#confirmDeleteBtn');

    if (nameEl) nameEl.textContent = name;
    if (confirmBtn) confirmBtn.setAttribute('href', url || '#');
  });
}

// Format Currency
function formatCurrency(amount) {
  return new Intl.NumberFormat('id-ID', {
    style: 'currency',
    currency: 'IDR',
    minimumFractionDigits: 0,
    maximumFractionDigits: 0
  }).format(amount);
}

// Toast Notification
function showToast(message, type) {
  const toast = document.createElement('div');
  toast.className = `alert alert-${type || 'success'} position-fixed top-0 end-0 m-3 shadow-lg`;
  toast.style.zIndex = '9999';
  toast.style.animation = 'fadeIn 0.3s ease-out';
  toast.innerHTML = `
    <div class="d-flex align-items-center gap-2">
      <span>${type === 'danger' ? '&#x274C;' : '&#x2705;'}</span>
      <span>${message}</span>
    </div>
  `;
  document.body.appendChild(toast);
  setTimeout(() => {
    toast.style.opacity = '0';
    toast.style.transition = 'opacity 0.3s';
    setTimeout(() => toast.remove(), 300);
  }, 3000);
}

// Confirm Delete with SweetAlert-style
function confirmDelete(name, callback) {
  if (confirm(`Are you sure you want to delete "${name}"? This action cannot be undone.`)) {
    callback();
  }
}

// Auto-hide alerts after 5 seconds
document.addEventListener('DOMContentLoaded', function () {
  document.querySelectorAll('.alert-auto-hide').forEach(function (el) {
    setTimeout(function () {
      el.style.opacity = '0';
      el.style.transition = 'opacity 0.5s';
      setTimeout(() => el.remove(), 500);
    }, 5000);
  });
});
