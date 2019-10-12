import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;

public interface FetchAndProcess {
    static String DB_NAME = "pokemon.db";
    static String TABLE_NAME = "pokemon";

    /* The map populated by fetch */
    // public Map<String, String> data = new HashMap<String, String>();
    
    // no default implementation
    void fetch(List<String> paths);

    // no default implementation
    Map<String, String> exposeData();
    
    /* Provides a default implementation that does a lot of work:
     * 1. Create the `TABLE_NAME` table if it does not exist (along with a uniqueness constraint).
     * 2. Inserts data into the table, safely. ensuring no duplication.
     * 3. Returns the Connection (useful for the FetchAndProcessNetwork* classes)
     */
    default List<String> process() {
	// you can use exposeData() here.
	return null;
    }
}
