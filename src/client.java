import java.io.*;
import java.net.*;
import java.util.concurrent.TimeUnit;
public class client {
    public static void main(String[] args) throws IOException {
        String FILE_PATH = "code2vec\\code2vec-master\\Input.java";
        try{
            new FileWriter(FILE_PATH, false).close();
            FileWriter fileWriter = new FileWriter(FILE_PATH);
            fileWriter.write("void f(int arr[])\n" +
                    "    {\n" +
                    "        int n = arr.length;\n" +
                    "        for (int i = 1; i < n; ++i) {\n" +
                    "            int key = arr[i];\n" +
                    "            int j = i - 1;\n" +
                    " \n" +
                    "            /* Move elements of arr[0..i-1], that are\n" +
                    "               greater than key, to one position ahead\n" +
                    "               of their current position */\n" +
                    "            while (j >= 0 && arr[j] > key) {\n" +
                    "                arr[j + 1] = arr[j];\n" +
                    "                j = j - 1;\n" +
                    "            }\n" +
                    "            arr[j + 1] = key;\n" +
                    "        }\n" +
                    "    }");
            fileWriter.close();
            TimeUnit.SECONDS.sleep(1);
            Socket socket = new Socket("localhost", 8080);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
            out.println("");
            System.out.println(".");
            String predictions;
            predictions = in.readLine();
            System.out.println(".");
            System.out.println(predictions);
            fileWriter = new FileWriter(FILE_PATH);
            fileWriter.write("int function1(int arr[], int l, int r, int x)\n" +
                    "{\n" +
                    "    while (l <= r) {\n" +
                    "        int m = l + (r - l) / 2;\n" +
                    " \n" +
                    "        // Check if x is present at mid\n" +
                    "        if (arr[m] == x)\n" +
                    "            return m;\n" +
                    " \n" +
                    "        // If x greater, ignore left half\n" +
                    "        if (arr[m] < x)\n" +
                    "            l = m + 1;\n" +
                    " \n" +
                    "        // If x is smaller, ignore right half\n" +
                    "        else\n" +
                    "            r = m - 1;\n" +
                    "    }\n" +
                    " \n" +
                    "    // If we reach here, then element was not present\n" +
                    "    return -1;\n" +
                    "}");
            fileWriter.close();
            out.println("again");
            System.out.println(".");;
            //predictions = in.readLine();
            //System.out.println(".");
            //System.out.println(predictions);
            socket.close();
        }catch(Exception e){
            System.out.println("Bad");
        }
    }
}
