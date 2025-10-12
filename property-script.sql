create database rap_db;
use rap_db;

-- -- amenities table (static master data, e.g. WiFi, Parking, etc.)
CREATE TABLE amenities(amenity_id BIGINT PRIMARY KEY AUTO_INCREMENT,
						amenity_name VARCHAR(100) NOT NULL
                        );
-- listings table (main entity for rental properties)
CREATE TABLE listings(listing_id BIGINT PRIMARY KEY AUTO_INCREMENT,
					  address_id BIGINT NOT NULL,
					  listing_name VARCHAR(100) NOT NULL,
                      listing_description TEXT,
                      price DECIMAL(10,2) NOT NULL,
                      owner_name VARCHAR(50) NOT NULL,
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                      FOREIGN KEY (listing_id) REFERENCES listings(listing_id)
                      );
                      
-- images table (many-to-one relationship with listings)
CREATE TABLE listing_images(image_id BIGINT PRIMARY KEY AUTO_INCREMENT,
							listing_id BIGINT NOT NULL,
                            image BLOB NOT NULL,
                            FOREIGN KEY (listing_id) REFERENCES listings(listing_id)
                            );


-- address table (one-to-one relationship with listings)
CREATE TABLE listing_address(address_id BIGINT PRIMARY KEY AUTO_INCREMENT,
							 listing_id BIGINT NOT NULL,
                             street VARCHAR(50),
                             city VARCHAR(50),
                             state VARCHAR(50),
                             country VARCHAR(50),
                             postal_code VARCHAR(50),
                             FOREIGN KEY (listing_id) REFERENCES listings(listing_id)
                             );
                             
-- join table (many-to-many relationship between listings and amenities)
CREATE TABLE lisitngs_amenities(listing_id BIGINT NOT NULL,
								amenity_id BIGINT NOT NULL,
                                PRIMARY KEY (listing_id, amenity_id),
                                FOREIGN KEY (listing_id) references listings(listing_id),
                                FOREIGN KEY (amenity_id) references amenities(amenity_id)
                                );
                                
                                

                        
 -- updated listing_images script
 ALTER TABLE listing_images DROP COLUMN image;

 ALTER TABLE listing_images ADD COLUMN is_primary BOOLEAN NOT NULL;
 ALTER TABLE listing_images ADD COLUMN s3_key VARCHAR(255) NOT NULL;
 ALTER TABLE listing_images ADD COLUMN file_name VARCHAR(255);
 ALTER TABLE listing_images ADD COLUMN content_type VARCHAR(50);
 ALTER TABLE listing_images ADD COLUMN size_bytes BIGINT;
 ALTER TABLE listing_images ADD COLUMN created_at TIMESTAMP DEFAULT current_timestamp;