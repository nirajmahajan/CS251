import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

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
                while ((inputLine = breader.readLine()) != null)
                    data.put(inputLine, s);
                if (breader != null) breader.close();
            } catch (Exception ex) {
                System.out.println("Error while connecting to URL");
                ex.printStackTrace();
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
            return foo;
        } catch (Exception e) {
            return null;
        }
    }
}
