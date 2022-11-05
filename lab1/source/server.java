import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Date;  
 

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

    static class MyHandler implements HttpHandler {
    	
    	
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "Hello World from java!\n";
            Instant nowUtc = Instant.now();
	    ZoneId warsaw = ZoneId.of("Europe/Warsaw");
            ZonedDateTime nowWarsaw = ZonedDateTime.ofInstant(nowUtc, warsaw);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	    String formattedString = nowWarsaw.format(formatter);
            response = response.concat(formattedString + "\n");
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
	    System.out.println("Served hello world...");
        }
    }

}
