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
                    "               of their current position*/\n" +
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
            String predictions;
            predictions = in.readLine();
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
            predictions = in.readLine();
            System.out.println(predictions);
            //String predictions = "Original name: function (0.440710) predicted: ['last', 'index', 'of'] (0.240531) predicted: ['binary', 'search'] (0.187620) predicted: ['index', 'of'] (0.075693) predicted: ['find'] (0.021126) predicted: ['m'] (0.009566) predicted: ['partition'] (0.008618) predicted: ['first', 'index', 'of'] (0.006454) predicted: ['find', 'last'] (0.005152) predicted: ['array', 'contains'] (0.004530) predicted: ['median']";
            String[][] extractedText = extractEncasedText(predictions);
            float temp;
            for(int i = 0; i < extractedText.length; i++){
                //temp = Float.parseFloat(extractedText[i][0]);
                if(!(extractedText[i][1].length()<3)){
                    System.out.println(extractedText[i][0]);
                    System.out.println(extractedText[i][1]);
                }
            }
            // Print the result
            //socket.close();
        }catch(Exception e){
            System.out.println("Bad");
        }
    }
    private static String[][] extractEncasedText(String input) {
        String[][] result = new String[0][2];
        // Regular expressions to match text inside parentheses and square brackets
        String regexParentheses = "\\(([^)]*)\\)";
        String regexSquareBrackets = "\\[([^\\]]*)\\]";
        // Find matches using the regular expressions
        java.util.regex.Pattern patternParentheses = java.util.regex.Pattern.compile(regexParentheses);
        java.util.regex.Pattern patternSquareBrackets = java.util.regex.Pattern.compile(regexSquareBrackets);
        java.util.regex.Matcher matcherParentheses = patternParentheses.matcher(input);
        java.util.regex.Matcher matcherSquareBrackets = patternSquareBrackets.matcher(input);
        // Extract and store the matches in the result array
        while (matcherParentheses.find() && matcherSquareBrackets.find()) {
            String textInParentheses = matcherParentheses.group(1);
            String textInSquareBrackets = matcherSquareBrackets.group(1);
            // Resize the result array and add the new pair
            result = resizeArray(result, result.length + 1);
            textInSquareBrackets = textInSquareBrackets.replaceAll(" ", "");
            textInSquareBrackets = textInSquareBrackets.replaceAll(",", "_");
            textInSquareBrackets = textInSquareBrackets.replaceAll("'", "");
            result[result.length - 1][0] = textInParentheses;
            result[result.length - 1][1] = textInSquareBrackets;
        }
        return result;
    }
    private static String[][] resizeArray(String[][] array, int newSize) {
        String[][] newArray = new String[newSize][2];
        System.arraycopy(array, 0, newArray, 0, Math.min(array.length, newSize));
        return newArray;
    }
}
