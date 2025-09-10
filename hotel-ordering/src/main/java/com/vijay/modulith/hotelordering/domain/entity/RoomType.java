package com.vijay.modulith.hotelordering.domain.entity;

import com.vijay.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "room_types")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RoomType extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @Column(nullable = false, length = 100)
    private String name; // Single, Double, Suite, etc.

    @Column(length = 500)
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal basePrice;

    @Column(nullable = false)
    private Integer maxOccupancy;

    @Column(nullable = false)
    private Integer totalRooms;

    @Column(nullable = false)
    private Double roomSize; // in square meters

    @ElementCollection
    @CollectionTable(name = "room_amenities", joinColumns = @JoinColumn(name = "room_type_id"))
    @Column(name = "amenity")
    private List<String> amenities;



    @OneToMany(mappedBy = "roomType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RoomInventory> inventory;

    @Column(nullable = false)
    private Boolean active = true;
}