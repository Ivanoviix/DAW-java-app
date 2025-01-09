<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<!DOCTYPE html>
<html lang="ca">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Llibres</title>
        <!-- Incluir Bootstrap desde CDN -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            @media (max-width: 767px) {
                .table-responsive {
                    -ms-overflow-style: none; 
                    scrollbar-width: none; 
                }
                .table-responsive::-webkit-scrollbar {
                    display: none;
                } 
                .table th, .table td {
                    white-space: normal; 
                }
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1 class="text-center mb-4">Llista de Llibres</h1>

            <!-- Contenedor para los botones de acción -->
            <div class="btn-container text-center mb-4">
                <a href="insertarLibro.jsp" class="btn btn-success">Insertar Nuevo Libro</a>
            </div>

            <!-- Tabla de libros con la clase 'table-responsive' para hacerlo adaptativo -->
            <div class="table-container table-responsive">
                <table class="table table-bordered table-striped d-none d-md-table">
                    <thead class="table-dark">
                        <tr>
                            <th>ID</th>
                            <th>Títol</th>
                            <th>Autor</th>
                            <th>ISBN</th>
                            <th>Any de Publicació</th>
                            <th>Editorial</th>
                            <th>Accions</th> <!-- Nueva columna para botones de acción -->
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            // Obtener la lista de libros desde el atributo de la solicitud
                            List<Map<String, String>> llibres = (List<Map<String, String>>) request.getAttribute("llibres");

                            // Comprobar si el atributo está presente
                            if (llibres != null && !llibres.isEmpty()) {
                                for (Map<String, String> llibre : llibres) {
                                    out.println("<tr>");
                                    out.println("<td>" + llibre.get("id") + "</td>");
                                    out.println("<td>" + llibre.get("titol") + "</td>");
                                    out.println("<td>" + llibre.get("autor") + "</td>");
                                    out.println("<td>" + llibre.get("isbn") + "</td>");
                                    out.println("<td>" + llibre.get("anyPublicacio") + "</td>");
                                    out.println("<td>" + llibre.get("editorial") + "</td>");

                                    // Agregar los botones de editar y borrar en una nueva columna
                                    out.println("<td>");
                                    out.println("<a href='editarLibro.jsp?id=" + llibre.get("id") + "' class='btn btn-warning btn-sm'>Editar</a> ");
                                    out.println("<a href='borrarLibro.jsp?id=" + llibre.get("id") + "' class='btn btn-danger btn-sm'>Borrar</a>");
                                    out.println("</td>");

                                    out.println("</tr>");
                                }
                            } else {
                                out.println("<tr><td colspan='7' class='text-center'>No hi ha llibres disponibles.</td></tr>");
                            }
                        %>
                    </tbody>
                </table>

                <!-- Versión en lista para pantallas pequeñas -->
                <div class="d-block d-md-none">
                    <%
                        // Repetir el contenido para pantallas pequeñas usando tarjetas
                        if (llibres != null && !llibres.isEmpty()) {
                            for (Map<String, String> llibre : llibres) {
                    %>
                    <div class="card mb-3">
                        <div class="card-body">
                            <h5 class="card-title"><strong>ID:</strong> <%= llibre.get("id")%></h5>
                            <p><strong>Títol:</strong> <%= llibre.get("titol")%></p>
                            <p><strong>Autor:</strong> <%= llibre.get("autor")%></p>
                            <p><strong>ISBN:</strong> <%= llibre.get("isbn")%></p>
                            <p><strong>Any de Publicació:</strong> <%= llibre.get("anyPublicacio")%></p>
                            <p><strong>Editorial:</strong> <%= llibre.get("editorial")%></p>
                            <a href="editarLibro.jsp?id=<%= llibre.get("id")%>" class="btn btn-warning btn-sm">Editar</a>
                            out.println("<a href='borrarLibro.jsp?id=" + llibre.get("id") + 
                                            "&titol=" + URLEncoder.encode(llibre.get("titol"), "UTF-8") + 
                                            "' class='btn btn-danger btn-sm'>Borrar</a>");

                            <a href="borrarLibro.jsp?id=<%= llibre.get("id")%>&titol=<%= URLEncoder.encode(llibre.get("titol"), "UTF-8")%>" class="btn btn-danger btn-sm">Borrar</a>

                        </div>
                    </div>
                    <%
                        }
                    } else {
                    %>
                    <p class="text-center">No hi ha llibres disponibles.</p>
                    <%
                        }
                    %>
                </div>

            </div>
        </div>

        <!-- Incluir Bootstrap JS y Popper.js desde CDN -->
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
    </body>
</html>
