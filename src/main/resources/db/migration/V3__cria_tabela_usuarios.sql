CREATE TABLE usuarios (
                        id BIGSERIAL PRIMARY KEY,
                        email VARCHAR(255) NOT NULL UNIQUE,
                        senha VARCHAR(255) NOT NULL,
                        role VARCHAR(50)

);

-- Insere um usuário admin para teste (senha: admin123 em BCrypt)
INSERT INTO usuarios (email, senha, role)
VALUES ('admin@biblioteca.com',
        '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.',
        'ADMIN');
