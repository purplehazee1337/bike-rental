package pl.wit.bikerental.reporting;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import pl.wit.bikerental.model.Bike;
import pl.wit.bikerental.model.Rental;

/**
 * Utility class providing reporting methods for bikes and rentals.
 * <p>
 * This class contains static methods to generate various reports about the status of bikes
 * and rentals, such as listing unrented bikes, overdue rentals, and currently rented bikes.
 * </p>
 * 
 * @author Krzysztof Mickiewicz
 */

public class Raports {

	/**
     * Returns a list of bikes that are currently not rented.
     */
    public static List<Bike> unrentedBikes(List<Bike> bikes) {
        return bikes.stream()
            .filter(bike -> !bike.isRented())
            .peek(System.out::println)
            .collect(Collectors.toList());
    }
    
    /**
     * Returns a list of bikes for rentals that are overdue (not yet returned and past the planned end date).
     */
    public static List<Bike> overtimeRentedBikes(List<Rental> rentals) {
        return rentals.stream()
            .filter(rent ->
                rent.getActualReturnDate() == null &&
                rent.getPlannedEnd() != null &&
                rent.getPlannedEnd().isBefore(LocalDateTime.now())
            )
            .peek(System.out::println)
            .map(Rental::getBike)
            .collect(Collectors.toList());
    }
    
    /**
     * Returns a list of bikes that are currently rented (not yet returned, planned end exists).
     */
    public static List<Bike> currentlyRentedBikes(List<Rental> rentals) {
        return rentals.stream()
            .filter(rent ->
                rent.getActualReturnDate() == null &&
                rent.getPlannedEnd() != null
            )
            .peek(System.out::println)
            .map(Rental::getBike)
            .collect(Collectors.toList());
    }
}