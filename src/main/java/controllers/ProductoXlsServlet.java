package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Producto;
import service.ProductoService;
import service.ProductoServiceImplement;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/*
 * Servlet unificado que maneja dos rutas:
 * /productos.html → muestra productos en HTML (a través del enlace corregido)
 * /productos.xls → exporta productos como archivo Excel
 */
@WebServlet({"/productos.xls", "/productos.html"})
public class ProductoXlsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ProductoService service = new ProductoServiceImplement();
        // Llama a tu método Listar()
        List<Producto> productos = service.listar();

        // 1. Determinar si es HTML o XLS
        String servletPath = req.getServletPath();
        boolean esXls = servletPath.endsWith(".xls");

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
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Listado de productos</h1>");

                // Enlace a la exportación de Excel, usando req.getContextPath() para compatibilidad
                out.println("<p><a href=\"" + req.getContextPath() + "/productos.xls" + "\">exportar a excel</a></p>");

                // si tiene este no hay ningun problema
                out.println("<p><a href=\"" + req.getContextPath() + "/productojson" + "\">mostrar json</a></p>");
            }

            // Generar la tabla (funciona para HTML y Excel)
            out.println("<table border='1'>"); // agregado borde para mejor visualización en Excel
            out.println("<tr>");
            out.println("<th>ID PRODUCTO</th>");
            out.println("<th>NOMBRE</th>");
            out.println("<th>CATEGORIA</th>");
            out.println("<th>PRECIO</th>");
            out.println("</tr>");

            productos.forEach(p -> {
                out.println("<tr>");
                out.println("<td>" + p.getIdProducto() + "</td>");
                out.println("<td>" + p.getNombre() + "</td>");
                out.println("<td>" + p.getCategoria() + "</td>");
                out.println("<td>" + p.getPrecio() + "</td>");
                out.println("</tr>");
            });

            out.println("</table>");

            // Cerrar etiquetas HTML solo si NO es Excel
            if (!esXls) {
                out.println("</body>");
                out.println("</html>");
            }
        }
    }
}