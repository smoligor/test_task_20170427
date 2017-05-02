package com.opinta.entity;

import java.math.BigDecimal;
import java.util.List;


import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Enumerated;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Cascade;
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
    @OneToMany(fetch = FetchType.EAGER)
    @LazyCollection(LazyCollectionOption.FALSE)
    @Fetch(value = FetchMode.SUBSELECT)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "parcel_id")
    private List<Parcel> parcels;
    private BigDecimal price;
    private BigDecimal postPay;
    private String description;

    public Shipment(Client sender, Client recipient, DeliveryType deliveryType, List<Parcel> parcels,
                    BigDecimal postPay) {
        this.sender = sender;
        this.recipient = recipient;
        this.deliveryType = deliveryType;
        this.parcels = parcels;
        this.postPay = postPay;
    }
    public Shipment(Client sender, Client recipient, DeliveryType deliveryType, List<Parcel> parcels,
                    BigDecimal postPay, BigDecimal price) {
        this.sender = sender;
        this.recipient = recipient;
        this.deliveryType = deliveryType;
        this.parcels = parcels;
        this.postPay = postPay;
        this.price = price;
    }

}
