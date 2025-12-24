package com.rura.servlet;

import com.rura.util.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Servlet para procesar el registro de nuevos usuarios
 * Maneja las peticiones POST del formulario de registro
 */
@WebServlet("/registroUsuario")
public class RegistroUsuarioServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        // Configurar codificación de caracteres para soportar tildes y ñ
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        
        // Captura de datos del formulario
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String nombre = firstName + " " + lastName;
        String correo = req.getParameter("correo");
        String mobile = req.getParameter("mobile");
        String contrasena = req.getParameter("contrasena");
        String confirmPassword = req.getParameter("confirmPassword");
        String tipoUsuario = req.getParameter("tipoUsuario");
        
        // Validación básica de datos
        if (firstName == null || firstName.trim().isEmpty() ||
            lastName == null || lastName.trim().isEmpty() ||
            correo == null || correo.trim().isEmpty() ||
            mobile == null || mobile.trim().isEmpty() ||
            contrasena == null || contrasena.trim().isEmpty() ||
            confirmPassword == null || confirmPassword.trim().isEmpty()) {
            
            // Error: datos incompletos
            req.setAttribute("exito", false);
            req.setAttribute("mensaje", "Error: Todos los campos obligatorios deben estar completos");
            req.getRequestDispatcher("/exito.jsp").forward(req, resp);
            return;
        }
        
        // Validar que las contraseñas coincidan
        if (!contrasena.equals(confirmPassword)) {
            req.setAttribute("exito", false);
            req.setAttribute("mensaje", "Error: Las contraseñas no coinciden");
            req.getRequestDispatcher("/exito.jsp").forward(req, resp);
            return;
        }
        
        // Validación del tipo de usuario
        if (tipoUsuario == null || tipoUsuario.trim().isEmpty()) {
            tipoUsuario = "productor"; // Valor por defecto
        }
        
        if (!tipoUsuario.equals("productor") && 
            !tipoUsuario.equals("comprador") && 
            !tipoUsuario.equals("administrador")) {
            
            req.setAttribute("exito", false);
            req.setAttribute("mensaje", "Error: Tipo de usuario no válido");
            req.getRequestDispatcher("/exito.jsp").forward(req, resp);
            return;
        }
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            // Obtener conexión a la base de datos
            conn = DBUtil.getConnection();
            
            // Consulta SQL con PreparedStatement para prevenir inyección SQL
            String sql = "INSERT INTO usuarios (nombre, correo, mobile, contrasena, company_name, tipo_usuario, fecha_registro) " +
                        "VALUES (?, ?, ?, ?, ?, ?, NOW())";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nombre.trim());
            pstmt.setString(2, correo.trim().toLowerCase());
            pstmt.setString(3, mobile.trim());
            pstmt.setString(4, contrasena); // En producción, debería estar hasheada
            pstmt.setString(6, tipoUsuario);
            
            // Ejecutar la inserción
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                // Registro exitoso
                req.setAttribute("exito", true);
                req.setAttribute("mensaje", "¡Registro exitoso! Bienvenido/a " + nombre);
                req.setAttribute("nombreUsuario", nombre);
                req.setAttribute("tipoUsuario", tipoUsuario);
            } else {
                // No se insertó ningún registro
                req.setAttribute("exito", false);
                req.setAttribute("mensaje", "Error: No se pudo completar el registro");
            }
            
        } catch (SQLException e) {
            // Manejo de errores SQL (ej: correo duplicado)
            req.setAttribute("exito", false);
            
            if (e.getMessage().contains("Duplicate entry") || e.getMessage().contains("duplicate key")) {
                req.setAttribute("mensaje", "Error: El correo electrónico ya está registrado");
            } else {
                req.setAttribute("mensaje", "Error en la base de datos: " + e.getMessage());
            }
            
            e.printStackTrace();
            
        } finally {
            // Cerrar recursos
            DBUtil.close(pstmt);
            DBUtil.close(conn);
        }
        
        // Redirigir a la página de resultado
        req.getRequestDispatcher("/exito.jsp").forward(req, resp);
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        // Redirigir al formulario si se accede por GET
        resp.sendRedirect("registro.html");
    }
}

