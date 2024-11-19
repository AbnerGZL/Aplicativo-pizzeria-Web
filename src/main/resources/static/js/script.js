document.addEventListener("DOMContentLoaded", function() {
    try {
        const loader = document.getElementById("loader");
        setTimeout(() => {
            loader.style.opacity="0%";
            loader.style.pointerEvents="none";
        }, 100);
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

