document.addEventListener("DOMContentLoaded", function () {
    const likeButton = document.getElementById("btn-like");
    if (!likeButton) return; // evita errores si el usuario no está logueado

    likeButton.addEventListener("click", async function() {
        const id = this.dataset.obraId;

        try {
            const res = await fetch(`/spring/obra/${id}/like`, { method: "POST" })
            const data = await res.json();

            if (res.ok) {
                document.getElementById("like-count").innerText = data.likesCount;
                //this.classList.toggle("liked", data.liked);
            } else {
                alert(data.error);
            }
        } catch (err) {
            console.error("Error actualizando contador:", err);
        }
    });
});