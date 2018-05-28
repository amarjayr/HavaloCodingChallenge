import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public final class HavaloCodingChallenge {

    /**
     * Should return true if the input string is a palindrome.  False if it is not.
     *
     * A palindrome is a word, phrase, number, or other sequence of characters which reads
     * the same backward as forward, such as "madam" or "racecar".
     *
     * "madam" -> true
     * "racecar" -> true
     * "dog" -> false
     * "123321" -> true
     */
    public static boolean isPalindrome(String word) {
        // TODO: your java code here!
        return false;
    }

    /**
     * Should return true if the input string contains duplicate characters.
     *
     * "code" -> false
     * "java" -> true
     * "mmmmm" -> true
     * "abcdefghijklmnopqrstuvwxyz" -> false
     * "12341" -> true
     */
    public static boolean containsDuplicateCharacters(String word) {
        // TODO: your java code here!
        return false;
    }

    /**
     * Must return the reverse string representation of the input string.
     *
     * "banana" -> "ananab"
     * "html" -> "lmth"
     * "havalo" -> "olavah"
     */
    public static String reverseWord(String word) {
        // TODO: your java code here!
        return word;
    }

    // ------------------------------------------------------------------------------
    // ------------------------------------------------------------------------------

    public static void main(String... args) throws Exception {
        new WebServer(4444).start();
    }

    private static class WebServer {

        private int port_;

        public WebServer(int port) {
            port_ = port;
        }

        public void start() throws Exception {
            ServerSocket serverSocket = new ServerSocket(port_);
            System.out.println("\nWeb-server started! ... press Ctrl-C to quit.");
            System.out.println("Load http://localhost:4444 in your web-browser.");

            while (true) {
                Socket s = serverSocket.accept(); // Wait for a browser to connect
                new Router(s).start(); // Start a separate thread to process the request
            }
        }

    }

    private static class Router extends Thread {

        private static final String CRLF = "\r\n";

        private Socket socket_;

        // Start the thread in the constructor
        public Router(Socket s) {
            socket_ = s;
            setDaemon(true);
        }

        // Read the HTTP request, respond, and close the connection
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket_.getInputStream()));
                 PrintStream out = new PrintStream(new BufferedOutputStream(socket_.getOutputStream()))) {

                String requestLine = in.readLine();
                System.out.println(requestLine); // Log the request

                String route;
                StringTokenizer st = new StringTokenizer(requestLine);

                // Parse the route from the GET request
                if (st.hasMoreElements() && st.nextToken().equalsIgnoreCase("GET")
                        && st.hasMoreElements()) {
                    route = st.nextToken();
                } else {
                    throw new FileNotFoundException(); // Bad request
                }

                Map<String, String> queryParameters = getQueryParams(route);
                if (!queryParameters.isEmpty()) {
                    route = route.split("\\?")[0];
                }

                if ("/".equals(route)) {
                    sendHtmlFile(out, "index.html");
                } else if ("/words.html".equals(route)) {
                    sendHtmlFile(out, "words.html");
                } else if ("/echo".equals(route)) {

                    // Example:
                    // Echos the value of the "word" query parameter right back to the browser.

                    // The URL calling this code looks like this:
                    // http://localhost:4444/echo?word=someword

                    // Get the "word" query parameter on the URL.
                    // E.g., ?word=cat then the word variable value would be "cat"
                    String word = queryParameters.get("word");

                    // Echo the input string back to the browser.
                    sendString(out, word);

                } else if ("/palindrome".equals(route)) {

                    // http://localhost:4444/palindrome?word=someword

                    String word = queryParameters.get("word");

                    if (isPalindrome(word)) {
                        sendString(out, "yes");
                    } else {
                        sendString(out, "no");
                    }

                } else if ("/duplicates".equals(route)) {

                    // http://localhost:4444/duplicates?word=someword

                    String word = queryParameters.get("word");

                    if (containsDuplicateCharacters(word)) {
                        sendString(out, "yes");
                    } else {
                        sendString(out, "no");
                    }

                } else if ("/reverse".equals(route)) {

                    // http://localhost:4444/reverse?word=someword

                    String word = queryParameters.get("word");

                    // Send back the reversed word.
                    sendString(out, reverseWord(word));

                } else {
                    // 404 Not Found
                    send404NotFound(out);
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

        private static Map<String, String> getQueryParams(String url) {
            Map<String, String> params = new HashMap<>();
            String[] urlParts = url.split("\\?");
            if (urlParts.length > 1) {
                String query = urlParts[1];
                for (String param : query.split("&")) {
                    String[] pair = param.split("=");
                    String key = pair[0];
                    String value = "";
                    if (pair.length > 1) {
                        value = pair[1];
                    }

                    params.put(key, value);
                }
            }
            return params;
        }

        private static void sendString(PrintStream out, String text) {
            out.print("HTTP/1.0 200 OK\r\n");
            out.print("Content-Type: text/plain; charset=utf-8");
            out.print(CRLF);
            out.print(CRLF);

            out.print(text);
        }

        private static void sendHtmlFile(PrintStream out, String fileName) throws Exception {
            out.print("HTTP/1.0 200 OK\r\n");
            out.print("Content-Type: text/html; charset=utf-8");
            out.print(CRLF);
            out.print(CRLF);

            try (FileInputStream fis = new FileInputStream(fileName)) {
                byte[] buffer = new byte[4096];
                int n;
                while ((n = fis.read(buffer)) > 0) {
                    out.write(buffer, 0, n);
                }
            }
        }

        private static void send404NotFound(PrintStream out) {
            out.print("HTTP/1.0 404 Not Found\r\n");
            out.print("Content-Type: text/html; charset=utf-8");
            out.print(CRLF);
            out.print(CRLF);

            out.print("<html><body><h2>404 Not Found</h2></body></html>");
        }

    }

}
