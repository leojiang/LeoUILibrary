package com.example.blurtest.algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * RailwayMap represents the whole map.
 * <p/>
 * Created by Leo Jiang on 2016/7/29.
 */
public class RailwayMap {
    private static final String TAG = RailwayMap.class.getSimpleName();
    private int lengthOfStationName = 1;

    private Map<String, RailwayStation> routeMap = new HashMap<>();

    public RailwayMap() {

    }

    public RailwayMap(int stationNameLength) {
        lengthOfStationName = stationNameLength;
    }

    public void setStationNameLength(int stationNameLength) {
        lengthOfStationName = stationNameLength;
    }

    private void addStation(RailwayStation station) {
        String stationName = station.getStationName();

        // if (TextUtils.isEmpty(stationName)) {
        // throw new RuntimeException("can not add a station without a valid
        // name");
        // }
        //
        // if (routeMap.containsKey(stationName)) {
        // Log.w(TAG, "a station with the same name exist already");
        // }

        routeMap.put(station.getStationName(), station);
    }

    public RailwayStation getStation(String name) {
        return routeMap.get(name);
    }

    public void buildMap(String graph) {
        // TODO: need to test if graph is valid.
        for (String route : graph.split(",")) {
            route = route.trim();
            String startStationName = route.substring(0, lengthOfStationName);
            String endStationName = route.substring(lengthOfStationName, lengthOfStationName * 2);
            int distance = Integer.parseInt(route.substring(lengthOfStationName * 2, route.length()));

            RailwayStation startStation;
            RailwayStation endStation;

            // get start station
            if (routeMap.containsKey(startStationName)) {
                startStation = routeMap.get(startStationName);
            } else {
                startStation = new RailwayStation(startStationName);
                addStation(startStation);
            }

            // get end station
            if (routeMap.containsKey(endStationName)) {
                endStation = routeMap.get(endStationName);

            } else {
                endStation = new RailwayStation(endStationName);
                addStation(endStation);
            }

            startStation.addNextStation(endStation, distance);
            endStation.addPreStation(startStation, distance);
        }
    }

    /**
     * Gets distance of given route.
     *
     * @param route given route.
     * @return distance
     */
    public int getDistanceOfGivenRoute(String route) {
        String[] stationNames = route.split("-");

        int totalDistance = 0;

        for (int i = 0; i < stationNames.length - 1; i++) {
            int distance = routeMap.get(stationNames[i]).getDistanceToNextStation(routeMap.get(stationNames[i + 1]));
            if (distance <= 0) {
                System.out.println("NO SUCH ROUTE");
                return 0;
            }
            totalDistance += distance;
        }

        System.out.println(totalDistance);

        return totalDistance;
    }

    /**
     * Gets trip numbers within given stop numbers.
     * startStationName and endStationName can be the same one.
     *
     * @param startStationName start station
     * @param endStationName   end station
     * @param maxStopNum       max stop number
     * @return number of trips
     */
    public int getTripNumWithinGivenStops(String startStationName, String endStationName, int maxStopNum) {

        return 0;
    }

    /**
     * Gets number of trips within given distance.
     * startStationName and endStationName can be the same one.
     *
     * @param startStationName
     * @param endStationName
     * @param maxDistance
     * @return
     */
    public int getTripNumWithinGivenDistance(String startStationName, String endStationName, int maxDistance) {
        return 0;
    }

    /**
     * Gets number of trips of given stop numbers. startStationName and
     * endStationName can be the same one.
     *
     * @param startStationName start station
     * @param endStationName   end station
     * @param stopNum          stop number
     * @return number of trips
     */
    public int getTripsWithGivenStops(String startStationName, String endStationName, int stopNum) {

        return 0;
    }

    /**
     * Gets the shortest route length between two statons. startStationName and
     * endStationName can be the same one.
     *
     * @param startStationName start station
     * @param endStationName   end station
     * @return shortest route length.
     */
    public int getShortestRouteLength(String startStationName, String endStationName) {
        return 0;
    }

}
