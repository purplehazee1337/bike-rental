package pl.wit.bikerental;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import pl.wit.bikerental.model.Bike;
import pl.wit.bikerental.storage.Db;

public class App {
	public static void main(String[] args) throws IOException {
		List<Bike> bikes = Arrays.asList(
				new Bike("Yamaha", 15), 
				new Bike("Honda", 20), 
				new Bike("Suzuki", 31));

		String fileName = "bikes.dat";

		Db.saveBikes(bikes, fileName);

		List<Bike> loadedBikes = Db.readBikes(fileName);

		for (Bike b : loadedBikes) {
			System.out.println(b.getName() + " - " + b.getPricePerH() + " - " + b.getPricePerH());
		}
	}
}
