package org.xmlcml.norma.image.ocr;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;
import org.xmlcml.norma.NormaFixtures;

public class HOCRNeuroImageTest {

	/** have to use 
	 * tesseract image.2.1.Im0.png out hocr
	 * 
	 * to generate
	 * @throws IOException 
	 */
	@Test
	public void testReadImage() throws IOException {
		File image21 = new File(NormaFixtures.TEST_PUBSTYLE_DIR, "neuro/image.2.1.Im0.png");
		BufferedImage image = ImageIO.read(image21);
		int w = image.getWidth();
		int h = image.getHeight();
		BufferedImage image1 = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				int col = image.getRGB(i,  j);
				String s = Integer.toHexString(col);
				if (s.equals("ffffff")) {
//					System.out.println(s+" ");
					col = 0xffffff;
					image1.setRGB(i, j, col);
				}
			}
		}
		ImageIO.write(image1, "png", new File(image21.toString()+".png"));
	}
}
