package com.opinta.service;

import com.opinta.dao.TariffGridDao;
import com.opinta.entity.Address;
import com.opinta.entity.DeliveryType;
import com.opinta.entity.Parcel;
import com.opinta.entity.Shipment;
import com.opinta.entity.TariffGrid;
import com.opinta.entity.W2wVariation;
import com.opinta.mapper.ParcelMapper;
import com.opinta.util.AddressUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ParcelServiceImpl implements ParcelService {
    private final TariffGridDao tariffGridDao;
    private final ParcelMapper parcelMapper;

    @Autowired
    public ParcelServiceImpl(TariffGridDao tariffGridDao, ParcelMapper parcelMapper) {
        this.tariffGridDao = tariffGridDao;
        this.parcelMapper = parcelMapper;
    }

    public BigDecimal getTotalPrice(List<Parcel> parcels) {
        return BigDecimal.valueOf(parcels.stream().
                mapToDouble(parcel -> Float.valueOf(parcel.getPrice().toString())).sum());
    }

    @Override
    public List<Parcel> setPrices(List<Parcel> parcels, Shipment shipment) {
        for (Parcel parcel : parcels) {
            log.info("Calculating price for parcel {}", parcel);

            Address senderAddress = shipment.getSender().getAddress();
            Address recipientAddress = shipment.getRecipient().getAddress();
            W2wVariation w2wVariation = W2wVariation.COUNTRY;
            if (AddressUtil.isSameTown(senderAddress, recipientAddress)) {
                w2wVariation = W2wVariation.TOWN;
            } else if (AddressUtil.isSameRegion(senderAddress, recipientAddress)) {
                w2wVariation = W2wVariation.REGION;
            }

            TariffGrid tariffGrid = tariffGridDao.getLast(w2wVariation);
            if (parcel.getWeight() < tariffGrid.getWeight() &&
                    parcel.getLength() < tariffGrid.getLength()) {
                tariffGrid = tariffGridDao.getByDimension(parcel.getWeight(), parcel.getLength(), w2wVariation);
            }
            log.info("TariffGrid for weight {} per length {} and type {}: {}",
                    parcel.getWeight(), parcel.getLength(), w2wVariation, tariffGrid);
            if (tariffGrid == null) {
                return new ArrayList<>();
            }
            float price = tariffGrid.getPrice() + getSurcharges(shipment);
            parcel.setPrice(BigDecimal.valueOf(price));
        }
        return parcels;
    }

    private float getSurcharges(Shipment shipment) {
        float surcharges = 0;
        if (shipment.getDeliveryType().equals(DeliveryType.D2W) ||
                shipment.getDeliveryType().equals(DeliveryType.W2D)) {
            surcharges += 9;
        } else if (shipment.getDeliveryType().equals(DeliveryType.D2D)) {
            surcharges += 12;
        }
        return surcharges;
    }
}
