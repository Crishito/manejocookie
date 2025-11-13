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

    //Declaramos e inicializamos dos variable
    //de tipo String para el usuario y el password
    // Usuario y contraseña quemados para pruebas
    private final static String USERNAME = "admin";
    private final static String PASSWORD = "12345";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Obtenemos las cookies del navegador
        Cookie[] cookies = req.getCookies() != null ? req.getCookies() : new Cookie[0];

        // Buscamos si existe una cookie llamada "username"
        //Obtenemos la información que esta dentro de la cookie
        Optional<String> cookieOptional = Arrays.stream(cookies)
                .filter(c -> "username".equals(c.getName()))
                //Convertimos la cookie a String
                .map(Cookie::getValue)
                .findAny();

        // Si ya existe cookie - Login exitoso
        if (cookieOptional.isPresent()) {
            resp.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = resp.getWriter()) {
                // Plantilla HTML
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<meta charset=\"utf-8\">");
                out.println("<title>Login " + cookieOptional.get() + "</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Bienvenido a mi sistema!</h1>");
                out.println("<h3>Login exitoso, usuario: <strong>" + cookieOptional.get() + "has iniciado sesión con exito</h3>");
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

        // Creamos las variables para priocesar la informacion del formulario
        String username = req.getParameter("user");
        String password = req.getParameter("password");

        // Implementamos una condicional para saber si el nombre del usuario y contraseña
        // es igual a la información que tenemos guardada
        if (username.equals(USERNAME) && password.equals(PASSWORD)) {
            resp.setContentType("text/html;charset=UTF-8");
            // Creamos e instanciamos el objeto cookie
            Cookie cookie = new Cookie("username", username);
            //Añadimos la cookie
            resp.addCookie(cookie);
            try (PrintWriter out = resp.getWriter()) {
                resp.setContentType("text/html;charset=UTF-8");

                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<meta charset=\"utf-8\">");
                out.println("<title>Login Exitoso</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Bienvenido a mi aplicación" + username + "sesión con exito</h1>");
                out.println("<a href='" + req.getContextPath() + "/index.html'>Ir al inicio</a>");
                out.println("</body>");
                out.println("</html>");

            }
            resp.sendRedirect(req.getContextPath() + "index.html");
        }else  {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Lo sentimos no tienes acceso revisa los datos de usuario y contraseña ");
        }
    }
}
