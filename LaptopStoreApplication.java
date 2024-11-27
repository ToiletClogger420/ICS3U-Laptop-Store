
//Derek Liang, Shawn Lu, Yucheng Chen


//laptop store application class

public class LaptopStoreApplication {
	
	//laptop array has 30 items, for each of the laptops
	public static Laptop[] laptopArray = new Laptop[30];
	
	//main method calls the other methods
	public static void main(String[]args) {
		
		//fill laptop array
		LaptopStoreFileInput.fillLaptops();
		
		//Run the GUI - Title frame
		new LaptopStoreTitleFrame();
		
	}
	
}
