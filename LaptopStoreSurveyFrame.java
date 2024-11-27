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

public class LaptopStoreSurveyFrame extends JFrame {
    private static final Color BACKGROUND_COLOR = new Color(248, 249, 250);
    private static final Font LABEL_FONT = new Font("Segoe UI", Font.BOLD, 21);
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
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        mainPanel = new JPanel(new BorderLayout(20, 20)); // 增加间距
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25)); // 增加边距
        setContentPane(mainPanel);
    }

    private void initializeComponents() {
        leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        // leftPanel.setPreferredSize(new Dimension(1600, 900)); // 调整左面板大小

        initializePriceRange();
<<<<<<< HEAD
        initializeCheckBoxes("Brand", 4); // 增加行数以适应更大的空间
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
        rightPanel.setPreferredSize(new Dimension(200, 1080)); // 调整右面板大小
    }

    private void initializePriceRange() {
        Set<String> prices = optionsMap.get("Price");
        double minPrice = prices.stream().mapToDouble(p -> Double.parseDouble(p)).min().getAsDouble();
        double maxPrice = prices.stream().mapToDouble(p -> Double.parseDouble(p)).max().getAsDouble();
        
        JPanel pricePanel = new JPanel(new BorderLayout(10, 0));
        pricePanel.setMaximumSize(new Dimension(1500, 60)); // 调整尺寸
        
        JSpinner minSpinner = new JSpinner(new SpinnerNumberModel(minPrice, minPrice, maxPrice, 50.0));
        JSpinner maxSpinner = new JSpinner(new SpinnerNumberModel(maxPrice, minPrice, maxPrice, 50.0));
        
        // 调整Spinner的大小
        Dimension spinnerSize = new Dimension(150, 30);
        minSpinner.setPreferredSize(spinnerSize);
        maxSpinner.setPreferredSize(spinnerSize);
        
        JPanel spinnerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        spinnerPanel.add(new JLabel("Min: $"));
        spinnerPanel.add(minSpinner);
        spinnerPanel.add(Box.createHorizontalStrut(20)); // 增加间距
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

        // 创建一个面板使用 GridBagLayout 来获得更好的控制
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setMaximumSize(new Dimension(1500, rows * 50));  // 增加行高
        String displayName = DISPLAY_NAMES.getOrDefault(category, category);
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY), 
                       displayName, TitledBorder.LEFT, TitledBorder.TOP, LABEL_FONT));

<<<<<<< HEAD
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);  // 增加内边距
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        int col = 0;
        int row = 0;
        
        // 计算需要的列数，确保选项分布均匀
        int totalColumns = 4;

        for (String option : options) {
            JCheckBox checkBox = new JCheckBox(option);
            checkBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            
            // 设置最小尺寸以确保复选框不会被压缩
            checkBox.setMinimumSize(new Dimension(200, 30));
            checkBox.setPreferredSize(new Dimension(350, 30));
            
            gbc.gridx = col;
            gbc.gridy = row;
            
            panel.add(checkBox, gbc);
            
            col++;
            if (col >= totalColumns) {
                col = 0;
                row++;
            }
=======
        for (String option : options) {
            JCheckBox checkBox = new JCheckBox(option);
            panel.add(checkBox);
>>>>>>> 3890a85790f8c0e9eef285b96f78fb9ab8080fd2
        }

        // 填充剩余空间
        while (col < totalColumns && row < rows) {
            gbc.gridx = col;
            gbc.gridy = row;
            panel.add(Box.createHorizontalStrut(350), gbc);
            col++;
            if (col >= totalColumns) {
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
        panel.setMaximumSize(new Dimension(1500, 40)); // 调整尺寸
        
        JCheckBox checkBox = new JCheckBox(category);
        checkBox.setFont(LABEL_FONT);
        panel.add(checkBox, BorderLayout.WEST);
        
        filterComponents.put(category, checkBox);
        leftPanel.add(panel);
        leftPanel.add(Box.createVerticalStrut(10));
    }

    private void layoutComponents() {
        // 创建一个面板来包含leftPanel，并设置其首选大小
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.add(leftPanel, BorderLayout.NORTH);
        containerPanel.setBackground(Color.WHITE);

        scrollPane = new JScrollPane(containerPanel);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        // 设置scrollPane的首选大小
        scrollPane.setPreferredSize(new Dimension(1600, 900));
        
        mainPanel.add(scrollPane, BorderLayout.CENTER);

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
        button.setMaximumSize(new Dimension(150, 40)); // 增大按钮尺寸
        button.setPreferredSize(new Dimension(150, 40));
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
<<<<<<< HEAD
}
=======
}
>>>>>>> 3890a85790f8c0e9eef285b96f78fb9ab8080fd2
