package com.thoughtworks.parking;

import com.thoughtworks.exceptions.ParkableNotPresentException;
import com.thoughtworks.exceptions.ParkingSlotFullException;
import com.thoughtworks.exceptions.TryToParkParkableEntityTwiceException;

import java.util.HashSet;
import java.util.Set;

public class ParkingLot {

    private final int capacity;
    private final Set<Parkable> parkableEntity;

    public ParkingLot(int capacity) {
        this.capacity = capacity;
        this.parkableEntity = new HashSet<>();
    }

    public void park(Parkable car) throws ParkingSlotFullException, TryToParkParkableEntityTwiceException, NullPointerException{

        if (car == null) {
            throw new NullPointerException();
        }

        if (parkableEntity.contains(car)) {
            throw new TryToParkParkableEntityTwiceException();
        }

        if (isFull())
            throw new ParkingSlotFullException();

        this.parkableEntity.add(car);
    }

    private boolean isFull() {
        return parkableEntity.size() >= capacity;
    }

    public void unpark(Parkable car) throws ParkableNotPresentException {
        if (!isParked(car))
            throw new ParkableNotPresentException();

        parkableEntity.remove(car);
    }

    public boolean isParked(Parkable car) {

        return parkableEntity.contains(car);
    }
}
