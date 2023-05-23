-- Adding an id column to the business_supported_requirements table
ALTER TABLE business_supported_requirements
    ADD COLUMN id INT AUTO_INCREMENT PRIMARY KEY FIRST;

-- Adding an id column to the template_requirements table
ALTER TABLE template_requirements
    ADD COLUMN id INT AUTO_INCREMENT PRIMARY KEY FIRST;

-- Adding an id column to the user_template_history table
ALTER TABLE user_template_history
    ADD COLUMN id INT AUTO_INCREMENT PRIMARY KEY FIRST;

-- Adding an id column to the business_user_profile table
ALTER TABLE business_user_profile
    ADD COLUMN id INT AUTO_INCREMENT PRIMARY KEY FIRST;
