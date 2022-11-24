import database.DatabaseConnectionHandler;
import model.Commit;
import model.Repository;
import model.UserAccount;
import ui.UIDrawer;
import model.Organization;

import java.sql.Timestamp;

public class Main {
    private static UIDrawer ui;
    public static void main(String[] args) {
        DatabaseConnectionHandler db = new DatabaseConnectionHandler();

        ui = new UIDrawer(db);
        System.out.println("DB initializes");
        db.login("ora_bsaoudio", "a69249035");
        Repository repo = db.getRepository("CPSC304");
        for(UserAccount usr : db.getTopContributors(repo)) {
            System.out.println(usr.getUsername());
        }
    }
}
