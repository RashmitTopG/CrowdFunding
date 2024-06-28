use student;

desc images;

alter table images add column name varchar(20),
add column description varchar (1000) ;

select * from images;

-- UPDATE images
-- SET name = "Palkhi ", description = "Palkhi Bro Lol"
-- WHERE id = 3;
-- delete from images where project_id = 4;
-- delete from donation where project_id =4;

truncate images;



-- INSERT INTO images (id, pic , name, description) VALUES (4, LOAD_FILE('C:/Users/Rashmit Mhatre/OneDrive/Desktop/WebD/MRunali/Cry Cutiee 2.jpg'), 'Random Shit', 'This is just for trial purpose');

alter table images 
add column start_date date , add column end_date date ;

alter table images 
add column raised_amount int , add column target int ;

alter table images modify raised_amount int not null;

-- UPDATE images
-- SET raised_amount = 0
-- WHERE id IN (1, 3, 4);

-- UPDATE images
-- SET target = 1000
-- WHERE id IN (1, 3, 4);


-- Step 2: Add the NOT NULL constraint
ALTER TABLE images
MODIFY raised_amount int NOT NULL default 0;

UPDATE images SET start_date = current_date(), end_date = current_date() WHERE project_id = 1;-- 

ALTER TABLE images
CHANGE COLUMN id project_id INT;

ALTER TABLE images
MODIFY COLUMN project_id INT AUTO_INCREMENT ;


ALTER TABLE images
MODIFY name varchar(255);


desc donation;

ALTER TABLE donation
DROP FOREIGN KEY donation_ibfk_2;

ALTER TABLE donation
DROP FOREIGN KEY fk_image;

ALTER TABLE donation
ADD CONSTRAINT fk_image
FOREIGN KEY (project_id) REFERENCES images (project_id);

delete from images where project_id = 10;

ALTER TABLE images MODIFY COLUMN pic LONGBLOB;


