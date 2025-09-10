package com.vijay.modulith.hotelordering.domain.entity;

import com.vijay.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Order extends BaseEntity {

    @Column(nullable = false, unique = true, length = 50)
    private String orderNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    @Column(nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal taxAmount;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(length = 50)
    private String paymentMethod;

    @Column(length = 100)
    private String paymentTransactionId;

    private LocalDateTime paymentCompletedAt;

    @Column(length = 500)
    private String notes;

    public enum OrderStatus {
        PENDING, PAID, FAILED, REFUNDED, CANCELLED
    }
}