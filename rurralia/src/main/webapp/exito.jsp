<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Obtener atributos enviados desde el servlet
    Boolean exito = (Boolean) request.getAttribute("exito");
    String mensaje = (String) request.getAttribute("mensaje");
    String nombreUsuario = (String) request.getAttribute("nombreUsuario");
    String tipoUsuario = (String) request.getAttribute("tipoUsuario");
    String companyName = (String) request.getAttribute("companyName");
    
    // Valor por defecto si no hay atributos
    if (exito == null) {
        exito = false;
        mensaje = "Error: No se recibieron datos del registro";
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%= exito ? "Registro Exitoso" : "Error en Registro" %> - Ruralia</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #6da76d 0%, #5a8f5a 100%);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;
        }

        .container {
            background: white;
            padding: 50px 40px;
            border-radius: 15px;
            box-shadow: 0 10px 40px rgba(0, 0, 0, 0.2);
            max-width: 500px;
            width: 100%;
            text-align: center;
        }

        .icon {
            font-size: 80px;
            margin-bottom: 20px;
        }

        .success-icon {
            color: #5a8f5a;
        }

        .error-icon {
            color: #e74c3c;
        }

        h1 {
            color: #333;
            margin-bottom: 15px;
            font-size: 28px;
        }

        .mensaje {
            color: #555;
            font-size: 18px;
            line-height: 1.6;
            margin-bottom: 30px;
        }

        .detalles {
            background: #f8f9fa;
            padding: 20px;
            border-radius: 10px;
            margin-bottom: 30px;
            text-align: left;
        }

        .detalles-item {
            margin-bottom: 10px;
            color: #555;
        }

        .detalles-item strong {
            color: #333;
        }

        .botones {
            display: flex;
            gap: 15px;
            justify-content: center;
            flex-wrap: wrap;
        }

        .btn {
            padding: 12px 30px;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            transition: transform 0.2s, box-shadow 0.2s;
        }

        .btn-primary {
            background: #5a8f5a;
            color: white;
        }

        .btn-secondary {
            background: #e0e0e0;
            color: #333;
        }

        .btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
        }

        .btn:active {
            transform: translateY(0);
        }

        @media (max-width: 480px) {
            .container {
                padding: 30px 20px;
            }

            .botones {
                flex-direction: column;
            }

            .btn {
                width: 100%;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <% if (exito) { %>
            <!-- Registro Exitoso -->
            <div class="icon success-icon">✓</div>
            <h1>¡Registro Exitoso!</h1>
            <p class="mensaje"><%= mensaje %></p>
            
            <% if (nombreUsuario != null) { %>
                <div class="detalles">
                    <div class="detalles-item">
                        <strong>Nombre:</strong> <%= nombreUsuario %>
                    </div>
                    <% if (companyName != null && !companyName.trim().isEmpty()) { %>
                    <div class="detalles-item">
                        <strong>Compañía:</strong> <%= companyName %>
                    </div>
                    <% } %>
                    <% if (tipoUsuario != null) { %>
                    <div class="detalles-item">
                        <strong>Tipo de usuario:</strong> 
                        <% 
                            String tipoFormatted = "";
                            switch(tipoUsuario) {
                                case "productor":
                                    tipoFormatted = "Productor Rural";
                                    break;
                                case "comprador":
                                    tipoFormatted = "Comprador";
                                    break;
                                case "administrador":
                                    tipoFormatted = "Administrador";
                                    break;
                                default:
                                    tipoFormatted = tipoUsuario;
                            }
                        %>
                        <%= tipoFormatted %>
                    </div>
                    <% } %>
                </div>
            <% } %>
            
            <div class="botones">
                <a href="login.html" class="btn btn-primary">Iniciar Sesión</a>
                <a href="index.html" class="btn btn-secondary">Ir al Inicio</a>
            </div>
            
        <% } else { %>
            <!-- Error en Registro -->
            <div class="icon error-icon">✗</div>
            <h1>Error en el Registro</h1>
            <p class="mensaje"><%= mensaje %></p>
            
            <div class="botones">
                <a href="registro.html" class="btn btn-primary">Intentar Nuevamente</a>
                <a href="index.html" class="btn btn-secondary">Volver al Inicio</a>
            </div>
        <% } %>
    </div>
</body>
</html>
