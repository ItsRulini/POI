<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ChiChat</title>
    <!-- css -->
    <link rel="stylesheet" href="MAIN.css">
    <!-- Uso de GSAP para animaciones -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/gsap/3.12.5/gsap.min.js" integrity="sha512-7eHRwcbYkK4d9g/6tD/mhkf++eoTHwpNM9woBxtPUBWm67zeAfFC+HrdoE2GanKeocly/VxeLvIqwvCdk7qScg==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    <!-- Fonts Awesome para iconos -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" integrity="sha512-DTOQO9RWCH3ppGqcWaEA1BIZOC6xxalwEsw9c2QQeAIftl+Vegovlnee1c9QX4TctnWMn13TZye+giMm8e2LwA==" crossorigin="anonymous" referrerpolicy="no-referrer" />

</head>
<body>
    <header>
        <img class="Logo" alt="ChiChat" src="logo.jpg">
        <nav>
            <ul class="navlinks">
                <li><a href="MAIN.jsp">Chats</a></li>
                <li><a href="#">Rewards</a></li>
                <li><a href="LOGIN.jsp">Log out</a></li>
            </ul>
        </nav>
        <a href="PROFILE.jsp" class="cta"><button class="btn-profile">Profile</button></a>
    </header>
    
    <%
        String usuario = request.getSession().getAttribute("Usuario").toString();
        String nombre = request.getSession().getAttribute("Nombres").toString();
        String paterno = request.getSession().getAttribute("Paterno").toString();
        String materno = request.getSession().getAttribute("Materno").toString();
        String email = request.getSession().getAttribute("Email").toString();
        String nacimiento = request.getSession().getAttribute("Nacimiento").toString();
        String registro = request.getSession().getAttribute("Registro").toString();
        //String descripcion = request.getSession().getAttribute("Descripcion").toString();
    %>
    
    
    <div class="container-interface">
        <!-- aqui se muestra el icono del usuario
         junto con sus chats disponibles y para crear nuevos -->
         <!-- 1. -->
        <div class="sidebar">
            <!-- nos ayuda a solo centrar el usuario (su foto) -->
            <div class="user-container">
                <div class="user-photo">
                    <img class="user-img" alt="ChiChat" src="logo.jpg">
                    <p><%=usuario%></p>
                </div>
            </div>
            <div class="separador-container">
                <hr class="h">
            </div>
            <div class="elements-container">
                <div class="elements-aux">
                    <p>Last chats</p>
                    <div class="icons-group">
                        <i class="fa-solid fa-plus"></i>
                        <i class="fa-solid fa-ellipsis-vertical"></i>
                    </div>
                </div>
            </div>
            <!-- 1. -->
            <div class="chats-display">
            <!-- chats disponibles o creados -->
             <div class="chats-active"> <!-- container principal del chat -->
                <div class="user-display-photo">
                    <img class="Pic" alt="ChiChat" src="logo.jpg">
                </div>
                <div class="user-convo">
                    <p class="username">Mi primer chat</p>
                    <p class="conversation">Mi primera conversacion</p>
                </div>
             </div>
             
            </div>
        </div>
        <!-- 2. -->
        <!-- chat acttivo -->
        <div class="chat-container">
            <!-- barra de arriba del chat -->
            <div class="chat-display-top">
                <div class="display-top-aux">
                    <p>Conversation name</p>
                </div>
                <div class="display-icons-group">
                    <i class="fa-solid fa-phone"></i>
                    <i class="fa-solid fa-video"></i>
                </div>
            </div>
            <div class="separador-aux">
                <hr class="h-display">
            </div>
            <div class="window-chat">

            </div>
            <!-- barra para escribir -->
            <div class="bar-chat">
                <div class="write-bar">
                    <input type="text" name="" placeholder="What's on your mind?">
                    <button type="submit" class="btn-post"><i class="fa-solid fa-paper-plane"></i></button>
                </div>
            </div>
        </div>
        <!-- 3. -->
        <!-- ventana auviliar? -->
        <div class="additional-container">
    
        </div>
    </div>
    
</body>
</html>