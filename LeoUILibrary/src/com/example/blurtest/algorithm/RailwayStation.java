package com.example.blurtest.algorithm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * RailwayStation represents a node on the route map.
 * It contains tow maps, one holds previous stations and distances to current station,
 * the other one holds next stations and distances to these stations.
 * <p/>
 * Created by Leo Jiang on 2016/7/29.
 */
public class RailwayStation {
    private Map<RailwayStation, Integer> preStations;
    private Map<RailwayStation, Integer> nextStations;
    private String stationName;

    public RailwayStation(String name) {
        stationName = name;
        preStations = new HashMap<>();
        nextStations = new HashMap<>();

    }

    public String getStationName() {
        return stationName;
    }

    public void addPreStation(RailwayStation preStation, int distance) {
        preStations.put(preStation, distance);
    }

    public void addNextStation(RailwayStation nextStation, int distance) {
        nextStations.put(nextStation, distance);
    }

    public Map<RailwayStation, Integer> getPreStations() {
        return preStations;
    }

    public Map<RailwayStation, Integer> getNextStations() {
        return nextStations;
    }

    public int getDistanceToNextStation(RailwayStation station) {
        if (nextStations.containsKey(station)) {
            return nextStations.get(station);
        }

        return 0;
    }
}
