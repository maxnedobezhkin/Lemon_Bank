package com.dlb.lemon_bank.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "history")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class HistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private UserEntity admin;
    @Column(name = "date_")
    private LocalDate date;
    @Column(name = "type_")
    private String type;
    @Column(name = "comment")
    private String comment;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrdersEntity order;
    @Column(name = "currency")
    private String currency;
    @Column(name = "value_")
    private Integer value;


}
