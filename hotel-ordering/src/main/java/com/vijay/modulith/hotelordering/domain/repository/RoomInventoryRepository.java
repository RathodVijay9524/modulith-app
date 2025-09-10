package com.vijay.modulith.hotelordering.domain.repository;

import com.vijay.modulith.hotelordering.domain.entity.RoomInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomInventoryRepository extends JpaRepository<RoomInventory, Long> {

    // Find inventory by room type and date
    Optional<RoomInventory> findByRoomTypeIdAndDate(Long roomTypeId, LocalDate date);

    // Find inventory by room type and date range
    List<RoomInventory> findByRoomTypeIdAndDateBetweenAndActiveTrue(Long roomTypeId, LocalDate startDate, LocalDate endDate);

    // Find inventory by date range
    List<RoomInventory> findByDateBetweenAndActiveTrue(LocalDate startDate, LocalDate endDate);

    // Find inventory with available rooms
    @Query("SELECT ri FROM RoomInventory ri WHERE ri.availableRooms > 0 AND ri.date = :date AND ri.active = true")
    List<RoomInventory> findAvailableInventoryByDate(@Param("date") LocalDate date);

    // Find inventory for specific hotel and date range
    @Query("SELECT ri FROM RoomInventory ri WHERE ri.roomType.hotel.id = :hotelId " +
            "AND ri.date BETWEEN :startDate AND :endDate AND ri.active = true")
    List<RoomInventory> findByHotelAndDateRange(@Param("hotelId") Long hotelId,
                                                @Param("startDate") LocalDate startDate,
                                                @Param("endDate") LocalDate endDate);

    // Check availability for room type and date range
    @Query("SELECT MIN(ri.availableRooms) FROM RoomInventory ri " +
            "WHERE ri.roomType.id = :roomTypeId " +
            "AND ri.date BETWEEN :checkIn AND :checkOut " +
            "AND ri.active = true")
    Integer findMinimumAvailableRooms(@Param("roomTypeId") Long roomTypeId,
                                      @Param("checkIn") LocalDate checkIn,
                                      @Param("checkOut") LocalDate checkOut);

    // Update available rooms (for booking)
    @Modifying
    @Query("UPDATE RoomInventory ri SET ri.availableRooms = ri.availableRooms - :rooms, " +
            "ri.reservedRooms = ri.reservedRooms + :rooms " +
            "WHERE ri.roomType.id = :roomTypeId AND ri.date BETWEEN :startDate AND :endDate")
    int reserveRooms(@Param("roomTypeId") Long roomTypeId,
                     @Param("startDate") LocalDate startDate,
                     @Param("endDate") LocalDate endDate,
                     @Param("rooms") Integer rooms);

    // Release reserved rooms (for cancellation)
    @Modifying
    @Query("UPDATE RoomInventory ri SET ri.availableRooms = ri.availableRooms + :rooms, " +
            "ri.reservedRooms = ri.reservedRooms - :rooms " +
            "WHERE ri.roomType.id = :roomTypeId AND ri.date BETWEEN :startDate AND :endDate")
    int releaseRooms(@Param("roomTypeId") Long roomTypeId,
                     @Param("startDate") LocalDate startDate,
                     @Param("endDate") LocalDate endDate,
                     @Param("rooms") Integer rooms);

    // Find low inventory (below threshold)
    @Query("SELECT ri FROM RoomInventory ri WHERE ri.availableRooms <= :threshold " +
            "AND ri.date >= :fromDate AND ri.active = true")
    List<RoomInventory> findLowInventory(@Param("threshold") Integer threshold, @Param("fromDate") LocalDate fromDate);

    // Delete old inventory records
    @Modifying
    @Query("DELETE FROM RoomInventory ri WHERE ri.date < :cutoffDate")
    int deleteOldInventory(@Param("cutoffDate") LocalDate cutoffDate);

    // Find inventory that needs to be created for future dates
    @Query("SELECT DISTINCT rt.id FROM RoomType rt " +
            "WHERE rt.active = true " +
            "AND NOT EXISTS (SELECT 1 FROM RoomInventory ri WHERE ri.roomType.id = rt.id AND ri.date = :date)")
    List<Long> findRoomTypesNeedingInventoryForDate(@Param("date") LocalDate date);
}
