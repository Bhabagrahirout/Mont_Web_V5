// 
// Decompiled by Procyon v0.5.36
// 

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ComparingImages {
	public static void main(String[] args) throws Exception {
		BufferedImage img1 = ImageIO.read(new File(
				"/home/chandrakant.jadhav@apmosys.mahape/Documents/Difference between Selenium 3 vs Selenium 4.jpg"));
		BufferedImage img2 = ImageIO.read(new File(
				"/home/chandrakant.jadhav@apmosys.mahape/Documents/Difference between Selenium 3 vs Selenium 4 (copy).jpg"));
		int w1 = img1.getWidth();
		System.out.println(w1);
		int h1 = img1.getHeight();
		System.out.println(h1);
		int w2 = img2.getWidth();
		System.out.println(w2);
		int h2 = img2.getHeight();
		System.out.println(h2);
		if (w1 != w2 || h1 != h2) {
			System.out.println("Both images should have same dimwnsions");
		} else {
			long diff = 0L;
			int[][] result = new int[h1][w1];
			for (int row = 0; row < h1; ++row) {
				int col = 0;
				if (0 < w1) {
					result[row][0] = img1.getRGB(0, row);
					int pixel1 = result[row][0];
					Color color1 = new Color(pixel1, true);
					System.out.println(color1);
					int r1 = color1.getRed();
					int g1 = color1.getGreen();
					int b1 = color1.getBlue();
					System.out.println("RGB value get for 1st image");
					int[][] result2 = new int[h2][w2];
					int row2 = 0;
					if (0 < h2) {
						int col2 = 0;
						if (0 < w2) {
							result2[0][0] = img2.getRGB(0, 0);
							int pixel2 = result2[0][0];
							Color color2 = new Color(pixel2, true);
							System.out.println(color2);
							int r2 = color2.getRed();
							int g2 = color2.getGreen();
							int b2 = color2.getBlue();
							System.out.println("RGB value get for 2nd image");
							long data = Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2);
							System.out.println(data);
							diff += data;
							System.out.println(diff);
							double avg = (double) (diff / (w1 * h1 * 3));
							System.out.println(avg);
							double percentage = avg / 255.0 * 100.0;
							System.out.println("Difference: " + percentage);
						}
					}
				}
			}
		}
	}
}
