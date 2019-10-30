package android.example.Planner.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NodeDAO {
    @Query("Select COUNT(*) FROM nodes")
    int countNodes();

    @Query("Select * FROM nodes ORDER BY name DESC")
    List<Node> getAllNodes();

    @Query("Select * FROM nodes WHERE name LIKE :name ORDER BY name DESC")
    List<Node> findByName(String name);

    @Query("Select * FROM nodes WHERE id LIKE :id ORDER BY name DESC")
    Node findById(int id);

    @Query("Select * FROM nodes WHERE parent LIKE :parent ORDER BY name DESC")
    List<Node> findByParent(String parent);

    @Query("Select * FROM nodes WHERE description LIKE :description ORDER BY name DESC")
    List<Node> findByDescription(String description);

    @Query("Select * FROM nodes WHERE date LIKE :date ORDER BY name DESC")
    List<Node> findByDate(String date);

    @Query("Select * FROM nodes WHERE expanded LIKE :expanded ORDER BY name DESC")
    List<Node> findExpanded(boolean expanded);

    @Query("Select * FROM nodes WHERE expanded LIKE :expanded AND name LIKE :name AND description LIKE :desc AND date LIKE :date ORDER BY name DESC")
    List<Node> findExact(String name, String desc, String date, boolean expanded);

    @Query("Select * FROM nodes ORDER BY name DESC")
    List<Node> getOrdered();

    @Insert
    long[] Insert(Node... nodes);

    @Delete
    void Delete(Node... nodes);
}
