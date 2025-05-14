package br.com.acbueno.itapira.service;

import java.net.URI;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class GeocodingService {

  @Autowired
  RestTemplate restTemplate;

  public double[] findCoord(String city, String country) {
    // String url = "https://nominatim.openstreetmap.org/search?q="
    // + URLEncoder.encode(city + ", Brasil", StandardCharsets.UTF_8) + "&format=json&limit=1";

    //@formatter:off
    URI uri = UriComponentsBuilder
        .fromHttpUrl("https://nominatim.openstreetmap.org/search")
        .queryParam("q", city + ", " + country)
        .queryParam("format", "json")
        .queryParam("limit", "1")
        .encode() // <- Aqui faz a codificação correta de espaços e acentos
        .build()
        .toUri();

    //@formatter:on
    HttpHeaders headers = new HttpHeaders();
    headers.set("User-Agent", "railway-network");
    HttpEntity<Void> entity = new HttpEntity<>(headers);

    ResponseEntity<List> response = restTemplate.exchange(uri, HttpMethod.GET, entity, List.class);

    if ((response.getStatusCode().is2xxSuccessful() && response.getBody() != null
        && !response.getBody().isEmpty())) {

      Map result = (Map) response.getBody().get(0);
      double lat = Double.parseDouble((String) result.get("lat"));
      double lon = Double.parseDouble((String) result.get("lon"));

      return new double[] {lat, lon};
    }

    throw new RuntimeException("Not found coord");

  }

}
