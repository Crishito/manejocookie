package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie; // Importar Cookie
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Producto;
import service.ProductoService;
import service.ProductoServiceImplement;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays; // Importar Arrays
import java.util.List;
import java.util.Optional; // Importar Optional

/*
 * Servlet unificado que maneja dos rutas:
 * /productos.html → muestra productos en HTML (a través del enlace corregido)
 * /productos.xls → exporta productos como archivo Excel
 */
@WebServlet({"/productos.xls", "/productos.html"})
public class ProductoXlsServlet extends HttpServlet {

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
        // Llama a tu método Listar()
        List<Producto> productos = service.listar();

        // 1. Determinar si es HTML o XLS
        String servletPath = req.getServletPath();
        boolean esXls = servletPath.endsWith(".xls");

        // ** Lógica para verificar la sesión (cookie) **
        Optional<String> usernameOptional = getUsernameCookie(req);
        boolean esLogeado = usernameOptional.isPresent();
        String username = esLogeado ? usernameOptional.get() : "";
        // **********************************************

        // 2. Establecer el tipo de contenido y cabeceras
        if (esXls) {
            // Configuración correcta para la descarga de Excel
            resp.setContentType("application/vnd.ms-excel");
            resp.setHeader("Content-Disposition", "attachment; filename=productos.xls");

        }

        try (PrintWriter out = resp.getWriter()) {

            // Generar HTML de inicio solo si NO es Excel
            if (!esXls) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<meta charset=\"utf-8\">");
                out.println("<title>Listado de Productos</title>");
                // ** Implementación de Bootstrap **
                out.println("<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH\" crossorigin=\"anonymous\">");
                out.println("</head>");
                out.println("<body class='bg-light'>");
                out.println("<div class='container mt-5'>");
                out.println("<h1>Listado de productos</h1>");

                // ** Mensaje de Bienvenida al Administrador (Si está logeado) **
                if (esLogeado) {
                    // Texto solicitado: hola admin esta ciomo color azul
                    out.println("<p class='text-primary fw-bold fs-4'>Hola " + username + "!</p>");
                }

                // ** Botones/Enlaces Condicionales (Solo si está logeado) **
                out.println("<div class='d-flex gap-2 mb-3'>");
                if (esLogeado) {
                    // Enlace a la exportación de Excel, usando req.getContextPath() para compatibilidad
                    out.println("<p><a href=\"" + req.getContextPath() + "/productos.xls" + "\" class='btn btn-success'>Exportar a Excel</a></p>");

                    // si tiene este no hay ningun problema
                    out.println("<p><a href=\"" + req.getContextPath() + "/productojson" + "\">mostrar json</a></p>");
                } else {
                    out.println("<p class='text-muted'>Logeate para ver precios y opciones de exportación.</p>");
                }
                out.println("</div>"); // Cierre de div.d-flex
            }

            // Generar la tabla (funciona para HTML y Excel)
            out.println("<table class='table table-striped table-hover mt-3' border='1'>"); // agregado borde para mejor visualización en Excel y clases de Bootstrap
            out.println("<thead class='table-dark'>");
            out.println("<tr>");
            out.println("<th>ID PRODUCTO</th>");
            out.println("<th>NOMBRE</th>");
            out.println("<th>CATEGORIA</th>");
            // ** Columna PRECIO condicional **
            if (esLogeado || esXls) { // Muestra precio si está logeado O si es una exportación a Excel
                out.println("<th>PRECIO</th>");
            }
            out.println("</tr>");
            out.println("</thead>");
            out.println("<tbody>");

            productos.forEach(p -> {
                out.println("<tr>");
                out.println("<td>" + p.getIdProducto() + "</td>");
                out.println("<td>" + p.getNombre() + "</td>");
                out.println("<td>" + p.getCategoria() + "</td>");
                // ** Mostrar el precio si está logeado **
                if (esLogeado || esXls) {
                    out.println("<td>" + p.getPrecio() + "</td>");
                }
                out.println("</tr>");
            });

            out.println("</tbody>");
            out.println("</table>");

            // Cerrar etiquetas HTML solo si NO es Excel
            if (!esXls) {
                out.println("</div>"); // Cierre de div.container
                out.println("<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz\" crossorigin=\"anonymous\"></script>");
                out.println("</body>");
                out.println("</html>");
            }
        }
    }
}