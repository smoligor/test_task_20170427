package com.opinta.dto;


import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ParcelDto {
    private long id;
    private float weight;
    private float length;
    private float width;
    private float height;
    private BigDecimal declaredPrice;
    private List<ParcelItemDto> parcelItems;
    private BigDecimal price;
}
