package android.example.Planner.Database;

import android.content.Context;
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
        String parent = INSTANCE.nodeDAO().findByName(name).get(0).getParent();
        return hierarchy(parent) + "/" + name;
    }

}
