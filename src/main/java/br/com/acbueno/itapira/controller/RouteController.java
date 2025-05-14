package br.com.acbueno.itapira.controller;

import java.util.List;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import br.com.acbueno.itapira.model.RouteRequest;
import br.com.acbueno.itapira.model.RouteResponse;
import br.com.acbueno.itapira.service.RouteService;

@RestController
@RequestMapping("/api/route")
@CrossOrigin
public class RouteController {


  private final RouteService service;

  private final CacheManager cacheManager;

  public RouteController(RouteService service, CacheManager cacheManager) {
    this.service = service;
    this.cacheManager = cacheManager;
  }

  @GetMapping
  public ResponseEntity<RouteResponse> calcute(@RequestParam String country,
      @RequestParam String origin, @RequestParam String destination, @RequestParam String typeCargo)
      throws InterruptedException {

    long start = System.currentTimeMillis();
    RouteRequest req = new RouteRequest(country, origin, destination, typeCargo);
    double distance = service.getDistanceKM(req);
    long time = service.calcTimeDistance(distance);
    String key = origin + "-" + destination;
    boolean cacheHit = cacheManager.getCache("distanceCity").get(key) != null;
    long end = System.currentTimeMillis();
    long totalTime = end - start;

    return ResponseEntity.ok(new RouteResponse(List.of(origin, destination), distance, time,
        req.typeCargo(), cacheHit, totalTime));
  }

  @PostMapping("/clean-cache")
  public ResponseEntity<Void> cleanCache() {
    service.cleanCache();
    return ResponseEntity.ok().build();
  }

}
