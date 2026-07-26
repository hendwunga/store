-- ============================================
-- DATA DUMMY UNTUK STAGING SERVER (MySQL)
-- Database Aiven khusus testing
-- Menggunakan INSERT IGNORE agar aman dijalankan berulang kali
-- ============================================

-- Kategori Dummy
INSERT IGNORE INTO categories (name) VALUES ('Elektronik');
INSERT IGNORE INTO categories (name) VALUES ('Fashion');
INSERT IGNORE INTO categories (name) VALUES ('Aksesoris');
INSERT IGNORE INTO categories (name) VALUES ('Komputer');
INSERT IGNORE INTO categories (name) VALUES ('Kamera');
INSERT IGNORE INTO categories (name) VALUES ('Lainnya');

-- Produk Dummy (category_id berdasarkan auto-increment id di atas)
-- Menggunakan INSERT IGNORE agar tidak error jika sudah ada
INSERT IGNORE INTO products (name, brand, price, description, category_id, created_at, image_file_name)
SELECT 'Laptop Gaming Tes', 'Asus ROG', 15000000, 'Ini adalah data dummy untuk testing API. Laptop gaming palsu untuk keperluan staging.', 1, NOW(), 'https://example.com/laptop.jpg'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Laptop Gaming Tes');

INSERT IGNORE INTO products (name, brand, price, description, category_id, created_at, image_file_name)
SELECT 'iPhone 15 Pro Max', 'Apple', 22000000, 'Produk dummy iPhone 15. Harga dan spesifikasi hanya untuk keperluan testing.', 1, NOW(), 'https://example.com/iphone.jpg'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'iPhone 15 Pro Max');

INSERT IGNORE INTO products (name, brand, price, description, category_id, created_at, image_file_name)
SELECT 'Kaos Polos Testing', 'Uniqlo', 100000, 'Kaos polos abu-abu. Ini data bohongan untuk QA mengetes fitur CRUD produk.', 2, NOW(), 'https://example.com/kaos.jpg'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Kaos Polos Testing');

INSERT IGNORE INTO products (name, brand, price, description, category_id, created_at, image_file_name)
SELECT 'Headset Gaming KW', 'Logitech', 250000, 'Headset gaming fake untuk testing. Jangan beli, ini cuma data staging!', 3, NOW(), 'https://example.com/headset.jpg'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Headset Gaming KW');

INSERT IGNORE INTO products (name, brand, price, description, category_id, created_at, image_file_name)
SELECT 'PC Rakitan Murah', 'Custom', 5000000, 'PC rakitan murah meriah. Data dummy ini bisa dihapus atau diedit sepuasnya.', 4, NOW(), 'https://example.com/pc.jpg'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'PC Rakitan Murah');

INSERT IGNORE INTO products (name, brand, price, description, category_id, created_at, image_file_name)
SELECT 'Kamera DSLR Palsu', 'Canon', 8000000, 'Kamera DSLR tiruan untuk testing upload gambar dan fitur lainnya di staging.', 5, NOW(), 'https://example.com/kamera.jpg'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Kamera DSLR Palsu');

INSERT IGNORE INTO products (name, brand, price, description, category_id, created_at, image_file_name)
SELECT 'Mouse Wireless Tes', 'Razer', 350000, 'Mouse wireless testing. Silakan coba hapus, update, atau buat produk baru.', 3, NOW(), 'https://example.com/mouse.jpg'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Mouse Wireless Tes');

INSERT IGNORE INTO products (name, brand, price, description, category_id, created_at, image_file_name)
SELECT 'Keyboard Mechanical', 'Keychron', 750000, 'Keyboard mechanical QMK. Data ini bisa di-regenerate kapan saja.', 3, NOW(), 'https://example.com/keyboard.jpg'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Keyboard Mechanical');

INSERT IGNORE INTO products (name, brand, price, description, category_id, created_at, image_file_name)
SELECT 'Tablet Murah Staging', 'Samsung', 3000000, 'Tablet Samsung murah. Seluruh data di staging ini tidak mempengaruhi production.', 1, NOW(), 'https://example.com/tablet.jpg'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Tablet Murah Staging');

INSERT IGNORE INTO products (name, brand, price, description, category_id, created_at, image_file_name)
SELECT 'Jam Smartwatch Test', 'Xiaomi', 500000, 'Smartwatch Xiaomi testing. Aman untuk dihapus dan dibuat ulang berulang kali.', 3, NOW(), 'https://example.com/jam.jpg'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Jam Smartwatch Test');
