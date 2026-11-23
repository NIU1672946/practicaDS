package baseNoStates;

import baseNoStates.requests.Request;
import baseNoStates.requests.RequestArea;
import baseNoStates.requests.RequestReader;
import baseNoStates.requests.RequestRefresh;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.StringTokenizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Lightweight socket-based HTTP server acting as the facade of the Access Control Unit.
 * Listens on localhost:8081 for REST-like requests sent by the simulator or mobile app,
 * spawns a SocketThread per client connection, builds and processes the corresponding
 * Request object, and sends back a JSON HTTP response.
 *
 * Design Pattern: Singleton.
 * Only one WebServer can bind to port 8080 during execution. The blocking accept loop
 * lives in start() instead of the constructor so getInstance() returns cleanly.
 */
// Based on:
// https://www.ssaurel.com/blog/create-a-simple-http-web-server-in-java
// http://www.jcgonzalez.com/java-socket-mini-server-http-example
public final class WebServer {
  private static final int PORT = 8081;
  private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
  // Dedicated logger routed to activity.log to keep user activity separate from debug traces
  private static final Logger activityLogger =
      LoggerFactory.getLogger("baseNoStates.WebServer.activity");
  private static final DateTimeFormatter FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

  private static WebServer uniqueInstance = null;

  private WebServer() {
  }

  public static synchronized WebServer getInstance() {
    if (uniqueInstance == null) {
      uniqueInstance = new WebServer();
      logger.debug(Markers.MILESTONE_2, "Created unique WebServer instance");
    }
    return uniqueInstance;
  }

  public void start() {
    try (ServerSocket serverConnect = new ServerSocket(PORT)) {
      logger.info(Markers.MILESTONE_1, "Server started. Listening on port {} ...", PORT);
      while (true) {
        new SocketThread(serverConnect.accept());
      }
    } catch (IOException e) {
      logger.error(Markers.MILESTONE_1, "Server connection error: {}", e.getMessage());
    }
  }

  private class SocketThread extends Thread {
    // Inner class so each connection thread directly sees WebServer constants and loggers
    private final Socket inSocket;

    SocketThread(Socket inSocket) {
      this.inSocket = inSocket;
      this.start();
    }

    @Override
    public void run() {
      try (Socket socket = inSocket;
           BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
           PrintWriter out = new PrintWriter(socket.getOutputStream())) {
        String input = in.readLine();
        if (input == null) {
          logger.debug(Markers.MILESTONE_1, "Ignored empty connection request");
          return;
        }
        logger.debug(Markers.MILESTONE_1, "SocketThread received: {}", input);

        StringTokenizer parse = new StringTokenizer(input);
        String method = parse.nextToken().toUpperCase();
        if (!method.equals("GET")) {
          logger.warn(Markers.MILESTONE_1, "501 Not Implemented: {} method", method);
          return;
        }

        String resource = parse.nextToken();
        logger.debug(Markers.MILESTONE_1, "HTTP method={}, resource={}", method, resource);

        parse = new StringTokenizer(resource, "/[?]=&");
        StringBuilder tokenLog = new StringBuilder();
        int i = 0;
        String[] tokens = new String[20];
        while (parse.hasMoreTokens()) {
          tokens[i] = parse.nextToken();
          tokenLog.append(i).append(' ').append(tokens[i]).append('\n');
          i++;
        }
        logger.debug(Markers.MILESTONE_1, "Parsed tokens:\n{}", tokenLog);

        Request request = makeRequest(tokens);
        if (request != null) {
          String typeRequest = tokens[0];
          logger.debug(Markers.MILESTONE_1, "Created request {}: {}", typeRequest, request);
          request.process();
          logger.debug(Markers.MILESTONE_1, "Processed request {}: {}", typeRequest, request);

          // Record user request activity cleanly in activity.log
          activityLogger.info(Markers.MILESTONE_1, "{}", request);

          String answer = makeJsonAnswer(request);
          logger.debug(Markers.MILESTONE_1, "JSON answer:\n{}", answer);
          out.println(answer);
          out.flush();
        }
      } catch (Exception e) {
        logger.error(Markers.MILESTONE_1, "Error handling client socket: {}", e.toString());
      }
    }

    private Request makeRequest(String[] tokens) {
      StringBuilder tokenSummary = new StringBuilder("tokens: ");
      for (String token : tokens) {
        if (token != null) {
          tokenSummary.append(token).append(", ");
        }
      }
      logger.debug(Markers.MILESTONE_1, "{}", tokenSummary);

      Request request;
      switch (tokens[0]) {
        case "refresh":
          request = new RequestRefresh();
          break;
        case "reader":
          request = makeRequestReader(tokens);
          break;
        case "area":
          request = makeRequestArea(tokens);
          break;
        case "get_children":
          // TODO: implement in Milestone 3 for Flutter mobile app navigation
          assert false : "request get_children is not yet implemented";
          request = null;
          System.exit(-1);
          break;
        default:
          assert false : "unknown request " + tokens[0];
          request = null;
          System.exit(-1);
      }
      return request;
    }

    private RequestReader makeRequestReader(String[] tokens) {
      String credential = tokens[2];
      String action = tokens[4];
      LocalDateTime dateTime = LocalDateTime.parse(tokens[6], FORMATTER);
      String doorId = tokens[8];
      return new RequestReader(credential, action, dateTime, doorId);
    }

    private RequestArea makeRequestArea(String[] tokens) {
      String credential = tokens[2];
      String action = tokens[4];
      LocalDateTime dateTime = LocalDateTime.parse(tokens[6], FORMATTER);
      String areaId = tokens[8];
      return new RequestArea(credential, action, dateTime, areaId);
    }

    private String makeHeaderAnswer() {
      // Access-Control-Allow-Origin prevents browser CORS errors with the local simulator
      return "HTTP/1.0 200 OK\r\n"
          + "Content-type: application/json\r\n"
          + "Access-Control-Allow-Origin: *\r\n"
          + "\r\n";
    }

    private String makeJsonAnswer(Request request) {
      return makeHeaderAnswer() + request.answerToJson().toString();
    }
  }
}