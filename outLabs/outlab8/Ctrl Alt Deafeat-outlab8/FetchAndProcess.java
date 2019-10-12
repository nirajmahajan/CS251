import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

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
        String sDriverName = "org.sqlite.JDBC";
        String sJdbc = "jdbc:sqlite";
        String sDbUrl = sJdbc + ":" + DB_NAME;
        String sMakeTable = String.format("CREATE TABLE IF NOT EXISTS %s (pokemon_name TEXT NOT NULL, source_path TEXT, PRIMARY KEY (pokemon_name, source_path))", TABLE_NAME);
        String sMakeInsert = String.format("INSERT INTO %s (pokemon_name, source_path) VALUES(?, ?)", TABLE_NAME);
        Map<String, String> temp = this.exposeData();
        try {
            Class.forName(sDriverName);
            Connection conn = DriverManager.getConnection(sDbUrl);
            conn.setAutoCommit(false);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate( sMakeTable );
            PreparedStatement stat = conn.prepareStatement(sMakeInsert);
            for (Map.Entry<String, String> el : temp.entrySet()) {
                try {
                    stat.setString(1, el.getKey());
                    stat.setString(2, el.getValue());
                    stat.executeUpdate();
                } catch (Exception e) {
                    // Duplicate Insertion Attempt! Skipped
                    continue;
                }
            }
            conn.commit();
            stmt.close();
            stat.close();
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error occured");
            return null;
        }
        return null;
    }
}
