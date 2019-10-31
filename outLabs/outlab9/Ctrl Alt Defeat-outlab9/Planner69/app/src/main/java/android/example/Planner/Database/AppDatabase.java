package android.example.Planner.Database;

import android.content.Context;
import android.content.Intent;
import android.example.Planner.NodeAdapter;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Node.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public static AppDatabase INSTANCE;

    public abstract NodeDAO nodeDAO();

    public static void buildAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "node-database")
                            // allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            .allowMainThreadQueries()
                            .build();
            INSTANCE.nodeDAO().purge();
            Node temp = new Node();
            temp.setName("Zen");
            temp.setExpanded(false);
            temp.setDate("1/1/1111");
            temp.setDescription("Do or Do not, there is no try");
            temp.setParentId(-1);
            INSTANCE.nodeDAO().insertinto(temp);
        }

    }

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "node-database")
                            // allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            .allowMainThreadQueries()
                            .build();
            INSTANCE.nodeDAO().purge();
        }
        return INSTANCE;
    }

    public static void Delete(Node... nodes) {
        INSTANCE.nodeDAO().deletefrom(nodes);
        for(Node elem : nodes) {
            NodeAdapter.removeFromNodes(elem);
        }
    }

    public static void Insert(Node... nodes) {
        INSTANCE.nodeDAO().insertinto(nodes);
        for(Node elem : nodes) {
            NodeAdapter.insertIntoNodes(elem);
        }
    }

    public static String hierarchy(String name) {
        if(name.equals("Zen")) {
            return name;
        }
        int parentId = INSTANCE.nodeDAO().findByName(name).get(0).getParentId();
        Node temp = INSTANCE.nodeDAO().findById(parentId).get(0);
        return hierarchy(temp.getName()) + "/" + name;
    }

}
