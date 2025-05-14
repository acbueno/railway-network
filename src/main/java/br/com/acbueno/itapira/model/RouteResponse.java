package br.com.acbueno.itapira.model;

import java.util.List;

public record RouteResponse(List<String> path, double distanceKM, long timeMinutes,
    String typeCargo, boolean cacheHit, long time) {

}
