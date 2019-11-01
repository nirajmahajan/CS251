package com.CtrlAltDefeat.Planner.Database;

import android.content.Context;

import com.CtrlAltDefeat.Planner.NodeAdapter;

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
            initDatabase();
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
            initDatabase();
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

    private static void initDatabase() {
        Node elem;
        elem = new Node("Zen","If you chase two rabbits, you catch none.","",-1);
        INSTANCE.nodeDAO().insertinto(elem);
        int id_zen = INSTANCE.nodeDAO().findByName("Zen").get(0).getId();
        elem = new Node("Acads","Padhai ki baatein","31/12/2019",id_zen);
        INSTANCE.nodeDAO().insertinto(elem);
        elem = new Node("Hobbies","<3","",id_zen);
        INSTANCE.nodeDAO().insertinto(elem);
        int id_hobbs = INSTANCE.nodeDAO().findByName("Hobbies").get(0).getId();
        elem = new Node("Self_improvement","Reading list, blogs, exercise, etc.","30/12/2019",id_zen);
        INSTANCE.nodeDAO().insertinto(elem);
        int id_selfimp = INSTANCE.nodeDAO().findByName("Self_improvement").get(0).getId();
        elem = new Node("Research","Pet projects","",id_zen);
        INSTANCE.nodeDAO().insertinto(elem);
        elem = new Node("Origami","cranes and tigers.","29/10/2019",id_hobbs);
        INSTANCE.nodeDAO().insertinto(elem);
        elem = new Node("Drum_practice","Aim:\nHallowed be thy name,\nAcid Rain (LTE)","29/10/2019",id_hobbs);
        INSTANCE.nodeDAO().insertinto(elem);
        elem = new Node("Exercise","someday?","29/2/2019",id_selfimp);
        INSTANCE.nodeDAO().insertinto(elem);
        elem = new Node("Reading_list","My bucket list:\nHear the Wind Sing\nThe Fountainhead\nAtlas Shrugged\nA prisoner of birth","",id_selfimp);
        INSTANCE.nodeDAO().insertinto(elem);
    }

}
