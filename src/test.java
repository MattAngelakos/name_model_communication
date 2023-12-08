import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.*;
import java.net.*;
public class test implements Runnable {
    @Override
    public void run(){
        try{
            ServerSocket server = new ServerSocket(8080);
            ProcessBuilder builder = new ProcessBuilder("C:\\Users\\squir\\AppData\\Local\\Programs\\Python\\Python39\\python.exe",
                    "code2vec/code2vec-master/code2vec.py", "--load", "code2vec/java14m_model/models/java14_model/saved_model_iter8.release", "--predict");
            Process process = builder.start();
            Socket jv_client = server.accept();
            Socket py_client = server.accept();
            PrintWriter out_jv = new PrintWriter(jv_client.getOutputStream(),true);
            BufferedReader in_jv = new BufferedReader(new InputStreamReader(jv_client.getInputStream()));
            PrintWriter out_py = new PrintWriter(py_client.getOutputStream(),true);
            BufferedReader in_py = new BufferedReader(new InputStreamReader(py_client.getInputStream()));
            String msg;
            msg = in_jv.readLine();
            //while(true) {
            String lines;
            out_py.println(msg);
            //System.out.println(" ");
            //while(!((lines = reader.readLine()).equals("stop"))){
            //    System.out.println(lines);
            //}
            msg = in_py.readLine();
            out_jv.println(msg);
            msg = in_jv.readLine();
            System.out.println(msg);
            out_py.println(msg);
            //while(!((lines = reader.readLine()).equals("stop"))){
            //    System.out.println(lines);
            //}
            msg = in_py.readLine();
            out_jv.println(msg);
            py_client.close();
            jv_client.close();
            //}
            server.close();
            System.exit(0);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
} 