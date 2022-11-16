connect ora_bsaoudio;

CREATE TABLE User_Account (
    Username CHAR (20),
    Password_hash CHAR (20),
    PRIMARY KEY (Username)
);
GRANT SELECT ON User_Account TO PUBLIC;

CREATE TABLE Date_Stamp (
    time_stamp TIMESTAMP,
    PRIMARY KEY (time_stamp)
);
GRANT SELECT ON Date_Stamp TO PUBLIC;


CREATE TABLE FIPPA_Details (
   Country CHAR (60),
   FIPPA_Compliance INTEGER,
   PRIMARY KEY (country)
);
GRANT SELECT ON FIPPA_Details TO PUBLIC;


CREATE TABLE Organization (
    Organization_name CHAR (20),
    Country CHAR(60),
    PRIMARY KEY (Organization_name),
    FOREIGN KEY (Country)
        REFERENCES FIPPA_Details
);
GRANT SELECT ON Organization TO PUBLIC;

CREATE TABLE Repository (
    Repository_name CHAR (40),
    Organization_name CHAR (20),
    Date_created TIMESTAMP,
    PRIMARY KEY (Repository_name),
    FOREIGN KEY (Organization_name)
        REFERENCES Organization,
    FOREIGN KEY (Date_created)
        REFERENCES Date_Stamp(time_stamp)
);
GRANT SELECT ON Repository TO PUBLIC;

CREATE TABLE Project_board (
   Board_title CHAR(20),
   Repository_name CHAR(40) NOT NULL,
   PRIMARY KEY (Board_title),
   FOREIGN KEY (Repository_name)
       REFERENCES Repository
);
GRANT SELECT ON Project_board TO PUBLIC;

CREATE TABLE Issue (
    Issue_ID INTEGER,
    Issue_message CHAR(200),
    Owning_board CHAR(20) NOT NULL,
    PRIMARY KEY (Issue_ID),
    FOREIGN KEY (Owning_board)
        REFERENCES Project_board(Board_title)
);
GRANT SELECT ON Issue TO PUBLIC;

CREATE TABLE Repository_Object (
    Repository CHAR(40),
    file_Path CHAR(40),
    file_extension CHAR(5),
    file_contents CHAR(2000),
    File_size INTEGER,
    Parent_Directory_Path CHAR(40) DEFAULT '/',
    Parent_Directory_Repository CHAR (40),
    PRIMARY KEY (Repository, file_Path),
    FOREIGN KEY (Repository)
        REFERENCES Repository,
    FOREIGN KEY (Parent_Directory_Repository, Parent_Directory_Path)
        REFERENCES Repository_Object(Repository, file_path)
);
GRANT SELECT ON Repository_Object TO PUBLIC;


CREATE TABLE Is_Member_of(
     Username CHAR(20),
     Organization_name CHAR(20) NOT NULL,
     PRIMARY KEY (Username, Organization_name),
     FOREIGN KEY (Username)
        REFERENCES User_Account(Username),
     FOREIGN KEY (Organization_name)
        REFERENCES Organization
);
GRANT SELECT ON Is_Member_of TO PUBLIC;


CREATE TABLE Contributor_of (
    Username CHAR(20),
    Repository_name CHAR(40),
    PRIMARY KEY (Username, Repository_name),
    FOREIGN KEY (Username)
            REFERENCES User_Account(Username),
    FOREIGN KEY (Repository_name)
            REFERENCES Repository
);
GRANT SELECT ON Contributor_of TO PUBLIC;


CREATE TABLE Assigned_to (
     Username CHAR(20),
     Issue_ID INTEGER,
     PRIMARY KEY (Username, Issue_ID),
     FOREIGN KEY (Username)
         REFERENCES User_Account(Username),
     FOREIGN KEY (Issue_ID)
         REFERENCES Issue
);
GRANT SELECT ON Assigned_to TO PUBLIC;


CREATE TABLE Commit (
    Commit_SHA CHAR (64),
    Commit_number INTEGER,
    Repository_name CHAR (20) NOT NULL,
    File_changed CHAR (20) NOT NULL,
    Author CHAR (20) NOT NULL,
    Date_created TIMESTAMP NOT NULL,
    PRIMARY KEY (Commit_SHA),
    FOREIGN KEY (Date_created)
        REFERENCES Date_Stamp(time_stamp),
    FOREIGN KEY (Repository_name)
        REFERENCES Repository,
    FOREIGN KEY (Repository_name, File_changed)
        REFERENCES Repository_Object(Repository, file_path),
    FOREIGN KEY (Author)
        REFERENCES User_Account(Username)
);
GRANT SELECT ON Commit TO PUBLIC;

CREATE TABLE Line_change (
     Commit_SHA CHAR (64),
     Line_number INTEGER,
     PRIMARY KEY (Commit_SHA, Line_number),
     FOREIGN KEY (Commit_SHA)
         REFERENCES Commit(Commit_SHA)
);
GRANT SELECT ON Line_change TO PUBLIC;

-- Insert values into User_Account
INSERT INTO ora_bsaoudio.User_Account(Username, Password_hash)
    VALUES ('eshaq', 'pass123');
INSERT INTO User_Account(Username, Password_hash)
    VALUES ('oliver', 'pass23');

SELECT t.*
FROM USER_ACCOUNT t

INSERT INTO User_Account(Username, Password_hash)
    VALUES ('oussama', 'password');
INSERT INTO User_Account(Username, Password_hash)
    VALUES ('jack', 'amazing');
INSERT INTO User_Account(Username, Password_hash)
    VALUES ('jill', 'wow');

-- Insert values into FIPPA_Details
INSERT INTO FIPPA_Details(Country, FIPPA_Compliance)
    VALUES ('Canada', 1);
INSERT INTO FIPPA_Details(Country, FIPPA_Compliance)
    VALUES ('USA', 0);
INSERT INTO FIPPA_Details(Country, FIPPA_Compliance)
    VALUES ('Singapore', 0);
INSERT INTO FIPPA_Details(Country, FIPPA_Compliance)
    VALUES ('UK', 0);
INSERT INTO FIPPA_Details(Country, FIPPA_Compliance)
    VALUES ('Russia', 0);

-- Insert values into Organization
INSERT INTO Organization(Organization_name, Country)
    VALUES ('UBC', 'Canada');
INSERT INTO Organization(Organization_name, Country)
    VALUES ('SFU', 'Canada');
INSERT INTO Organization(Organization_name, Country)
    VALUES ('Harvard', 'USA');
INSERT INTO Organization(Organization_name, Country)
    VALUES ('Oxford', 'UK');
INSERT INTO Organization(Organization_name, Country)
    VALUES ('NUS', 'Singapore');

-- Insert into Date_Stampe
INSERT INTO Date_Stamp(time_stamp)
    VALUES (TO_TIMESTAMP('2022-04-12 11:32:06', 'YYYY-MM-DD HH24:MI:SS'))

-- Insert into Repository
INSERT INTO Repository(Repository_name, Organization_name, Date_created)
    VALUES ('CPSC304', 'UBC', TO_TIMESTAMP('2022-04-12 11:32:06', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO Repository(Repository_name, Organization_name, Date_created)
    VALUES ('CPSC310', 'UBC', TO_TIMESTAMP('2022-04-12 11:32:06', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO Repository(Repository_name, Organization_name, Date_created)
    VALUES ('CMPT120', 'SFU', TO_TIMESTAMP('2022-04-12 11:32:06', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO Repository(Repository_name, Organization_name, Date_created)
    VALUES ('G400', 'Oxford', TO_TIMESTAMP('2022-04-12 11:32:06', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO Repository(Repository_name, Organization_name, Date_created)
    VALUES ('CS50', 'Harvard', TO_TIMESTAMP('2022-04-12 11:32:06', 'YYYY-MM-DD HH24:MI:SS'));

-- Insert into RepositoryObject
INSERT INTO Repository_Object(Repository, file_Path, File_size, Parent_Directory_Path, Parent_Directory_Repository)
    VALUES ('CPSC304','/', 32, '/', 'CPSC304');
INSERT INTO Repository_Object(Repository, file_Path, file_extension, file_contents, File_size, Parent_Directory_Path, Parent_Directory_Repository)
    VALUES ('CPSC304','/README.md', '.md', '# Hello world!', 32, '/', 'CPSC304');

INSERT INTO Repository_Object(Repository, file_Path, File_size, Parent_Directory_Path, Parent_Directory_Repository)
VALUES ('CPSC310','/', 32, '/', 'CPSC310');
INSERT INTO Repository_Object(Repository, file_Path, file_extension, file_contents, File_size, Parent_Directory_Path, Parent_Directory_Repository)
VALUES ('CPSC310','/README.md', '.md', '# Hello world!', 32, '/', 'CPSC310')

INSERT INTO Repository_Object(Repository, file_Path, File_size, Parent_Directory_Path, Parent_Directory_Repository)
    VALUES ('CS50','/', 32, '/', 'CS50');
INSERT INTO Repository_Object(Repository, file_Path, file_extension, file_contents, File_size, Parent_Directory_Path, Parent_Directory_Repository)
    VALUES ('CS50','/README.md', '.md', '# Hello world!', 32, '/', 'CS50');

-- Insert into Commit
-- INSERT INTO Commit(Commit_SHA, Commit_number, Repository_name, File_changed, Author, Date_created)
--     VALUES ('331dbc09c8afa4c077f448e4c1ef1e42297879d3fcfdbf2e7e6494fa6d831cec',
--             1,
--             'CPSC304',
--             '/README.md',
--             'eshaq',
--             TO_TIMESTAMP('2022-04-12 11:32:06', 'YYYY-MM-DD HH24:MI:SS')
-- );

