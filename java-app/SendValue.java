import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SendValue {

    public static void main(String[] args) {
        String value = loadConfigValue("/etc/config/value.txt");
        if (value == null) {
            System.err.println("Config value not found!");
            return;
        }

        HttpClient client = HttpClient.newHttpClient();

        String jsonData = "{\"value\": " + value + "}";

        String goApiUrl = "http://go-app-service.ns-B.svc.cluster.local:8080/api/process";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(goApiUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonData))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Response Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String loadConfigValue(String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath))).trim();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
