/**
 * @package com.doteplay.editor.component
 * @file ShowImageDialog.java
 */
package com.doteplay.editor.component;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.doteplay.editor.EditorConfig;
import com.doteplay.editor.util.GUtil;

/**
 */
public class ShowImageDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private BufferedImage img;
	private JScrollPane jScrollPane = null;
	private JPanel canvas = null;
	private JToolBar jToolBar = null;
	private JButton saveButton = null;

	/**
	 * @param owner
	 */
	public ShowImageDialog(Frame owner) {
		super(owner);
		initialize();
	}

	public static void showDialog(Frame owner, BufferedImage img) {
		ShowImageDialog dialog = new ShowImageDialog(owner);
		dialog.setImg(img);
		dialog.setVisible(true);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(800, 600);
		this.setModal(true);
		this.setContentPane(getJContentPane());
	}

	protected void draw(Graphics g) {
		if (img == null) {
			return;
		}
		g.drawImage(img, 0, 0, null);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJScrollPane(), BorderLayout.CENTER);
			jContentPane.add(getJToolBar(), BorderLayout.NORTH);
		}
		return jContentPane;
	}

	public void setImg(BufferedImage img) {
		if(img == null){
			return;
		}
		this.img = img;
		canvas = new JPanel() {
			/** serialVersionUID */
			private static final long serialVersionUID = 1L;

			@Override
			public void paint(Graphics g) {
				super.paint(g);
				draw(g);
			}
		};
		canvas.setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
		jScrollPane.setViewportView(canvas);
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jToolBar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getSaveButton());
		}
		return jToolBar;
	}

	/**
	 * This method initializes saveButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getSaveButton() {
		if (saveButton == null) {
			saveButton = new JButton();
			saveButton.setText("Save");
			saveButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser chooser = new JFileChooser();
					chooser.setDialogType(JFileChooser.SAVE_DIALOG);
					FileNameExtensionFilter filter = new FileNameExtensionFilter("png", "png");
					chooser.addChoosableFileFilter(filter);
					filter = new FileNameExtensionFilter("jpg", "jpg");
					chooser.addChoosableFileFilter(filter);
					filter = new FileNameExtensionFilter("gif", "gif");
					chooser.addChoosableFileFilter(filter);
					
					int returnVal = chooser.showOpenDialog(jContentPane);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						File f = chooser.getSelectedFile();
						String fileName = f.getName();
						String[] s = fileName.split("\\.");
						String ss = "";
						if (s.length < 2) {
							ss = chooser.getFileFilter().getDescription();
							fileName = fileName + "." + ss;
						}
						
						String filePath = f.getParent()+"\\"+fileName;
						System.out.println(filePath);
						f = new File(filePath);
						try {
							FileOutputStream out = new FileOutputStream(f);
							if(fileName.endsWith(".png")){
								GUtil.saveBufferedImage2Png24(img, out);
							}else if(fileName.endsWith(".jpg")){
								GUtil.saveBufferedImage2Jpg(img, EditorConfig.JPG_QUALITY, out);
								System.out.println("JPG_QUALITY = "+EditorConfig.JPG_QUALITY);
							}else if(fileName.endsWith(".gif")){
								GUtil.saveBufferedImage2Gif(img, out);
							}
							out.close();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						
					}
				}
			});
		}
		return saveButton;
	}
}
