package server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Date;  
import java.util.Map; 
import java.util.HashMap;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.json.simple.JSONObject;
import java.time.format.DateTimeFormatter;  
import java.time.*;

public class Server {

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
            if(queryMap.get("str") != null) {
                String str = queryMap.get("str");
                if(str != null) {
                    System.out.println("Working");
                    JSONObject obj = new JSONObject();
                    obj.put("name", "foo");
                    obj.put("num","test ");
                    response = obj.toJSONString();
                }
            }
            else {
                System.out.println("Missing str parameter!");
                response = "Missing str parameter!";
                t.sendResponseHeaders(400, response.length());
            }
            
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
	   
        }
    }

}

