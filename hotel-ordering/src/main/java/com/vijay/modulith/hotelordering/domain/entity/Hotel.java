package com.vijay.modulith.hotelordering.domain.entity;

import com.vijay.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "hotels")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Hotel extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 200)
    private String address;

    @Column(nullable = false, length = 100)
    private String city;

    @Column(nullable = false, length = 50)
    private String state;

    @Column(nullable = false, length = 20)
    private String country;

    @Column(nullable = false, length = 10)
    private String zipCode;

    @Column(length = 1000)
    private String description;

    @Column(length = 20)
    private String phoneNumber;

    @Column(length = 100)
    private String email;

    @Column(length = 200)
    private String website;

    @Column(precision = 3, scale = 2)
    private BigDecimal rating;

    @Column(length = 50)
    private String category; // 3-star, 4-star, 5-star, etc.

    @ElementCollection
    @CollectionTable(name = "hotel_amenities", joinColumns = @JoinColumn(name = "hotel_id"))
    @Column(name = "amenity")
    private List<String> amenities;

    @Column(length = 1000)
    private String policies;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RoomType> roomTypes;

    @Column(nullable = false)
    private Boolean active = true;
}