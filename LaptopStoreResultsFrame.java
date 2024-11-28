//Results frame
//displays the results for the user's survey results
//imports
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.*;
import javax.swing.border.*;

import java.io.*;
import java.util.*;
import java.util.List;

//class to store laptop score
class LaptopScore implements Comparable<LaptopScore> {
   int index;
   double score;

   public LaptopScore(int index, double score) {
       this.index = index;
       this.score = score;
   }

   @Override
   public int compareTo(LaptopScore other) {
       return Double.compare(other.score, this.score); // Sort in descending order
   }
}

public class LaptopStoreResultsFrame extends JFrame implements ActionListener {
   
   //field to store recommended laptops indices
   private static int[] recommendedLaptop;
   
   //field to store filter values
   private Map<String, Object> filtervalue;
   
   //field to store all laptops data
   private List<String[]> laptops;
   
   // Constants for styling
   private static final Color titleColour = new Color(33, 37, 41);
   private static final Color buttonColour = new Color(13, 110, 253);
   private static final Color hoverColour = new Color(0, 85, 227);
   
   Font mainFont = new Font("Segoe UI", Font.BOLD, 20);
   Font largeFont = new Font("Segoe UI", Font.BOLD, 35);
   
   //swing objects
   JButton inventory = new JButton("Inventory");
   JButton survey = new JButton("Survey");
   JButton toTitle = new JButton("Back to title");
   
   //recommended laptop 1
   JLabel recommended1 = new JLabel("Recommended Laptop");
   JLabel name1 = new JLabel("");
   JLabel price1 = new JLabel("");
   JButton specs1 = new JButton("Specifications");
   JButton toCart1 = new JButton("Add to cart");
   JPanel panel1 = new JPanel();
   
   //recommended 2
   JLabel recommended2 = new JLabel("Runner Up");
   JLabel name2 = new JLabel("");
   JLabel price2 = new JLabel("");
   JButton specs2 = new JButton("Specifications");
   JButton toCart2 = new JButton("Add to cart");
   JPanel panel2 = new JPanel();
   
   //recommended 3
   JLabel recommended3 = new JLabel("Third Option");
   JLabel name3 = new JLabel("");
   JLabel price3 = new JLabel("");
   JButton specs3 = new JButton("Specifications");
   JButton toCart3 = new JButton("Add to cart");
   JPanel panel3 = new JPanel();
   
   // GUI elements    
   public LaptopStoreResultsFrame() {
   	
   	//set window settings
   	setSize(1920,1080);
   	setLayout(null);
   	setTitle("DCS Laptops");
   	
   	//so the program ends when the frame closes
   	setDefaultCloseOperation(EXIT_ON_CLOSE);
   	
   	//make the GUI appear in the middle
   	setLocationRelativeTo(null);
   	
   	//create and set image icon
   	ImageIcon img = new ImageIcon("images/logo_square.png");
   	setIconImage(img.getImage());
       
       //load laptops data
       this.laptops = readCSV("data/database.csv");
   	
   	//add swing components
   	
   	//TOP BUTTONS
   	inventory.setBounds(20, 20, 300, 80);
   	setupButton(inventory);
   	
   	survey.setBounds(340, 20, 300, 80);
   	setupButton(survey);
   	
   	toTitle.setBounds(660, 20, 300, 80);
   	setupButton(toTitle);
   	
   	//JLabels - Text
   	
   	//text for recommended 1
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
   	
   	//recommended 2  
   	recommended2.setBounds(820, 180, 700, 100);
   	recommended2.setFont(largeFont);
   	recommended2.setForeground(titleColour);
   	name2.setBounds(740, 610, 350, 100);
   	price2.setBounds(740, 660, 350, 100);
   	specs2.setBounds(735, 750, 360, 70);
   	toCart2.setBounds(735, 850, 360, 70);
   	setupLabels(name2, price2);
   	setupButton(specs2);
   	setupButton(toCart2);
   	add(recommended2);	
   	panel2.setBounds(725, 265, 380, 380);
   	panel2.setBackground(new Color(252, 255, 60));
   	add(panel2);
   	
   	//recommended 3
   	recommended3.setBounds(1400, 180, 700, 100);
   	recommended3.setFont(largeFont);
   	recommended3.setForeground(titleColour);
   	name3.setBounds(1340, 610, 350, 100);
   	price3.setBounds(1340, 660, 350, 100);
   	specs3.setBounds(1335, 750, 360, 70);
   	toCart3.setBounds(1335, 850, 360, 70);
   	setupLabels(name3, price3);
   	setupButton(specs3);
   	setupButton(toCart3);
   	add(recommended3);	
   	panel3.setBounds(1325, 265, 380, 380);
   	panel3.setBackground(new Color(252, 255, 60));
   	add(panel3);
   	
   	//show frame
   	setVisible(true);
   }
   
   //method to set filter values and update display
   public void setFilterValues(Map<String, Object> values) {
       this.filtervalue = values;
       this.recommendedLaptop = findRecommended();
       updateDisplay();
   }

   //Update the display with recommended laptops info
   private void updateDisplay() {
        if (recommendedLaptop != null && laptops != null) {
            //Update first recommendation
            name1.setText(laptops.get(recommendedLaptop[0] + 1)[2]);
            price1.setText("$" + laptops.get(recommendedLaptop[0] + 1)[4]);
            updatePanel(panel1, "images/laptops/laptop" + (recommendedLaptop[0] + 1) + ".jpg");

            //Update second recommendation  
            name2.setText(laptops.get(recommendedLaptop[1] + 1)[2]);
            price2.setText("$" + laptops.get(recommendedLaptop[1] + 1)[4]);
            updatePanel(panel2, "images/laptops/laptop" + (recommendedLaptop[1] + 1) + ".jpg");

            //Update third recommendation
            name3.setText(laptops.get(recommendedLaptop[2] + 1)[2]);
            price3.setText("$" + laptops.get(recommendedLaptop[2] + 1)[4]);
            updatePanel(panel3, "images/laptops/laptop" + (recommendedLaptop[2] + 1) + ".jpg");
        }
    }

    private void updatePanel(JPanel panel, String imagePath) {
        panel.removeAll();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(255, 255, 255)); // White background

        ImageIcon originalIcon = new ImageIcon(imagePath);
        Image originalImage = originalIcon.getImage();
        
        // Get panel size
        int panelSize = Math.min(panel.getWidth(), panel.getHeight());
        
        // Calculate new dimensions maintaining aspect ratio
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

   //Find top 3 recommended laptops
   private int[] findRecommended() {
       if (filtervalue == null) {
           return null;
       }

       if (laptops == null || laptops.isEmpty()) {
           return null;
       }

       List<LaptopScore> scores = new ArrayList<>();
       
       for (int i = 1; i < laptops.size(); i++) {
           double score = calculateMatchScore(laptops.get(i), filtervalue);
           scores.add(new LaptopScore(i - 1, score));
       }

       Collections.sort(scores);
       int[] topThree = new int[3];
       for (int i = 0; i < Math.min(3, scores.size()); i++) {
           topThree[i] = scores.get(i).index;
       }

       return topThree;
   }

   //Read laptop data from CSV file
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

   //Calculate match score for a laptop based on filter values
   private double calculateMatchScore(String[] laptop, Map<String, Object> filters) {
        // Hard constraints check
        if (!checkHardConstraints(laptop, filters)) {
            return 0.0;
        }

        double score = 0.0;
        Map<String, Double> weights = new HashMap<>();
        
        // Initialize weights
        weights.put("Price", 0.20);
        weights.put("Brand", 0.05);
        weights.put("Type", 0.10);
        weights.put("CPU Brand", 0.05);
        weights.put("GPU Brand", 0.05);
        weights.put("RAM", 0.10);
        weights.put("Storage", 0.10);
        weights.put("Rating", 0.10);
        weights.put("Speed Rating", 0.05);
        weights.put("USB Ports", 0.05);
        weights.put("Other Ports", 0.05);
        weights.put("Display", 0.05);
        weights.put("Weight", 0.05);

        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            switch (key) {
                case "minPrice":
                case "maxPrice":
                    double minPrice = Double.parseDouble(filters.get("minPrice").toString());
                    double maxPrice = Double.parseDouble(filters.get("maxPrice").toString());
                    double price = Double.parseDouble(laptop[4].trim());
                    if (price >= minPrice && price <= maxPrice) {
                        score += weights.get("Price");
                    }
                    break;

                case "Brand":
                    List<String> selectedBrands = (List<String>) value;
                    if (selectedBrands.contains(laptop[0].trim())) {
                        score += weights.get("Brand");
                    }
                    break;

                case "Type":
                    List<String> selectedTypes = (List<String>) value;
                    if (selectedTypes.contains(laptop[3].trim())) {
                        score += weights.get("Type");
                    }
                    break;

                case "Rating (1-10)":
                    int requiredRating = Integer.parseInt(value.toString());
                    int actualRating = Integer.parseInt(laptop[1].trim());
                    score += weights.get("Rating") * (1 - Math.abs(actualRating - requiredRating)/10.0);
                    break;

                case "Speed Rating (1-10)":
                    int requiredSpeed = Integer.parseInt(value.toString());
                    int actualSpeed = Integer.parseInt(laptop[9].trim());
                    score += weights.get("Speed Rating") * (1 - Math.abs(actualSpeed - requiredSpeed)/10.0);
                    break;

                // Add other criteria here...
            }
        }

        return score * 100; // Convert to percentage
    }

    private boolean checkHardConstraints(String[] laptop, Map<String, Object> filters) {
        // Check OS
        if (filters.containsKey("OS")) {
            String requiredOS = (String) filters.get("OS");
            if (!laptop[17].trim().equals(requiredOS)) {
                return false;
            }
        }

        // Check Touchscreen
        if (filters.containsKey("Touchscreen")) {
            boolean touchRequired = Boolean.parseBoolean(filters.get("Touchscreen").toString());
            boolean hasTouchscreen = Boolean.parseBoolean(laptop[20].trim());
            if (touchRequired != hasTouchscreen) {
                return false;
            }
        }

        return true;
    }

   //method to set up the buttons
   private void setupButton(JButton button) {
   	button.setFont(mainFont);
   	button.setForeground(Color.white);
   	button.setBackground(buttonColour);
   	button.setBorderPainted(false);
   	button.setFocusPainted(false);
   	button.setOpaque(true);
       button.addActionListener(this);
       
       //Add a mouse hovering effect
       button.addMouseListener(new MouseAdapter() {
           public void mouseEntered(MouseEvent e) {
               button.setBackground(hoverColour);
               setCursor(new Cursor(Cursor.HAND_CURSOR));
           }
           
           public void mouseExited(MouseEvent e) {
               button.setBackground(buttonColour);
               setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
           }
       });
       
       add(button);
   }
   
   //method to set up laptop name and price
   private void setupLabels(JLabel name, JLabel price) {
   	name.setFont(mainFont);
   	price.setFont(mainFont);
   	name.setHorizontalAlignment(JLabel.CENTER);
   	price.setHorizontalAlignment(JLabel.CENTER);    	
   	add(name);
   	add(price);
   }

   @Override
   public void actionPerformed(ActionEvent event) {
   	if (event.getSource() == inventory) {
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
   }

   //delete later - test
   public static void main(String[]args) {
   	new LaptopStoreResultsFrame();
   }
}