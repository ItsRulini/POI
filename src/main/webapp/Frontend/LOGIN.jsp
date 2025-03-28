<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Login</title>
        <!-- css -->
        <link rel="stylesheet" href="LOGIN.css">
        <!-- Uso de GSAP para animaciones -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/gsap/3.12.5/gsap.min.js" integrity="sha512-7eHRwcbYkK4d9g/6tD/mhkf++eoTHwpNM9woBxtPUBWm67zeAfFC+HrdoE2GanKeocly/VxeLvIqwvCdk7qScg==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
        <!-- Fonts Awesome para iconos -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" integrity="sha512-DTOQO9RWCH3ppGqcWaEA1BIZOC6xxalwEsw9c2QQeAIftl+Vegovlnee1c9QX4TctnWMn13TZye+giMm8e2LwA==" crossorigin="anonymous" referrerpolicy="no-referrer" />

    </head>
    <body>
        <div class="login-container">
            <div class="container">
                <div class="main">
                    <h2>LOGIN</h2>
                    <p class="text">Start chating</p>
                    <div class="content">
                        <form id="Main" action="../LoginServlet" method="POST">
                            <input type="text" name="user" placeholder="Username" required autofocus="">
                            <input type="password" name="pwrd" placeholder="Password" required autofocus="">
                            <button class="btn" type="submit">Log In</button>
                            <div class="SignUp">
                                <p class="p-signup">Don't have an account yet? </p>
                                <a href="#" id="Crear"> Sign Up</a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        
        <script>
            // Obtener los parámetros de la URL
            const params = new URLSearchParams(window.location.search);
            if (params.has("error")) {
                alert("Error al iniciar sesión: usuario o contraseña incorrectos.");
            }
        </script>

        <!-- Ventana PopUp para registrarse -->
        <div class="PopUp"> <!-- fondo -->
            <form id="registroForm" enctype="multipart/form-data">
                <div class="modal-content"> <!-- Ventana base -->
                    <div class="modal-display">
                        <button class="close"><i class="fa-solid fa-arrow-left"></i></button>
                        <p>Sign Up</p>
                    </div>
                    <div class="separador">
                        <hr class="h">
                    </div>
                    <div class="modal-pfp">
                        <!-- aceptar archivo -->
                        <div class="ImageLoad">
                            <img class="ImageLoaded" src="logo.jpg" id="profile-image">
                            <label for="input-file"> Choose image</label>
                            <input type="file" name ="avatar" accept="image/*" id="input-file">    
                        </div>
                    </div>
                    <div class="modal-icon-name">
                        <!-- primero -->
                        <div class="modal-firtst-name">
                            <i class="fa-solid fa-user"></i>
                            <input type="text" name="nombres" id="" placeholder="Fisrt Name">
                        </div class="modal-firtst-name">
                        <!-- segundo -->
                        <div class="modal-second-name">
                            <i class="fa-solid fa-user"></i>
                            <input type="text" name="apellidoPaterno" id="" placeholder="Last Name">
                        </div class="modal-second-name">

                    </div>
                    <div class="modal-icon-birthdate">
                        <!-- tercero -->
                        <div class="modal-third-name">
                            <i class="fa-solid fa-user"></i>
                            <input type="text" name="apellidoMaterno" id="" placeholder="Last Name">
                        </div class="modal-third-name">
                        <!-- fecha de nacimiento -->
                        <div class="modal-birthdate">
                            <i class="fa-solid fa-cake"></i>
                            <input type="date" name="nacimiento" id="" placeholder="">
                        </div class="modal-birthdate">
                    </div>
                    <div class="modal-user-password">
                        <!-- correo -->
                        <div class="modal-user">
                            <i class="fa-solid fa-user"></i>
                            <input type="text" name="usuario" id="" placeholder="Username">
                        </div class="modal-user">
                        <!-- contraseña -->
                        <div class="modal-password">
                            <i class="fa-solid fa-key"></i>
                            <input type="password" name="password" id="" placeholder="Password1234">
                        </div class="modal-password">
                    </div>
                    <div class="modal-email">
                        <i class="fa-solid fa-envelope"></i>
                        <input type="text" name="correo" id="" placeholder="email@example.com">
                    </div class="modal-email">
                    <div class="btn-sign">
                        <a href="LOGIN.jsp" >Register</a>
                    </div>
                </div>
            </form>


            <!--
            <div class="modal-register">
                <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                <button class="button">
                <i class="fa-solid fa-heart"></i>Like
                </button>
            </div>
            -->
        </div>
        <!-- gsap es para animar la página -->
        <script>
            gsap.from('.main', 1.2, {opacity: 0, y: 150, delay: 0.5})
            gsap.from('h2', 1.2, {opacity: 0, y: -170, delay: 0.5})
            gsap.from('.text', 1.2, {opacity: 0, x: 0, delay: 1})

            gsap.from('.SignUp', 1.2, {opacity: 0, y: 150, delay: 0.5})

        </script>
        <script>
            let profilePic = document.getElementById("profile-image");
            let inputFile = document.getElementById("input-file");

            inputFile.onchange = function () {
                profilePic.src = URL.createObjectURL(inputFile.files[0]);
            }

        </script>
        <script src="js/bootstrap.bundle.js"></script>
        <script src="LOGIN.js"></script>
        <script src="Registro.js"></script>
    </body>
</html>
