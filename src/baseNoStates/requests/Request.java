package baseNoStates.requests;

import org.json.JSONObject;

/*
 * Common interface implemented by all request types received by the WebServer
 * (RequestRefresh, RequestReader, RequestArea). Allows the server to process
 * any request polymorphically and convert its result into a JSON response.
 */
public interface Request {
  JSONObject answerToJson();

  String toString();

  void process();
}
