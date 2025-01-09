
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author ivan-
 */
@WebServlet("/editarLibro")
public class EditarLibro extends HttpServlet {

    
    /** 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idLibro = Integer.parseInt(request.getParameter("id"));
        System.out.println("idLibro recibido: " + idLibro);  // Verifica que recibes el ID correctamente

        try (Connection conn = Connexio.getConnexio()) {
            String sql = "SELECT * FROM llibres WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, idLibro);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    // Verifica que los datos son correctos
                    String titol = rs.getString("titol");
                    String isbn = rs.getString("isbn");
                    int anyPublicacio = rs.getInt("any_publicacio");
                    int idEditorial = rs.getInt("id_editorial");
                    System.out.println("Datos libro: " + titol + ", " + isbn + ", " + anyPublicacio + ", " + idEditorial);  // Verifica que los datos sean correctos

                    // Establecemos los atributos que se usarán en el formulario de edición
                    request.setAttribute("titol", titol);
                    request.setAttribute("isbn", isbn);
                    request.setAttribute("anyPublicacio", anyPublicacio);
                    request.setAttribute("idEditorial", idEditorial);

                    // Redirigimos a la página de edición (editarLibro.jsp)
                    request.getRequestDispatcher("editarLibro.jsp").forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Libro no encontrado.");
                }
            }
        } catch (Exception e) {
            throw new ServletException("Error al obtener los datos del libro.", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recupera los parámetros del formulario
        String titol = request.getParameter("titol");
        String isbn = request.getParameter("isbn");
        String anyPublicacioParam = request.getParameter("any_publicacio");
        String editorialParam = request.getParameter("editorial");

        // Verifica si los parámetros necesarios están presentes y son válidos
        if (titol == null || titol.isEmpty() || isbn == null || isbn.isEmpty() || anyPublicacioParam == null || anyPublicacioParam.isEmpty() || editorialParam == null || editorialParam.isEmpty()) {
            request.setAttribute("error", "Todos los campos son obligatorios.");
            request.getRequestDispatcher("editarLibro.jsp").forward(request, response);
            return;
        }

        // Convertir el parámetro "any_publicacio" y "editorial" a int
        int anyPublicacio = Integer.parseInt(anyPublicacioParam); // Se asume que "any_publicacio" siempre será un número válido
        int editorialId = Integer.parseInt(editorialParam); // Se asume que "editorial" siempre será un valor numérico

        try (Connection conn = Connexio.getConnexio()) {
            String updateQuery = "UPDATE llibres SET titol = ?, isbn = ?, any_publicacio = ?, id_editorial = ? WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
                stmt.setString(1, titol);
                stmt.setString(2, isbn);
                stmt.setInt(3, anyPublicacio);
                stmt.setInt(4, editorialId);
                stmt.setInt(5, Integer.parseInt(request.getParameter("id")));

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    response.sendRedirect("Consulta");  // Redirigir a la lista de libros
                } else {
                    request.setAttribute("error", "No se pudo actualizar el libro.");
                    request.getRequestDispatcher("editarLibro.jsp").forward(request, response);
                }
            }
        } catch (Exception e) {
            throw new ServletException("Error al actualizar el libro.", e);
        }
    }
}
