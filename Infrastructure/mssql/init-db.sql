-- Her servis için ayrı DB oluşturulur
IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'auth_db') CREATE DATABASE auth_db;
GO
IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'user_db') CREATE DATABASE user_db;
GO
IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'product_db') CREATE DATABASE product_db;
GO
IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'order_db') CREATE DATABASE order_db;
GO
IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'payment_db') CREATE DATABASE payment_db;
GO