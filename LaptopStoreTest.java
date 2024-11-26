import java.awt.event.*;
import javax.swing.*;

// LaptopStoreTest.java
public class LaptopStoreTest {
    public static void main(String[] args) {
        // 确保在EDT (Event Dispatch Thread)中运行GUI代码
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // 创建主窗口
                LaptopStoreTitleFrame mainFrame = new LaptopStoreTitleFrame();
                
                // 测试窗口是否可见
                System.out.println("Main frame visible: " + mainFrame.isVisible());
                
                // 测试窗口尺寸
                System.out.println("Window size: " + mainFrame.getSize().width + "x" + 
                                 mainFrame.getSize().height);
                
                // 测试标题
                System.out.println("Window title: " + mainFrame.getTitle());
                
                // 获取所有组件并验证它们是否存在
                java.awt.Component[] components = mainFrame.getContentPane().getComponents();
                System.out.println("\nTesting components:");
                for (java.awt.Component comp : components) {
                    System.out.println("Found component: " + comp.getClass().getSimpleName());
                }
            }
        });
    }
}