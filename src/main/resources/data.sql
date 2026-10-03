INSERT INTO animal (registration_number) VALUES
    (101),
    (102),
    (103)
ON CONFLICT (registration_number) DO NOTHING;

INSERT INTO product (product_id) VALUES
    (1),
    (2),
    (3)
ON CONFLICT (product_id) DO NOTHING;

INSERT INTO product_animal (product_id, registration_number) VALUES
    (1, 101),
    (1, 102),
    (2, 101),
    (2, 103),
    (3, 102)
ON CONFLICT (product_id, registration_number) DO NOTHING;