package monitoringFunctions;

import java.awt.SystemColor;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class PopUpFunctions {

	

	public static void JFrameTest() {
		JFrame jf = new JFrame("Test");
		jf.getContentPane().setLayout(null);
		JTextArea comp = new JTextArea();
		comp.setBounds(100, 100, 200, 200);
		jf.getContentPane().add(comp);
		jf.getContentPane().setBackground(SystemColor.control);
		jf.setSize(500, 500);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(3);
	}

	public static void showMessageDialog(String[] args) { // 0 ❌,1 ℹ️,2 ⚠️,3 ❓,-1 (no icon)
		Object[] objects = { "Show Message Dialog" };
		JOptionPane.showMessageDialog(null, objects, "This is the title", 3);
	}

}
