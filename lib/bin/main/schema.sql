create table userTypes (
    userTypeId serial primary key,
    typeName varchar(255) unique
);

create table users (
    userId serial primary key,
    userType int not null references userTypes(userTypeId),
    userName varchar(255) unique not null,
    userPassword varchar(255) not null,
    fname varchar(255),
    lname varchar(255), 
    manager int
);

create table tasks (
    taskId serial primary key,
    assigner int not null references users(userId),
    reciever int not null references users(userId),
    title text not null,
    body text not null,
    currentStatus integer not null,
    evidenceLocation text
);

INSERT INTO userTypes(typeName) VALUES ('manager'), ('employee');