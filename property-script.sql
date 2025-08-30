create database rap_db;
use rap_db;

-- amenties table static
CREATE TABLE amenities(amenity_id BIGINT PRIMARY KEY AUTO_INCREMENT,
						amenity_name VARCHAR(100) NOT NULL
                        );
-- properties table
CREATE TABLE listings(listing_id BIGINT PRIMARY KEY AUTO_INCREMENT,
					  listing_name VARCHAR(100) NOT NULL,
                      listing_description TEXT,
                      price DECIMAL(10,2) NOT NULL,
                      owner_name VARCHAR(50) NOT NULL,
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
                      );
                      
-- property images table many to one with listings table
CREATE TABLE listing_images(image_id BIGINT PRIMARY KEY AUTO_INCREMENT,
							listing_id BIGINT NOT NULL,
                            image BLOB NOT NULL,
                            FOREIGN KEY (listing_id) REFERENCES listings(listing_id)
                            );


-- address table one to one with listings(properties) table
CREATE TABLE listing_address(address_id BIGINT PRIMARY KEY AUTO_INCREMENT,
							 listing_id BIGINT NOT NULL,
                             street VARCHAR(50),
                             city VARCHAR(50),
                             state VARCHAR(50),
                             country VARCHAR(50),
                             postcal_code VARCHAR(50),
                             FOREIGN KEY (listing_id) REFERENCES listings(listing_id)
                             );
                             
-- listings to amenities many to many : lisitngs_amenities
CREATE TABLE lisitngs_amenities(listing_id BIGINT NOT NULL,
								amenity_id BIGINT NOT NULL,
                                PRIMARY KEY (listing_id, amenity_id),
                                FOREIGN KEY (listing_id) references listings(listing_id),
                                FOREIGN KEY (amenity_id) references amenities(amenity_id)
                                );
                                

                        
