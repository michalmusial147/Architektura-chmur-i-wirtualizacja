import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Date;  
import java.util.Map; 
import java.util.HashMap;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.time.format.DateTimeFormatter;  
import java.time.*;

public class Test {

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(4080), 0);
        server.createContext("/", new MyHandler());
        server.setExecutor(null); 
        server.start();
    }

    public static Map<String, String> queryToMap(String query) {
        if(query == null) {
            return null;
        }
        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            if (entry.length > 1) {
                result.put(entry[0], entry[1]);
            }else{
                result.put(entry[0], "");
            }
        }
        return result;
    }

    static class MyHandler implements HttpHandler {
    	
    	
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "";
            Map<String, String> queryMap = queryToMap(t.getRequestURI().getQuery());
            if(queryMap == null) {
                System.out.println("Querry param was not provided!");
                response = "Hello World from java!\n";
                t.sendResponseHeaders(200, response.length());
            }
            else if(queryMap.get("cmd").equals("time")) {
                Instant nowUtc = Instant.now();
                ZoneId warsaw = ZoneId.of("Europe/Warsaw");
                ZonedDateTime nowWarsaw = ZonedDateTime.ofInstant(nowUtc, warsaw);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                String formattedString = nowWarsaw.format(formatter);
                response = formattedString;
                t.sendResponseHeaders(200, response.length());
            }
            else if(queryMap.get("cmd").equals("rev")) {
                response = "rev";

                t.sendResponseHeaders(200, response.length());
            }

            
            
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
	   
        }
    }

}

