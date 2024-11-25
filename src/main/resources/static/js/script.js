//Pantalla de carga
document.addEventListener("DOMContentLoaded", function() {
    try {
        const loader = document.getElementById("loader");
        setTimeout(() => {
            loader.style.opacity="0%";
            loader.style.pointerEvents="none";
        }, 400);

        //Bloquear entrada de input
        document.getElementById("id_cliente").addEventListener("keypress", function(e) {
            e.preventDefault();
        })

        // Quitar formulario de edición de datos del cliente
        document.getElementById("cancelEdit").addEventListener("click", function() {
            changeDetails()
            document.getElementById("username").value = document.getElementById("dat-user").textContent;
            document.getElementById("email").value = document.getElementById("dat-email").textContent;
            document.getElementById("password").value = document.getElementById("dat-password").value;
            document.getElementById("phone").value = document.getElementById("dat-phone").textContent;
        })

        // Abrir formulario de edición de datos del cliente
        document.getElementById("openEdit").addEventListener("click", function() {
            changeDetails()
        })

    } catch(e){
    }
});
//Botón volver
function goBack() {
    window.history.back();
}

//Selección register
function registercheckvalidation() {
    let check = document.getElementById("flexCheckChecked1");
    let button = document.getElementById("register");

    if (check.checked) {
        button.removeAttribute("disabled");
        button.classList.remove("btn-disabled");
        button.classList.add("btn-pizza-yellow");
    } else if (button.getAttribute("disabled")=== null) {
        button.setAttribute("disabled","true");
        button.classList.add("btn-disabled");
        button.classList.remove("btn-pizza-yellow");
    }
}

// Cambiar a editar datos de usuario
function changeDetails() {
    let view_profile = document.getElementById("view-profile");
    let edit_profile = document.getElementById("edit-profile");

    if (!view_profile.classList.contains("disappear")) {
        view_profile.classList.add("slide-out");
        setTimeout(() => {
            view_profile.classList.add("disappear");
            view_profile.classList.remove("slide-out");
            edit_profile.classList.remove("disappear");
        }, 700);
    } else {
        edit_profile.classList.add("slide-out");
        setTimeout(() => {
            view_profile.classList.remove("disappear");
            edit_profile.classList.remove("slide-out");
            edit_profile.classList.add("disappear");
        }, 700);
    }
}

