import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class FetchAndProcessFromDisk implements FetchAndProcess {
    private Map<String, String> data;

    @Override
    public Map<String, String> exposeData() {
	return data;
    }

    @Override
    public void fetch(List<String> paths) {
	// Implement here
    }
}
