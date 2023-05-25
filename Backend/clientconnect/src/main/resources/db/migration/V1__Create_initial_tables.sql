CREATE TABLE users
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    email             VARCHAR(255) UNIQUE NOT NULL,
    password          VARCHAR(2048)       NOT NULL,
    language_settings VARCHAR(10) DEFAULT 'EN',
    registration_date DATETIME(6),
    last_login_date   DATETIME(6),
    is_business       BOOLEAN     DEFAULT false,
    role              VARCHAR(255)
);

CREATE TABLE user_profile
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    user_id        INT,
    first_name     VARCHAR(255),
    last_name      VARCHAR(255),
    date_of_birth  DATE,
    gender         VARCHAR(10),
    contact_number VARCHAR(15),
    country        VARCHAR(255),
    state          VARCHAR(255),
    city           VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES Users (id)
);

CREATE TABLE service_categories
(
    category_id   INT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(255) NOT NULL
);

CREATE TABLE templates
(
    template_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id     INT,
    category_id INT,
    created_at  TIMESTAMP,
    updated_at  TIMESTAMP,
    is_public   BOOLEAN DEFAULT false,
    FOREIGN KEY (user_id) REFERENCES Users (id),
    FOREIGN KEY (category_id) REFERENCES Service_Categories (category_id)
);



CREATE TABLE requirements
(
    requirement_id   INT AUTO_INCREMENT PRIMARY KEY,
    requirement_name VARCHAR(255),
    category_id      INT,
    FOREIGN KEY (category_id) REFERENCES Service_Categories (category_id)
);

CREATE TABLE businesses
(
    business_id   INT AUTO_INCREMENT PRIMARY KEY,
    user_id       INT,
    business_name VARCHAR(255),
    address       VARCHAR(255),
    category_id   INT,
    created_at    TIMESTAMP,
    updated_at    TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users (id),
    FOREIGN KEY (category_id) REFERENCES Service_Categories (category_id)
);

CREATE TABLE business_supported_requirements
(
    business_id    INT,
    requirement_id INT,
    FOREIGN KEY (business_id) REFERENCES Businesses (business_id),
    FOREIGN KEY (requirement_id) REFERENCES Requirements (requirement_id)
);

CREATE TABLE template_requirements
(
    template_id       INT,
    requirement_id    INT,
    requirement_value VARCHAR(255),
    FOREIGN KEY (template_id) REFERENCES Templates (template_id),
    FOREIGN KEY (requirement_id) REFERENCES Requirements (requirement_id)
);

CREATE TABLE user_template_history
(
    user_id     INT,
    template_id INT,
    used_at     TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users (id),
    FOREIGN KEY (template_id) REFERENCES Templates (template_id)
);

CREATE TABLE reviews
(
    review_id   INT AUTO_INCREMENT PRIMARY KEY,
    user_id     INT,
    template_id INT,
    business_id INT,
    rating      INT,
    review_text TEXT,
    reviewed_at TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users (id),
    FOREIGN KEY (template_id) REFERENCES Templates (template_id),
    FOREIGN KEY (business_id) REFERENCES Businesses (business_id)
);

CREATE TABLE terminals
(
    terminal_id            INT AUTO_INCREMENT PRIMARY KEY,
    business_id            INT,
    is_contactless_enabled BOOLEAN DEFAULT false,
    FOREIGN KEY (business_id) REFERENCES Businesses (business_id)
);

CREATE TABLE cards
(
    card_id    INT AUTO_INCREMENT PRIMARY KEY,
    user_id    INT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users (id)
);

CREATE TABLE business_user_profile
(
    user_id     INT,
    position    VARCHAR(255),
    business_id INT,
    FOREIGN KEY (user_id) REFERENCES Users (id),
    FOREIGN KEY (business_id) REFERENCES Businesses (business_id)
);
