
//file input class
//the "database" crv file is read and put into the laptop objects

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

//class begins
public class LaptopStoreFileInput {

	public static void fillLaptops() {

		// try catch statement to ensure no errors
		try {

			// scan for file
			Scanner inputFile = new Scanner(new File("database.csv"));

			// input file delimiter
			inputFile.useDelimiter(",|\r\n");

			// to not show the first line
			inputFile.nextLine();

			// for loop to go through the array
			for (int index = 0; index < LaptopStoreApplication.laptopArray.length; index++) {

				String brand = inputFile.next();
				int customerRating = inputFile.nextInt();

				String model = inputFile.next();

				String type = inputFile.next();

				double laptopCost = inputFile.nextDouble();

				String cpuBrand = inputFile.next();
				String cpuModel = inputFile.next();
				int cores = inputFile.nextInt();

				double speedGHZ = inputFile.nextDouble();
				int speedRating = inputFile.nextInt();

				int ram = inputFile.nextInt();
				int ssd = inputFile.nextInt();

				int storageRating = inputFile.nextInt();

				String gpuBrand = inputFile.next();

				String gpuModel = inputFile.next();

				int usbPorts = inputFile.nextInt();
				String otherPorts = inputFile.next();

				String os = inputFile.next();

				double displaySize = inputFile.nextDouble();
				String displayResolution = inputFile.next();

				boolean touchscreen = inputFile.nextBoolean();

				double weight = inputFile.nextDouble();

				String link = inputFile.next();

				//the specific laptop is put into its object
				LaptopStoreApplication.laptopArray[index] = new Laptop(model, cpuModel, gpuModel, brand, cpuBrand,
						gpuBrand, laptopCost, type, cores, speedGHZ, speedRating, storageRating, customerRating, ram,
						ssd, usbPorts, otherPorts, os, displaySize, displayResolution, touchscreen, weight, link);

				System.out.println(LaptopStoreApplication.laptopArray[index]);
			}

			// close input, it actually matters
			inputFile.close();

			// catch statement stops errors
		} catch (FileNotFoundException e) {

			System.out.println("File Error");

		}
		
	}
}
