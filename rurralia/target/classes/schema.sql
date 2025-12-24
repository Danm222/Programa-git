-- Script SQL para crear la tabla de usuarios en PostgreSQL
-- Base de datos: ruralia

-- Crear la tabla de usuarios
CREATE TABLE IF NOT EXISTS usuarios (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    mobile VARCHAR(20) NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    company_name VARCHAR(150),
    tipo_usuario VARCHAR(20) NOT NULL CHECK (tipo_usuario IN ('productor', 'comprador', 'administrador')),
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE
);

-- Crear índice en el campo correo para búsquedas rápidas
CREATE INDEX idx_usuarios_correo ON usuarios(correo);

-- Crear índice en el campo tipo_usuario
CREATE INDEX idx_usuarios_tipo ON usuarios(tipo_usuario);

-- Comentarios descriptivos
COMMENT ON TABLE usuarios IS 'Tabla principal de usuarios del sistema RurRalia';
COMMENT ON COLUMN usuarios.id IS 'Identificador único del usuario';
COMMENT ON COLUMN usuarios.nombre IS 'Nombre completo del usuario';
COMMENT ON COLUMN usuarios.correo IS 'Correo electrónico único del usuario';
COMMENT ON COLUMN usuarios.mobile IS 'Número de teléfono móvil del usuario';
COMMENT ON COLUMN usuarios.contrasena IS 'Contraseña del usuario (debe hashearse en producción)';
COMMENT ON COLUMN usuarios.company_name IS 'Nombre de la compañía (opcional)';
COMMENT ON COLUMN usuarios.tipo_usuario IS 'Tipo de usuario: productor, comprador o administrador';
COMMENT ON COLUMN usuarios.fecha_registro IS 'Fecha y hora de registro del usuario';
COMMENT ON COLUMN usuarios.activo IS 'Indica si el usuario está activo en el sistema';
