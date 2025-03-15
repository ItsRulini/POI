<%@page import="models.Usuario"%>
<%@page import="models.Tarea_Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashSet"%>


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
                <li><a href="#" id="Crear">Tasks</a></li>
                <li><a href="../LogOutServlet">Log out</a></li>
            </ul>
        </nav>
        <a href="PROFILE.jsp" class="cta"><button class="btn-profile">Profile</button></a>
    </header>
    
    <%
        List<Usuario> usuarios = (List) request.getSession().getAttribute("Lista");
        
        HashSet<Tarea_Usuario> listaTareas = (HashSet<Tarea_Usuario>) request.getSession().getAttribute("ListaTareas");

        String usuario = request.getSession().getAttribute("Usuario").toString();
        String avatar = request.getSession().getAttribute("Avatar") == null ? "" : request.getSession().getAttribute("Avatar").toString();
    %>
    
    
    <div class="container-interface">
        <!-- aqui se muestra el icono del usuario
         junto con sus chats disponibles y para crear nuevos -->
         <!-- 1. -->
         
        <div class="sidebar">
            <!-- nos ayuda a solo centrar el usuario (su foto) -->
            <div class="user-container">
                <div class="user-photo">
                    <img class="user-img" alt="ChiChat" src="<%=avatar%>">
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
                        <i class="fa-solid fa-plus" id="CrearChat"></i>
                        <i class="fa-solid fa-ellipsis-vertical" id="CrearDelete"></i>
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
                    <p class="username">Nombre de la conversación</p>
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
                    <p>Conversation</p>
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
                <div class="window-bubble">
                    <img class="BubblePic" alt="ChiChat" src="logo.jpg">
                    <textarea type="text" placeholder="Este es mi primer mensaje"></textarea>
                </div>
                <div class="window-bubble-dos">
                    <textarea type="text" placeholder="Este es mi primer mensaje"></textarea>
                    <img class="BubblePicDos" alt="ChiChat" src="<%=avatar%>">
                </div>
            </div>
            <!-- barra para escribir -->
            <div class="bar-chat">
                <div class="write-bar">
                    <input type="text" name="barraMensaje" placeholder="What's on your mind?">
                    <button type="submit" class="btn-post-media"><i class="fa-solid fa-paperclip"></i></button>
                    <button type="submit" class="btn-post-location"><i class="fa-solid fa-location-dot"></i></button>
                    
                    <!--Botón para enviar el contenido de este mensaje-->
                    <button type="submit" class="btn-post"><i class="fa-solid fa-paper-plane"></i></button>
                </div>
            </div>
        </div>
        
        <!-- 3. -->
        <!-- ventana de premios -->
        <div class="additional-container">
            <div class="additional-container-n">
                <h1>Rewards</h1>
                <div class="separador-h1">
                    <hr class="h-h1">
                </div>
                <!-- burbuja de recompensas (lo puse de notificaciones pq si era para notificaciones, pero asi lo dejo ya xd)-->
                <div class="notificacion-container">
                    <div class="notificacion-bubble">
                        <p id="CrearReward">Claim your reward</p>
                    </div>
                </div>
            </div>
            <div class="additional-container-p">
                <h1>Task Progress</h1>
                <div class="separador-h2">
                    <hr class="h-h2">
                </div>
                <!-- progreso de las tareas -->
                
                <%for(Tarea_Usuario item : listaTareas) {%>
                
                <div class="task-container">
                    <div class="task-progress">
                        <p> <%=item.getTarea().getDescripcion()%></p>
                    </div>
                </div>
                <%}%>
            </div>
        </div>
    <!-- Ventana PopUp para crear las tareas -->
    <div class="PopUp"> <!-- fondo -->
        <div class="modal-content"> <!-- Ventana base -->
            <button class="close"><i class="fa-solid fa-arrow-left"></i></button>
            <div class="task-info">
                <h4>Create a new task</h4>
                <div class="separador-h4">
                    <hr class="h-h4">
                </div>
                <textarea name="description-task" id="task" placeholder="Task description..."></textarea>
                <div class="task-check">
                    <h3>Select the reward</h3>
                    <p><input type="checkbox" name="section" value="sports">#1 Reward</p>
                    <p><input type="checkbox" name="section" value="business">#2 Reward</p>
                </div>
                <button type="submit" class="task-btn">Create task</button>
            </div>
        </div>
    </div>
    
    </div>
    
            <!-- Ventana PopUp para reclamar recompensas -->
    <div class="PopUpReward"> <!-- fondo -->
        <div class="modal-reward"> <!-- Ventana base -->
            <button class="close-reward"><i class="fa-solid fa-arrow-left"></i></button>
            <div class="task-reward">
                <div class="separador-reward">
                    <hr class="h-reward">
                </div>
                <h3>Select your reward</h3>
                <p><input type="checkbox" name="section" value="sports">#1 Reward</p>
                <p><input type="checkbox" name="section" value="business">#2 Reward</p>
                <button type="submit" class="task-reward-btn">Claim</button>
            </div>
        </div>
    </div>
    
    <!-- Ventana PopUp para crear chat -->
    <div class="PopUpChat"> <!-- fondo -->
        <form action="../InsertarChatServlet" method="POST" class="modal-chat"> <!-- Ventana base -->
            <div class="close-newchat">
                <button class="close-chat"><i class="fa-solid fa-arrow-left"></i></button>
                <!--<p>New message</p>-->
                <input type="text" name="nombreChat" placeholder="Chat name" required>
            </div>
            <div class="chat-to">
                <p>To:</p>
            </div>
            <div id="selected-users"></div> <!-- Aquí van los inputs ocultos -->
            <div class="separador-chat">
                <hr class="h-chat">
            </div>
            <div class="c-chat">
                <%
                for(Usuario item : usuarios) {
                %>
                <div class="new-convo" data-id="<%=item.getIdUsuario()%>">
                    <img class="new-pfp" alt="ChiChat" src="<%=item.getAvatar()%>">
                    <p><%=item.getUsuario()%></p>
                </div>
                <%}%>
            </div>
            <button type="submit" class="start-btn">Start chatting</button>
        </form>
    </div>
    <!-- Ventana PopUp para eliminar chat -->
    <div class="PopUpDelete"> <!-- fondo -->
        <div class="modal-delete"> <!-- Ventana base -->
            <div class="close-deletechat">
                <button class="close-delete"><i class="fa-solid fa-arrow-left"></i></button>
                <p>Delete chat</p>
            </div>
            <div class="separador-delete">
                <hr class="h-delete">
            </div>
            <div class="c-delete">
                <div class="new-delete">
                    <img class="new-deleteimg" alt="ChiChat" src="logo.jpg">
                    <p>Chat or Username</p>
                </div>
            </div>
        </div>
    </div>
    
    <script src="js/bootstrap.bundle.js"></script>
    <script src="MAIN.js"></script>
    
</body>
</html>