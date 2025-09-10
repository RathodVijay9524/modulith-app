package com.vijay.modulith.hotelordering.domain.repository;

import com.vijay.modulith.hotelordering.domain.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    // Find active hotels
    List<Hotel> findByActiveTrue();

    // Find hotels by city
    List<Hotel> findByCityAndActiveTrue(String city);

    // Find hotels by city and state
    List<Hotel> findByCityAndStateAndActiveTrue(String city, String state);

    // Find hotels by rating range
    @Query("SELECT h FROM Hotel h WHERE h.rating >= :minRating AND h.rating <= :maxRating AND h.active = true")
    List<Hotel> findByRatingBetween(@Param("minRating") BigDecimal minRating, @Param("maxRating") BigDecimal maxRating);

    // Find hotels by category
    List<Hotel> findByCategoryAndActiveTrue(String category);

    // Find hotels by name (case insensitive)
    @Query("SELECT h FROM Hotel h WHERE LOWER(h.name) LIKE LOWER(CONCAT('%', :name, '%')) AND h.active = true")
    List<Hotel> findByNameContainingIgnoreCase(@Param("name") String name);

    // Find hotels with specific amenity
    @Query("SELECT h FROM Hotel h JOIN h.amenities a WHERE a = :amenity AND h.active = true")
    List<Hotel> findByAmenity(@Param("amenity") String amenity);

    // Find hotels in a specific area (city, state, country)
    @Query("SELECT h FROM Hotel h WHERE h.city = :city AND h.state = :state AND h.country = :country AND h.active = true")
    List<Hotel> findByLocation(@Param("city") String city, @Param("state") String state, @Param("country") String country);

    // Find top rated hotels
    @Query("SELECT h FROM Hotel h WHERE h.active = true ORDER BY h.rating DESC")
    List<Hotel> findTopRatedHotels();

    // Check if hotel exists by name and city
    boolean existsByNameAndCityAndActiveTrue(String name, String city);

    // Find hotel by email
    Optional<Hotel> findByEmailAndActiveTrue(String email);
}
