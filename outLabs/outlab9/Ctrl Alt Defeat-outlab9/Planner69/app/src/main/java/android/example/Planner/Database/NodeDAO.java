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

    @Query("Select * FROM nodes")
    List<Node> getAllNodes();

    @Query("Select * FROM nodes WHERE name LIKE :name")
    List<Node> findByName(String name);

    @Query("Select * FROM nodes WHERE parent LIKE :parent")
    List<Node> findByParent(String parent);

    @Query("Select * FROM nodes WHERE description LIKE :description")
    List<Node> findByDescription(String description);

    @Query("Select * FROM nodes WHERE date LIKE :date")
    List<Node> findByDate(String date);

    @Query("Select * FROM nodes WHERE expanded LIKE :expanded")
    List<Node> findExpanded(boolean expanded);

    @Insert
    long[] Insert(Node... nodes);

    @Delete
    void Delete(Node... nodes);
}
