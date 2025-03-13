document.getElementById('Crear').addEventListener('click',
    function(){
        document.querySelector('.PopUp').style.display = 'flex';
    });
    
    document.querySelector('.close').addEventListener('click',
    function(){
        document.querySelector('.PopUp').style.display = 'none';
    });

document.getElementById('CrearReward').addEventListener('click',   
    function(){
    document.querySelector('.PopUpReward').style.display = 'flex';
    });
        
    document.querySelector('.close-reward').addEventListener('click',
    function(){
    document.querySelector('.PopUpReward').style.display = 'none';
    });

document.getElementById('CrearChat').addEventListener('click',   
    function(){
    document.querySelector('.PopUpChat').style.display = 'flex';
    });
            
    document.querySelector('.close-chat').addEventListener('click',
    function(){
    document.querySelector('.PopUpChat').style.display = 'none';
    });

document.getElementById('CrearDelete').addEventListener('click',   
    function(){
    document.querySelector('.PopUpDelete').style.display = 'flex';
    });
                
    document.querySelector('.close-delete').addEventListener('click',
    function(){
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

    userElements.forEach(user => {
        user.addEventListener("click", function () {
            const username = this.dataset.username || this.querySelector("p").textContent.trim(); //Obtener el nombre del usuario seleccionado

            //Se crea el boton
            const button = document.createElement("button");
            //Se pone el nombre del usuario seleccionado
            button.setAttribute("data-username", username);
            button.innerHTML = `${username} <i class="fa-solid fa-xmark"></i>`;

            //Elimina el boton al hacer click
            button.addEventListener("click", function () {
                button.remove();
            });

            //Agregar el botón al contenedor
            chatToContainer.appendChild(button);
        });
    });
});

// Manejo de los mensajes en JavaScript

const socket = new WebSocket("ws://localhost:8084/testPOI/chat");

socket.onopen = () => console.log("Conectado al WebSocket");
socket.onmessage = (event) => {
    let chatWindow = document.querySelector(".window-chat");
    let newBubble = document.createElement("div");
    newBubble.classList.add("window-bubble-dos");
    newBubble.innerHTML = `<textarea type="text" readonly>${event.data}</textarea>`;
    chatWindow.appendChild(newBubble);
};

document.querySelector(".btn-post").addEventListener("click", function() {
    console.log("Hola perrillo");
    let input = document.querySelector("input[name='barraMensaje']");
    let message = input.value.trim();

    if (message !== "") {
        socket.send(message);
        input.value = "";
    }
});
