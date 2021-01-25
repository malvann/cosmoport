package com.space.service;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.ShipRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class ShipService {
    private final ShipRepository repository;

    public ShipService(ShipRepository repository) {
        this.repository = repository;
    }

    public Ship getById(Long id) {
        return repository.findById(id).get();
    }

    public void delete(Ship ship) {
        repository.delete(ship);
    }

    public Ship save(Ship ship) {
        ship.setRating(80.*ship.getSpeed()*1./(3019-ship.getProdDate().getYear()+1));
        return repository.save(ship);
    }

    public Integer getShipsCount(Map<String, String> requestParams) {
        requestParams = validateParams(requestParams);
        return repository.getShipsCount(
                requestParams.get("name"),
                requestParams.get("planet"),
                requestParams.get("shipType") != null ? ShipType.valueOf(requestParams.get("shipType")) : null,
                new Date(Long.parseLong(requestParams.get("after"))),
                new Date(Long.parseLong(requestParams.get("before"))),
                requestParams.get("isUsed") != null ? Boolean.valueOf(requestParams.get("isUsed")) : null,
                Double.valueOf(requestParams.get("minSpeed")),
                Double.valueOf(requestParams.get("maxSpeed")),
                Integer.parseInt(requestParams.get("minCrewSize")),
                Integer.parseInt(requestParams.get("maxCrewSize")),
                Double.valueOf(requestParams.get("minRating")),
                Double.valueOf(requestParams.get("maxRating"))
        );
    }

    public Page<Ship> findByCriteria(Map<String, String> requestParams) {
        requestParams = validateParams(requestParams);
        requestParams.putIfAbsent("pageNumber", "0");
        requestParams.putIfAbsent("pageSiz", "3");
        requestParams.putIfAbsent("order", "id");

        Pageable paging = PageRequest.of(
                Integer.parseInt(requestParams.get("pageNumber")),
                Integer.parseInt(requestParams.get("pageSize")),
                Sort.by(requestParams.get("order")));
        return repository.findByCriteria(
                requestParams.get("name"),
                requestParams.get("planet"),
                ShipType.valueOf(requestParams.get("shipType")),
                new Date(Long.parseLong(requestParams.get("after"))),
                new Date(Long.parseLong(requestParams.get("before"))),
                Boolean.valueOf(requestParams.get("isUsed")),
                Double.valueOf(requestParams.get("minSpeed")),
                Double.valueOf(requestParams.get("maxSpeed")),
                Integer.parseInt(requestParams.get("minCrewSize")),
                Integer.parseInt(requestParams.get("maxCrewSize")),
                Double.valueOf(requestParams.get("minRating")),
                Double.valueOf(requestParams.get("maxRating")),
                paging);
    }

    private Map<String, String> validateParams(Map<String, String> requestParams){
        Map<String, String> res = new HashMap<>(requestParams);
        res.putIfAbsent("name", null);
        res.putIfAbsent("planet", null);
        res.putIfAbsent("shipType", null);
        res.putIfAbsent("after", "0");
        res.putIfAbsent("before", "33103198800000");
        res.putIfAbsent("isUsed", null);
        res.putIfAbsent("minSpeed", "0.01");
        res.putIfAbsent("maxSpeed", "0.99");
        res.putIfAbsent("minCrewSize", "0");
        res.putIfAbsent("maxCrewSize", "9999");
        res.putIfAbsent("minRating", "0");
        res.putIfAbsent("maxRating", "79.20");
        return res;
    }
}
