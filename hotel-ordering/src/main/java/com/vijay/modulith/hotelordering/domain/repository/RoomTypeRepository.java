package com.vijay.modulith.hotelordering.domain.repository;

import com.vijay.modulith.hotelordering.domain.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {

    // Find room types by hotel
    List<RoomType> findByHotelIdAndActiveTrue(Long hotelId);

    // Find room types by hotel and name
    List<RoomType> findByHotelIdAndNameContainingIgnoreCaseAndActiveTrue(Long hotelId, String name);

    // Find room types by price range
    @Query("SELECT rt FROM RoomType rt WHERE rt.basePrice >= :minPrice AND rt.basePrice <= :maxPrice AND rt.active = true")
    List<RoomType> findByPriceRange(@Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice);

    // Find room types by occupancy
    List<RoomType> findByMaxOccupancyGreaterThanEqualAndActiveTrue(Integer occupancy);

    // Find room types with specific amenity
    @Query("SELECT rt FROM RoomType rt JOIN rt.amenities a WHERE a = :amenity AND rt.active = true")
    List<RoomType> findByAmenity(@Param("amenity") String amenity);

    // Find available room types for date range
    @Query("SELECT DISTINCT rt FROM RoomType rt " +
            "LEFT JOIN rt.inventory ri " +
            "WHERE rt.hotel.id = :hotelId " +
            "AND rt.active = true " +
            "AND (ri.date BETWEEN :checkIn AND :checkOut) " +
            "GROUP BY rt.id " +
            "HAVING MIN(ri.availableRooms) >= :roomsNeeded")
    List<RoomType> findAvailableRoomTypes(@Param("hotelId") Long hotelId,
                                          @Param("checkIn") LocalDate checkIn,
                                          @Param("checkOut") LocalDate checkOut,
                                          @Param("roomsNeeded") Integer roomsNeeded);

    // Find room types by hotel and size range
    @Query("SELECT rt FROM RoomType rt WHERE rt.hotel.id = :hotelId " +
            "AND rt.roomSize >= :minSize AND rt.roomSize <= :maxSize AND rt.active = true")
    List<RoomType> findByHotelAndSizeRange(@Param("hotelId") Long hotelId,
                                           @Param("minSize") Double minSize,
                                           @Param("maxSize") Double maxSize);

    // Count active room types by hotel
    Long countByHotelIdAndActiveTrue(Long hotelId);

    // Find cheapest room types by hotel
    @Query("SELECT rt FROM RoomType rt WHERE rt.hotel.id = :hotelId AND rt.active = true ORDER BY rt.basePrice ASC")
    List<RoomType> findCheapestByHotel(@Param("hotelId") Long hotelId);

    // Find room types with minimum rooms available
    @Query("SELECT rt FROM RoomType rt WHERE rt.totalRooms >= :minRooms AND rt.active = true")
    List<RoomType> findByMinimumRooms(@Param("minRooms") Integer minRooms);
}