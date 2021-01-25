package com.space.repository;

import com.space.model.Ship;
import com.space.model.ShipType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface ShipRepository extends JpaRepository<Ship, Long> {

  @Query(
      "select count(s) from Ship s where "
          + "(:name IS NOT NULL AND lower(s.name) like concat('%',lower(:name),'%') OR :name IS NULL)"
          + " and (:planet IS NOT NULL AND lower(s.planet) like concat('%',lower(:planet),'%') OR :planet IS NULL)"
          + " and (:shipType IS NOT NULL and s.shipType = :shipType OR :shipType IS NULL)"
          + " and s.prodDate between :after AND :before"
          + " and (:isUsed IS NOT NULL and s.isUsed = :isUsed OR :isUsed IS NULL)"
          + " and s.speed between :minSpeed AND :maxSpeed"
          + " and s.crewSize between :minCrewSize AND :maxCrewSize"
          + " and s.rating between :minRating AND :maxRating")
  Integer getShipsCount(
      @Param("name") String name,
      @Param("planet") String planet,
      @Param("shipType") ShipType shipType,
      @Param("after") Date after,
      @Param("before") Date before,
      @Param("isUsed") Boolean isUsed,
      @Param("minSpeed") Double minSpeed,
      @Param("maxSpeed") Double maxSpeed,
      @Param("minCrewSize") Integer minCrewSize,
      @Param("maxCrewSize") Integer maxCrewSize,
      @Param("minRating") Double minRating,
      @Param("maxRating") Double maxRating);

    @Query("select s from Ship s where"
            + "(:name IS NOT NULL AND lower(s.name) like concat('%',lower(:name),'%') OR :name IS NULL)"
            + " and (:planet IS NOT NULL AND lower(s.planet) like concat('%',lower(:planet),'%') OR :planet IS NULL)"
            + " and (:shipType IS NOT NULL and s.shipType = :shipType OR :shipType IS NULL)"
            + " and s.prodDate between :after AND :before"
            + " and (:isUsed IS NOT NULL and s.isUsed = :isUsed OR :isUsed IS NULL)"
            + " and s.speed between :minSpeed AND :maxSpeed"
            + " and s.crewSize between :minCrewSize AND :maxCrewSize"
            + " and s.rating between :minRating AND :maxRating")
    Page<Ship> findByCriteria(
            @Param("name") String name,
            @Param("planet") String planet,
            @Param("shipType") ShipType shipType,
            @Param("after") Date after,
            @Param("before") Date before,
            @Param("isUsed") Boolean isUsed,
            @Param("minSpeed") Double minSpeed,
            @Param("maxSpeed") Double maxSpeed,
            @Param("minCrewSize") Integer minCrewSize,
            @Param("maxCrewSize") Integer maxCrewSize,
            @Param("minRating") Double minRating,
            @Param("maxRating") Double maxRating,
            Pageable paging);
}
