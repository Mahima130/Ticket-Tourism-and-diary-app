-- ============================
-- INSERT Users
-- ============================

INSERT INTO users (id, name, email, password) VALUES
(1, 'Mahima', 'mahima@example.com', '$2a$10$Ew.BfF.k2fB.0fX7z7tq/u8n.1L6c5.vQ2R/c.f4j2G.j5p3s.C6'), -- password1
(2, 'Deepak', 'deepak@example.com', '$2a$10$Ew.BfF.k2fB.0fX7z7tq/u8n.1L6c5.vQ2R/c.f4j2G.j5p3s.C6'); -- password2
-- Note: Passwords are hashed. If you are not using BCryptPasswordEncoder for login,
-- you might temporarily use plain text for testing in development like:
-- (1, 'Mahima', 'mahima@example.com', 'password1'),
-- (2, 'Deepak', 'deepak@example.com', 'password2');


-- ============================
-- INSERT Holiday Packages
-- ============================
INSERT INTO holiday_package (id, name, duration, hotel_type, facilities, price, image_url) VALUES
(1, 'Manali Escape', '4N/5D', '4-star', 'Cab, Buffet, Snow Games', 14999.00, 'manali.jpg'),
(2, 'Goa Beach Delight', '3N/4D', '4-star', 'Cab, Buffet, Beach Walk', 13999.00, 'goa.jpg'),
(3, 'Kerala Backwaters', '5N/6D', '5-star', 'Houseboat, Cab, Buffet', 16999.00, 'kerala.jpg'),
(4, 'Jaipur Heritage Tour', '2N/3D', '3-star', 'Guide, Cab, Buffet', 9999.00, 'jaipur.jpg'),
(5, 'Shimla Hills Package', '3N/4D', '4-star', 'Snowfall View, Buffet, Cab', 12499.00, 'shimla.jpg');

-- ============================
-- INSERT Train Routes
-- ============================
INSERT INTO train_route (
    train_number, train_name, source, destination, ac_seats, sleeper_seats, general_seats
) VALUES
('12345', 'Shatabdi Express', 'Delhi', 'Chandigarh', 50, 100, 200),
('22401', 'Rajdhani Express', 'Kolkata', 'Delhi', 60, 120, 180),
('12627', 'Karnataka Express', 'Bangalore', 'Chandigarh', 40, 90, 160),
('12402', 'Poorva Express', 'Delhi', 'Howrah', 45, 80, 150),
('12951', 'Mumbai Rajdhani', 'Mumbai', 'Delhi', 55, 100, 190),
('12005', 'Kalka Shatabdi', 'New Delhi', 'Kalka', 60, 90, 160),
('12129', 'Azad Hind Express', 'Pune', 'Howrah', 50, 100, 200),
('12296', 'Sanghamitra Express', 'Bangalore', 'Patna', 48, 95, 180),
('12559', 'Shiv Ganga Express', 'Varanasi', 'New Delhi', 50, 85, 170),
('12615', 'Grand Trunk Express', 'Chennai', 'Delhi', 52, 110, 210);

-- ============================
-- INSERT Bus Routes
-- ============================
INSERT INTO bus_route (bus_number, bus_name, departure_city, arrival_city, total_seats) VALUES
('DL01BUS1234', 'Laxmi Holidays', 'Delhi', 'Manali', 45),
('RJ02BUS5678', 'Neugo Travels', 'Jaipur', 'Udaipur', 40),
('MH03BUS9012', 'Purple Bus', 'Mumbai', 'Pune', 50),
('PB04BUS3456', 'Pepsu Volvo', 'Ludhiana', 'Amritsar', 35),
('UP05BUS7890', 'UPSRTC Express', 'Lucknow', 'Varanasi', 48),
('KA06BUS1122', 'KSRTC Rajahamsa', 'Bangalore', 'Mysore', 52),
('TN07BUS3344', 'TNSTC Deluxe', 'Chennai', 'Madurai', 46),
('GJ08BUS5566', 'Gujarat Travels', 'Ahmedabad', 'Surat', 44),
('HR09BUS7788', 'Haryana Roadways', 'Gurgaon', 'Panipat', 49),
('WB10BUS9900', 'WBTC AC Bus', 'Kolkata', 'Digha', 50);

-- ============================
-- INSERT Flight Routes
-- ============================
INSERT INTO flight_route (flight_number, flight_name, source, destination, economy_seats, business_seats, first_class_seats) VALUES
('AI101', 'Air India Express', 'Delhi', 'Mumbai', 120, 40, 10),
('IN202', 'IndiGo Airlines', 'Mumbai', 'Bangalore', 100, 30, 15),
('SG303', 'SpiceJet', 'Chennai', 'Delhi', 90, 20, 8),
('AK404', 'AirAsia India', 'Kolkata', 'Hyderabad', 110, 25, 12),
('GO505', 'GoAir', 'Ahmedabad', 'Pune', 95, 35, 20),
('UK606', 'Vistara', 'Delhi', 'Goa', 130, 50, 18),
('6E707', 'IndiGo Airlines', 'Hyderabad', 'Chennai', 105, 28, 14),
('AI808', 'Air India', 'Lucknow', 'Delhi', 115, 30, 16),
('SG909', 'SpiceJet', 'Jaipur', 'Mumbai', 100, 25, 11),
('IX010', 'Air India Express', 'Trivandrum', 'Kochi', 85, 15, 5);