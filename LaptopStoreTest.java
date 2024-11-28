
// LaptopStoreTest.java
import java.util.*;

public class LaptopStoreTest {
    public static void main(String[] args) {
        // Simulate filter selections
        Map<String, Object> testFilters = new HashMap<>();
        
        // Brand selection
        List<String> brands = new ArrayList<>();
        brands.add("HP");
        brands.add("Lenovo");
        testFilters.put("Brand", brands);
        
        // Price range
        testFilters.put("minPrice", 500.0);
        testFilters.put("maxPrice", 1500.0);
        
        // Type selection
        List<String> types = new ArrayList<>();
        types.add("Gaming");
        testFilters.put("Type (ex. Student; Professional; Gaming; etc.)", types);
        
        // CPU preference
        List<String> cpus = new ArrayList<>();
        cpus.add("Intel");
        testFilters.put("CPU - Brand", cpus);
        
        // Create and show results frame
        javax.swing.SwingUtilities.invokeLater(() -> {
            new LaptopStoreResultFrame(testFilters);
        });
    }
}