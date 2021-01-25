package com.space.controller;

import com.space.exeptions.InvalidData;
import com.space.exeptions.NotFoundException;
import com.space.model.Ship;
import com.space.service.ShipService;
import com.space.util.*;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/rest/ships")
public class ShipController {
    private final ShipService service;

    public ShipController(ShipService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<Ship>> getShipsList(@RequestParam Map<String, String> requestParams) {
        Page<Ship> pageTuts = service.findByCriteria(requestParams);
        return ResponseEntity.ok(pageTuts);
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getShipsCount(@RequestParam Map<String, String> requestParams){
        return ResponseEntity.ok().body(service.getShipsCount(requestParams));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ship> getShip(@PathVariable Long id){
        if (id <= 0) throw new InvalidData("Invalid id: "+id);
        Ship ship;
        try{
            ship = service.getById(id);
        } catch (NoSuchElementException e){
            throw new NotFoundException(e.getMessage());
        }
        return ResponseEntity.ok().body(ship);
    }

    @PostMapping
    public ResponseEntity<Ship> createShip(@RequestParam Map<String, String> request){
        if (request == null || !CreateShipService.isValid(request)) throw new InvalidData("Invalid request");
        Ship ship = CreateShipService.createShip(request);
        service.save(ship);
        return ResponseEntity.ok(ship);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Ship> updateShip(@RequestParam Map<String, String> request, @PathVariable Long id){
        if (id <= 0) throw new InvalidData("Invalid id: "+id);
        Ship ship;
        try{
            ship = service.getById(id);
            if (request != null) UpdateShipService.updateShip(ship, request);
            ship = service.save(ship);
        } catch (NoSuchElementException e){
            throw new NotFoundException(e.getMessage());
        }
        return ResponseEntity.ok(ship);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus( HttpStatus.OK)
    public void deleteShip(@PathVariable Long id){
        if (id <= 0) throw new InvalidData("Invalid id: "+id);
        Ship ship;
        try{
            ship =service.getById(id);
            service.delete(ship);
        } catch (NoSuchElementException e){
            throw new NotFoundException("No ship with id = "+ id);
        }
    }
}
