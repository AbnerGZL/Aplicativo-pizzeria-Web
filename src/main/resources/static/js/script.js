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


try {
    // Cerrar ubicaciones
    document.addEventListener('keydown', function(event) {
        if (event.keyCode === 27) {
            document.getElementById("fetch-location").classList.add("disabled");
        }
    });

    document.getElementById("locate").addEventListener('keydown', function(event) {
        if (event.keyCode === 8) {
            document.getElementById("menu").setAttribute("disabled","");
            if(document.getElementById("menu").classList.contains("btn-pizza-yellow")){
                document.getElementById("menu").classList.remove("btn-pizza-yellow");
            }
            document.getElementById("menu").classList.add("btn-disabled");
        }
    });

    // Listar ubicaciones
    let debounceTimeout;

    document.getElementById('locate').addEventListener('keyup', function() {
        const query = this.value;

        // Limitar las solicitudes con debounce (espera 500ms después de dejar de escribir)
        clearTimeout(debounceTimeout);
        debounceTimeout = setTimeout(function() {
            if (query.length > 2) {
                document.getElementById('fetch-location').classList.remove('disabled');
                fetchLocationData(query);
            } else {
                // Si no hay texto o es demasiado corto, limpiar la lista
                document.getElementById('location-list').innerHTML = '';
                document.getElementById('fetch-location').classList.add('disabled');

                document.getElementById("menu").setAttribute("disabled","");
                if(document.getElementById("menu").classList.contains("btn-pizza-yellow")){
                    document.getElementById("menu").classList.remove("btn-pizza-yellow");
                }
                document.getElementById("menu").classList.add("btn-disabled");
            }
        }, 250);

    });
    function fetchLocationData(query) {
        // Realizar una solicitud a la API usando fetch
        fetch(`https://us1.locationiq.com/v1/search?key=pk.b399cb145a3bfd1a341b631e4f4f4db7&q=${query}&format=json&`)
            .then(response => response.json())
            .then(data => updateLocationList(data))
            .catch(error => console.error('Error fetching location:', error));
    }

    function updateLocationList(locations) {
        const list = document.getElementById('location-list');
        list.innerHTML = '';

        if (locations && locations.length > 0) {
            // Filtrar resultados para que solo aparezcan aquellos que contienen "Perú"
            const filteredLocations = locations.filter(location => location.display_name.includes("Perú"));
            var count =0;
            if (filteredLocations.length > 0) {
                filteredLocations.forEach(location => {
                    count++;
                    const listItem = document.createElement('li');
                    const anchor = document.createElement('button');

                    const parts = location.display_name.split(',');
                    const avenida = parts[0];
                    const departamentoPais = parts.slice(1).join(',');
                    // Formato: Avenida / Departamento, País
                    anchor.innerHTML = `<strong>${avenida}</strong><br><small>${departamentoPais}</small>`;
                    anchor.type="button";
                    anchor.id="l-"+count;
                    anchor.addEventListener("click", function () {
                        document.getElementById("locate").value = this.textContent;
                        document.getElementById("fetch-location").classList.add("disabled");
                        document.getElementById("location-list").innerHTML = "";
                        document.getElementById("menu").classList.add("btn-pizza-yellow");
                        if(document.getElementById("menu").classList.contains("btn-disabled")) {
                            document.getElementById("menu").classList.remove("btn-disabled");
                        }
                        document.getElementById("menu").removeAttribute("disabled");

                    })

                    listItem.appendChild(anchor);
                    list.appendChild(listItem);
                });
            } else {
                const listItem = document.createElement('li');
                document.getElementById("menu").setAttribute("disabled","");
                if(document.getElementById("menu").classList.contains("btn-pizza-yellow")){
                    document.getElementById("menu").classList.remove("btn-pizza-yellow");
                }
                document.getElementById("menu").classList.add("btn-disabled");
                listItem.textContent = 'No se encontraron ubicaciones';
                listItem.style.padding="10px";
                list.appendChild(listItem);
            }
        } else {
            const listItem = document.createElement('li');
            document.getElementById("menu").setAttribute("disabled","");
            if(document.getElementById("menu").classList.contains("btn-pizza-yellow")){
                document.getElementById("menu").classList.remove("btn-pizza-yellow");
            }
            document.getElementById("menu").classList.add("btn-disabled");
            listItem.textContent = 'No se encontraron ubicaciones';
            listItem.style.padding="10px";
            list.appendChild(listItem);
        }
    }

}catch(e){}