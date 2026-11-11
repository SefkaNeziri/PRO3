-- Part 2 sample data. The extra fields provide complete sample registrations for Part 3.
INSERT INTO animal (registration_number, arrival_date, weight, origin) VALUES
                                                                           (101, DATE '2026-11-03', 425.50, 'Farm A'),
                                                                           (102, DATE '2026-11-03', 398.00, 'Farm B'),
                                                                           (103, DATE '2026-11-04', 442.25, 'Farm A')
    ON CONFLICT (registration_number) DO NOTHING;

-- Backfill the Part 2 sample rows if they already existed before the Part 3 schema update.
UPDATE animal SET arrival_date = DATE '2026-11-03', weight = 425.50, origin = 'Farm A'
WHERE registration_number = 101 AND arrival_date IS NULL;
UPDATE animal SET arrival_date = DATE '2026-11-03', weight = 398.00, origin = 'Farm B'
WHERE registration_number = 102 AND arrival_date IS NULL;
UPDATE animal SET arrival_date = DATE '2026-11-04', weight = 442.25, origin = 'Farm A'
WHERE registration_number = 103 AND arrival_date IS NULL;

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