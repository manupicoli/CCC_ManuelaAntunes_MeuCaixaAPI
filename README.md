# 📦 Meu Caixa – Backend

## 📌 Sobre
API desenvolvida em **Java 21** com **Spring Boot** para gerenciar o fluxo de caixa de pequenos negócios.  
Responsável pela autenticação, persistência de dados, geração de relatórios e envio de notificações.

---

## 🛠️ Tecnologias
- Java 21
- Spring Boot
- PostgreSQL
- JUnit / Mockito (testes)
- Docker
- Swagger (documentação da API)

---

## ⚙️ Funcionalidades
- Autenticação e gerenciamento de usuários
- CRUD de categorias
- CRUD de registros financeiros (entradas e saídas)
- Geração de relatórios financeiros (PDF)
- Notificações por e-mail

---

## 🚀 Como rodar

### Pré-requisitos
- Java 21
- PostgreSQL
- Maven ou Gradle

### Rodando localmente
```bash
# Clone o repositório
git clone https://github.com/seu-usuario/meu-caixa-backend.git
cd meu-caixa-backend

# Configure o banco no application.properties

# Rodar a aplicação
./mvnw spring-boot:run
```

Acesse a documentação da API 👉 http://localhost:8080/swagger-ui.html

---

Desenvolvido por **Manuela Picoli Antunes**