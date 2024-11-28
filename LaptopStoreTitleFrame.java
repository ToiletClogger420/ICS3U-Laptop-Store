import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class LaptopStoreTitleFrame extends JFrame implements ActionListener {
    private static final Color BACKGROUND_COLOR = new Color(248, 249, 250);
    private static final Color TITLE_COLOR = new Color(33, 37, 41);
    private static final Color BUTTON_COLOR = new Color(13, 110, 253);
    private static final Color BUTTON_HOVER_COLOR = new Color(0, 85, 227);
    private static final int TITLE_SIZE = 48; // 增大标题字体
    private static final int BUTTON_TEXT_SIZE = 20; // 增大按钮字体
    
    private JLabel titleLabel = new JLabel("DCS Computers - Laptop Store");
    private ImageIcon originalLogo = new ImageIcon("images/logo.png");
    private ImageIcon logo;
    private JLabel logoLabel;
    private JButton surveyButton = new JButton("Survey");
    private JButton inventoryButton = new JButton("Inventory");
    private JPanel mainPanel;
    private JPanel buttonPanel;

    public LaptopStoreTitleFrame() {
        setupMainPanel();
        setupComponents();
        finalFrameSetup();
    }

    private void setupMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 60)); // 增加边距
        setContentPane(mainPanel);
    }

    private void setupComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, TITLE_SIZE));
        titleLabel.setForeground(TITLE_COLOR);
        gbc.insets = new Insets(0, 0, 50, 0); // 增加标题下方间距
        mainPanel.add(titleLabel, gbc);

        // 增大 logo 尺寸
        Image scaledImage = originalLogo.getImage().getScaledInstance(450, 254, Image.SCALE_SMOOTH);
        logo = new ImageIcon(scaledImage);
        logoLabel = new JLabel(logo) {
            @Override
            protected void paintComponent(Graphics g) {
                int shadowGap = 8; // 增大阴影
                int shadowOffset = 6; // 增大阴影偏移
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 0, 0, 50));
                g2.fillRect(shadowOffset, shadowOffset, getWidth() - shadowGap, getHeight() - shadowGap);
                super.paintComponent(g);
            }
        };
        logoLabel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // 增加logo内边距
        gbc.insets = new Insets(0, 0, 70, 0); // 增加logo下方间距
        mainPanel.add(logoLabel, gbc);

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 0)); // 增加按钮间距
        buttonPanel.setBackground(BACKGROUND_COLOR);

        setupButton(surveyButton);
        setupButton(inventoryButton);
        
        buttonPanel.add(surveyButton);
        buttonPanel.add(inventoryButton);

        gbc.insets = new Insets(0, 0, 0, 0);
        mainPanel.add(buttonPanel, gbc);
    }

    private void setupButton(JButton button) {
        button.setPreferredSize(new Dimension(350, 80)); // 增大按钮尺寸
        button.setFont(new Font("Segoe UI", Font.BOLD, BUTTON_TEXT_SIZE));
        button.setForeground(Color.WHITE);
        button.setBackground(BUTTON_COLOR);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(true);
        
        button.setBorder(new EmptyBorder(15, 35, 15, 35)); // 增加按钮内边距
        button.putClientProperty("JButton.buttonType", "roundRect");
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(BUTTON_HOVER_COLOR);
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            
            public void mouseExited(MouseEvent e) {
                button.setBackground(BUTTON_COLOR);
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        
        button.addActionListener(this);
    }

    private void finalFrameSetup() {
        setSize(1920, 1080);

        // setting logo of window
        ImageIcon icon = new ImageIcon("images/logo_square.png");
        Image scaledIcon = icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        setIconImage(scaledIcon);
        
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Laptop Store");
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        setVisible(false);
        if (event.getSource() == surveyButton) {
            new LaptopStoreSurveyFrame();
        } else if (event.getSource() == inventoryButton) {
            new LaptopStoreInventoryFrame();
        }
    }
}
