package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Optional;

/*
 * Servlet de Login usando Cookies
 * Autor: Christian Zumárraga
 * Fecha: 7/11/2025
 */
@WebServlet({"/login", "/login.html"})
public class LoginServlet extends HttpServlet {

    // Usuario y contraseña quemados para pruebas
    private final static String USERNAME = "admin";
    private final static String PASSWORD = "12345";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Obtenemos las cookies del navegador
        Cookie[] cookies = req.getCookies() != null ? req.getCookies() : new Cookie[0];

        // Buscamos si existe una cookie llamada "username"
        Optional<String> cookieOptional = Arrays.stream(cookies)
                .filter(c -> "username".equals(c.getName()))
                .map(Cookie::getValue)
                .findAny();

        // Si ya existe cookie → Login exitoso
        if (cookieOptional.isPresent()) {
            resp.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = resp.getWriter()) {
                // Plantilla HTML
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<meta charset=\"utf-8\">");
                out.println("<title>Login - Bienvenido</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Bienvenido a mi sistema!</h1>");
                out.println("<h3>Login exitoso, usuario: <strong>" + cookieOptional.get() + "</strong></h3>");
                out.println("<a href='" + req.getContextPath() + "/index.html'>Ir al inicio</a>");
                out.println("</body>");
                out.println("</html>");
            }
        } else {
            // Si NO existe sesión → mostrar login.jsp
            getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Recibimos los valores del formulario
        String username = req.getParameter("user");
        String password = req.getParameter("password");

        // Validación quemada (solo pruebas)
        if (username.equals(USERNAME) && password.equals(PASSWORD)) {

            // Creamos cookie
            Cookie cookie = new Cookie("username", username);
            resp.addCookie(cookie);

            // Redireccionar al index
            resp.sendRedirect(req.getContextPath() + "/index.html");
        } else {
            // Si no coincide usuario/contraseña → error 401
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "Lo sentimos, usuario o contraseña incorrectos");
        }
    }
}
