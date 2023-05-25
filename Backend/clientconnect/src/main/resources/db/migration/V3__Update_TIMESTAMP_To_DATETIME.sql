ALTER TABLE templates
    MODIFY COLUMN created_at DATETIME(6),
    MODIFY COLUMN updated_at DATETIME(6);

ALTER TABLE businesses
    MODIFY COLUMN created_at DATETIME(6),
    MODIFY COLUMN updated_at DATETIME(6);

ALTER TABLE user_template_history
    MODIFY COLUMN used_at DATETIME(6);

ALTER TABLE reviews
    MODIFY COLUMN reviewed_at DATETIME(6);

ALTER TABLE cards
    MODIFY COLUMN created_at DATETIME(6),
    MODIFY COLUMN updated_at DATETIME(6);
