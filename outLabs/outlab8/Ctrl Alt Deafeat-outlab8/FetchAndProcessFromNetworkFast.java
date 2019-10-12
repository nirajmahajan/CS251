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
                while ((inputLine = breader.readLine()) != null)
                    data.put(inputLine, url);
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
        ExecutorService executor = Executors.newCachedThreadPool();
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
            return foo;
        } catch (Exception e) {
            return null;
        }
    }
}

