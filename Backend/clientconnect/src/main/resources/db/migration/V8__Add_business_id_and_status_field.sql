ALTER TABLE user_template_history
    ADD COLUMN business_id INT,
    ADD FOREIGN KEY (business_id) REFERENCES Businesses(business_id);

ALTER TABLE user_template_history
    ADD COLUMN status VARCHAR(255);
