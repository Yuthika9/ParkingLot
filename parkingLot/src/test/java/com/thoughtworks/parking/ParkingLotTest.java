package com.thoughtworks.parking;

import com.thoughtworks.exceptions.ParkableNotPresentException;
import com.thoughtworks.exceptions.ParkingSlotFullException;
import com.thoughtworks.exceptions.TryToParkParkableEntityTwiceException;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class ParkingLotTest {

    @Test
    public void shouldParkACarWhenAParkingSlotIsAvailable() throws ParkingSlotFullException, TryToParkParkableEntityTwiceException {
        ParkingLot parking = new ParkingLot(1);
        Parkable car = new Car();

        parking.park(car);
        boolean result = parking.isParked(car);

        assertTrue(result);
    }

    @Test
    public void shouldParkMultipleCarsWhenAParkingSlotsAreAvailable() throws ParkingSlotFullException, TryToParkParkableEntityTwiceException {
        ParkingLot parking = new ParkingLot(5);
        Parkable car1 = new Car();
        Parkable car2 = new Car();

        parking.park(car1);
        parking.park(car2);

        boolean isParkablePresent = parking.isParked(car2);

        assertTrue(isParkablePresent);
    }

    @Test
    public void shouldReturnParkingSlotFullExceptionWhenParkingSlotIsUnavailable() {
        ParkingLot parking = new ParkingLot(0);
        Parkable car = new Car();

        assertThrows(ParkingSlotFullException.class, () -> parking.park(car));
    }

    @Test
    public void shouldReturnTryToParkParkableEntityTwiceExceptionWhenParkingSameCarTwice() throws ParkingSlotFullException, TryToParkParkableEntityTwiceException {
        ParkingLot parking = new ParkingLot(2);
        Parkable car = new Car();

        parking.park(car);

        assertThrows(TryToParkParkableEntityTwiceException.class, () -> parking.park(car));
    }

    @Test
    public void shouldReturnNullPointerExceptionWhenParkableIsNull() {
        ParkingLot parking = new ParkingLot(2);

        assertThrows(NullPointerException.class, () -> parking.park(null));
    }

    @Test
    void shouldUnparkACarWhenItIsAlreadyParked() throws ParkingSlotFullException, TryToParkParkableEntityTwiceException, ParkableNotPresentException {
        ParkingLot parkingLot = new ParkingLot(2);

        Parkable car1 = new Car();
        Parkable car2 = new Car();
        parkingLot.park(car1);
        parkingLot.park(car2);
        parkingLot.unpark(car1);

        boolean isParked = parkingLot.isParked(car1);

        assertFalse(isParked);
    }

    @Test
    void shouldReturnParkableNotPresentExceptionWhenTryingToUnparkWhenThatCarIsNotPresent() throws ParkableNotPresentException{
        ParkingLot parkingLot = new ParkingLot(1);

        Parkable car1 = new Car();

        assertThrows(ParkableNotPresentException.class,()->parkingLot.unpark(car1));

    }
}
