
CREATE TABLE User_Account (
    Username CHAR (20),
    Password_hash CHAR (20),
    PRIMARY KEY (Username)
);

CREATE TABLE Date_Stamp (
    time_stamp TIMESTAMP,
    PRIMARY KEY (time_stamp)
);

CREATE TABLE FIPPA_Details (
   Country CHAR (60),
   FIPPA_Compliance INTEGER,
   PRIMARY KEY (country)
);

CREATE TABLE Organization (
    Organization_name CHAR (20),
    Country CHAR(60),
    PRIMARY KEY (Organization_name),
    FOREIGN KEY (Country)
        REFERENCES FIPPA_Details
);


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
CREATE TABLE Project_board (
   Board_title CHAR(20),
   Repository_name CHAR(40) NOT NULL,
   PRIMARY KEY (Board_title),
   FOREIGN KEY (Repository_name)
       REFERENCES Repository
);

CREATE TABLE Issue (
    Issue_ID INTEGER,
    Issue_message CHAR(200),
    Owning_board CHAR(20) NOT NULL,
    PRIMARY KEY (Issue_ID),
    FOREIGN KEY (Owning_board)
        REFERENCES Project_board(Board_title)
);


CREATE TABLE Repository_Object (
    Repository CHAR(40),
    file_Path CHAR(40),
    file_extension CHAR(5),
    file_contents CHAR(2000),
    File_size INTEGER,
    Parent_Directory_Path CHAR(40) DEFAULT '/',
    Parent_Directory_Repository CHAR (40) NOT NULL,
    PRIMARY KEY (Repository, file_Path),
    FOREIGN KEY (Repository)
        REFERENCES Repository,
    FOREIGN KEY (Parent_Directory_Repository, Parent_Directory_Path)
        REFERENCES Repository_Object(Repository, file_path)
);


CREATE TABLE Is_Member_of(
     Username CHAR(20),
     Organization_name CHAR(20) NOT NULL,
     PRIMARY KEY (Username, Organization_name),
     FOREIGN KEY (Username)
        REFERENCES User_Account(Username),
     FOREIGN KEY (Organization_name)
        REFERENCES Organization
);

CREATE TABLE Contributor_of (
    Username CHAR(20),
    Repository_name CHAR(40),
    PRIMARY KEY (Username, Repository_name),
    FOREIGN KEY (Username)
            REFERENCES User_Account(Username),
    FOREIGN KEY (Repository_name)
            REFERENCES Repository
);

CREATE TABLE Assigned_to (
     Username CHAR(20),
     Issue_ID INTEGER,
     PRIMARY KEY (Username, Issue_ID),
     FOREIGN KEY (Username)
         REFERENCES User_Account(Username),
     FOREIGN KEY (Issue_ID)
         REFERENCES Issue
);


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

CREATE TABLE Line_change (
     Commit_SHA CHAR (64),
     Line_number INTEGER,
     PRIMARY KEY (Commit_SHA, Line_number),
     FOREIGN KEY (Commit_SHA)
         REFERENCES Commit(Commit_SHA)
);