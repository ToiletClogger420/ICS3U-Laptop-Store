
//Results frame
//displays the results for the user's survey results
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.*;
import javax.swing.border.*;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * Class to store a laptop's index and its similarity score for ranking purposes.
 * Implements Comparable to allow sorting of recommendations.
 */
class LaptopScore implements Comparable<LaptopScore> {
    int index;     // Index of the laptop in the database
    double score;  // Similarity score computed for this laptop

    public LaptopScore(int index, double score) {
        this.index = index;
        this.score = score;
    }

    @Override
    public int compareTo(LaptopScore other) {
        return Double.compare(other.score, this.score); // Sort in descending order (highest score first)
    }
}

/**
 * Main frame for displaying laptop recommendations based on user preferences.
 * Uses a sophisticated recommendation algorithm based on weighted cosine similarity.
 */
@SuppressWarnings("unchecked")  // Suppress unchecked casting warnings for filter values
public class LaptopStoreResultsFrame extends JFrame implements ActionListener {
    private static int[] recommendedLaptop;
    private Map<String, Object> filtervalue;
    private List<String[]> laptops;
    
    private static final Color titleColour = new Color(33, 37, 41);
    private static final Color buttonColour = new Color(13, 110, 253);
    private static final Color hoverColour = new Color(0, 85, 227);
    
    Font mainFont = new Font("Segoe UI", Font.BOLD, 20);
    Font largeFont = new Font("Segoe UI", Font.BOLD, 35);
    
    JButton inventory = new JButton("Inventory");
    JButton survey = new JButton("Back to Survey");
    JButton toTitle = new JButton("Back to title");
    
    JLabel recommended1 = new JLabel("Recommended Laptop");
    JLabel name1 = new JLabel("");
    JLabel price1 = new JLabel("");
    JButton specs1 = new JButton("Specifications");
    JButton toCart1 = new JButton("Add to cart");
    JPanel panel1 = new JPanel();
    
    JLabel recommended2 = new JLabel("Runner Up");
    JLabel name2 = new JLabel("");
    JLabel price2 = new JLabel("");
    JButton specs2 = new JButton("Specifications");
    JButton toCart2 = new JButton("Add to cart");
    JPanel panel2 = new JPanel();
    
    JLabel recommended3 = new JLabel("Third Option");
    JLabel name3 = new JLabel("");
    JLabel price3 = new JLabel("");
    JButton specs3 = new JButton("Specifications");
    JButton toCart3 = new JButton("Add to cart");
    JPanel panel3 = new JPanel();
    
    public LaptopStoreResultsFrame() {
        setSize(1920,1080);
        setLayout(null);
        setTitle("DCS Laptops");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        ImageIcon img = new ImageIcon("images/logo_square.png");
        setIconImage(img.getImage());
        
        this.laptops = readCSV("data/database.csv");
        
        inventory.setBounds(20, 20, 300, 80);
        setupButton(inventory);
        
        survey.setBounds(340, 20, 300, 80);
        setupButton(survey);
        
        toTitle.setBounds(660, 20, 300, 80);
        setupButton(toTitle);
        
        recommended1.setBounds(125, 180, 700, 100);
        recommended1.setFont(largeFont);
        recommended1.setForeground(titleColour);
        name1.setBounds(140, 610, 350, 100);
        price1.setBounds(140, 660, 350, 100);
        specs1.setBounds(135, 750, 360, 70);
        toCart1.setBounds(135, 850, 360, 70);
        setupLabels(name1, price1);
        setupButton(specs1);
        setupButton(toCart1);
        add(recommended1);
        panel1.setBounds(125, 265, 380, 380);
        panel1.setBackground(new Color(213, 255, 115));
        add(panel1);
        
        recommended2.setBounds(820, 180, 700, 100);
        recommended2.setFont(largeFont);
        recommended2.setForeground(titleColour);
        name2.setBounds(740, 610, 350, 100);
        price2.setBounds(740, 660, 350, 100);
        specs2.setBounds(735, 750, 350, 80);
        toCart2.setBounds(735, 850, 350, 80);
        setupLabels(name2, price2);
        setupButton(specs2);
        setupButton(toCart2);
        add(recommended2);    
        panel2.setBounds(725, 265, 380, 380);
        panel2.setBackground(new Color(252, 255, 60));
        add(panel2);
        
        recommended3.setBounds(1400, 180, 700, 100);
        recommended3.setFont(largeFont);
        recommended3.setForeground(titleColour);
        name3.setBounds(1340, 610, 350, 100);
        price3.setBounds(1340, 660, 350, 100);
        specs3.setBounds(1335, 750, 350, 80);
        toCart3.setBounds(1335, 850, 350, 80);
        setupLabels(name3, price3);
        setupButton(specs3);
        setupButton(toCart3);
        add(recommended3);    
        panel3.setBounds(1325, 265, 380, 380);
        panel3.setBackground(new Color(252, 255, 60));
        add(panel3);
        
        setVisible(true);
    }
    
    /**
     * Sets user preferences and triggers recommendation calculation
     * @param values Map containing user preferences from survey
     */
    public void setFilterValues(Map<String, Object> values) {
        this.filtervalue = values;
        this.recommendedLaptop = findRecommended();
        updateDisplay();
    }

    private void updateDisplay() {
        if (recommendedLaptop != null && laptops != null) {
        	
            // LoggerUtils.logln("Top 3 recommended laptops indices: " + 
            //                  recommendedLaptop[0] + ", " + 
            //                  recommendedLaptop[1] + ", " + 
            //                  recommendedLaptop[2]); // Using SLF4J logger.
            System.out.println("Top 3 recommended laptops indices: " + 
                             recommendedLaptop[0] + ", " + 
                             recommendedLaptop[1] + ", " + 
                             recommendedLaptop[2]); 
            
            name1.setText(laptops.get(recommendedLaptop[0] + 1)[2]);
            price1.setText("$" + laptops.get(recommendedLaptop[0] + 1)[4]);
            updatePanel(panel1, "images/laptops/laptop" + (recommendedLaptop[0]) + ".jpg");

            name2.setText(laptops.get(recommendedLaptop[1] + 1)[2]);
            price2.setText("$" + laptops.get(recommendedLaptop[1] + 1)[4]);
            updatePanel(panel2, "images/laptops/laptop" + (recommendedLaptop[1]) + ".jpg");

            name3.setText(laptops.get(recommendedLaptop[2] + 1)[2]);
            price3.setText("$" + laptops.get(recommendedLaptop[2] + 1)[4]);
            updatePanel(panel3, "images/laptops/laptop" + (recommendedLaptop[2]) + ".jpg");
        }
    }

    private void updatePanel(JPanel panel, String imagePath) {
        panel.removeAll();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(255, 255, 255));
        
        ImageIcon originalIcon = new ImageIcon(imagePath);
        Image originalImage = originalIcon.getImage();
        
        int panelSize = Math.min(panel.getWidth(), panel.getHeight());
        
        int originalWidth = originalImage.getWidth(null);
        int originalHeight = originalImage.getHeight(null);
        double scale = Math.min((double)panelSize/originalWidth, (double)panelSize/originalHeight);
        
        int scaledWidth = (int)(originalWidth * scale);
        int scaledHeight = (int)(originalHeight * scale);
        
        Image scaledImage = originalImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        
        panel.add(imageLabel, BorderLayout.CENTER);
        panel.revalidate();
        panel.repaint();
    }

    /**
     * Core recommendation algorithm implementation.
     * Uses weighted cosine similarity to find the best matching laptops.
     * @return int array containing indices of top 3 recommended laptops
     */
    private int[] findRecommended() {
        if (filtervalue == null) {
            return null;
        }

        if (laptops == null || laptops.isEmpty()) {
            return null;
        }

        List<LaptopScore> scores = new ArrayList<>();
        
        for (int i = 1; i < laptops.size(); i++) {
            double score = calculateCosineSimilarity(laptops.get(i), filtervalue);
            scores.add(new LaptopScore(i - 1, score));
        }

        Collections.sort(scores);
        int[] topThree = new int[3];
        for (int i = 0; i < Math.min(3, scores.size()); i++) {
            topThree[i] = scores.get(i).index;
        }

        return topThree;
    }

    /**
     * Calculates weighted cosine similarity between user preferences and a laptop.
     * Implements feature-specific normalization and weighting.
     * @param laptop Array containing laptop specifications
     * @param filters Map containing user preferences
     * @return Similarity score between 0 and 1
     */
    private double calculateCosineSimilarity(String[] laptop, Map<String, Object> filters) {
        if (!checkHardConstraints(laptop, filters)) {
            return 0.0;
        }

        Map<String, Double> weights = new HashMap<>();
        weights.put("Price", 0.20);
        weights.put("Brand", 0.05);
        weights.put("Type", 0.10);
        weights.put("CPU Brand", 0.05);
        weights.put("GPU brand", 0.05);
        weights.put("RAM", 0.10);
        weights.put("Storage", 0.10);
        weights.put("Rating", 0.10);
        weights.put("Speed Rating", 0.05);
        weights.put("USB ports", 0.05);
        weights.put("Other ports", 0.05);
        weights.put("Display", 0.05);
        weights.put("Weight", 0.05);

        double[] userVector = new double[13];
        double[] laptopVector = new double[13];
        double[] weightVector = new double[13];
        int vectorIndex = 0;

        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            switch (key) {
                case "minPrice":
                case "maxPrice":
                    double minPrice = Double.parseDouble(filters.get("minPrice").toString());
                    double maxPrice = Double.parseDouble(filters.get("maxPrice").toString());
                    double price = Double.parseDouble(laptop[4].trim());
                    userVector[vectorIndex] = normalizeValue((minPrice + maxPrice) / 2, 500, 5000);
                    laptopVector[vectorIndex] = normalizeValue(price, 500, 5000);
                    weightVector[vectorIndex] = weights.get("Price");
                    vectorIndex++;
                    break;

                case "Brand":
                    List<String> selectedBrands = (List<String>) value;
                    userVector[vectorIndex] = selectedBrands.contains(laptop[0].trim()) ? 1.0 : 0.0;
                    laptopVector[vectorIndex] = 1.0;
                    weightVector[vectorIndex] = weights.get("Brand");
                    vectorIndex++;
                    break;

                case "Type":
                    List<String> selectedTypes = (List<String>) value;
                    userVector[vectorIndex] = selectedTypes.contains(laptop[3].trim()) ? 1.0 : 0.0;
                    laptopVector[vectorIndex] = 1.0;
                    weightVector[vectorIndex] = weights.get("Type");
                    vectorIndex++;
                    break;

                case "CPU Brand":
                    List<String> selectedCPUBrands = (List<String>) value;
                    userVector[vectorIndex] = selectedCPUBrands.contains(laptop[5].trim()) ? 1.0 : 0.0;
                    laptopVector[vectorIndex] = 1.0;
                    weightVector[vectorIndex] = weights.get("CPU Brand");
                    vectorIndex++;
                    break;

                case "GPU brand":
                    List<String> selectedGPUBrands = (List<String>) value;
                    userVector[vectorIndex] = selectedGPUBrands.contains(laptop[13].trim()) ? 1.0 : 0.0;
                    laptopVector[vectorIndex] = 1.0;
                    weightVector[vectorIndex] = weights.get("GPU brand");
                    vectorIndex++;
                    break;

                case "RAM":
                    int requiredRAM = Integer.parseInt(value.toString());
                    int actualRAM = Integer.parseInt(laptop[10].trim());
                    userVector[vectorIndex] = normalizeValue(requiredRAM, 4, 64);
                    laptopVector[vectorIndex] = normalizeValue(actualRAM, 4, 64);
                    weightVector[vectorIndex] = weights.get("RAM");
                    vectorIndex++;
                    break;

                case "Storage":
                    int requiredStorage = Integer.parseInt(value.toString());
                    int actualStorage = Integer.parseInt(laptop[11].trim());
                    userVector[vectorIndex] = normalizeValue(requiredStorage, 128, 2048);
                    laptopVector[vectorIndex] = normalizeValue(actualStorage, 128, 2048);
                    weightVector[vectorIndex] = weights.get("Storage");
                    vectorIndex++;
                    break;

                case "Rating (1-10)":
                    int requiredRating = Integer.parseInt(value.toString());
                    int actualRating = Integer.parseInt(laptop[1].trim());
                    userVector[vectorIndex] = normalizeValue(requiredRating, 1, 10);
                    laptopVector[vectorIndex] = normalizeValue(actualRating, 1, 10);
                    weightVector[vectorIndex] = weights.get("Rating");
                    vectorIndex++;
                    break;

                case "Speed Rating (1-10)":
                    int requiredSpeed = Integer.parseInt(value.toString());
                    int actualSpeed = Integer.parseInt(laptop[9].trim());
                    userVector[vectorIndex] = normalizeValue(requiredSpeed, 1, 10);
                    laptopVector[vectorIndex] = normalizeValue(actualSpeed, 1, 10);
                    weightVector[vectorIndex] = weights.get("Speed Rating");
                    vectorIndex++;
                    break;

                case "USB ports":
                    List<String> usbPorts = (List<String>) value;
                    int requiredUSB = Integer.parseInt(usbPorts.get(0));
                    int actualUSB = Integer.parseInt(laptop[15].trim());
                    userVector[vectorIndex] = normalizeValue(requiredUSB, 1, 6);
                    laptopVector[vectorIndex] = normalizeValue(actualUSB, 1, 6);
                    weightVector[vectorIndex] = weights.get("USB ports");
                    vectorIndex++;
                    break;

                case "Other ports":
                    List<String> selectedPorts = (List<String>) value;
                    String[] laptopPorts = laptop[16].trim().split(";");
                    double portMatch = 0.0;
                    for (String port : selectedPorts) {
                        if (Arrays.asList(laptopPorts).contains(port)) {
                            portMatch += 1.0 / selectedPorts.size();
                        }
                    }
                    userVector[vectorIndex] = 1.0;
                    laptopVector[vectorIndex] = portMatch;
                    weightVector[vectorIndex] = weights.get("Other ports");
                    vectorIndex++;
                    break;

                case "Display":
                    double requiredDisplay = Double.parseDouble(value.toString());
                    double actualDisplay = Double.parseDouble(laptop[18].trim());
                    userVector[vectorIndex] = normalizeValue(requiredDisplay, 11, 17);
                    laptopVector[vectorIndex] = normalizeValue(actualDisplay, 11, 17);
                    weightVector[vectorIndex] = weights.get("Display");
                    vectorIndex++;
                    break;

                case "Weight":
                    double maxWeight = Double.parseDouble(value.toString());
                    double actualWeight = Double.parseDouble(laptop[21].trim());
                    userVector[vectorIndex] = normalizeValue(maxWeight, 1, 5);
                    laptopVector[vectorIndex] = normalizeValue(actualWeight, 1, 5);
                    weightVector[vectorIndex] = weights.get("Weight");
                    vectorIndex++;
                    break;
            }
        }

        return calculateWeightedCosineSimilarity(userVector, laptopVector, weightVector);
    }

    /**
     * Normalizes a value to range [0,1] using min-max scaling
     */
    private double normalizeValue(double value, double min, double max) {
        return Math.max(0.0, Math.min(1.0, (value - min) / (max - min)));
    }

    /**
     * Calculates weighted cosine similarity between two vectors
     * Formula: cos(θ) = (Σ wi·ui·li) / (sqrt(Σ wi·ui²) · sqrt(Σ wi·li²))
     */
    private double calculateWeightedCosineSimilarity(double[] userVector, double[] laptopVector, double[] weightVector) {
        double numerator = 0.0;
        double userNorm = 0.0;
        double laptopNorm = 0.0;

        for (int i = 0; i < userVector.length; i++) {
            numerator += weightVector[i] * userVector[i] * laptopVector[i];
            userNorm += weightVector[i] * userVector[i] * userVector[i];
            laptopNorm += weightVector[i] * laptopVector[i] * laptopVector[i];
        }

        if (userNorm == 0 || laptopNorm == 0) {
            return 0.0;
        }

        return numerator / (Math.sqrt(userNorm) * Math.sqrt(laptopNorm));
    }

    /**
     * Checks if laptop meets mandatory requirements (hard constraints)
     */
    private boolean checkHardConstraints(String[] laptop, Map<String, Object> filters) {
        if (filters.containsKey("OS")) {
            List<String> requiredOS = (List<String>) filters.get("OS");
            if (!requiredOS.contains(laptop[17].trim())) {
                return false;
            }
        }

        if (filters.containsKey("Touchscreen")) {
            boolean touchRequired = Boolean.parseBoolean(filters.get("Touchscreen").toString());
            boolean hasTouchscreen = Boolean.parseBoolean(laptop[20].trim());
            if (touchRequired != hasTouchscreen) {
                return false;
            }
        }

        return true;
    }

    private void setupButton(JButton button) {
        button.setFont(mainFont);
        button.setForeground(Color.white);
        button.setBackground(buttonColour);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.addActionListener(this);
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColour);
            }
            
            public void mouseExited(MouseEvent e) {
                button.setBackground(buttonColour);
            }
        });
        
        add(button);
    }
    
    private void setupLabels(JLabel name, JLabel price) {
        name.setFont(mainFont);
        price.setFont(mainFont);
        name.setHorizontalAlignment(JLabel.CENTER);
        price.setHorizontalAlignment(JLabel.CENTER);        
        add(name);
        add(price);
    }

    private List<String[]> readCSV(String filename) {
        List<String[]> records = new ArrayList<>();
        try {
            String content = new String(Files.readAllBytes(Paths.get(filename)));
            String[] lines = content.split("\n");
            for (String line : lines) {
                records.add(line.split(","));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return records;
    }

    private void openWebpage(String urlString) {
        try {
            // Clean and encode the URL
            urlString = urlString.trim(); // Remove any whitespace
            String encodedUrl = URLEncoder.encode(urlString, StandardCharsets.UTF_8.toString())
                    .replace("+", "%20") // Replace + with %20 for spaces
                    .replace("%3A", ":") // Preserve protocol separator
                    .replace("%2F", "/") // Preserve path separator
                    .replace("%3F", "?") // Preserve query separator
                    .replace("%3D", "=") // Preserve parameter separator
                    .replace("%26", "&"); // Preserve parameter concatenator
            
            Desktop.getDesktop().browse(new URI(encodedUrl));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error opening link: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    //action events
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == inventory) {
        	new LaptopStoreInventoryFrame();
            dispose();
        }
        else if (event.getSource() == survey) {
            new LaptopStoreSurveyFrame();
            dispose();
        }
        else if (event.getSource() == toTitle) {
            new LaptopStoreTitleFrame();
            dispose();
        }
        else if (event.getSource() == specs1 && recommendedLaptop != null) {
            String hyperlink = laptops.get(recommendedLaptop[0] + 1)[22]; // hyperlink is in column 22
            openWebpage(hyperlink);
        }
        else if (event.getSource() == specs2 && recommendedLaptop != null) {
            String hyperlink = laptops.get(recommendedLaptop[1] + 1)[22];
            openWebpage(hyperlink);
        }
        else if (event.getSource() == specs3 && recommendedLaptop != null) {
            String hyperlink = laptops.get(recommendedLaptop[2] + 1)[22];
            openWebpage(hyperlink);
        }
        
        else if (event.getSource() == toCart1 && recommendedLaptop != null) {
        	toCart1.setBackground(Color.white);
        	toCart1.setEnabled(false);
        	toCart1.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    toCart1.setBackground(Color.white);
                }
                public void mouseExited(MouseEvent e) {
                    toCart1.setBackground(Color.white);
                }
        	});
        	toCart1.setText("Added!");
        }
        	
        else if (event.getSource() == toCart2 && recommendedLaptop != null) {
        	toCart2.setBackground(Color.white);
        	toCart2.setEnabled(false);
        	toCart2.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    toCart2.setBackground(Color.white);
                }
                public void mouseExited(MouseEvent e) {
                    toCart2.setBackground(Color.white);
                }
        	});
        	toCart2.setText("Added!");
        	
        }
        else if (event.getSource() == toCart3 && recommendedLaptop != null) {
        	toCart3.setBackground(Color.white);
        	toCart3.setEnabled(false);
        	toCart3.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    toCart3.setBackground(Color.white);
                }
                public void mouseExited(MouseEvent e) {
                    toCart3.setBackground(Color.white);
                }
        	});
        	toCart1.setText("Added!");
        }
    }
}
