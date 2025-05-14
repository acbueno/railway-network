package br.com.acbueno.itapira.service;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RouteApiExternanlService {

  private final RestTemplate restTemplate = new RestTemplate();

  @Value("${openrouteservice.api.key}")
  private String apiKey;

  public double findDistanceToKM(double[] origin, double[] destination) {
    String endpoint = "https://api.openrouteservice.org/v2/directions/driving-car";



    //@formatter:off
    String body = String.format(Locale.US, """
            {
              "coordinates": [
                [%f, %f],
                [%f, %f]
              ]
            }
        """, origin[1], origin[0], destination[1], destination[0]);
    //@formatter:on

    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", apiKey);
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("User-Agent", "railway-network");

    HttpEntity<String> request = new HttpEntity<>(body, headers);
    ResponseEntity<Map> response = restTemplate.postForEntity(endpoint, request, Map.class);

    if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
      Map route = (Map) ((List) response.getBody().get("routes")).get(0);
      Map resume = (Map) route.get("summary");
      return ((Number) resume.get("distance")).doubleValue() / 1000.0;
    }

    throw new RuntimeException(
        "Error find route: " + response.getStatusCode() + " - " + response.getBody());
  }

}
