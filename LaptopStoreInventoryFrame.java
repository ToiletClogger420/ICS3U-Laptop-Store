import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

public class LaptopStoreInventoryFrame {
	static String[] sortingMethods = { "Best Match", "Price Low-to-High", "Price High-to-Low", "Highest Overall Rating",
			"Highest Speed Rating", "Highest Storage Rating", "Highest Customer Reviews" };

	static JFrame frame = new JFrame();
	static JLabel invetoryHeading = new JLabel("         DCS Laptops - Inventory");
	static ImageIcon logo = new ImageIcon("images/logo_square.png");
	static JLabel logoLabel = new JLabel(logo);

	static JPanel panel = new JPanel();

	static Font headingFont = new Font("helvetica", Font.BOLD, 50);
	static Font subHeadingFont = new Font("helvetica", Font.BOLD, 25);

	static JButton toSurvey = new JButton("Survey");
	static JButton viewCart = new JButton("view cart");
	static JButton toTitle = new JButton("Back to Title");

    //Colours for aesthetics
    private static final Color buttonColour = new Color(13, 110, 253);
    private static final Color hoverColour = new Color(0, 85, 227);

	static HashMap<String, Integer> cart = new HashMap<>();

	static JComboBox sort = new JComboBox<>(sortingMethods);

	static JLabel filters = new JLabel("Filters");
	static JLabel pricing = new JLabel("Price:");
	static JCheckBox cheap = new JCheckBox("$0-$500");
	static JCheckBox moderate = new JCheckBox("$500-$1000");
	static JCheckBox expensive = new JCheckBox("$1000-$2000");
	static JLabel operatingSystem = new JLabel("Operating System:");
	static JCheckBox windows = new JCheckBox("windows");
	static JCheckBox mac = new JCheckBox("mac");
	static JCheckBox otherOS = new JCheckBox("other operating system");
	static JLabel processorBrand = new JLabel("Processor Brand:");
	static JCheckBox intel = new JCheckBox("intel");
	static JCheckBox amd = new JCheckBox("AMD");
	static JCheckBox apple = new JCheckBox("Apple");
	static JCheckBox otherProcessor = new JCheckBox("Other processor brand");
	static JLabel touchscreen = new JLabel("Touchscreen: ");
	static JCheckBox touchscreenYes = new JCheckBox("yes");
	static JCheckBox touchscreenNo = new JCheckBox("no");
	static JPanel checkboxPanel = new JPanel();
	static JButton submitFiltersSorting = new JButton("Submit");

	public LaptopStoreInventoryFrame() {
//        BufferedImage headerImage = ImageIO.read(new File("C:\\Users\\huget\\Downloads\\laptopStoreHeader.jpg"));
//        JLabel headerHolder = new JLabel(new ImageIcon(headerImage));
//        headerHolder.setBounds(0,0,1920,120);
//        frame.add(headerHolder);
		frame.setSize(1920, 1080);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setTitle("Laptop Inventory");
		frame.setLayout(null);

		//set image icon
       		setIconImage(logo.getImage());
		
		setupButton(submitFiltersSorting);

		filters.setFont(subHeadingFont);
		checkboxPanel.add(filters);
		checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.Y_AXIS));
		checkboxPanel.add(pricing);
		checkboxPanel.add(cheap);
		checkboxPanel.add(moderate);
		checkboxPanel.add(expensive);
		checkboxPanel.add(operatingSystem);
		checkboxPanel.add(windows);
		checkboxPanel.add(mac);
		checkboxPanel.add(otherOS);
		checkboxPanel.add(processorBrand);
		checkboxPanel.add(intel);
		checkboxPanel.add(amd);
		checkboxPanel.add(apple);
		checkboxPanel.add(otherProcessor);
		checkboxPanel.add(touchscreen);
		checkboxPanel.add(touchscreenYes);
		checkboxPanel.add(touchscreenNo);
		checkboxPanel.add(submitFiltersSorting);
		checkboxPanel.setBounds(1000, 190, 500, 500);
		frame.add(checkboxPanel);

		invetoryHeading.setFont(headingFont);
		invetoryHeading.setBackground(Color.black);
		invetoryHeading.setForeground(Color.white);
		invetoryHeading.setOpaque(true);
		invetoryHeading.setBounds(0, 0, 1900, 100);

		sort.setBounds(1000, 100, 250, 50);

		toSurvey.setBounds(50,105,300,40);
		toSurvey.setFont(subHeadingFont);
		toSurvey.addActionListener(e -> {
			frame.setVisible(false);
			new LaptopStoreSurveyFrame();
		});
        setupButton(toSurvey);
        

        toTitle.setBounds(550,105,300,40);
        toTitle.setFont(subHeadingFont);
		toTitle.addActionListener(e -> {
			frame.setVisible(false);
			new LaptopStoreTitleFrame();
		});
		setupButton(toTitle);

		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(Color.WHITE);
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		// panel.setPreferredSize(new Dimension(600, 1200));
		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		scrollPane.setBounds(0, 150, 1000, 700);
		scrollPane.setPreferredSize(new Dimension(1000, 300));
		scrollPane.getVerticalScrollBar().setUnitIncrement(13);

		logoLabel.setBounds(0, 0, 50, 50);

		frame.add(logoLabel);
		frame.add(invetoryHeading);
		frame.add(toSurvey);
		frame.add(toTitle);
		frame.add(sort);
		frame.add(scrollPane, BorderLayout.CENTER);

		for (int i = 0; i < 30; i++) {
			ImageIcon laptopImage = new ImageIcon("images/laptop"+i+".jpg");
			Image scaledImage = laptopImage.getImage().getScaledInstance(300, 250, Image.SCALE_SMOOTH);
			ImageIcon resizedImage = new ImageIcon(scaledImage);
			JLabel laptopImageLabel = new JLabel(resizedImage);
			panel.add(laptopImageLabel);
//            JButton button = new JButton("Add to cart");
//            var lambdaVariable = new Object(){int n = 0;};
//            button.addActionListener(e -> {
//                JOptionPane.showMessageDialog(null,"Laptop "+lambdaVariable.n+" added to cart");
//                lambdaVariable.n++;
//            });
//            panel.add(button);
		}

		frame.setVisible(true);

		
		
	}

    //button setup for swing buttons
	//Formatting
    private void setupButton(JButton button) {
        button.setForeground(Color.white);
        button.setBackground(buttonColour);
        button.setBorderPainted(false);
        button.setFocusPainted(false);

        //Add colour change when hovered
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColour);
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(buttonColour);
            }
        });
        
        frame.add(button);
    }
	
}
