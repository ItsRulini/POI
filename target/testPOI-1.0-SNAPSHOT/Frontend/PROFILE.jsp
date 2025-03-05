<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Profile</title>
    <!-- css -->
    <link rel="stylesheet" href="PROFILE.css">
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
        String biografia = request.getSession().getAttribute("Descripcion") != null ? request.getSession().getAttribute("Descripcion").toString() : "";
        String pass = request.getSession().getAttribute("Contraseña").toString();
        String avatar = request.getSession().getAttribute("Avatar").toString();
    %>
    

    <div class="profile-container">
        <div class="profile-container-general">
            <div class="profile-img-user">
                <div class="profile-pfp">
                    <img class="Pfp" alt="ChiChat" src="<%=avatar%>">
                </div>
                <div class="profile-user-bio">
                    <input type="text" name="" id="" value="<%=usuario%>" placeholder="Username" class="profile-p">
                    
                    <input type="text" name="" id="" value= "<%=biografia%>" placeholder="Mi biografia">
                </div>
            </div>
            <!-- separador -->
            <div class="separador">
                <hr class="h">
            </div>
            <!-- 1. -->
            <div class="profile-icon-name">
                <i class="fa-solid fa-user"></i>
                <p>Names</p>
            </div>
            <div class="profile-names">
                <input type="text" name="" id="" value="<%=nombre%>" placeholder="First name">
                <input type="text" name="" id="" value="<%=paterno%>" placeholder="Last name">
                <input type="text" name="" id="" value="<%=materno%>" placeholder="Last name">
            </div>
            <!-- 2. -->
            <div class="profile-icon-manage">
                <i class="fa-solid fa-lock"></i>
                <p>Account Managment</p>
            </div>
            <div class="profile-manage">
                <input type="text" name="" id="" value="<%=email%>" placeholder="email@example.com">
                <input type="password" name="" id="" value="<%=pass%>" placeholder="Password">
            </div>
            <!-- 3. -->
            <div class="profile-icon-birth">
                <i class="fa-solid fa-cake"></i>
                <p>Birthdate</p>

                <i class="fa-solid fa-user"></i>
                <p class="Member-since">Member Since</p>
            </div>
            <div class="profile-birth">
                <input type="date" name="" id="" value="<%=nacimiento%>" placeholder="Birthdate">
                <input type="date" name="" id="" value="<%=registro%>" placeholder="Member Since">
            </div>
            <!-- boton -->
            <div class="profile-save-changes">
                <button type="submit" class="btn-save-changes"></i>Save Changes</button>
            </div>
            <!-- separador -->
            <div class="separador-dos">
                <hr class="h2">
            </div>
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
    <!-- para el popup -->   
    <script src="js/bootstrap.bundle.js"></script>
    <script src="PROFILE.js"></script>
</body>
</html>