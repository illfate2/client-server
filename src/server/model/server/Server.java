package server.model.server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import server.MyLogger;
import server.model.SearchInfo;
import server.model.StudentInfo;
import server.model.server.service.Service;

import java.io.*;
import java.lang.reflect.Type;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.List;

public class Server {
    private HttpServer server;
    private Service service;
    private MyLogger logger;

    public Server(MyLogger logger) throws IOException {
        server = HttpServer.
                create(new InetSocketAddress("localhost", 8001), 0);
        service = new Service();
        this.logger = logger;
    }

    public void start() {
        server.createContext("/list", new Handler());
        server.start();
        logger.logMessage("starting server");
    }

    public void stop() {
        server.stop(1);
    }

    private class Handler implements HttpHandler {

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            switch (httpExchange.getRequestMethod()) {
                case "GET":
                    handleGetRequest(httpExchange);
                    break;
                case "POST":
                    try {
                        handlePostRequest(httpExchange);
                    } catch (Exception e) {
                        logger.logError(e.getMessage());
                    }
                    break;
                case "DELETE":
                    handleDeleteRequest(httpExchange);
                    break;
                default:
            }
        }

        private void handleGetRequest(HttpExchange httpExchange) throws IOException {
            String[] url = httpExchange.
                    getRequestURI()
                    .toString()
                    .split("\\?");
            String[] urlValues = url[0].split("/");
            if (urlValues.length == 4) {
                Integer listId = Integer.parseInt(urlValues[2]);
                Gson gson = new Gson();
                String json = gson.toJson(service.getSize(listId));
                httpExchange.sendResponseHeaders(200, json.length());
                OutputStream outputStream = httpExchange.getResponseBody();
                outputStream.write(json.getBytes());
                outputStream.flush();
                outputStream.close();
                return;
            }
            Integer listId = Integer.parseInt(urlValues[2]);
            SearchInfo searchInfo = parseInfo(httpExchange.getRequestURI().getQuery());
            List<StudentInfo> studentInfos = service.getStudentInfoList(listId, searchInfo);

            Gson gson = new Gson();
            String json = gson.toJson(studentInfos);
            httpExchange.sendResponseHeaders(200, json.length());
            OutputStream outputStream = httpExchange.getResponseBody();
            outputStream.write(gson.toJson(studentInfos).getBytes());
            outputStream.flush();
            outputStream.close();
        }

        private void handlePostRequest(HttpExchange httpExchange) throws IOException {
            String[] url = httpExchange.
                    getRequestURI()
                    .toString()
                    .split("/");
            if (url.length == 2) {
                byte[] body = httpExchange.getRequestBody().readAllBytes();
                String jsonBody = new String(body);

                if (jsonBody.isEmpty()) {
                    Integer id = service.createEmptyList();
                    Gson gson = new Gson();
                    String json = gson.toJson(id);
                    httpExchange.sendResponseHeaders(200, json.length());
                    OutputStream outputStream = httpExchange.getResponseBody();
                    outputStream.write(gson.toJson(id).getBytes());
                    outputStream.flush();
                    outputStream.close();
                    httpExchange.sendResponseHeaders(200, 0);
                } else {
                    Gson gson = new Gson();
                    StudentInfo[] infos = gson.fromJson(jsonBody, (Type) StudentInfo[].class);
                    Integer id = service.setInfos(Arrays.asList(infos));

                    String json = gson.toJson(id);
                    httpExchange.sendResponseHeaders(200, json.length());
                    OutputStream outputStream = httpExchange.getResponseBody();
                    outputStream.write(gson.toJson(id).getBytes());
                    outputStream.flush();
                    outputStream.close();
                    httpExchange.sendResponseHeaders(200, 0);
                }
                return;
            } else if (url.length == 4) {
                Integer listId = Integer.parseInt(url[2]);

                byte[] body = httpExchange.getRequestBody().readAllBytes();
                String jsonBody = new String(body);
                Gson gson = new Gson();
                StudentInfo info = gson.fromJson(jsonBody, StudentInfo.class);
                info.reloadSummary();
                service.insertInfo(listId, info);
                httpExchange.sendResponseHeaders(200, 0);
                httpExchange.getResponseBody().close();
                return;
            }
            httpExchange.sendResponseHeaders(400, 0);

        }

        private void handleDeleteRequest(HttpExchange httpExchange) throws IOException {
            String[] url = httpExchange.
                    getRequestURI()
                    .toString()
                    .split("/");
            String queryParams = httpExchange.getRequestURI().getQuery();
            if (url.length == 3 && queryParams == null) {
                Integer listId = Integer.parseInt(url[2]);
                service.removeInfos(listId);
                httpExchange.sendResponseHeaders(200, 0);
                OutputStream outputStream = httpExchange.getResponseBody();
                outputStream.close();
            }
            Integer listId = Integer.parseInt(url[2]);
            SearchInfo searchInfo = parseInfo(httpExchange.getRequestURI().getQuery());

            Integer count = service.removeStudentInfo(listId, searchInfo);

            Gson gson = new Gson();
            String json = gson.toJson(count);
            httpExchange.sendResponseHeaders(200, json.length());
            OutputStream outputStream = httpExchange.getResponseBody();
            outputStream.write(gson.toJson(count).getBytes());
            outputStream.flush();
            outputStream.close();
        }
    }

    private SearchInfo parseInfo(String urlParams) {
        String[] params = urlParams.split("&");
        SearchInfo info = new SearchInfo();
        for (String p :
                params) {
            String[] parsed = p.split("=");
            switch (parsed[0]) {
                case "name":
                    info.name = parsed[1];
                    break;
                case "group":
                    info.group = Integer.parseInt(parsed[1]);
                    break;
                case "typeOfSkip":
                    info.typeOfSkip = parsed[1];
                    break;
                case "highAmountOfSkip":
                    info.highAmountOfSkip = Integer.parseInt(parsed[1]);
                    break;
                case "lowAmountOfSkip":
                    info.lowAmountOfSkip = Integer.parseInt(parsed[1]);
                    break;
                case "limit":
                    info.limit = Integer.parseInt(parsed[1]);
                    break;
                case "offset":
                    info.offset = Integer.parseInt(parsed[1]);
                    break;
            }
        }
        return info;
    }
}
