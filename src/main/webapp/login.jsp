<%--
  Created by IntelliJ IDEA.
  User: Bluematrix
  Date: 11/11/2025
  Time: 16:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <title>Login Usuario</title>
</head>
<body>
<h1>INICIO DE SESIÓN</h1>
<div>
    <form action="/manejocookie/login" method="post">
        <div>
            <label for="user">Ingrese el usuario</label>
            <input type="text" id="user" name="user">
        </div>

        <div>
            <label for="password">Ingrese el password</label>
            <input type="password" id="password" name="password">
        </div>

        <div>
            <input type="submit" value="INICIAR SESIÓN">
        </div>
    </form>
</div>

</body>
</html>

