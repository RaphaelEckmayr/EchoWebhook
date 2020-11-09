package at.farizio.echoWebhook;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

@WebServlet("/requests")
public class OutputServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String filename = "EchoWebhook.json";
        Path directory = Paths.get(System.getProperty("user.home"), "EchoWebhook");
        Path path = Paths.get(String.valueOf(directory), filename);

        JsonFile requests;
        try {
            requests = new Gson().fromJson(new FileReader(path.toFile()), JsonFile.class);
        }catch(Exception e) {
            requests = new JsonFile();
            requests.setRequests(new ArrayList<>());
        }

        response.getOutputStream().write(new Gson().toJson(requests).getBytes());
        response.getOutputStream().flush();
    }
}
