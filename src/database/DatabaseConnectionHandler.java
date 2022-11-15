package database;

import model.File;
import model.Repository;
import model.Repository_Object;

import java.sql.*;
import java.util.ArrayList;

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
    public Repository[] get_repositories(String repo_name, String organization_name) {
        ArrayList<Repository> result = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM Repository R WHERE R.Organization_name = ? AND R.Repository_name LIKE ?"
            );
            ps.setString(1, organization_name);
            ps.setString(2, repo_name);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Repository model = new Repository(
                        rs.getString("Repository_name"),
                        rs.getString("Organization_name"),
                        rs.getDate("Date_created"));
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
                    "SELECT * " +
                        "FROM Repository_Object " +
                        "WHERE Repository = ?"
            );
            ps.setString(1,   repository_name);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Repository_Object model = new Repository_Object(
                        rs.getString("Repository"),
                        rs.getString("file_path"),
                        rs.getInt("File_size")
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

    public String view_contents(File file) {
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

}
