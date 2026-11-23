package baseNoStates.requests;

import org.json.JSONObject;

/*
 * Common interface implemented by all request types handled by WebServer
 * (RequestRefresh, RequestReader, RequestArea). Allows the server to process any
 * incoming request polymorphically and serialize its result into JSON.
 */
public interface Request {
  JSONObject answerToJson();

  String toString();

  void process();
}