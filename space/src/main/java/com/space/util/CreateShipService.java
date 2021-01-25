package com.space.util;

import com.space.model.Ship;
import com.space.model.ShipType;

import java.util.Date;
import java.util.Map;

public class CreateShipService {
    private CreateShipService(){}

    public static boolean isValid(Map<String, String> request){

        if (!request.containsKey("name")
            || !request.containsKey("planet")
            || !request.containsKey("speed")
            || !request.containsKey("crewSize")
            || !request.containsKey("prodDate")
            || !request.containsKey("shipType")
        ) return false;

        String name;
        String planet;
        String speed;
        String crewSize;
        String prodDate;

        if (       (name = request.get("name")) == null
                || (planet = request.get("planet")) == null
                || request.get("shipType") == null
                || (prodDate = request.get("prodDate")) == null
                || (speed = request.get("speed")) == null
                || (crewSize = request.get("crewSize")) == null
        ) return false;

        int size = name.length();
        if (size == 0 || size > 50) return false;
        size = planet.length();
        if (size == 0 || size > 50) return false;
        double speedD = Double.parseDouble(speed);
        if (speedD < 0.01 || speedD > 0.99) return false;
        int crewSizeI = Integer.parseInt(crewSize);
        if (crewSizeI < 1 || crewSizeI > 9999) return false;
        long dateMillis = Long.parseLong(prodDate);
        return dateMillis >= 26192235600000L && dateMillis <= 33134648400000L;
    }

    public static Ship createShip(Map<String, String> request){
        String name = request.get("name");
        String planet = request.get("planet");
        Double speed = Double.parseDouble(request.get("speed"));
        Integer crewSize = Integer.parseInt(request.get("crewSize"));
        Date prodDate = new Date(Long.parseLong(request.get("prodDate")));
        ShipType shipType = ShipType.valueOf(request.get("shipType"));
        Boolean isUsed = request.get("isUsed") != null && Boolean.parseBoolean(request.get("isUsed"));

        Ship ship = new Ship();
        ship.setName(name);
        ship.setPlanet(planet);
        ship.setSpeed(speed);
        ship.setCrewSize(crewSize);
        ship.setProdDate(prodDate);
        ship.setShipType(shipType);
        ship.setUsed(isUsed);

        return ship;
    }
}
