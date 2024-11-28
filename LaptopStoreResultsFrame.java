
//Results frame
//displays the results for the user's survey results
//imports
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class LaptopStoreResultsFrame extends JFrame 

	//implement action listener
	implements ActionListener {
	
	//find the recommended laptops
	static int[] recommendedLaptop = findRecommended();
	
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
    JLabel name1 = new JLabel("LaptopStoreApplication.laptopArray[0].getName()");
    JLabel price1 = new JLabel("price");
    JButton specs1 = new JButton("Specifications");
    JButton toCart1 = new JButton("Add to cart");
    JPanel panel1 = new JPanel();
    
    //recommended 2
    JLabel recommended2 = new JLabel("Runner Up");
    JLabel name2 = new JLabel("name");
    JLabel price2 = new JLabel("price2");
    JButton specs2 = new JButton("Specifications");
    JButton toCart2 = new JButton("Add to cart");
    JPanel panel2 = new JPanel();
    
    //recommended 3
    JLabel recommended3 = new JLabel("Third Option");
    JLabel name3 = new JLabel("name");
    JLabel price3 = new JLabel("price3");
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
    
    private static int[] findRecommended() {
		
    	
    	
		return null;
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
		
		else if (event.getSource() == inventory) {
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
