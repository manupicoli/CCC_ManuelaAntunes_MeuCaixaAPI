-- liquibase formatted sql
-- changeset manuela:0001_initial_schema

CREATE TABLE customer (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    code VARCHAR(20) NOT NULL
);

CREATE TABLE category (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    is_default BOOLEAN DEFAULT FALSE,
    customer_id BIGINT NULL,
    CONSTRAINT fk_category_customer FOREIGN KEY(customer_id) REFERENCES customer(id)
);

CREATE TABLE financial_record (
    id BIGSERIAL PRIMARY KEY,
    type VARCHAR(20) NOT NULL,
    amount DECIMAL(19,4) NOT NULL,
    due_date TIMESTAMP,
    payment_date TIMESTAMP,
    description VARCHAR(255),
    category_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    CONSTRAINT fk_financial_record_category FOREIGN KEY(category_id) REFERENCES category(id),
    CONSTRAINT fk_financial_record_customer FOREIGN KEY(customer_id) REFERENCES customer(id)
);

CREATE TABLE notification (
    id BIGSERIAL PRIMARY KEY,
    content TEXT NOT NULL,
    status VARCHAR(50) NOT NULL,
    recipient_email VARCHAR(255) NOT NULL,
    scheduled_date TIMESTAMP,
    financial_record_id BIGINT NULL,
    CONSTRAINT fk_notification_financial_record FOREIGN KEY(financial_record_id) REFERENCES financial_record(id)
);

CREATE TABLE "user" (
    id BIGSERIAL PRIMARY KEY,
    role VARCHAR(15) NOT NULL,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(50) NOT NULL,
    customer_id BIGINT NOT NULL,
    CONSTRAINT fk_user_customer FOREIGN KEY(customer_id) REFERENCES customer(id)
);

INSERT INTO category (title, description, is_default, customer_id) VALUES
   ('Vendas', 'Receitas obtidas com vendas de produtos', TRUE, NULL),
   ('Prestação de serviços', 'Receitas obtidas por serviços prestados', TRUE, NULL),
   ('Comissões', 'Receitas provenientes de comissões ou parcerias', TRUE, NULL);

INSERT INTO category (title, description, is_default, customer_id) VALUES
   ('Estoque', 'Compras de mercadorias para revenda', TRUE, NULL),
   ('Aluguel', 'Aluguel do espaço ou escritório', TRUE, NULL),
   ('Contas e serviços', 'Luz, água, internet, telefone e serviços gerais', TRUE, NULL),
   ('Impostos', 'Tributos, taxas e contribuições obrigatórias', TRUE, NULL);
