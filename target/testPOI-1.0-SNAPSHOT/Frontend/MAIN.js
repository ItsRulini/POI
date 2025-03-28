document.getElementById('Crear').addEventListener('click',
        function () {
            document.querySelector('.PopUp').style.display = 'flex';
        });

document.querySelector('.close').addEventListener('click',
        function () {
            document.querySelector('.PopUp').style.display = 'none';
        });

document.getElementById('CrearReward').addEventListener('click',
        function () {
            document.querySelector('.PopUpReward').style.display = 'flex';
        });

document.querySelector('.close-reward').addEventListener('click',
        function () {
            document.querySelector('.PopUpReward').style.display = 'none';
        });

document.getElementById('CrearChat').addEventListener('click',
        function () {
            document.querySelector('.PopUpChat').style.display = 'flex';
        });

document.querySelector('.close-chat').addEventListener('click',
        function () {
            document.querySelector('.PopUpChat').style.display = 'none';
        });

document.getElementById('CrearDelete').addEventListener('click',
        function () {
            document.querySelector('.PopUpDelete').style.display = 'flex';
        });

document.querySelector('.close-delete').addEventListener('click',
        function () {
            document.querySelector('.PopUpDelete').style.display = 'none';
        });

//Scroll para los usuarios q quieres crear el nuevo chat
const container = document.querySelector(".chat-to");

container.addEventListener("wheel", (event) => {
    event.preventDefault(); // Evita el scroll de la página
    container.scrollLeft += event.deltaY; // Mueve el contenido horizontalmente
});

//agregar botones de los usuarios con los q se crea un nuevo chat
document.addEventListener("DOMContentLoaded", function () {
    const chatToContainer = document.querySelector(".chat-to");
    const userElements = document.querySelectorAll(".new-convo");
    const selectedUsersContainer = document.getElementById("selected-users");

    userElements.forEach(user => {
        user.addEventListener("click", function () {
            const username = this.querySelector("p").textContent.trim();
            const userId = this.dataset.id; // Obtiene el ID del usuario

            // Verifica si el usuario ya está en la lista seleccionada
            if (chatToContainer.querySelector(`[data-username="${username}"]`)) {
                return; // Evita duplicados
            }

            // Se oculta el usuario seleccionado
            this.style.display = "none";

            // Se crea el botón con el nombre del usuario seleccionado
            const button = document.createElement("button");
            button.setAttribute("data-username", username);
            button.innerHTML = `${username} <i class="fa-solid fa-xmark"></i>`;

            // Se crea un input oculto con el ID del usuario
            const hiddenInput = document.createElement("input");
            hiddenInput.type = "hidden";
            hiddenInput.name = "usuariosSeleccionados"; // Nombre del array en el request
            hiddenInput.value = userId;
            hiddenInput.setAttribute("data-id", userId);

            // Elimina el botón y el input oculto cuando se hace clic en la 'x'
            button.addEventListener("click", function () {
                button.remove(); // Elimina el botón
                user.style.display = "flex"; // Muestra al usuario de nuevo

                // Elimina el input oculto correspondiente
                selectedUsersContainer.querySelector(`[data-id="${userId}"]`).remove();
            });

            // Agregar el botón al contenedor de usuarios seleccionados
            chatToContainer.appendChild(button);
            selectedUsersContainer.appendChild(hiddenInput); // Agregar input oculto
        });
    });
});



// Manejo de los mensajes en JavaScript
/*
 const socket = new WebSocket("ws://localhost:8084/testPOI/chat");
 
 socket.onopen = () => console.log("Conectado al WebSocket");
 socket.onmessage = (event) => {
 let chatWindow = document.querySelector(".window-chat");
 let newBubble = document.createElement("div");
 newBubble.classList.add("window-bubble-dos");
 newBubble.innerHTML = `<textarea type="text" readonly>${event.data}</textarea>`;
 chatWindow.appendChild(newBubble);
 };*/

let socket = null; // Cambié const a let

// Selecciona todos los elementos con la clase "user-convo"
const chatElements = document.querySelectorAll(".user-convo");

// Agrega un evento de clic a cada elemento
chatElements.forEach(chatElement => {
    chatElement.addEventListener("click", function () {
        const chatId = this.id;
        console.log("Chat seleccionado:", chatId);
        openWebSocket(chatId);
    });
});

// Función para abrir el WebSocket
function openWebSocket(chatId) {
    if (socket) {
        socket.close(); // Cierra el WebSocket anterior si existe
    }

    socket = new WebSocket(`ws://192.168.1.102:8084/testPOI/chat/${chatId}`);

    socket.onopen = () => console.log("Conectado al WebSocket para el chat:", chatId);

    socket.onmessage = (event) => {
        let chatWindow = document.querySelector(".window-chat");
        let newBubble = document.createElement("div");
        newBubble.classList.add("window-bubble-dos");
        newBubble.innerHTML = `<textarea type="text" readonly>${event.data}</textarea>`;
        chatWindow.appendChild(newBubble);
    };

    socket.onerror = (error) => console.error("Error en WebSocket:", error);
    socket.onclose = () => console.log("WebSocket cerrado.");
}

document.querySelector(".btn-post").addEventListener("click", function () {
    let input = document.querySelector("input[name='barraMensaje']");
    let message = input.value.trim();

    if (message !== "" && socket && socket.readyState === WebSocket.OPEN) {
        socket.send(message);
        input.value = "";
    } else {
        console.log("No hay conexión activa con WebSocket.");
    }
});