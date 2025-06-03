SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS service_features;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS service_offerings;
DROP TABLE IF EXISTS users;

SET FOREIGN_KEY_CHECKS = 1;


CREATE TABLE users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'USER') NOT NULL,
    address VARCHAR(300),
    city VARCHAR(100)
);


CREATE TABLE service_offerings (
    service_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    service_name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    category VARCHAR(100),
    cost DECIMAL(10,2),
    location VARCHAR(100),
    contact VARCHAR(100),
    image VARCHAR(500),
    rating DECIMAL(3,2) DEFAULT 0.0,
    review_count INT DEFAULT 0,
    provider_name VARCHAR(100),
    provider_avatar VARCHAR(500),
    phone VARCHAR(15)
);


CREATE TABLE service_features (
    service_id BIGINT NOT NULL,
    feature VARCHAR(255) NOT NULL,
    FOREIGN KEY (service_id) REFERENCES service_offerings(service_id) ON DELETE CASCADE
);

CREATE TABLE orders (
    order_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    service_id BIGINT NOT NULL,
    order_date DATETIME NOT NULL,
    scheduled_date DATETIME,
    status ENUM('PENDING', 'CONFIRMED', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED') NOT NULL DEFAULT 'PENDING',
    is_reviewed BOOLEAN DEFAULT FALSE,
    rating INT,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (service_id) REFERENCES service_offerings(service_id) ON DELETE CASCADE
);

INSERT INTO users (user_id, username, email, password_hash, role, address, city) VALUES
(1, 'admin', 'admin@example.com', '$2a$10$O5pFjq8dPIytI2nG7Rl/1uLtlemR11LJTuF0IZBR6Rar5FXkUaxkC', 'ADMIN', '123 Admin Street, Mumbai, Maharashtra, 400001, India', 'Mumbai'),
(2, 'johndoe', 'user@example.com', '$2a$10$e.l6HVPyEDQBPnqwvwjP2OCtREqLD1gUeA5G5/rM8drpDokLg6L5u', 'USER', '456 User Avenue, Delhi, Delhi, 110001, India', 'Delhi');


INSERT INTO service_offerings (service_id, service_name, description, category, cost, location, contact, image, rating, review_count, provider_name, provider_avatar, phone) VALUES
(1, 'Professional Home Cleaning', 'Expert cleaning services for your home, including deep cleaning, sanitization, and organization.', 'Cleaning', 2500.00, 'Mumbai', 'cleaning@cleanpro.com', 'https://images.unsplash.com/photo-1581578731548-c64695cc6952?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80', 4.8, 156, 'CleanPro Services', 'https://randomuser.me/api/portraits/lego/5.jpg', '+91 98765 43210'),

(2, 'Expert Plumbing Solutions', '24/7 emergency plumbing services, pipe repairs, and installation of new fixtures.', 'Plumbing', 1800.00, 'Delhi', 'plumbing@delhi-experts.com', 'https://images.unsplash.com/photo-1607472586893-edb57bdc0e39?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80', 4.6, 89, 'Delhi Plumbing Experts', 'https://randomuser.me/api/portraits/lego/5.jpg', '+91 98765 43211'),

(3, 'Electrical Safety & Repairs', 'Complete electrical services including repairs, installations, and safety inspections.', 'Electrical', 1500.00, 'Bangalore', 'electrical@bangalore-solutions.com', 'https://images.unsplash.com/photo-1621905251189-08b45d6a269e?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80', 4.9, 234, 'Bangalore Electric Solutions', 'https://randomuser.me/api/portraits/lego/5.jpg', '+91 98765 43212'),

(4, 'Professional Painting Services', 'Interior and exterior painting services with premium quality paints and expert craftsmanship.', 'Painting', 12000.00, 'Hyderabad', 'painting@hyderabad-masters.com', 'https://images.unsplash.com/photo-1589939705384-5185137a7f0f?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80', 4.7, 178, 'Hyderabad Paint Masters', 'https://randomuser.me/api/portraits/lego/5.jpg', '+91 98765 43213'),

(5, 'Custom Carpentry Work', 'Custom furniture making, repairs, and woodwork installations by skilled craftsmen.', 'Carpentry', 3500.00, 'Chennai', 'carpentry@chennai-woodworks.com', 'https://images.unsplash.com/photo-1416879595882-3373a0480b5b?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80', 4.8, 92, 'Chennai Woodworks', 'https://randomuser.me/api/portraits/lego/5.jpg', '+91 98765 43214'),

(6, 'Garden Maintenance & Design', 'Professional gardening services including maintenance, landscaping, and plant care.', 'Gardening', 2000.00, 'Kolkata', 'garden.care@kolkata.com', 'https://images.unsplash.com/photo-1558904541-efa843a96f01?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80', 4.5, 67, 'Kolkata Garden Care', 'https://randomuser.me/api/portraits/lego/5.jpg', '+91 98765 43215'),

(7, 'Professional Moving Services', 'Safe and efficient moving services for homes and offices with complete packing and unpacking.', 'Moving', 8000.00, 'Pune', 'movers@pune.com', 'https://onroadpackersandmovers.com/wp-content/uploads/2024/11/expert-moving-services-1024x585.jpg?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80', 4.7, 145, 'Pune Movers', 'https://randomuser.me/api/portraits/lego/5.jpg', '+91 98765 43216'),

(8, 'Home Renovation Experts', 'Complete home renovation and remodeling services with expert craftsmanship.', 'Other', 25000.00, 'Ahmedabad', 'renovation@ahmedabad-experts.com', 'https://images.unsplash.com/photo-1504307651254-35680f356dfd?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80', 4.9, 89, 'Ahmedabad Renovations', 'https://randomuser.me/api/portraits/lego/5.jpg', '+91 98765 43217'),

(9, 'Premium Home Services', 'This is a comprehensive home service that offers a wide range of services including but not limited to deep cleaning, maintenance, repairs, renovations, landscaping, interior design consulting, and much more to test the text truncation feature and ensure it works properly on all devices and screen sizes.', 'Other', 15000.00, 'Mumbai', 'premium@home-services.com', 'https://images.unsplash.com/photo-1560448204-e02f11c3d0e2?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80', 4.8, 123, 'Premium Home Services Provider with a Very Long Name', 'https://randomuser.me/api/portraits/lego/5.jpg', '+91 98765 43218');


INSERT INTO service_features (service_id, feature) VALUES
(1, 'Deep Cleaning'),
(1, 'Sanitization'),
(1, 'Window Cleaning'),
(1, 'Bathroom Cleaning'),
(1, 'Kitchen Cleaning'),

(2, 'Emergency Repairs'),
(2, 'Pipe Installation'),
(2, 'Leak Detection'),
(2, 'Water Heater Service'),
(2, 'Drain Cleaning'),

(3, 'Electrical Repairs'),
(3, 'Safety Inspections'),
(3, 'New Installations'),
(3, 'Emergency Service'),
(3, 'Wiring Upgrades'),

(4, 'Interior Painting'),
(4, 'Exterior Painting'),
(4, 'Wall Texturing'),
(4, 'Color Consultation'),
(4, 'Premium Paints'),

(5, 'Custom Furniture'),
(5, 'Wood Repairs'),
(5, 'Installations'),
(5, 'Cabinet Making'),
(5, 'Wood Finishing'),

(6, 'Garden Maintenance'),
(6, 'Landscaping'),
(6, 'Plant Care'),
(6, 'Lawn Care'),
(6, 'Garden Design'),

(7, 'Home Moving'),
(7, 'Office Relocation'),
(7, 'Packing Service'),
(7, 'Furniture Moving'),
(7, 'Storage Solutions'),

(8, 'Home Renovation'),
(8, 'Interior Design'),
(8, 'Space Planning'),
(8, 'Material Selection'),
(8, 'Project Management'),

(9, 'Comprehensive Services'),
(9, 'Professional Team'),
(9, 'Quality Assurance'),
(9, 'Flexible Scheduling'),
(9, 'Customer Support');


INSERT INTO orders (order_id, user_id, service_id, order_date, scheduled_date, status, is_reviewed, rating) VALUES
(1, 2, 1, '2024-01-15 10:30:00', '2024-01-20 09:00:00', 'COMPLETED', TRUE, 5),
(2, 2, 3, '2024-01-10 14:15:00', '2024-01-25 11:00:00', 'IN_PROGRESS', FALSE, NULL),
(3, 1, 2, '2024-01-05 16:45:00', '2024-01-30 08:30:00', 'PENDING', FALSE, NULL),
(4, 2, 9, '2024-01-15 00:00:00', '2024-01-20 00:00:00', 'COMPLETED', TRUE, 4);


SELECT 'Database initialization completed successfully!' AS Status;


SELECT 
    'users' AS table_name, 
    COUNT(*) AS record_count 
FROM users
UNION ALL
SELECT 
    'service_offerings' AS table_name, 
    COUNT(*) AS record_count 
FROM service_offerings
UNION ALL
SELECT 
    'service_features' AS table_name, 
    COUNT(*) AS record_count 
FROM service_features
UNION ALL
SELECT 
    'orders' AS table_name, 
    COUNT(*) AS record_count 
FROM orders; 