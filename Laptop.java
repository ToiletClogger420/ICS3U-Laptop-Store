import javax.swing.JLabel;

public class Laptop extends JLabel{
    private String model;
    private String cpuModel;
    private String gpuModel;

    private String brand;
    private String cpuBrand;
    private String gpuBrand;

    private double laptopCost;

    private String type;

    private int cores;
    private double speedGHZ;

    private int speedRating;
    private int storageRating;
    private int customerRating;

    private int ram;
    private int ssd;

    private int usbPorts;
    private String otherPorts;

    private String os;

    private double displaySize;
    private String displayResolution;

    private boolean touchscreen;

    private double weight;
    
    private String link;

	public Laptop(String model, String cpuModel, String gpuModel, String brand, String cpuBrand, String gpuBrand,
			double laptopCost, String type, int cores, double speedGHZ, int speedRating, int storageRating,
			int customerRating, int ram, int ssd, int usbPorts, String otherPorts, String os, double displaySize,
			String displayResolution, boolean touchscreen, double weight, String link) {
		super();
		this.model = model;
		this.cpuModel = cpuModel;
		this.gpuModel = gpuModel;
		this.brand = brand;
		this.cpuBrand = cpuBrand;
		this.gpuBrand = gpuBrand;
		this.laptopCost = laptopCost;
		this.type = type;
		this.cores = cores;
		this.speedGHZ = speedGHZ;
		this.speedRating = speedRating;
		this.storageRating = storageRating;
		this.customerRating = customerRating;
		this.ram = ram;
		this.ssd = ssd;
		this.usbPorts = usbPorts;
		this.otherPorts = otherPorts;
		this.os = os;
		this.displaySize = displaySize;
		this.displayResolution = displayResolution;
		this.touchscreen = touchscreen;
		this.weight = weight;
		this.link = link;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getCpuModel() {
		return cpuModel;
	}

	public void setCpuModel(String cpuModel) {
		this.cpuModel = cpuModel;
	}

	public String getGpuModel() {
		return gpuModel;
	}

	public void setGpuModel(String gpuModel) {
		this.gpuModel = gpuModel;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getCpuBrand() {
		return cpuBrand;
	}

	public void setCpuBrand(String cpuBrand) {
		this.cpuBrand = cpuBrand;
	}

	public String getGpuBrand() {
		return gpuBrand;
	}

	public void setGpuBrand(String gpuBrand) {
		this.gpuBrand = gpuBrand;
	}

	public double getLaptopCost() {
		return laptopCost;
	}

	public void setLaptopCost(double laptopCost) {
		this.laptopCost = laptopCost;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getCores() {
		return cores;
	}

	public void setCores(int cores) {
		this.cores = cores;
	}

	public double getSpeedGHZ() {
		return speedGHZ;
	}

	public void setSpeedGHZ(double speedGHZ) {
		this.speedGHZ = speedGHZ;
	}

	public int getSpeedRating() {
		return speedRating;
	}

	public void setSpeedRating(int speedRating) {
		this.speedRating = speedRating;
	}

	public int getStorageRating() {
		return storageRating;
	}

	public void setStorageRating(int storageRating) {
		this.storageRating = storageRating;
	}

	public int getCustomerRating() {
		return customerRating;
	}

	public void setCustomerRating(int customerRating) {
		this.customerRating = customerRating;
	}

	public int getRam() {
		return ram;
	}

	public void setRam(int ram) {
		this.ram = ram;
	}

	public int getSsd() {
		return ssd;
	}

	public void setSsd(int ssd) {
		this.ssd = ssd;
	}

	public int getUsbPorts() {
		return usbPorts;
	}

	public void setUsbPorts(int usbPorts) {
		this.usbPorts = usbPorts;
	}

	public String getOtherPorts() {
		return otherPorts;
	}

	public void setOtherPorts(String otherPorts) {
		this.otherPorts = otherPorts;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public double getDisplaySize() {
		return displaySize;
	}

	public void setDisplaySize(double displaySize) {
		this.displaySize = displaySize;
	}

	public String getDisplayResolution() {
		return displayResolution;
	}

	public void setDisplayResolution(String displayResolution) {
		this.displayResolution = displayResolution;
	}

	public boolean isTouchscreen() {
		return touchscreen;
	}

	public void setTouchscreen(boolean touchscreen) {
		this.touchscreen = touchscreen;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@Override
	public String toString() {
		return "Laptop [model=" + model + ", cpuModel=" + cpuModel + ", gpuModel=" + gpuModel + ", brand="
				+ brand + ", cpuBrand=" + cpuBrand + ", gpuBrand=" + gpuBrand + ", laptopCost=" + laptopCost + ", type="
				+ type + ", cores=" + cores + ", speedGHZ=" + speedGHZ + ", speedRating=" + speedRating
				+ ", storageRating=" + storageRating + ", customerRating=" + customerRating + ", ram=" + ram + ", ssd="
				+ ssd + ", usbPorts=" + usbPorts + ", otherPorts=" + otherPorts + ", os=" + os + ", displaySize="
				+ displaySize + ", displayResolution=" + displayResolution + ", touchscreen=" + touchscreen
				+ ", weight=" + weight + ", link=" + link + "]";
	}

}
