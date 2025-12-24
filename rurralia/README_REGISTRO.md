# Sistema de Registro de Usuarios - RurRalia

## 📋 Componentes Creados

### 1. **Formulario HTML** - [registro.html](src/main/webapp/registro.html)
Formulario responsivo con validación HTML5 que captura:
- Nombre completo
- Correo electrónico
- Contraseña (mínimo 6 caracteres)
- Tipo de usuario (productor, comprador, administrador)

Los datos se envían mediante POST al servlet `/registroUsuario`.

### 2. **Servlet de Procesamiento** - [RegistroUsuarioServlet.java](src/main/java/com/rura/servlet/RegistroUsuarioServlet.java)
Servlet que actúa como controlador del módulo de registro:
- ✅ Recibe datos del formulario
- ✅ Valida campos obligatorios y tipos de usuario
- ✅ Usa `PreparedStatement` para prevenir inyección SQL
- ✅ Maneja errores (campos vacíos, correos duplicados, errores de BD)
- ✅ Redirige a la página de resultado con atributos

### 3. **Página de Resultado** - [exito.jsp](src/main/webapp/exito.jsp)
Página JSP dinámica que muestra:
- ✅ Mensaje de éxito o error según el resultado
- ✅ Detalles del usuario registrado (si tuvo éxito)
- ✅ Botones para iniciar sesión o volver al inicio
- ✅ Diseño responsive con iconos visuales

## 🗄️ Base de Datos

### Configuración PostgreSQL
```properties
URL: jdbc:postgresql://localhost:5432/ruralia
Usuario: postgres
Contraseña: 123
```

### Crear la tabla de usuarios
Ejecuta el script [schema.sql](src/main/resources/schema.sql):
```bash
psql -U postgres -d ruralia -f src/main/resources/schema.sql
```

O manualmente:
```sql
CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    tipo_usuario VARCHAR(20) NOT NULL CHECK (tipo_usuario IN ('productor', 'comprador', 'administrador')),
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE
);
```

## 🚀 Compilar y Ejecutar

### 1. Compilar el proyecto
```bash
mvn clean package
```

### 2. Desplegar en servidor
Copia el archivo WAR generado en `target/rurralia-1.0-SNAPSHOT.war` a tu servidor de aplicaciones (Tomcat, WildFly, etc.).

### 3. Acceder al formulario
```
http://localhost:8080/rurralia/registro.html
```

## 📦 Dependencias Maven

```xml
- jakarta.servlet-api 5.0.0 (Servlet API)
- jakarta.servlet.jsp-api 3.0.0 (JSP API)
- postgresql 42.6.0 (Driver PostgreSQL)
- jakarta.servlet.jsp.jstl-api 2.0.0 (JSTL)
```

## 🔒 Seguridad

### Implementado:
- ✅ `PreparedStatement` para prevenir inyección SQL
- ✅ Validación de datos en el servidor
- ✅ Codificación UTF-8 para caracteres especiales
- ✅ Constraint de tipos de usuario en BD

### Recomendaciones para producción:
- ⚠️ **Hashear contraseñas** usando BCrypt o similar
- ⚠️ Implementar HTTPS
- ⚠️ Añadir validación de formato de correo en servidor
- ⚠️ Implementar CAPTCHA para prevenir bots
- ⚠️ Añadir validación de fortaleza de contraseña

## 📁 Estructura del Proyecto

```
rurralia/
├── pom.xml                          # Configuración Maven
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/rura/
│   │   │       ├── servlet/
│   │   │       │   └── RegistroUsuarioServlet.java  # Servlet de registro
│   │   │       └── util/
│   │   │           └── DBUtil.java                  # Utilidad de BD
│   │   ├── resources/
│   │   │   └── schema.sql                           # Script de BD
│   │   └── webapp/
│   │       ├── registro.html                        # Formulario HTML
│   │       └── exito.jsp                            # Página de resultado
│   └── test/java/
└── README_REGISTRO.md                               # Este archivo
```

## 🔄 Flujo de Datos

1. Usuario completa [registro.html](src/main/webapp/registro.html)
2. Formulario envía POST a `/registroUsuario`
3. [RegistroUsuarioServlet](src/main/java/com/rura/servlet/RegistroUsuarioServlet.java) procesa:
   - Captura parámetros
   - Valida datos
   - Inserta en BD usando `PreparedStatement`
   - Establece atributos de resultado
4. Forward a [exito.jsp](src/main/webapp/exito.jsp)
5. JSP muestra resultado dinámicamente

## 🧪 Probar el Sistema

### Registro exitoso:
```
Nombre: Juan Pérez
Correo: juan@example.com
Contraseña: 123456
Tipo: productor
```

### Casos de error a probar:
- Campos vacíos
- Correo duplicado
- Contraseña < 6 caracteres
- Tipo de usuario inválido

## 📝 Notas Adicionales

- El servlet redirige peticiones GET a `registro.html`
- Los errores SQL se manejan específicamente (ej: duplicados)
- La página de éxito muestra información personalizada según el resultado
- Todos los archivos usan UTF-8 para soportar caracteres especiales
