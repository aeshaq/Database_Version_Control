import database.DatabaseConnectionHandler;
import model.Repository_Object;

public class Main {
    public static void main(String[] args) {
        DatabaseConnectionHandler db = new DatabaseConnectionHandler();
        System.out.println("DB initializes");
        db.login("ora_bsaoudio", "a69249035");
        System.out.println("Login completed");
        Repository_Object[] repos = db.get_repository_objects("CPSC304");
        System.out.println("Repos recieved");
        for (Repository_Object obj : repos) {
            System.out.println("OBJ: " +  obj.getPath() + ", " + obj.getSize());
        }

    }
}
