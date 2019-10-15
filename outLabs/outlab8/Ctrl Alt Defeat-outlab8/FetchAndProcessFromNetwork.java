import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class FetchAndProcessFromNetwork implements FetchAndProcess {
    private Map<String, String> data = new HashMap<String, String>();
    @Override
    public Map<String, String> exposeData() {
        return data;
    }
    @Override
    public void fetch(List<String> paths) {
        for (String s : paths) {
            try {
                URL curr = new URL(s);
                URLConnection conn = curr.openConnection();
                conn.connect();
                BufferedReader breader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                while ((inputLine = breader.readLine()) != null) {
                    if (data.containsKey(inputLine)) {
                        data.put(inputLine, data.get(inputLine) + "@@@" + s);
                    } else  data.put(inputLine, s);
                }
                if (breader != null) breader.close();
            } catch (Exception ex) {
                System.out.println("Error while connecting to URL");
                // ex.printStackTrace();
            }
        }
    }
    @Override
    public List<String> process() {
        // Implement here
        // Can you make use of the default implementation here?
        // See https://dzone.com/articles/interface-default-methods-java
        try {
            List<String> foo = FetchAndProcess.super.process();
            String sDriverName = "org.sqlite.JDBC";
            String sJdbc = "jdbc:sqlite";
            String sDbUrl = sJdbc + ":" + DB_NAME;
            String sql = String.format("SELECT pokemon_name FROM %s GROUP BY pokemon_name HAVING COUNT(*) > 1", TABLE_NAME);
            Class.forName(sDriverName);
            Connection conn = DriverManager.getConnection(sDbUrl);
            conn.setAutoCommit(false);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            List<String> outp = new ArrayList<String>();
            while (rs.next())
                outp.add(rs.getString("pokemon_name"));
            for(String s : outp)
                System.out.println(s);
            stmt.close();
            conn.close();
            return outp;
        } catch (Exception e) {
            System.out.println("Error occured");
            return null;
        }
    }
}
