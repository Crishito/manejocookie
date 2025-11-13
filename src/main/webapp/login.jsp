<%--
  Created by IntelliJ IDEA.
  User: Bluematrix
  Date: 11/11/2025
  Time: 16:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login Usuario</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="bg-primary d-flex align-items-center justify-content-center" style="height: 100vh;">
<div class="container">
    <div class="card p-5 mx-auto shadow-lg" style="max-width: 450px;">
        <h1 class="card-title text-center mb-4 text-primary">INICIO DE SESIÓN</h1>
        <form action="/manejocookie/login" method="post">
            <div class="mb-3 form-floating">
                <input type="text" id="user" name="user" class="form-control" placeholder="Ingrese el usuario" required>
                <label for="user">Ingrese el usuario</label>
            </div>

            <div class="mb-4 form-floating">
                <input type="password" id="password" name="password" class="form-control" placeholder="Ingrese el password" required>
                <label for="password">Ingrese el password</label>
            </div>

            <div class="d-grid gap-2">
                <input type="submit" value="INICIAR SESIÓN" class="btn btn-primary btn-lg">
            </div>
        </form>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>