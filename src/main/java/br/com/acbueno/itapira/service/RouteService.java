package br.com.acbueno.itapira.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import br.com.acbueno.itapira.model.RouteRequest;

@Service
public class RouteService {

  @Autowired
  private GeocodingService geocodingService;

  @Autowired
  private RouteApiExternanlService api;

  @Cacheable(value = "distanceCity", key = "#req.origin() + '-' + #req.destination()")
  public double getDistanceKM(RouteRequest req) {
    double[] originCoord = geocodingService.findCoord(req.origin(), req.country());
    double[] destinationCoord = geocodingService.findCoord(req.destination(), req.country());

    return api.findDistanceToKM(originCoord, destinationCoord);
  }


  public long calcTimeDistance(double distanceKM) {
    double mediaSpeed = 80.0;

    return Math.round((distanceKM / mediaSpeed) * 60);
  }

  @CacheEvict(value = "distanceCity")
  public void cleanCache() {}

}
