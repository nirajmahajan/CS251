import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
/* add some more imports */
import java.io.BufferedReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class FetchAndProcessFromNetworkFast implements FetchAndProcess {
    private Map<String, String> data = new HashMap<String, String>();
    @Override
    public Map<String, String> exposeData() {
        return data;
    }
    class MyRunnable implements Runnable {
        private final String url;
        MyRunnable(String url) {
            this.url = url;
        }
        @Override
        public void run() {
            try {
                URL curr = new URL(url);
                URLConnection conn = curr.openConnection();
                conn.connect();
                BufferedReader breader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                while ((inputLine = breader.readLine()) != null) {
                    if (data.containsKey(inputLine)) {
                        data.put(inputLine, data.get(inputLine) + "@@@" + url);
                    } else  data.put(inputLine, url);
                }
                if (breader != null) breader.close();
            } catch (Exception ex) {
                System.out.println("Error while connecting to URL");
                // ex.printStackTrace();
            }
        }
    }

    @Override
    public void fetch(List<String> paths) {
        // Implement here, just do it parallely!
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (String s : paths) {
            Runnable worker = new MyRunnable(s);
            executor.execute(worker);
        }
        executor.shutdown();
        while (!executor.isTerminated()) {}
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
