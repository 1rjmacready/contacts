DROP table Contacts if exists;
CREATE TABLE Contacts (
    id BIGINT IDENTITY NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    phone VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);
insert into Contacts (first_name, last_name, email, phone) values ('RJ', 'MacReady', 'rjmacready@xyz.com', '5551234567');
insert into Contacts (first_name, last_name, email, phone) values ('Ellen', 'Ripley', 'eripley@xyz.com', '5551239874');
insert into Contacts (first_name, last_name, email, phone) values ('Rita', 'Vrataski', 'rvrataski@xyz.com', '5551233216');
insert into Contacts (first_name, last_name, email, phone) values ('Jack', 'Bauer', 'jbauer@xyz.com', '5551239541');
