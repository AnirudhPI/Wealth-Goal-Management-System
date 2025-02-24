-- User table
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

-- Expense table
CREATE TABLE expense (
    id SERIAL PRIMARY KEY,
    category VARCHAR(255),
    amount NUMERIC(12,2),
    date DATE,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Income table
CREATE TABLE income (
    id SERIAL PRIMARY KEY,
    source VARCHAR(255),
    amount NUMERIC(12,2),
    date DATE,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Investment table
CREATE TABLE investment (
    id SERIAL PRIMARY KEY,
    type VARCHAR(255),
    name VARCHAR(255),
    amount NUMERIC(12,2),
    purchase_date DATE,
    current_value NUMERIC(12,2),
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- SavingsGoal table
CREATE TABLE savings_goal (
    id SERIAL PRIMARY KEY,
    description VARCHAR(255),
    target_amount NUMERIC(12,2),
    current_amount NUMERIC(12,2),
    target_date DATE,
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);