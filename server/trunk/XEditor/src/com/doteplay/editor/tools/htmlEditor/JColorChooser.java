package com.doteplay.editor.tools.htmlEditor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.BevelBorder;
/**
 * 
 * 颜色选择器
 */
public class JColorChooser extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jPanel = null;
	private static JColorChooser jColorChooser;
	/**
	 * 选中的颜色
	 */
	private static Color chooserColor;  //  @jve:decl-index=0:
	/**
	 * 颜色框中选择颜色
	 */
	private Color color;
	/**
	 * 颜色面板值
	 */
	private static int[] colorValue = {
		0x00ff00, 0xa335ee, 0xfefab0, 0xf3d42e, 0x0070dd, 0xffff00, 0x0fccd2, 0x0ecdd1, 0xffffff, 0xffffff,
		0xff0000, 0x00ff00, 0x0000ff, 0xffffff, 0xffffff, 0xffffff, 0xffffff, 0xffffff, 0xffffff, 0xffffff,
	};
	private JPanel jPanelPreview = null;
	private JPanel jPanelColor = null;
	private JLabel jLabelRGB = null;
	private JButton jButtonOK = null;
	private JButton jButtonCancel = null;
	private JTextPane jTextPane = null;
	
	public JColorChooser(Color color) {
		super();
		this.color = color;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(374, 288));
        this.setContentPane(getJPanel());
        this.setResizable(false);
        this.setModal(true);
        this.setTitle("颜色选择面板");
        addJPanel();
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(getJPanelPreview(), null);
			jPanel.add(getJButtonOK(), null);
			jPanel.add(getJButtonCancel(), null);
			jPanel.addMouseListener(new java.awt.event.MouseAdapter() {
	        	public void mousePressed(java.awt.event.MouseEvent e) {
	        		JPanel jp = (JPanel)jPanel.getComponentAt(e.getPoint());
	        		color = jp.getBackground();
	        		setPreviewColor();
	        	}
	        });
		}
		return jPanel;
	}
	private void setPreviewColor() {
		jPanelPreview.setBorder(BorderFactory.createLineBorder(color, 5));
		jPanelColor.setBackground(color);
		jTextPane.setCaretColor(color);
		setTextColor(color);
		jLabelRGB.setText("R = "+color.getRed()+",G = "+color.getGreen()+",B = "+color.getBlue());
	}
	public static Color showDialog(Component component, String title, Color initialColor) throws HeadlessException {
		if(jColorChooser == null) {
			jColorChooser = new JColorChooser(initialColor != null? initialColor : Color.BLACK);
			jColorChooser.setTitle(title);
		}
		jColorChooser.setLocationRelativeTo(component);
		jColorChooser.setVisible(true);
		return chooserColor;
	}
	
	private void addJPanel() {
		int x = 30,y = 10;
		int width = 30,height = 20;
		int rowNum = 10;
		for (int i = 0; i < colorValue.length; i++) {
			JPanel jp = new JPanel();
			jp.setBounds(new Rectangle(x + (i % rowNum) * width, y + (i / rowNum) * height, width, height));
			jp.setBackground(new Color(colorValue[i]));
			jp.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));//createLineBorder方法指定边界的颜色与宽度.
			jp.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			jPanel.add(jp);
		}
	}
	

	/**
	 * This method initializes jPanelPreview	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelPreview() {
		if (jPanelPreview == null) {
			jLabelRGB = new JLabel();
			jLabelRGB.setBounds(new Rectangle(100, 15, 142, 18));
			jLabelRGB.setText("RGB:");
			jPanelPreview = new JPanel();
			jPanelPreview.setLayout(null);
			jPanelPreview.setBounds(new Rectangle(50, 85, 262, 106));
			jPanelPreview.setBorder(BorderFactory.createLineBorder(color, 5));
			jPanelPreview.add(getJPanelColor(), null);
			jPanelPreview.add(jLabelRGB, null);
			jPanelPreview.add(getJTextPane(), null);
		}
		return jPanelPreview;
	}

	/**
	 * This method initializes jPanelColor	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelColor() {
		if (jPanelColor == null) {
			jPanelColor = new JPanel();
			jPanelColor.setBackground(color);
			jPanelColor.setLayout(null);
			jPanelColor.setBounds(new Rectangle(21, 29, 68, 52));
			jPanelColor.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		}
		return jPanelColor;
	}

	/**
	 * This method initializes jButtonOK	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonOK() {
		if (jButtonOK == null) {
			jButtonOK = new JButton();
			jButtonOK.setBounds(new Rectangle(40, 215, 90, 30));
			jButtonOK.setText("确定");
			jButtonOK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					jColorChooser.setVisible(false);
					chooserColor = color;
				}
			});
		}
		return jButtonOK;
	}

	/**
	 * This method initializes jButtonCancel	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonCancel() {
		if (jButtonCancel == null) {
			jButtonCancel = new JButton();
			jButtonCancel.setBounds(new Rectangle(220, 215, 90, 30));
			jButtonCancel.setText("取消");
			jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					jColorChooser.setVisible(false);
					chooserColor = null;
				}
			});
		}
		return jButtonCancel;
	}

	/**
	 * This method initializes jEditorPane	
	 * 	
	 * @return javax.swing.JEditorPane	
	 */
	private JTextPane getJTextPane() {
		if (jTextPane == null) {
			jTextPane = new JTextPane();
			jTextPane.setBounds(new Rectangle(99, 41, 146, 54));
			jTextPane.setText("预览效果");
			jTextPane.setEditable(false);
			if(color != null)
				setTextColor(color);
		}
		return jTextPane;
	}
	/**
	 * 设置预览演示文字颜色
	 * @param color
	 */
	private void setTextColor(Color color) {
		jTextPane.setSelectionStart(0);
		jTextPane.setSelectionEnd(jTextPane.getText().length());
		Tools.setFontForegroundColor(jTextPane, color);
//		System.out.println("R = "+color.getRed()+",G = "+color.getGreen()+",B = "+color.getBlue()+",十六进制："+Integer.toHexString(color.getRGB()));
		jTextPane.setSelectionEnd(0);
	}
	
}  //  @jve:decl-index=0:visual-constraint="10,10"
