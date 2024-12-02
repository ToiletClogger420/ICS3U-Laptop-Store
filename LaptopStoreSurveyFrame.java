import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import javax.swing.border.TitledBorder;
import java.awt.event.*;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.ArrayList;
import java.io.*;
import java.util.Comparator;

public class LaptopStoreSurveyFrame extends JFrame {
    private static final Color BACKGROUND_COLOR = new Color(248, 249, 250);
    private static final Font LABEL_FONT = new Font("Segoe UI", Font.BOLD, 22);
    private static final Map<String, String> DISPLAY_NAMES = new HashMap<>() {{
        put("Type (ex. Student; Professional; Gaming; etc.)", "Type");
        put("CPU - Brand", "CPU Brand");
        put("GPU brand", "GPU Brand");
        put("RAM - GB", "RAM (GB)");
        put("SSD (GB)", "Storage (GB)");
        put("USB ports", "USB Ports");
        put("other ports", "Other Ports");
        put("Disp. (in)", "Display Size");
        put("Weight (lbs)", "Weight");
    }};
    
    private Map<String, Set<String>> optionsMap;
    private Map<String, JComponent> filterComponents;
    private JPanel mainPanel, leftPanel, rightPanel;
    private JScrollPane scrollPane;
    private JButton clearButton, backButton, continueButton;
    
    public LaptopStoreSurveyFrame() {
        optionsMap = readDatabase();
        filterComponents = new HashMap<>();
        setupMainFrame();
        initializeComponents();
        layoutComponents();
        addEventListeners();
        setVisible(true);
    }

    private Map<String, Set<String>> readDatabase() {
        Map<String, Set<String>> options = new HashMap<>();
        
        // Create a custom comparator for weight ranges
        Comparator<String> weightComparator = (a, b) -> {
            // Define the order of weight categories
            Map<String, Integer> weightOrder = new HashMap<>() {{
                put("Ultra-light (less than 3)", 1);
                put("Light (3-4 lbs)", 2);
                put("Medium (4-5 lbs)", 3);
                put("Heavy (5+ lbs)", 4);
            }};
            return weightOrder.get(a) - weightOrder.get(b);
        };

        try (BufferedReader br = new BufferedReader(new FileReader("data/database.csv"))) {
            String[] headers = br.readLine().split(",");
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                for (int i = 0; i < headers.length && i < values.length; i++) {
                    String header = headers[i].trim();
                    String trimmedValue = values[i].trim();
                    
                    if (header.equals("other ports")) {
                        String[] ports = values[i].split(";");
                        for (String port : ports) {
                            String trimmedPort = port.trim();
                            if (!trimmedPort.isEmpty()) {
                                options.computeIfAbsent(header, k -> new TreeSet<>()).add(trimmedPort);
                            }
                        }
                    } 
                    // Special handling for RAM and Storage
                    else if (header.equals("RAM - GB") || header.equals("SSD (GB)")) {
                        if (!trimmedValue.isEmpty()) {
                            options.computeIfAbsent(header, k -> new TreeSet<>((a, b) -> {
                                try {
                                    return Integer.parseInt(a.replaceAll("[^0-9]", "")) - 
                                           Integer.parseInt(b.replaceAll("[^0-9]", ""));
                                } catch (NumberFormatException e) {
                                    return a.compareTo(b);
                                }
                            })).add(trimmedValue);
                        }
                    }
                    // Special handling for Weight with ordered categories
                    else if (header.equals("Weight (lbs)")) {
                        if (!trimmedValue.isEmpty()) {
                            try {
                                double weight = Double.parseDouble(trimmedValue);
                                String weightRange = categorizeWeight(weight);
                                options.computeIfAbsent(header, k -> new TreeSet<>(weightComparator))
                                      .add(weightRange);
                            } catch (NumberFormatException e) {
                                // Skip invalid weight values
                            }
                        }
                    }
                    // Default handling for other fields
                    else {
                        if (!trimmedValue.isEmpty()) {
                            options.computeIfAbsent(header, k -> new TreeSet<>()).add(trimmedValue);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return options;
    }

    private void setupMainFrame() {
        setTitle("DCS Laptops - Survey");
        setSize(1920, 1080);
        
        // setting logo of window
        ImageIcon icon = new ImageIcon("images/logo_square.png");
        Image scaledIcon = icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        setIconImage(scaledIcon);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        setContentPane(mainPanel);
    }

    private void initializeComponents() {
        // Create a scrollable panel
        leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create a scroll pane for the left panel
        JScrollPane leftScrollPane = new JScrollPane(leftPanel);
        leftScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        leftScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        leftScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        leftScrollPane.setBorder(null);
        
        // Set preferred size for the scroll pane
        leftScrollPane.setPreferredSize(new Dimension(1600, 900));

        // Initialize all components
        initializePriceRange();
        initializeCheckBoxes("Brand", 4);
        initializeCheckBoxes("Type (ex. Student; Professional; Gaming; etc.)", 3);
        initializeCheckBoxes("CPU - Brand", 3);
        initializeCheckBoxes("GPU brand", 3);
        initializeCheckBoxes("OS", 2);
        initializeCheckBoxes("RAM - GB", 3);
        initializeCheckBoxes("SSD (GB)", 3);
        initializeSlider("Rating (1-10)", 1, 10);
        initializeSlider("Speed Rating (1-10)", 1, 10);
        initializeCheckBoxes("USB ports", 3);
        initializeCheckBoxes("other ports", 3);
        initializeCheckBoxes("Disp. (in)", 3);
        initializeCheckBoxes("Weight (lbs)", 3);
        initializeCheckBox("Touchscreen");

        clearButton = createStyledButton("Clear");
        backButton = createStyledButton("Back");
        continueButton = createStyledButton("Continue");
        
        rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(BACKGROUND_COLOR);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        rightPanel.setPreferredSize(new Dimension(200, 1080));
        
        // Add the scroll pane to the main panel
        mainPanel.add(leftScrollPane, BorderLayout.CENTER);
    }

    private void initializePriceRange() {
        Set<String> prices = optionsMap.get("Price");
        double minPrice = prices.stream().mapToDouble(p -> Double.parseDouble(p)).min().getAsDouble();
        double maxPrice = prices.stream().mapToDouble(p -> Double.parseDouble(p)).max().getAsDouble();
        
        JPanel pricePanel = new JPanel(new BorderLayout(10, 0));
        pricePanel.setMaximumSize(new Dimension(1500, 60));
        
        JSpinner minSpinner = new JSpinner(new SpinnerNumberModel(minPrice, minPrice, maxPrice, 50.0));
        JSpinner maxSpinner = new JSpinner(new SpinnerNumberModel(maxPrice, minPrice, maxPrice, 50.0));
        
        // 调整Spinner的大小
        Dimension spinnerSize = new Dimension(150, 30);
        minSpinner.setPreferredSize(spinnerSize);
        maxSpinner.setPreferredSize(spinnerSize);
        
        JPanel spinnerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        spinnerPanel.add(new JLabel("Min: $"));
        spinnerPanel.add(minSpinner);
        spinnerPanel.add(Box.createHorizontalStrut(20));
        spinnerPanel.add(new JLabel("Max: $"));
        spinnerPanel.add(maxSpinner);

        JLabel priceLabel = new JLabel("Price Range");
        priceLabel.setFont(LABEL_FONT);
        pricePanel.add(priceLabel, BorderLayout.WEST);
        pricePanel.add(spinnerPanel, BorderLayout.EAST);

        filterComponents.put("minPrice", minSpinner);
        filterComponents.put("maxPrice", maxSpinner);
        leftPanel.add(pricePanel);
        leftPanel.add(Box.createVerticalStrut(10));
    }

    private void initializeCheckBoxes(String category, int rows) {
        Set<String> options = optionsMap.get(category);
        if (options == null || options.isEmpty()) return;

        // Use GridBagLayout instead of GridLayout for better control
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5); // Add padding
        
        panel.setMaximumSize(new Dimension(1500, rows * 50)); // Increased height per row
        String displayName = DISPLAY_NAMES.getOrDefault(category, category);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY), 
            displayName, 
            TitledBorder.LEFT, 
            TitledBorder.TOP, 
            LABEL_FONT
        ));

        int col = 0;
        int row = 0;
        for (String option : options) {
            JCheckBox checkBox = new JCheckBox("<html><body style='width: 150px'>" + option + "</body></html>");
            checkBox.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            
            gbc.gridx = col;
            gbc.gridy = row;
            gbc.weightx = 1.0;
            panel.add(checkBox, gbc);

            col++;
            if (col >= 4) {
                col = 0;
                row++;
            }
        }

        // Fill remaining cells if needed
        while (row < rows || (row == rows - 1 && col > 0)) {
            gbc.gridx = col;
            gbc.gridy = row;
            panel.add(Box.createHorizontalStrut(150), gbc);
            col++;
            if (col >= 4) {
                col = 0;
                row++;
            }
        }

        filterComponents.put(category, panel);
        leftPanel.add(panel);
        leftPanel.add(Box.createVerticalStrut(10));
    }

    private void initializeSlider(String category, int min, int max) {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setMaximumSize(new Dimension(1500, 70)); // 调整尺寸
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY), 
                       category, TitledBorder.LEFT, TitledBorder.TOP, LABEL_FONT));
        
        JSlider slider = new JSlider(min, max);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(1);
        slider.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        panel.add(slider, BorderLayout.CENTER);
        
        filterComponents.put(category, slider);
        leftPanel.add(panel);
        leftPanel.add(Box.createVerticalStrut(10));
    }

    private void initializeCheckBox(String category) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setMaximumSize(new Dimension(1500, 40));
        
        JCheckBox checkBox = new JCheckBox(category);
        checkBox.setFont(LABEL_FONT);
        panel.add(checkBox, BorderLayout.WEST);
        
        filterComponents.put(category, checkBox);
        leftPanel.add(panel);
        leftPanel.add(Box.createVerticalStrut(10));
    }

    private void layoutComponents() {
        rightPanel.add(Box.createVerticalGlue());
        rightPanel.add(clearButton);
        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(backButton);
        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(continueButton);
        rightPanel.add(Box.createVerticalGlue());
        
        mainPanel.add(rightPanel, BorderLayout.EAST);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(LABEL_FONT);
        button.setMaximumSize(new Dimension(150, 40));
        button.setPreferredSize(new Dimension(150, 40));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(new Color(13, 110, 253));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(0, 85, 227));
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(13, 110, 253));
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        return button;
    }

    private void addEventListeners() {
        clearButton.addActionListener(e -> clearForm());
        backButton.addActionListener(e -> {
            dispose();  // Close Current Window
            new LaptopStoreTitleFrame();  // Open new TitleFrame
        });
        continueButton.addActionListener(e -> {
            Map<String, Object> values = getFilterValues();
            // Print the selected values for testing
            for (Map.Entry<String, Object> entry : values.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue()); // Directly print out.
                // LoggerUtils.logln(entry.getKey() + ": " + entry.getValue()); // Using SLF4J logger.
            }
            dispose();  // Close Current Window
            LaptopStoreResultsFrame frame = new LaptopStoreResultsFrame();  // Open new ResultsFrame
            frame.setFilterValues(values);
        });
    }

    private void clearForm() {
        for (JComponent component : filterComponents.values()) {
            if (component instanceof JPanel) {
                for (Component c : ((JPanel)component).getComponents()) {
                    if (c instanceof JCheckBox) {
                        ((JCheckBox)c).setSelected(false);
                    }
                }
            } else if (component instanceof JSlider) {
                ((JSlider)component).setValue(((JSlider)component).getMinimum());
            } else if (component instanceof JSpinner) {
                ((JSpinner)component).setValue(((SpinnerNumberModel)((JSpinner)component).getModel()).getMinimum());
            } else if (component instanceof JCheckBox) {
                ((JCheckBox)component).setSelected(false);
            }
        }
    }
    
    private String categorizeWeight(double weight) {
        if (weight < 3.0) return "Ultra-light (less than 3)";
        else if (weight < 4.0) return "Light (3-4 lbs)";
        else if (weight < 5.0) return "Medium (4-5 lbs)";
        else return "Heavy (5+ lbs)";
    }

    public Map<String, Object> getFilterValues() {
        Map<String, Object> values = new HashMap<>();
        for (Map.Entry<String, JComponent> entry : filterComponents.entrySet()) {
            String key = entry.getKey();
            JComponent component = entry.getValue();
            
            if (component instanceof JPanel) {
                ArrayList<String> selectedValues = new ArrayList<>();
                for (Component c : ((JPanel)component).getComponents()) {
                    if (c instanceof JCheckBox && ((JCheckBox)c).isSelected()) {
                        // Strip HTML tags from checkbox text
                        String text = ((JCheckBox)c).getText()
                            .replaceAll("<[^>]*>", "") // Remove HTML tags
                            .trim();
                        selectedValues.add(text);
                    }
                }
                if (!selectedValues.isEmpty()) {
                    values.put(key, selectedValues);
                }
            } else if (component instanceof JSlider) {
                values.put(key, ((JSlider)component).getValue());
            } else if (component instanceof JSpinner) {
                values.put(key, ((JSpinner)component).getValue());
            } else if (component instanceof JCheckBox) {
                values.put(key, ((JCheckBox)component).isSelected());
            }
        }
        return values;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LaptopStoreSurveyFrame();
        });
    }
}
