connect ora_bsaoudio;

CREATE TABLE User_Account (
    username VARCHAR2(20),
    password_hash CHAR (20),
    PRIMARY KEY (username)
);
GRANT SELECT ON User_Account TO PUBLIC;

CREATE TABLE Date_Stamp (
    time_stamp TIMESTAMP,
    PRIMARY KEY (time_stamp)
);
GRANT SELECT ON Date_Stamp TO PUBLIC;


CREATE TABLE FIPPA_Details (
   country VARCHAR2(60),
   fippa_compliance INTEGER,
   PRIMARY KEY (country)
);
GRANT SELECT ON FIPPA_Details TO PUBLIC;


CREATE TABLE Organization (
    organization_name VARCHAR2(20),
    country VARCHAR2(60),
    PRIMARY KEY (organization_name),
    FOREIGN KEY (country)
        REFERENCES FIPPA_Details
);
GRANT SELECT ON Organization TO PUBLIC;

CREATE TABLE Repository (
    repository_name VARCHAR2(40),
    organization_name VARCHAR2(20),
    date_created TIMESTAMP,
    PRIMARY KEY (repository_name),
    FOREIGN KEY (organization_name)
        REFERENCES organization,
    FOREIGN KEY (date_created)
        REFERENCES Date_Stamp(time_stamp)
);
GRANT SELECT ON Repository TO PUBLIC;

CREATE TABLE Project_board (
   board_title VARCHAR2(20),
   repository_name VARCHAR2(40) NOT NULL,
   PRIMARY KEY (board_title),
   FOREIGN KEY (repository_name)
       REFERENCES Repository
);
GRANT SELECT ON Project_board TO PUBLIC;

CREATE TABLE Issue (
    issue_id INTEGER,
    issue_message VARCHAR2(200),
    owning_board VARCHAR2(20) NOT NULL,
    PRIMARY KEY (issue_ID),
    FOREIGN KEY (owning_board)
        REFERENCES Project_board(board_title)
);
GRANT SELECT ON Issue TO PUBLIC;

CREATE TABLE Repository_Object (
    repository VARCHAR2(40),
    file_Path VARCHAR2(40),
    file_extension VARCHAR2(5),
    file_contents VARCHAR2(2000),
    File_size INTEGER,
    parent_directory_path VARCHAR2(40) DEFAULT '/',
    parent_directory_repository VARCHAR2 (40),
    PRIMARY KEY (repository, file_Path),
    FOREIGN KEY (repository)
        REFERENCES Repository,
    FOREIGN KEY (parent_directory_repository, parent_directory_path)
        REFERENCES Repository_Object(repository, file_path)
);
GRANT SELECT ON Repository_Object TO PUBLIC;


CREATE TABLE Is_Member_of(
     username VARCHAR2(20),
     organization_name VARCHAR2(20) NOT NULL,
     PRIMARY KEY (username, organization_name),
     FOREIGN KEY (username)
        REFERENCES User_Account(username),
     FOREIGN KEY (organization_name)
        REFERENCES Organization
);
GRANT SELECT ON Is_Member_of TO PUBLIC;


CREATE TABLE Contributor_of (
    username VARCHAR2(20),
    repository_name VARCHAR2(40),
    PRIMARY KEY (username, repository_name),
    FOREIGN KEY (username)
            REFERENCES User_Account(username),
    FOREIGN KEY (repository_name)
            REFERENCES Repository
);
GRANT SELECT ON Contributor_of TO PUBLIC;


CREATE TABLE Assigned_to (
     username VARCHAR2(20),
     issue_id INTEGER,
     PRIMARY KEY (username, issue_id),
     FOREIGN KEY (username)
         REFERENCES User_Account(username),
     FOREIGN KEY (issue_id)
         REFERENCES Issue
);
GRANT SELECT ON Assigned_to TO PUBLIC;


CREATE TABLE Commit (
    commit_sha VARCHAR2(64),
    commit_number INTEGER,
    repository_name VARCHAR2(20) NOT NULL,
    file_changed VARCHAR2(20) NOT NULL,
    author VARCHAR2(20) NOT NULL,
    date_created TIMESTAMP NOT NULL,
    PRIMARY KEY (commit_sha),
    FOREIGN KEY (date_created)
        REFERENCES Date_Stamp(time_stamp),
    FOREIGN KEY (repository_name)
        REFERENCES Repository,
    FOREIGN KEY (repository_name, file_changed)
        REFERENCES Repository_Object(repository, file_path),
    FOREIGN KEY (author)
        REFERENCES User_Account(Username)
);
GRANT SELECT ON Commit TO PUBLIC;

CREATE TABLE Line_change (
     commit_sha VARCHAR2(64),
     line_number INTEGER,
     PRIMARY KEY (commit_sha, line_number),
     FOREIGN KEY (commit_sha)
         REFERENCES Commit(commit_sha)
);
GRANT SELECT ON Line_change TO PUBLIC;

-- Insert values into User_Account
INSERT INTO User_Account(Username, Password_hash)
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
    VALUES (TO_TIMESTAMP('2022-04-12 11:32:06', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO Date_Stamp(time_stamp)
    VALUES (TO_TIMESTAMP('2022-04-12 11:36:06', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO Date_Stamp(time_stamp)
    VALUES (TO_TIMESTAMP('2022-04-12 11:37:06', 'YYYY-MM-DD HH24:MI:SS'));

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
INSERT INTO Commit(Commit_SHA, Commit_number, Repository_name, File_changed, Author, Date_created)
    VALUES ('331dbc09c8afa4c077f448e4c1ef1e42297879d3fcfdbf2e7e6494fa6d831cec',
            1,
            'CPSC304',
            '/README.md',
            'eshaq',
            TO_TIMESTAMP('2022-04-12 11:32:06', 'YYYY-MM-DD HH24:MI:SS')
);
INSERT INTO Commit(Commit_SHA, Commit_number, Repository_name, File_changed, Author, Date_created)
VALUES ('331dbc09c8afa4c07fdadfasfadsf297879d3fcfdbf2e7e6494fa6d831cec',
        1,
        'CPSC310',
        '/README.md',
        'oussama',
        TO_TIMESTAMP('2022-04-12 11:36:06', 'YYYY-MM-DD HH24:MI:SS')
       );
INSERT INTO Commit(Commit_SHA, Commit_number, Repository_name, File_changed, Author, Date_created)
VALUES ('331dbc09c8afaasdfaadsf297879d3fcfdbf2e7e6494fa6d831cec',
        2,
        'CPSC310',
        '/README.md',
        'oussama',
        TO_TIMESTAMP('2022-04-12 11:37:06', 'YYYY-MM-DD HH24:MI:SS')
       );
INSERT INTO Commit(Commit_SHA, Commit_number, Repository_name, File_changed, Author, Date_created)
VALUES ('asfdadsfasdfadsfasdfadsfads',
        2,
        'CPSC304',
        '/README.md',
        'oussama',
        TO_TIMESTAMP('2022-04-12 11:37:06', 'YYYY-MM-DD HH24:MI:SS')
       );

INSERT INTO IS_MEMBER_OF(Username, organization_name)
    VALUES ('oussama', 'UBC');
COMMIT;

