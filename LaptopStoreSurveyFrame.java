import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.ArrayList;
import java.io.*;

public class LaptopStoreSurveyFrame extends JFrame {
    private static final Color BACKGROUND_COLOR = new Color(248, 249, 250);
    private static final Font LABEL_FONT = new Font("Segoe UI", Font.BOLD, 12);
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
        try (BufferedReader br = new BufferedReader(new FileReader("data/database.csv"))) {
            String[] headers = br.readLine().split(",");
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                for (int i = 0; i < headers.length && i < values.length; i++) {
                    if (headers[i].trim().equals("other ports")) {
                        String[] ports = values[i].split(";");
                        for (String port : ports) {
                            String trimmedPort = port.trim();
                            if (!trimmedPort.isEmpty()) {
                                options.computeIfAbsent(headers[i], k -> new TreeSet<>()).add(trimmedPort);
                            }
                        }
                    } else {
                        String trimmedValue = values[i].trim();
                        if (!trimmedValue.isEmpty()) {
                            options.computeIfAbsent(headers[i], k -> new TreeSet<>()).add(trimmedValue);
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
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setContentPane(mainPanel);
    }

    private void initializeComponents() {
        leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        leftPanel.setPreferredSize(new Dimension(600, 1200));

        initializePriceRange();
        initializeCheckBoxes("Brand", 3);
        initializeCheckBoxes("Type (ex. Student; Professional; Gaming; etc.)", 2);
        initializeCheckBoxes("CPU - Brand", 2);
        initializeCheckBoxes("GPU brand", 2);
        initializeCheckBoxes("OS", 1);
        initializeCheckBoxes("RAM - GB", 2);
        initializeCheckBoxes("SSD (GB)", 2);
        initializeSlider("Rating (1-10)", 1, 10);
        initializeSlider("Speed Rating (1-10)", 1, 10);
        initializeCheckBoxes("USB ports", 2);
        initializeCheckBoxes("other ports", 2);
        initializeCheckBoxes("Disp. (in)", 2);
        initializeCheckBoxes("Weight (lbs)", 2);
        initializeCheckBox("Touchscreen");

        clearButton = createStyledButton("Clear");
        backButton = createStyledButton("Back");
        continueButton = createStyledButton("Continue");
        
        rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(BACKGROUND_COLOR);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private void initializePriceRange() {
        Set<String> prices = optionsMap.get("Price");
        double minPrice = prices.stream().mapToDouble(p -> Double.parseDouble(p)).min().getAsDouble();
        double maxPrice = prices.stream().mapToDouble(p -> Double.parseDouble(p)).max().getAsDouble();
        
        JPanel pricePanel = new JPanel(new BorderLayout(5, 0));
        pricePanel.setMaximumSize(new Dimension(580, 40));
        
        JSpinner minSpinner = new JSpinner(new SpinnerNumberModel(minPrice, minPrice, maxPrice, 50.0));
        JSpinner maxSpinner = new JSpinner(new SpinnerNumberModel(maxPrice, minPrice, maxPrice, 50.0));
        
        JPanel spinnerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        spinnerPanel.add(new JLabel("Min: $"));
        spinnerPanel.add(minSpinner);
        spinnerPanel.add(new JLabel("Max: $"));
        spinnerPanel.add(maxSpinner);

        pricePanel.add(new JLabel("Price Range"), BorderLayout.WEST);
        pricePanel.add(spinnerPanel, BorderLayout.EAST);

        filterComponents.put("minPrice", minSpinner);
        filterComponents.put("maxPrice", maxSpinner);
        leftPanel.add(pricePanel);
        leftPanel.add(Box.createVerticalStrut(5));
    }

    private void initializeCheckBoxes(String category, int rows) {
        Set<String> options = optionsMap.get(category);
        if (options == null || options.isEmpty()) return;

        JPanel panel = new JPanel(new GridLayout(0, 3, 5, 5));
        panel.setMaximumSize(new Dimension(580, rows * 35));
        String displayName = DISPLAY_NAMES.getOrDefault(category, category);
        panel.setBorder(BorderFactory.createTitledBorder(displayName));

        for (String option : options) {
            JCheckBox checkBox = new JCheckBox(option);
            panel.add(checkBox);
        }

        int cells = rows * 3;
        while (panel.getComponentCount() < cells) {
            panel.add(new JLabel());
        }

        filterComponents.put(category, panel);
        leftPanel.add(panel);
        leftPanel.add(Box.createVerticalStrut(5));
    }

    private void initializeSlider(String category, int min, int max) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setMaximumSize(new Dimension(580, 50));
        panel.setBorder(BorderFactory.createTitledBorder(category));
        
        JSlider slider = new JSlider(min, max);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(1);
        
        panel.add(slider, BorderLayout.CENTER);
        
        filterComponents.put(category, slider);
        leftPanel.add(panel);
        leftPanel.add(Box.createVerticalStrut(5));
    }

    private void initializeCheckBox(String category) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setMaximumSize(new Dimension(580, 30));
        
        JCheckBox checkBox = new JCheckBox(category);
        panel.add(checkBox, BorderLayout.WEST);
        
        filterComponents.put(category, checkBox);
        leftPanel.add(panel);
        leftPanel.add(Box.createVerticalStrut(5));
    }

    private void layoutComponents() {
        scrollPane = new JScrollPane(leftPanel);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        rightPanel.add(Box.createVerticalGlue());
        rightPanel.add(clearButton);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(backButton);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(continueButton);
        rightPanel.add(Box.createVerticalGlue());
        
        mainPanel.add(rightPanel, BorderLayout.EAST);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(LABEL_FONT);
        button.setMaximumSize(new Dimension(100, 30));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(new Color(13, 110, 253));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
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
            // 打印选中的值用于测试
            for (Map.Entry<String, Object> entry : values.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
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

    public Map<String, Object> getFilterValues() {
        Map<String, Object> values = new HashMap<>();
        for (Map.Entry<String, JComponent> entry : filterComponents.entrySet()) {
            String key = entry.getKey();
            JComponent component = entry.getValue();
            
            if (component instanceof JPanel) {
                ArrayList<String> selectedValues = new ArrayList<>();  // 改为直接使用ArrayList
                for (Component c : ((JPanel)component).getComponents()) {
                    if (c instanceof JCheckBox && ((JCheckBox)c).isSelected()) {
                        selectedValues.add(((JCheckBox)c).getText());
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
