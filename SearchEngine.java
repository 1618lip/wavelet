import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> lst = new ArrayList<>();

    public String handleRequest(URI url) {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    lst.add(parameters[1]);
                }
            }
            else if (url.getPath().contains("/search")) {
                String[] param = url.getQuery().split("=");
                if (param[0].equals("s")) {
                    String toReturn = "";
                    String keyword = param[1];
                    for (int i=0; i<lst.size();i++) {
                        if (lst.get(i).contains(keyword)) {
                            toReturn += lst.get(i);
                        }
                    }
                    return toReturn;
                }
            }
            return "404 Not Found!";
        }
    }

public class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
