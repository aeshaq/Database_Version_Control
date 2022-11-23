import database.DatabaseConnectionHandler;
import model.Commit;
import model.Repository;
import model.UserAccount;
import model.Organization;

import java.sql.Timestamp;

public class Main {
    public static void main(String[] args) {
        DatabaseConnectionHandler db = new DatabaseConnectionHandler();
        System.out.println("DB initializes");
        db.login("ora_bsaoudio", "a69249035");
        Repository repo = db.getRepository("CPSC304");
        for(UserAccount usr : db.getTopContributors(repo)) {
            System.out.println(usr.getUsername());
        }
    }
}
