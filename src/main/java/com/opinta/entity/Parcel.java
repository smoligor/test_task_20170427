package com.opinta.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Parcel {
    @Id
    @GeneratedValue
    private long id;
    private float weight;
    private float length;
    private float width;
    private float height;
    private BigDecimal declaredPrice;
    private BigDecimal price;
    @OneToMany(fetch = FetchType.EAGER)
    @LazyCollection(LazyCollectionOption.FALSE)
    @Fetch(value = FetchMode.SUBSELECT)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "parcel_item_id")
    private List<ParcelItem> parcelItems;

    public Parcel(float weight, float length,
                  BigDecimal declaredPrice, List<ParcelItem> parcelItems) {
        this.weight = weight;
        this.length = length;
        this.declaredPrice = declaredPrice;
        this.price = new BigDecimal("0");
        this.parcelItems = parcelItems;

    }
}

