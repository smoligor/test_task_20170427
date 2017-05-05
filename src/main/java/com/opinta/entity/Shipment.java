package com.opinta.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Data
@NoArgsConstructor
public class Shipment {
    @Id
    @GeneratedValue
    private long id;
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Client sender;
    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private Client recipient;
    @OneToOne
    private BarcodeInnerNumber barcode;
    @Enumerated(EnumType.STRING)
    private DeliveryType deliveryType;
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @Fetch(value = FetchMode.SUBSELECT)
    //@JoinColumn(name = "parcel_id")
    private List<Parcel> parcels;
    private BigDecimal price;
    private BigDecimal postPay;
    private String description;

    public Shipment(Client sender, Client recipient, BarcodeInnerNumber barcode, DeliveryType deliveryType,
                    List<Parcel> parcels, BigDecimal price, BigDecimal postPay, String description) {
        this.sender = sender;
        this.recipient = recipient;
        this.barcode = barcode;
        this.deliveryType = deliveryType;
        this.parcels = parcels;
        this.price = price;
        this.postPay = postPay;
        this.description = description;
    }
}
