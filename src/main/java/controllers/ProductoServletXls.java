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
 * Servlet que muestra la lista de productos en HTML
 */
@WebServlet("/producto.html")
public class ProductoServletXls extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ProductoService service = new ProductoServiceImplement();
        List<Producto> productos = service.listar();

        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            //Creo la plantilla html
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset=\"utf-8\">");
            out.println("<title>Lista de Productos</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Lista de Productos</h1>");

            //Imprime en el HTML un enlace "Exportar a Excel".
            out.println("<a href=\"" + req.getContextPath() + "/productos.xls\">Exportar a Excel</a>");


            out.println("<table>");
            out.println("<tr>");
            out.println("<th>ID PRODUCTO</th>");
            out.println("<th>NOMBRE</th>");
            out.println("<th>CATEGORIA</th>");
            out.println("<th>PRECIO</th>");
            out.println("</tr>");
            // lleno mi tavla con los productos
            productos.forEach(p -> {
                out.println("<tr>");
                out.println("<td>" + p.getIdProducto() + "</td>");
                out.println("<td>" + p.getNombre() + "</td>");
                out.println("<td>" + p.getCategoria() + "</td>");
                out.println("<td>" + p.getPrecio() + "</td>");
                out.println("</tr>");
            });

            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
