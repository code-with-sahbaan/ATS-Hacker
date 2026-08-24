package code.with.sahbaan.neohire;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
@RequiredArgsConstructor
public class KeepAliveScheduler {

    @Value("${app.url}")
    private String appUrl;

    private final HttpClient httpClient = HttpClient.newHttpClient();

    @Scheduled(cron = "0 */10 * * * *")
    public void keepAlive() {

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(appUrl + "/health"))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            System.out.println(
                    "Keep-alive request: " + response.statusCode()
            );

        } catch (Exception e) {
            System.err.println("Keep-alive failed: " + e.getMessage());
        }
    }
}
