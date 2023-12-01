import java.io.*;

public class startup {
    private static ProcessBuilder server;
    private static Process serverProcess;

    public static void main(String[] args) throws IOException{
        try{
            Thread server = new Thread(new test());
            server.start();
            client cl = new client();
            cl.main(null);
        }
        catch(Exception e){
            System.out.println("Exception start");
        }
    }
}
