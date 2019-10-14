import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
//check closing the sockets
public class MultiServer {
    public static Map<Integer,Integer> m= new HashMap<Integer,Integer>();
    
    public static void main(String[] args) throws IOException {
        System.out.println("Listening on 5000"); 
        
        ExecutorService pool = Executors.newFixedThreadPool(8);
        ServerSocket serverSocket = new ServerSocket(5000);
        while(true){
            Runnable subThread = new MyServerThread(serverSocket.accept());
            pool.execute(subThread);
        }
    }


    public static class MyServerThread implements Runnable {
        private Socket clientSocket = null;
    
        // my multithread class
        public MyServerThread(Socket socket) {
            // super("KKMultiServerThread");
            this.clientSocket = socket;
        }
        
        public void run() {
            synchronized(m){
                try (

	                PrintWriter out =
	                    new PrintWriter(clientSocket.getOutputStream(), true);                   
	                BufferedReader in = new BufferedReader(
	                    new InputStreamReader(clientSocket.getInputStream()));
	            ) 

	            {
	                
	                int portNumber = 5000;
	                String inputLine;
	                if((inputLine = in.readLine()) != null){
	                    String[] arr=inputLine.split(" ",2);
	                    while (!arr[0].equals("disconnect")) {
	                        
	                        //System.out.println(arr[0]+"@"+arr[1]);
	                        if(arr[0].equals("add"))
	                        {
	                            //System.out.println("HERE");
	                            int i=Integer.parseInt(arr[1]);
	                            int ans;
	                            if(m.containsKey(i))
	                            {
	                                ans=m.put(i,m.get(i)+1);
	                                ans++;
	                            }
	                            else
	                            {
	                                m.put(i,1);
	                                ans=1;
	                            }
	                            out.println(ans);
	                            System.out.println("ADD "+i);
	                        }
	                        else if(arr[0].equals("read"))
	                        {
	                            int i=Integer.parseInt(arr[1]);
	                            int ans;
	                            if(m.containsKey(i))
	                            {
	                                ans=m.get(i);
	                            }
	                            else
	                            {
	                                ans=0;
	                            }
	                            out.println(ans);
	                            System.out.println("READ "+i+" "+ans);   
	                        }
	                        inputLine = in.readLine();
	                        arr=inputLine.split(" ",2); 
	                    }
	                    System.out.println("DIS");
	                    //m= new HashMap<Integer,Integer>();
	                    out.close();in.close();
	                    // serverSocket.close();
	                }
	            } catch (IOException e) {
	                System.out.println("IOException " + e);
	                /*System.out.println("Exception caught when trying to listen on port "
	                    + portNumber + " or listening for a connection");
	                System.out.println(e.getMessage());*/
	            }
	        }
	    }
	}
}