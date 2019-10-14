import java.io.*;
import java.net.*;
import java.util.*; 
//check closing the sockets
public class BasicClient {
    public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException{
         
        if (args.length != 1) {
            System.err.println(
                "Usage: java BasicClient <host name> <port number>");
            System.exit(0);
        }
        int delta=0;
        Map<Integer,Integer> m= new HashMap<Integer,Integer>();
        String filename = args[0];
        File file=new File(filename);
        //int portNumber = Integer.parseInt(args[1]);
        BufferedReader stdIn =
                new BufferedReader(
                    new FileReader(file));
        String userInput=stdIn.readLine();
        while(userInput!=null){
            String[] ar=userInput.split(" ",3);
            if(ar[0].equals("connect")){ 
                try (
                    Socket echoSocket = new Socket(ar[1], Integer.parseInt(ar[2]));
                    PrintWriter out =
                        new PrintWriter(echoSocket.getOutputStream(), true);
                    BufferedReader in =
                        new BufferedReader(
                            new InputStreamReader(echoSocket.getInputStream()));
                    
                ) {
                    System.out.println("OK");
                    //System.out.print("till");
                    int serverInput;
                    userInput=stdIn.readLine();
                    //System.out.print("till");
                    String[] myin=userInput.split(" ",2);
                    //System.out.print("till");
                    while (!myin[0].equals("disconnect")) {
                        //System.out.print("till");
                        if(myin[0].equals("read")){
                                out.println(userInput);
                                //System.out.print("till");
                                serverInput=Integer.parseInt(in.readLine());
                                int i=Integer.parseInt(myin[1]);
                                int change;
                                if(m.containsKey(i))
                                {
                                    change=serverInput-m.get(i);
                                    delta=delta+change;
                                }
                                else
                                {
                                    //m.put(i,serverInput);
                                    change=serverInput;
                                    delta=delta+serverInput;
                                }
                                System.out.print(serverInput);
                                System.out.print(" ");
                                System.out.println(change);
                        }
                        else if(myin[0].equals("add")){
                                out.println(userInput);
                                serverInput=Integer.parseInt(in.readLine());
                                int i=Integer.parseInt(myin[1]);
                                m.put(i,serverInput);
                        }
                        /*else{
                                //System.out.print("till");
                                out.println(userInput);
                                //System.out.print("till");
                                serverInput=Integer.parseInt(in.readLine());
                                //System.out.print("till");
                                //System.out.println(serverInput);
                        }*/
                        userInput=stdIn.readLine();
                        if(userInput != null){
                            myin=userInput.split(" ",2);
                        }
                    }
                    out.println("disconnect");
                    out.close();
                    in.close();
                    echoSocket.close();
                    System.out.println("OK");
                    //Thread.sleep(100);
                    userInput=stdIn.readLine();
                    m= new HashMap<Integer,Integer>();
                } catch (UnknownHostException e) {
                    System.out.println("No Server");
                    System.exit(1);
                } catch (IOException e) {
                    System.err.println("No Server");
                    //System.out.println("No Server");
                    System.exit(1);
                }
            }
            else if(ar[0].equals("sleep"))
            {
                Thread.sleep(Integer.parseInt(ar[1]));
                userInput=stdIn.readLine();
            }
            else
            {
                System.out.print("Wrong place");
                System.exit(1);
            }
        }
        System.out.println(delta);
    }
}
