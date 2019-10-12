import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;

public class Main {
    static List<String> NET_PATHS;
    
    public static void main(String[] args) {
	NET_PATHS = new ArrayList<>();
	NET_PATHS.add("https://www.cse.iitb.ac.in/~ananya/ainegjbf.php");
	NET_PATHS.add("https://www.cse.iitb.ac.in/~ananya/woetng34.php");
	NET_PATHS.add("https://www.cse.iitb.ac.in/~ananya/87g1y3fb.php");

	if (args.length != 1) {
            System.out.println("Usage: java Main <impl-option>");
            System.out.println("\tWhere <impl-option> == disk or net or fastnet");
            System.exit(1);
        }
        FetchAndProcess fetchAndProcess = null;
        List<String> paths = new ArrayList<>();
	
        switch (args[0]) {
            case "disk":
		// 10 points
                fetchAndProcess = new FetchAndProcessFromDisk();
                for (int i = 1; i <= 4; i++) {
                    paths.add("task3/data/gym" + String.valueOf(i));
                }
                break;
            case "net":
		// 10 points
                fetchAndProcess = new FetchAndProcessFromNetwork();
		paths = NET_PATHS;
                break;
            case "fastnet":
		// 5 points
                fetchAndProcess = new FetchAndProcessFromNetworkFast();
		paths = NET_PATHS;
                break;
            default:
                System.out.println("Unknown Option");
                System.exit(2);
        }
        fetchAndProcess.fetch(paths);
	// 25 points
	fetchAndProcess.process();
    }
}
