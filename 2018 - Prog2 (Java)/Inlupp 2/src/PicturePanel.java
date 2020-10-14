import javax.swing.*;
import java.awt.*;

public class PicturePanel extends JPanel {
	private ImageIcon picture;

	PicturePanel(String fileName) {
		picture = new ImageIcon(fileName);
		setLayout(null);
		int width = picture.getIconWidth();
		int height = picture.getIconHeight();
		setPreferredSize(new Dimension(width, height));
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(picture.getImage(), 0, 0, this);
	}

}
