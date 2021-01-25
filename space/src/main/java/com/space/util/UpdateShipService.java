package com.space.util;

import com.space.exeptions.InvalidData;
import com.space.model.Ship;
import com.space.model.ShipType;

import java.util.Date;
import java.util.Map;

public class UpdateShipService {
    private UpdateShipService(){}

    public static void updateShip(Ship ship, Map<String, String> request){
        String name;
        String planet;
        ShipType shipType;
        Long prodDate;
        Boolean isUsed;
        Double speed;
        Integer crewSize;

        if (request.containsKey("name") && (name = request.get("name")) != null) {
            int size = name.length();
            if (size == 0 || size > 50) throw new InvalidData("Bad name");
            ship.setName(name);
        }

        if (request.containsKey("planet") && (planet = request.get("planet")) != null) {
            int size = planet.length();
            if (size == 0 || size > 50) throw new InvalidData("Bad planet");
            ship.setPlanet(planet);
        }

        String type;
        if (request.containsKey("shipType") && (type = request.get("shipType")) != null) {
            shipType = ShipType.valueOf(type);
            ship.setShipType(shipType);
        }

        String date;
        if (request.containsKey("prodDate") && (date = request.get("prodDate")) != null) {
            prodDate = Long.parseLong(date);
            if (prodDate < 26192235600000L || prodDate > 33134648400000L) throw new InvalidData("Bad prodDate");
            ship.setProdDate(new Date(prodDate));
        }

        String used;
        if (request.containsKey("isUsed") && (used = request.get("isUsed")) != null){
            isUsed = Boolean.valueOf(used);
            ship.setUsed(isUsed);
        } else ship.setUsed(false);

        String strSpeed;
        if (request.containsKey("speed") && (strSpeed = request.get("speed")) != null){
            speed = Double.parseDouble(strSpeed);
            if (speed < 0.01 || speed > 0.99) throw new InvalidData("Bad speed");
            ship.setSpeed(speed);
        }

        String size;
        if (request.containsKey("crewSize") && (size = request.get("crewSize")) != null){
        crewSize = Integer.parseInt(size);
        if (crewSize < 1 || crewSize > 9999) throw new InvalidData("Bad crewSize");
        ship.setCrewSize(crewSize);
    }
    }
}
