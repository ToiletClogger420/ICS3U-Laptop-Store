import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class LaptopStoreTitleFrame extends JFrame implements ActionListener {
    // Constants for styling
    private static final Color BACKGROUND_COLOR = new Color(248, 249, 250);
    private static final Color TITLE_COLOR = new Color(33, 37, 41);
    private static final Color BUTTON_COLOR = new Color(13, 110, 253);
    private static final Color BUTTON_HOVER_COLOR = new Color(0, 85, 227);
    private static final int TITLE_SIZE = 36;
    private static final int BUTTON_TEXT_SIZE = 16;
    
    // GUI elements
    private JLabel titleLabel = new JLabel("Laptop Store");
    private ImageIcon originalLogo = new ImageIcon("images/logo.png");
    private ImageIcon logo;
    private JLabel logoLabel;
    private JButton templateButton = new JButton("Template Frame");
    private JPanel mainPanel;

    // Constructor Method
    public LaptopStoreTitleFrame() {
        // 1. Setup the main panel with a modern look
        setupMainPanel();
        
        // 2. Configure and add components
        setupComponents();
        
        // 3. Final frame setup
        finalFrameSetup();
    }

    private void setupMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);
        
        // Add padding to the main panel
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        setContentPane(mainPanel);
    }

    private void setupComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        
        // Setup title with modern font and styling
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, TITLE_SIZE));
        titleLabel.setForeground(TITLE_COLOR);
        gbc.insets = new Insets(0, 0, 30, 0);
        mainPanel.add(titleLabel, gbc);

        // Setup logo with drop shadow
        Image scaledImage = originalLogo.getImage().getScaledInstance(300, 169, Image.SCALE_SMOOTH);
        logo = new ImageIcon(scaledImage);
        logoLabel = new JLabel(logo) {
            @Override
            protected void paintComponent(Graphics g) {
                // Create drop shadow effect
                int shadowGap = 5;
                int shadowOffset = 4;
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Draw shadow
                g2.setColor(new Color(0, 0, 0, 50));
                g2.fillRect(shadowOffset, shadowOffset, getWidth() - shadowGap, getHeight() - shadowGap);
                
                super.paintComponent(g);
            }
        };
        logoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        gbc.insets = new Insets(0, 0, 50, 0);
        mainPanel.add(logoLabel, gbc);

        // Setup modern-looking button
        setupButton();
        gbc.insets = new Insets(0, 0, 0, 0);
        mainPanel.add(templateButton, gbc);
    }

    private void setupButton() {
        templateButton.setPreferredSize(new Dimension(250, 60));
        templateButton.setFont(new Font("Segoe UI", Font.BOLD, BUTTON_TEXT_SIZE));
        templateButton.setForeground(Color.WHITE);
        templateButton.setBackground(BUTTON_COLOR);
        templateButton.setBorderPainted(false);
        templateButton.setFocusPainted(false);
        templateButton.setOpaque(true);
        
        // Create rounded corners
        templateButton.setBorder(new EmptyBorder(10, 25, 10, 25));
        templateButton.putClientProperty("JButton.buttonType", "roundRect");
        
        // Add hover effect
        templateButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                templateButton.setBackground(BUTTON_HOVER_COLOR);
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            
            public void mouseExited(MouseEvent e) {
                templateButton.setBackground(BUTTON_COLOR);
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        
        templateButton.addActionListener(this);
    }

    private void finalFrameSetup() {
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("App Name - Screen Title");
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == templateButton) {
            setVisible(false);
            new LaptopStoreTemplateFrame();
        }
    }
}

class LaptopStoreTemplateFrame extends JFrame {
    public LaptopStoreTemplateFrame() {
        setSize(1000, 700);
        setTitle("Template Frame");
        setLayout(new GridBagLayout());
        setLocationRelativeTo(null);
        setBackground(new Color(248, 249, 250));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}