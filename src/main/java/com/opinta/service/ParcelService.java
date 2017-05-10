package com.opinta.service;

import com.opinta.entity.Parcel;
import com.opinta.entity.Shipment;
import java.math.BigDecimal;
import java.util.List;

public interface ParcelService {

    BigDecimal getTotalPrice(List<Parcel> parcels);

    List<Parcel> setPrices(List<Parcel> parcels, Shipment shipment);
}
