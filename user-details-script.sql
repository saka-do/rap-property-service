use rap_db;

CREATE TABLE user_details(user_id BIGINT PRIMARY KEY AUTO_INCREMENT,full_name VARCHAR(50) NOT NULL, email_id VARCHAR(50) NOT NULL,
						  user_password VARCHAR(200) NOT NULL);
                          
                          
CREATE TABLE roles(role_id BIGINT PRIMARY KEY AUTO_INCREMENT, role_cd VARCHAR(3) NOT NULL, role_name VARCHAR(10) NOT NULL);


-- join table (many-to-many relationship between users and roles)

CREATE TABLE user_roles(user_id BIGINT NOT NULL, role_id BIGINT NOT NULL, 
						PRIMARY KEY(user_id,role_id),
                        FOREIGN KEY(user_id) REFERENCES user_details(user_id),
                        FOREIGN KEY(role_id) REFERENCES roles(role_id));
