package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Producto;
import service.ProductoService;
import service.ProductoServiceImplement;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/*
 * Servlet que muestra la lista de productos en HTML
 */
@WebServlet("/producto.html")
public class ProductoServletXls extends HttpServlet {

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

        ProductoService service = new ProductoServiceImplement();
        List<Producto> productos = service.listar();

        // ** Lógica para verificar la sesión (cookie) **
        Optional<String> usernameOptional = getUsernameCookie(req);
        boolean esLogeado = usernameOptional.isPresent();
        String username = esLogeado ? usernameOptional.get() : "";
        // **********************************************

        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            //Creo la plantilla html
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset=\"utf-8\">");
            out.println("<title>Lista de Productos</title>");
            // ** Implementación de Bootstrap **
            out.println("<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH\" crossorigin=\"anonymous\">");
            out.println("</head>");
            out.println("<body class='bg-light'>");
            out.println("<div class='container mt-5'>");
            out.println("<h1>Lista de Productos</h1>");

            // ** Mensaje de Bienvenida al Administrador (Si está logeado) **
            if (esLogeado) {
                out.println("<p class='text-primary fw-bold fs-4'>Hola " + username + "!</p>");
            }

            // ** Botones/Enlaces Condicionales (Solo si está logeado) **
            out.println("<div class='d-flex gap-2 mb-3'>");
            if (esLogeado) {
                //Imprime en el HTML un enlace "Exportar a Excel".
                out.println("<a href=\"" + req.getContextPath() + "/productos.xls\" class='btn btn-success'>Exportar a Excel</a>");
            } else {
                out.println("<p class='text-muted'>Logeate para ver precios y exportar.</p>");
            }
            out.println("</div>"); // Cierre de div.d-flex

            out.println("<table class='table table-striped table-hover mt-3'>");
            out.println("<thead class='table-dark'>");
            out.println("<tr>");
            out.println("<th>ID PRODUCTO</th>");
            out.println("<th>NOMBRE</th>");
            out.println("<th>CATEGORIA</th>");
            // ** Columna PRECIO condicional **
            if (esLogeado) {
                out.println("<th>PRECIO</th>");
            }
            out.println("</tr>");
            out.println("</thead>");
            out.println("<tbody>");

            // lleno mi tavla con los productos
            productos.forEach(p -> {
                out.println("<tr>");
                out.println("<td>" + p.getIdProducto() + "</td>");
                out.println("<td>" + p.getNombre() + "</td>");
                out.println("<td>" + p.getCategoria() + "</td>");
                // ** Mostrar el precio si está logeado **
                if (esLogeado) {
                    out.println("<td>" + p.getPrecio() + "</td>");
                }
                out.println("</tr>");
            });
            out.println("</tbody>");
            out.println("</table>");
            out.println("</div>"); // Cierre de div.container
            out.println("<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz\" crossorigin=\"anonymous\"></script>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}