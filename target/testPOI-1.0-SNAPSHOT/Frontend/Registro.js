/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

document.addEventListener("DOMContentLoaded", function () {
    // Captura el botón de registro
    document.querySelector(".btn-sign a").addEventListener("click", function (event) {
        event.preventDefault(); // Evita que el enlace redirija la página

        // Crear FormData
        const formData = new FormData();
        
        // Obtener los valores de los inputs
        let data = {
            nombres: document.querySelector("input[name='nombres']").value,
            apellidoPaterno: document.querySelector("input[name='apellidoPaterno']").value,
            apellidoMaterno: document.querySelector("input[name='apellidoMaterno']").value,
            nacimiento: document.querySelector("input[name='nacimiento']").value,
            usuario: document.querySelector("input[name='usuario']").value,
            password: document.querySelector("input[name='password']").value,
            correo: document.querySelector("input[name='correo']").value
        };

        // Añadir el JSON como una parte del FormData
        formData.append('datos', JSON.stringify(data));

        // Añadir la imagen
        const fileInput = document.querySelector("input[name='avatar']");
        if (fileInput.files[0]) {
            formData.append('imagen', fileInput.files[0]);
        }

        // Enviar los datos al servlet
        fetch("../RegistroServlet", {
            method: "POST",
            body: formData
        })
                .then(response => {
                    console.log("Response status:", response.status);
                    return response.json();
                })
                .then(result => {
                    console.log("Respuesta del servidor:", result);
                    alert("Registro exitoso");
                    window.location.href = "MAIN.jsp";
                })
                .catch(error => {
                    console.error("Error detallado:", error);
                    alert("Hubo un error al registrarse");
                });
    });
});
