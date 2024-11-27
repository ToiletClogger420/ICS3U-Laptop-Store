import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.util.Map;

public class LaptopStoreSurveyTest {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LaptopStoreSurveyFrame frame = new LaptopStoreSurveyFrame();
                
                // 添加窗口关闭监听器
                frame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        // 获取并打印过滤结果
                        Map<String, Object> filterResults = frame.getFilterValues();
                        System.out.println("\n=== Filter Results ===");
                        filterResults.forEach((key, value) -> {
                            System.out.println(key + ": " + value);
                        });
                        System.out.println("===================\n");
                    }
                });
                
                frame.setVisible(true);
            }
        });
    }
}