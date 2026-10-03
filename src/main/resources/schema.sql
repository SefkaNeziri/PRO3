CREATE TABLE IF NOT EXISTS animal (
    registration_number INTEGER PRIMARY KEY
);

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
