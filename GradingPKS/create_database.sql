CREATE DATABASE grading;

USE grading;

CREATE TABLE suppliers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nama VARCHAR(100) NOT NULL,
    alamat TEXT,
    kontak VARCHAR(50)
);

CREATE TABLE grading_results (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    supplier_id BIGINT,
    tanggal_pengiriman DATETIME,
    berat_total_tbs DOUBLE,
    berat_per_tandan DOUBLE,
    persentase_buah_mentah DOUBLE,
    persentase_buah_lewat_matang DOUBLE,
    persentase_tandan_kosong DOUBLE,
    persentase_gagang_panjang DOUBLE,
    persentase_brondolan DOUBLE,
    persentase_brondolan_kotor DOUBLE,
    denda_buah_mentah DOUBLE,
    denda_buah_lewat_matang DOUBLE,
    denda_tandan_kosong DOUBLE,
    denda_gagang_panjang DOUBLE,
    denda_brondolan DOUBLE,
    denda_brondolan_kotor DOUBLE,
    denda_berat_minimal DOUBLE,
    harga_per_kg DOUBLE,
    total_pembayaran DOUBLE,
    total_denda DOUBLE,
    pembayaran_bersih DOUBLE,
    jumlah_tandan INT,
    berat_buah_mentah DOUBLE,
    berat_buah_kurang_matang DOUBLE,
    berat_buah_matang DOUBLE,
    berat_buah_lewat_matang DOUBLE,
    FOREIGN KEY (supplier_id) REFERENCES suppliers(id)
);

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    nama VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL
);

-- Insert default admin user
INSERT INTO users (username, password, nama, role) 
VALUES ('admin', 'admin123', 'Administrator', 'ADMIN'); 

ALTER TABLE grading_results 
MODIFY berat_total_tbs DOUBLE DEFAULT 0.0,
MODIFY berat_per_tandan DOUBLE DEFAULT 0.0,
MODIFY persentase_buah_mentah DOUBLE DEFAULT 0.0,
MODIFY persentase_buah_lewat_matang DOUBLE DEFAULT 0.0,
MODIFY persentase_tandan_kosong DOUBLE DEFAULT 0.0,
MODIFY persentase_gagang_panjang DOUBLE DEFAULT 0.0,
MODIFY persentase_brondolan DOUBLE DEFAULT 0.0,
MODIFY persentase_brondolan_kotor DOUBLE DEFAULT 0.0,
MODIFY denda_buah_mentah DOUBLE DEFAULT 0.0,
MODIFY denda_buah_lewat_matang DOUBLE DEFAULT 0.0,
MODIFY denda_tandan_kosong DOUBLE DEFAULT 0.0,
MODIFY denda_gagang_panjang DOUBLE DEFAULT 0.0,
MODIFY denda_brondolan DOUBLE DEFAULT 0.0,
MODIFY denda_brondolan_kotor DOUBLE DEFAULT 0.0,
MODIFY denda_berat_minimal DOUBLE DEFAULT 0.0,
MODIFY harga_per_kg DOUBLE DEFAULT 0.0,
MODIFY total_pembayaran DOUBLE DEFAULT 0.0,
MODIFY total_denda DOUBLE DEFAULT 0.0,
MODIFY pembayaran_bersih DOUBLE DEFAULT 0.0,
MODIFY berat_buah_mentah DOUBLE DEFAULT 0.0,
MODIFY berat_buah_kurang_matang DOUBLE DEFAULT 0.0,
MODIFY berat_buah_matang DOUBLE DEFAULT 0.0,
MODIFY berat_buah_lewat_matang DOUBLE DEFAULT 0.0; 