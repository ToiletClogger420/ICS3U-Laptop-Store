import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

public class LaptopStoreSurveyTest {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LaptopStoreSurveyFrame frame = new LaptopStoreSurveyFrame();
                frame.setVisible(true);
            }
        });
    }
}