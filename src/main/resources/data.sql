-- Заполнение таблицы customer
INSERT INTO customer (name, phone, password, email, is_courier, is_admin) VALUES
                                                                              ('Иван Иванов', '+79991234567', 'password123', 'ivan@example.com', false, false),
                                                                              ('Мария Петрова', '+79997654321', 'mypassword', 'maria@example.com', true, false),
                                                                              ('Алексей Смирнов', '+79993456789', 'alexeypass', 'alexey@example.com', false, true);

-- Заполнение таблицы ingredient
INSERT INTO ingredient (name, type) VALUES
                                        ('Мука', 'Сухой ингредиент'),
                                        ('Сахар', 'Сухой ингредиент'),
                                        ('Яйца', 'Животный продукт'),
                                        ('Молоко', 'Жидкий ингредиент'),
                                        ('Яблоки', 'Фрукты');

-- Заполнение таблицы bill
INSERT INTO bill (customer_id, total_price) VALUES
                                                   (1, 1500.00),  -- Предполагаем, что ID покупателя 1
                                                   (2, 2000.00),  -- ID покупателя 2
                                                   (1, 3000.00);  -- ID покупателя 1

-- Заполнение таблицы product
INSERT INTO product (name, description, image, price, bill_id) VALUES
                                                                                   ('Пирог', 'Вкусный яблочный пирог', 'image_pie.jpg', 500, 1),  -- ID чека 1 и покупателя 1
                                                                                   ('Торт', 'Шоколадный торт', 'image_cake.jpg', 700, 2),      -- ID чека 2 и покупателя 2
                                                                                   ('Кексы', 'Ванильные кексы', 'image_cupcakes.jpg', 300, 3); -- ID чека 3 и покупателя 1

-- Заполнение связи между product и ingredient
-- Предполагаем, что ID продукта: Пирог = 1, Торт = 2, Кексы = 3
-- ИД ингредиентов: Мука = 1, Сахар = 2, Яйца = 3, Молоко = 4, Яблоки = 5

INSERT INTO product_ingredient (product_id, ingredient_id) VALUES
                                                               (1, 1), -- Пирог - Мука
                                                               (1, 2), -- Пирог - Сахар
                                                               (1, 3), -- Пирог - Яйца
                                                               (2, 2), -- Торт - Сахар
                                                               (2, 3), -- Торт - Яйца
                                                               (3, 1), -- Кексы - Мука
                                                               (3, 4); -- Кексы - Молоко