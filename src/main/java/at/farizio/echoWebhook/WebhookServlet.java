package at.farizio.echoWebhook;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet("/echo")
public class WebhookServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.addHeader("Access-Control-Allow-Origin", "*");

        handleRequest(request, "POST");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.addHeader("Access-Control-Allow-Origin", "*");

        handleRequest(request, "GET");
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.addHeader("Access-Control-Allow-Origin", "*");

        handleRequest(request, "PUT");
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.addHeader("Access-Control-Allow-Origin", "*");

        handleRequest(request, "DELETE");
    }

    private void handleRequest(HttpServletRequest request, String method) throws ServletException, IOException {
        {
            Map<String, String[]> params = request.getParameterMap();

            String url = request.getRequestURL().toString();

            BufferedReader br = request.getReader();
            StringBuilder text = new StringBuilder();

            while (br.ready()) {
                text.append(br.readLine());
            }

            Request req = new Request();
            req.setUrl(url);
            req.setBody(new Gson().fromJson(text.toString(), JsonObject.class));
            req.setMethod(method);
            req.setParameters(params);

            JsonFile requests;

            String filename = "EchoWebhook.json";
            Path directory = Paths.get(System.getProperty("user.home"), "EchoWebhook");
            Path path = Paths.get(String.valueOf(directory), filename);

            try {
                requests = new Gson().fromJson(new FileReader(path.toFile()), JsonFile.class);
            } catch (Exception e) {
                requests = new JsonFile();
                requests.setRequests(new ArrayList<>());
            }

            requests.getRequests().add(req);

            System.out.println(path.toString());

            if (!Files.exists(path)) {
                Files.createDirectories(directory);
                Files.createFile(path);
            }

            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(path), StandardCharsets.UTF_8))) {
                writer.write(new Gson().toJson(requests));
            }
        }
    }
}
