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
document.addEventListener("DOMContentLoaded", function() {
    try {
        const loader = document.getElementById("loader");
        loader.style.opacity="0%";
        loader.style.pointerEvents="none";
    } catch(e){
    }
});