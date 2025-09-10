package com.vijay.modulith.hotelordering.domain.entity;

import com.vijay.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Entity
@Table(name = "room_inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RoomInventory extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_type_id", nullable = false)
    private RoomType roomType;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private Integer availableRooms;

    @Column(nullable = false)
    private Integer reservedRooms;

    @Column(nullable = false)
    private Integer blockedRooms;

    @Column(nullable = false)
    private Boolean active = true;
}