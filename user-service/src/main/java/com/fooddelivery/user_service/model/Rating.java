package com.fooddelivery.user_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ratings")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;               // who gave the rating

    private Long restaurantId;       // optional if rating a restaurant
    private Long deliveryPersonId;   // optional if rating a delivery person

    private Integer score;           // 1-5
    private String comment;

    @Enumerated(EnumType.STRING)
    private RatingType type;         // RESTAURANT or DELIVERY
}
