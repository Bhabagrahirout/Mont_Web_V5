package majorFunctionality;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

public class PNGConfiguration {

	static final int TARGET_KB = 30;
	static final long TARGET_BYTES = TARGET_KB * 1024;

	public static void main(String[] args) throws Exception {

		String inputFolder = "/home/apmosys/Videos/Monitoring & Floor Automation/Img_Compress/BNK_Yes RIB Bill Payment Section Web";
		String outputFolder = "/home/apmosys/Videos/Monitoring & Floor Automation/Img_Compress/Cpmpressed";

		compressFolder(inputFolder, outputFolder);
	}

	static void compressFolder(String inputFolder, String outputFolder) throws Exception {

		Files.walk(Paths.get(inputFolder)).filter(Files::isRegularFile).forEach(path -> {

			String name = path.getFileName().toString().toLowerCase();

			if (!name.endsWith(".png"))
				return;

			try {

				Path rel = Paths.get(inputFolder).relativize(path.getParent());
				Path outDir = Paths.get(outputFolder).resolve(rel);

				String outName = name.replace(".png", "_30kb.png");

				Path outPath = outDir.resolve(outName);

				long size = compressImage(path.toFile(), outPath.toFile());

				System.out.println("✅ " + path + " -> " + outPath + " (" + size / 1024.0 + " KB)");

			} catch (Exception e) {
				
				e.printStackTrace();
				System.out.println("❌ Failed: " + path);
			}
		});
	}

	static long compressImage(File input, File output) throws Exception {

		BufferedImage img = ImageIO.read(input);

		BufferedImage rgb = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g = rgb.createGraphics();
		g.drawImage(img, 0, 0, null);
		g.dispose();

		int minQ = 10;
		int maxQ = 95;

		byte[] bestData = null;

		int lo = minQ;
		int hi = maxQ;

		while (lo <= hi) {

			int mid = (lo + hi) / 2;

			byte[] data = saveJPEG(rgb, mid);

			if (data.length <= TARGET_BYTES) {
				bestData = data;
				lo = mid + 1;
			} else {
				hi = mid - 1;
			}
		}

		if (bestData == null) {

			int w = rgb.getWidth();
			int h = rgb.getHeight();

			while (true) {

				w = (int) (w * 0.9);
				h = (int) (h * 0.9);

				if (w < 200 || h < 200) {
					bestData = saveJPEG(rgb, minQ);
					break;
				}

				Image tmp = rgb.getScaledInstance(w, h, Image.SCALE_SMOOTH);

				BufferedImage resized = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

				Graphics2D g2 = resized.createGraphics();
				g2.drawImage(tmp, 0, 0, null);
				g2.dispose();

				lo = minQ;
				hi = maxQ;

				while (lo <= hi) {

					int mid = (lo + hi) / 2;

					byte[] data = saveJPEG(resized, mid);

					if (data.length <= TARGET_BYTES) {
						bestData = data;
						lo = mid + 1;
					} else {
						hi = mid - 1;
					}
				}

				if (bestData != null)
					break;
			}
		}

		output.getParentFile().mkdirs();

		FileOutputStream fos = new FileOutputStream(output);
		fos.write(bestData);
		fos.close();

		return bestData.length;
	}

	static byte[] saveJPEG(BufferedImage img, int quality) throws Exception {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		javax.imageio.ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();

		javax.imageio.stream.ImageOutputStream ios = ImageIO.createImageOutputStream(baos);
		writer.setOutput(ios);

		javax.imageio.ImageWriteParam param = writer.getDefaultWriteParam();

		param.setCompressionMode(javax.imageio.ImageWriteParam.MODE_EXPLICIT);
		param.setCompressionQuality(quality / 100f);

		writer.write(null, new javax.imageio.IIOImage(img, null, null), param);

		ios.close();
		writer.dispose();

		return baos.toByteArray();
	}
}