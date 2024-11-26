import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

public class LaptopStoreSurveyFrame extends JFrame {
    // Constants for styling
    private static final Color BACKGROUND_COLOR = new Color(248, 249, 250);
    private static final Color BUTTON_COLOR = new Color(13, 110, 253);
    private static final Color PANEL_COLOR = new Color(255, 255, 255);
    private static final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 24);
    private static final Font LABEL_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    
    // GUI Components
    private JPanel mainPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JScrollPane scrollPane;
    private JButton clearButton;
    private JButton backButton;
    private JButton continueButton;
    private JSlider priceSlider;
    
    // Form Components
    private JTextField brandField;
    private JSlider ratingSlider;
    private JTextField modelField;
    private ButtonGroup typeGroup;
    private JTextField cpuBrandField;
    private JTextField cpuModelField;
    private JSpinner coresSpinner;
    private JTextField speedField;
    private JSlider speedRatingSlider;
    private JSpinner ramSpinner;
    private JSpinner ssdSpinner;
    private JSlider storageRatingSlider;
    private JTextField gpuBrandField;
    private JTextField gpuModelField;
    private JSpinner usbPortsSpinner;
    private JTextField otherPortsField;
    private JTextField osField;
    private JTextField displaySizeField;
    private JTextField resolutionField;
    private JCheckBox touchscreenCheck;
    private JTextField weightField;
    private JTextField hyperlinkField;

    public LaptopStoreSurveyFrame() {
        setupMainFrame();
        initializeComponents();
        layoutComponents();
        addEventListeners();
    }

    private void setupMainFrame() {
        setTitle("DCS Laptops - Survey");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setContentPane(mainPanel);
    }

    private void initializeComponents() {
        // Initialize left panel with scroll pane
        leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(PANEL_COLOR);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Add components to left panel
        addFormComponents();

        scrollPane = new JScrollPane(leftPanel);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        
        // Initialize right panel with buttons
        rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(BACKGROUND_COLOR);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 20));
        
        // Initialize buttons with modern styling
        clearButton = createStyledButton("Clear");
        backButton = createStyledButton("Back");
        continueButton = createStyledButton("Continue");
    }

    private void addFormComponents() {
        // Add title
        JLabel titleLabel = new JLabel("What Kind of Laptop do you Prefer?");
        titleLabel.setFont(HEADER_FONT);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(titleLabel);
        leftPanel.add(Box.createVerticalStrut(20));

        // Add price range slider
        JLabel priceLabel = new JLabel("Price Range");
        priceLabel.setFont(LABEL_FONT);
        priceSlider = new JSlider(0, 5000, 1000);
        priceSlider.setMajorTickSpacing(1000);
        priceSlider.setPaintTicks(true);
        priceSlider.setPaintLabels(true);
        leftPanel.add(priceLabel);
        leftPanel.add(priceSlider);
        leftPanel.add(Box.createVerticalStrut(20));

        // Add brand preference radio buttons
        JLabel brandLabel = new JLabel("Brand Preference");
        brandLabel.setFont(LABEL_FONT);
        leftPanel.add(brandLabel);
        
        String[] brands = {"Apple", "Asus", "Lenovo", "ROG", "Acer", "HP"};
        ButtonGroup brandGroup = new ButtonGroup();
        JPanel brandPanel = new JPanel(new GridLayout(2, 3, 10, 5));
        brandPanel.setBackground(PANEL_COLOR);
        
        for (String brand : brands) {
            JRadioButton radioButton = new JRadioButton(brand);
            radioButton.setFont(LABEL_FONT);
            brandGroup.add(radioButton);
            brandPanel.add(radioButton);
        }
        leftPanel.add(brandPanel);
        leftPanel.add(Box.createVerticalStrut(20));

        // Add type selection
        JLabel typeLabel = new JLabel("Type");
        typeLabel.setFont(LABEL_FONT);
        leftPanel.add(typeLabel);
        
        String[] types = {"Professional", "Student", "Gaming"};
        typeGroup = new ButtonGroup();
        JPanel typePanel = new JPanel(new GridLayout(1, 3, 10, 5));
        typePanel.setBackground(PANEL_COLOR);
        
        for (String type : types) {
            JRadioButton radioButton = new JRadioButton(type);
            radioButton.setFont(LABEL_FONT);
            typeGroup.add(radioButton);
            typePanel.add(radioButton);
        }
        leftPanel.add(typePanel);
        
        // Add remaining form fields
        addFormField("Model", modelField = new JTextField());
        addFormField("CPU Brand", cpuBrandField = new JTextField());
        addFormField("CPU Model", cpuModelField = new JTextField());
        // ... Add other fields similarly
    }

    private void addFormField(String labelText, JComponent field) {
        JLabel label = new JLabel(labelText);
        label.setFont(LABEL_FONT);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(label);
        
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(field);
        leftPanel.add(Box.createVerticalStrut(10));
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(BUTTON_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setMaximumSize(new Dimension(150, 40));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(button.getBackground().darker());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(BUTTON_COLOR);
            }
        });
        
        return button;
    }

    private void layoutComponents() {
        // Add scroll pane to main panel
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Add buttons to right panel
        rightPanel.add(Box.createVerticalGlue());
        rightPanel.add(clearButton);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(backButton);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(continueButton);
        rightPanel.add(Box.createVerticalGlue());
        
        // Add right panel to main panel
        mainPanel.add(rightPanel, BorderLayout.EAST);
    }

    private void addEventListeners() {
        clearButton.addActionListener(e -> clearForm());
        backButton.addActionListener(e -> goBack());
        continueButton.addActionListener(e -> continue_());
    }

    private void clearForm() {
        // Implementation for clearing the form
    }

    private void goBack() {
        this.dispose();
        new LaptopStoreTitleFrame();
    }

    private void continue_() {
        // Implementation for continuing to next frame
    }
}