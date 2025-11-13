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

    // Función auxiliar para obtener el usuario de la cookie
    private Optional<String> getUsernameCookie(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies() != null ? req.getCookies() : new Cookie[0];
        return Arrays.stream(cookies)
                .filter(c -> "username".equals(c.getName()))
                .map(Cookie::getValue)
                .findAny();
    }

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
            String username = cookieOptional.get(); // Obtener el nombre de usuario
            resp.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = resp.getWriter()) {
                // Plantilla HTML
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<meta charset=\"utf-8\">");
                out.println("<title>Login " + username + "</title>");
                // ** Implementación de Bootstrap **
                out.println("<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH\" crossorigin=\"anonymous\">");
                out.println("</head>");
                out.println("<body class='bg-light'>");
                out.println("<div class='container mt-5 text-center'>");
                out.println("<h1 class='text-success'>Bienvenido a mi sistema!</h1>");
                out.println("<h3 class='alert alert-success mt-3'>Login exitoso, usuario: <strong>" + username + "</strong> has iniciado sesión con exito</h3>");
                out.println("<a href='" + req.getContextPath() + "/index.html' class='btn btn-primary mt-3'>Ir al inicio</a>");
                out.println("</div>");
                out.println("<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz\" crossorigin=\"anonymous\"></script>");
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
                // ** Implementación de Bootstrap **
                out.println("<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH\" crossorigin=\"anonymous\">");
                out.println("</head>");
                out.println("<body class='bg-light'>");
                out.println("<div class='container mt-5 text-center'>");
                out.println("<h1 class='text-success'>Login correcto</h1>");
                // ** Mensaje de bienvenida solicitado: Login correcto bienvenido a mi aplicacion (nombre del usuario que se esta logeando ) sesion conexito! **
                out.println("<h3 class='alert alert-success mt-3'>Bienvenido a mi aplicación **" + username + "** sesión con éxito!</h3>");
                out.println("<a href='" + req.getContextPath() + "/index.html' class='btn btn-primary mt-3'>Ir al inicio</a>");
                out.println("</div>");
                out.println("<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz\" crossorigin=\"anonymous\"></script>");
                out.println("</body>");
                out.println("</html>");

            }
            // Mantenemos la redirección comentada o la quitamos si quieres que la vista de éxito se muestre antes.
            // Si quieres que el mensaje de éxito se vea ANTES de redirigir, quita el sendRedirect.
            // resp.sendRedirect(req.getContextPath() + "index.html");
        }else  {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Lo sentimos no tienes acceso revisa los datos de usuario y contraseña ");
        }
    }
}