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
<body class="bg-light">
<div class="container mt-5">
    <div class="card p-4 mx-auto" style="max-width: 400px;">
        <h1 class="card-title text-center mb-4">INICIO DE SESIÓN</h1>
        <form action="/manejocookie/login" method="post">
            <div class="mb-3">
                <label for="user" class="form-label">Ingrese el usuario</label>
                <input type="text" id="user" name="user" class="form-control" required>
            </div>

            <div class="mb-3">
                <label for="password" class="form-label">Ingrese el password</label>
                <input type="password" id="password" name="password" class="form-control" required>
            </div>

            <div class="d-grid gap-2">
                <input type="submit" value="INICIAR SESIÓN" class="btn btn-primary">
            </div>
        </form>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>