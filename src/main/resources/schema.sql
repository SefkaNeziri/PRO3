CREATE TABLE IF NOT EXISTS animal (
    registration_number INTEGER PRIMARY KEY,
    arrival_date DATE NOT NULL,
    weight NUMERIC(10, 2) NOT NULL CHECK (weight > 0),
    origin VARCHAR(255) NOT NULL
);

-- Upgrade an existing Part 2 database without dropping its animal/product relations.
ALTER TABLE animal ADD COLUMN IF NOT EXISTS arrival_date DATE;
ALTER TABLE animal ADD COLUMN IF NOT EXISTS weight NUMERIC(10, 2);
ALTER TABLE animal ADD COLUMN IF NOT EXISTS origin VARCHAR(255);

CREATE TABLE IF NOT EXISTS product (
    product_id INTEGER PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS product_animal (
    product_id INTEGER NOT NULL,
    registration_number INTEGER NOT NULL,

    PRIMARY KEY (product_id, registration_number),

    FOREIGN KEY (product_id)
      REFERENCES product(product_id),

    FOREIGN KEY (registration_number)
      REFERENCES animal(registration_number)
);
