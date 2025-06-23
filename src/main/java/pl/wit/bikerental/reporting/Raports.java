package pl.wit.bikerental.reporting;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import pl.wit.bikerental.model.Bike;
import pl.wit.bikerental.model.Rental;

/**
*
* @author Krzysztof Mickiewicz
* @version 1.0
* @since 2025-06-15
*/

public class Raports {

    // --- Report: List of not rented bikes --- //
    public static List<Bike> unrentedBikes(List<Bike> bikes) {
        return bikes.stream()
            .filter(bike -> !bike.isRented())
            .peek(System.out::println)
            .collect(Collectors.toList());
    }

    // --- Report: List of overdue rentals (not yet returned and past deadline) --- //
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

    // --- Report: List of currently rented bikes (not returned, planned end exists) --- //
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