package database;

import model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseConnectionHandler {
    private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";

    private Connection connection = null;

    public DatabaseConnectionHandler() {
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }
    public boolean login(String username, String password) {
        try {
            if (connection != null) {
                connection.close();
            }

            System.out.println("Attempting to connect to Oracle");
            connection = DriverManager.getConnection(ORACLE_URL, username, password);
            System.out.println("Connection complete");
            connection.setAutoCommit(false);

            System.out.println("\nConnected to Oracle!");
            return true;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return false;
        }
    }
    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    /**
     *  selects list of repository objects matching the given name
     * @return
     */
    public Repository[] getRepositories(String repo_name, UserAccount user) {
        ArrayList<Repository> result = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT R.repository_name as repository_name, " +
                            "R.organization_name as organization_name, " +
                            "R.date_created as date_created" +
                        "FROM Repository R, IS_MEMBER_OF I " +
                        "WHERE R.organization_name = I.organization_name " +
                            "AND I.username = ? " +
                            "AND R.repository_name LIKE ?"
            );
            ps.setString(1, user.getUsername());
            ps.setString(2, "%" + repo_name + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Repository model = new Repository(
                        rs.getString("Repository_name"),
                        rs.getString("Organization_name"),
                        rs.getTimestamp("Date_created"));
                result.add(model);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return result.toArray(new Repository[result.size()]);
    }

    public Repository_Object[] get_repository_objects(String repository_name)
    {
        ArrayList<Repository_Object> result = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM Repository_Object WHERE repository = ?"
            );
            ps.setString(1,   repository_name);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Repository_Object model = new Repository_Object(
                        rs.getString("Repository"),
                        rs.getString("parent_directory_path"),
                        rs.getString("file_path"),
                        rs.getInt("file_size")
                );
                System.out.println("Repsitory name: " + rs.getString("Repository"));
                result.add(model);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return result.toArray(new Repository_Object[result.size()]);
    }

    public String viewContents(File file) {
        String out = "";
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT file_contents FROM Repository_Object R WHERE R.Repository = ? and R.file_path = ?"
            );
            ps.setString(1, file.getRepositoryName());
            ps.setString(2, file.getPath());

            ResultSet rs = ps.executeQuery();
            /* Move pointer to the first element */
            rs.next();
            /* Check that there exists a 'first element' */
            if (rs != null) {
                out = rs.getString("file_contents");
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return out;
    }

    public void createUser(String username, String password_hash) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO User_Account VALUES (?, ?)"
            );
            ps.setString(1, username);
            ps.setString(2, password_hash);

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public UserAccount selectUser(String username, String password_hash) {
        UserAccount out = null;
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM User_Account WHERE username = ? AND password_hash = ?"
            );
            ps.setString(1,   username);
            ps.setString(2,   password_hash);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UserAccount account = new UserAccount(
                        rs.getString("username"),
                        rs.getString("password_hash")
            );
            out = account;
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return out;
    }

    public File selectFile(String repository, String file_path) {
        File out = null;
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM REPOSITORY_OBJECT WHERE repository = ? AND file_path = ?"
            );
            ps.setString(1,   repository);
            ps.setString(2,   file_path);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                File file = new File(
                        rs.getString("repository"),
                        rs.getString("parent_directory_path"),
                        rs.getString("file_path"),
                        rs.getInt("file_size"),
                        rs.getString("file_contents"),
                        rs.getString("file_extension")
                );
                out = file;
                break;
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return out;
    }
    public void addFile(File f) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO Repository_Object VALUES (?, ?, ?, ?, ?, ?, ?)"
            );
            ps.setString(1, f.getRepositoryName());
            ps.setString(2, f.getPath());
            ps.setString(3, f.getFile_extension());
            ps.setString(4, f.getFile_contents());
            ps.setInt(5, f.getSize());
            ps.setString(6, f.getParentDirectory());
            ps.setString(7, f.getRepositoryName());


            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }
    public void updateFile(File f, String newContents) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE REPOSITORY_OBJECT SET file_contents = ? WHERE repository = ? AND file_path = ?"
            );
            ps.setString(1, newContents);
            ps.setString(2, f.getRepositoryName());
            ps.setString(3, f.getPath());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void addCommit(Commit c) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO Commit VALUES (?, ?, ?, ?, ?, ?)"
            );
            ps.setString(1, c.getCommit_SHA());
            ps.setInt(2, c.getCommit_number());
            ps.setString(3, c.getRepository_name());
            ps.setString(4, c.getFile_changed());
            ps.setString(5, c.getAuthor());
            ps.setTimestamp(6, c.getTimestamp());


            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public Organization getOrganization(String orgName) {
        Organization out = null;
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM Organization WHERE organization_name LIKE ?"
            );
            ps.setString(1,   orgName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Organization account = new Organization(
                        rs.getString("organization_name"),
                        rs.getString("country")
                );
                out = account;
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return out;
    }


    public void setOrganization(UserAccount user, Organization org) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO IS_MEMBER_OF VALUES (?, ?)"
            );
            ps.setString(1, user.getUsername());
            ps.setString(2, org.getOrganization_name());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public Repository getRepository(String repository)
    {
        Repository repo = null;
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM REPOSITORY WHERE repository_name = ?"
            );
            ps.setString(1,   repository);
            ResultSet rs = ps.executeQuery();
            rs.next();
            if (rs != null){
                repo = new Repository(
                        rs.getString("repository_name"),
                        rs.getString("organization_name"),
                        rs.getTimestamp("date_created")
                );
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return repo;
    }
    public String[] get_extensions_list(Repository repo) {
        ArrayList<String> result = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT file_extension FROM Repository_Object WHERE repository = ? AND file_extension IS NOT NULL"
            );
            ps.setString(1,   repo.getRepository_name());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(rs.getString("file_extension"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return result.toArray(new String[result.size()]);
    }
    private void rollbackConnection() {
        try  {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public HashMap<String, Integer> get_num_user_commits_per_repo(UserAccount user) {
        HashMap<String, Integer> out = new HashMap<>();
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT COUNT(*) as count_val, repository_name FROM Commit WHERE author = ? GROUP BY repository_name"
            );
            ps.setString(1, user.getUsername());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                out.put(rs.getString("repository_name"), rs.getInt("count_val"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return out;
    }

    /* Orgs tab */
    public int get_avg_contributer_commit_per_repo(Organization org) {
        int out = 0;
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT AVG(count_val)" +
                        "FROM (SELECT COUNT(*) as count_val " +
                            "FROM Commit C, Repository R " +
                            "WHERE C.repository_name = R.repository_name AND R.organization_name = ?" +
                            "GROUP BY R.repository_name)"
            );
            ps.setString(1, org.getOrganization_name());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                out = rs.getInt(1);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return out;
    }
    public String[] find_mega_contributers(Organization org) {
        ArrayList<String> megaContributors = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT U.username " +
                        "FROM User_Account U " +
                        "WHERE NOT EXISTS" +
                            "((SELECT R.repository_name" +
                            "   FROM Repository R " +
                            "   WHERE R.organization_name = ?)" +
                            "MINUS " +
                            "(SELECT C.repository_name " +
                            "   FROM Commit C, Repository R" +
                            "   WHERE C.author = U.username AND C.repository_name = R.repository_name AND R.organization_name = ?))"
            );
            ps.setString(1, org.getOrganization_name());
            ps.setString(2, org.getOrganization_name());

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                megaContributors.add(rs.getString(1));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return megaContributors.toArray(new String[megaContributors.size()]);
    }
    public UserAccount[] getTopContributors(Repository repo) {
        ArrayList<UserAccount> out = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT U.username, U.password_hash " +
                            "        FROM User_Account U " +
                            "        GROUP BY U.username, U.password_hash " +
                            "        HAVING (SELECT Count(*) " +
                            "                 FROM Commit Co " +
                            "                 WHERE Co.author = U.username AND Co.repository_name = ?)" +
                            "                >= " +
                            "               (SELECT AVG(counts) " +
                            "               FROM ( " +
                            "                   SELECT COUNT(*) as counts " +
                            "                   FROM Commit Co " +
                            "                   WHERE Co.repository_name = ? " +
                            "                   GROUP BY Co.author))"
            );
            ps.setString(1, repo.getRepository_name());
            ps.setString(2, repo.getRepository_name());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UserAccount account = new UserAccount(
                        rs.getString("username"),
                        rs.getString("password_hash")
                );
                out.add(account);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return out.toArray(new UserAccount[out.size()]);
    }
}
