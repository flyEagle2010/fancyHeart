package com.doteplay.editor.tools;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;

import com.doteplay.editor.EditorConfig;
import com.doteplay.editor.util.FileNameFilter;
import com.doteplay.editor.util.Util;

public class TileImageToolDialog extends JDialog {

	public int map_gw,map_gh;
	public int result=JOptionPane.CANCEL_OPTION;
	public Vector<TileRGBData> destData;  //  @jve:decl-index=0:
	public BufferedImage srcImage;  //  @jve:decl-index=0:
	public BufferedImage destImage;  //  @jve:decl-index=0:
	
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel gwLabel = null;
	private JTextField gridWidth_tf = null;
	private JLabel ghLabel = null;
	private JTextField gridHeight_tf = null;
	private JScrollPane srcScrollPane = null;
	private JScrollPane destScrollPane = null;
	private JPanel srcPanel = null;
	private JPanel destPanel = null;
	private JPanel jPanel = null;
	private JButton updateButton = null;
	private JPanel jPanel1 = null;
	private JToolBar jToolBar = null;
	private JButton openButton = null;
	private JButton saveButton = null;
	private JToggleButton gridToggleButton = null;
	private JLabel numLabel = null;
	private JTextField clipNumTextField = null;
	/**
	 * @param owner
	 */
	public TileImageToolDialog(Frame owner) {
		super(owner);
		initialize();
	}

	private void init(BufferedImage src)
	{
		srcImage=src;
		System.out.println("src:"+src.getWidth()+","+src.getHeight());
        srcPanel.setPreferredSize(new Dimension(src.getWidth(),src.getHeight()));
//		srcPanel.setSize(src.getWidth(), src.getHeight());
//		srcScrollPane.getViewport().setViewSize(new Dimension(src.getWidth(),src.getHeight()));
		srcScrollPane.revalidate();//.validate();
		destData=Util.clipImage2TileRGBList(src);

		clipNumTextField.setText(""+destData.size());
		
		updateDestImage();
		srcPanel.repaint();
	}
	private void updateDestImage()
	{
		map_gw=Integer.valueOf(gridWidth_tf.getText());
		map_gh=Integer.valueOf(gridHeight_tf.getText());
		if(map_gw>16)
			map_gw=16;
		if(map_gw<0)
			map_gw=0;
		if(map_gh>16)
			map_gh=16;
		if(map_gh<0)
			map_gh=0;
		int size=destData.size();
		if(map_gw*map_gh<size)
		{
			map_gh=size/map_gw;
			if(size%map_gw>0)
				map_gh++;
		}
		gridWidth_tf.setText(""+map_gw);
		gridHeight_tf.setText(""+map_gh);
		
		
		destImage=Util.createTileImage(destData,map_gw,map_gh);
//		destScrollPane.getViewport().setViewSize(new Dimension(destImage.getWidth(),destImage.getHeight()));
		destPanel.setPreferredSize(new Dimension(destImage.getWidth(),destImage.getHeight()));
		destScrollPane.revalidate();
//		System.out.println("dest:"+destImage.getWidth()+","+destImage.getHeight());
		//destPanel.invalidate();
		destPanel.repaint();
	}
	private void loadImage()
	{
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new FileNameFilter("png"));
		
		fc.setCurrentDirectory(new File("."));
        int returnVal = fc.showOpenDialog(this);

        if(returnVal == JFileChooser.APPROVE_OPTION)
        {
            File f=fc.getSelectedFile();
            byte[] t=Util.loadFile(f.getAbsolutePath());
            BufferedImage i=Util.loadBufferedImage(t);
            if(i==null)
            	return;
            init(i);
        }
	}
	private void saveTileImage()
	{
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new FileNameFilter("png"));
		
		fc.setCurrentDirectory(EditorConfig.CURRENT_DIR);
        int returnVal = fc.showSaveDialog(this);

        if(returnVal == JFileChooser.APPROVE_OPTION)
        {
        	try
        	{
        		File f=fc.getSelectedFile();
        		if(ImageIO.write(destImage, "png", f))
        		{
        			JOptionPane.showMessageDialog(this, "保存成功！");
        			return;
        		}
        	}
        	catch(Exception e)
        	{
        		
        	}
        	JOptionPane.showMessageDialog(this, "保存失败！");
        }
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(302, 461);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setResizable(true);
		this.setTitle("Tile图形工具");
		this.setContentPane(getJContentPane());
	}
	public void release()
	{
		srcImage=null;
		destImage=null;
	}
	private boolean makeData()
	{
		try
		{
			map_gw=Integer.valueOf(gridWidth_tf.getText());
			map_gh=Integer.valueOf(gridHeight_tf.getText());
			if(map_gw>200 || map_gh>200)
				return false;
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}

	public void drawSrc(Graphics2D g)
	{
		if(srcImage==null)
			return;
		g.drawImage(srcImage, 0, 0, null);
//		System.out.println("drawSrc");
	}
	
	public void drawDest(Graphics2D g)
	{
		if(srcImage==null || destImage==null)
			return;
		g.drawImage(destImage, 0, 0, null);
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			ghLabel = new JLabel();
			ghLabel.setText("高度：");
			ghLabel.setBounds(new Rectangle(72, 4, 39, 18));
			gwLabel = new JLabel();
			gwLabel.setText("宽度：");
			gwLabel.setBounds(new Rectangle(4, 4, 39, 18));
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJPanel1(), BorderLayout.CENTER);
			jContentPane.add(getJToolBar(), BorderLayout.NORTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes gridWidth_tf	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getGridWidth_tf() {
		if (gridWidth_tf == null) {
			gridWidth_tf = new JTextField();
			gridWidth_tf.setPreferredSize(new Dimension(80, 22));
			gridWidth_tf.setBounds(new Rectangle(44, 4, 25, 22));
			gridWidth_tf.setText("16");
		}
		return gridWidth_tf;
	}

	/**
	 * This method initializes gridHeight_tf	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getGridHeight_tf() {
		if (gridHeight_tf == null) {
			gridHeight_tf = new JTextField();
			gridHeight_tf.setPreferredSize(new Dimension(80, 22));
			gridHeight_tf.setBounds(new Rectangle(112, 4, 25, 22));
			gridHeight_tf.setText("16");
		}
		return gridHeight_tf;
	}

	/**
	 * This method initializes srcScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getSrcScrollPane() {
		if (srcScrollPane == null) {
			srcScrollPane = new JScrollPane();
			srcScrollPane.setPreferredSize(new Dimension(3, 160));
			srcScrollPane.setViewportView(getSrcPanel());
			srcScrollPane.setBorder(BorderFactory.createTitledBorder(null, "\u539f\u56fe\uff1a", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
		}
		return srcScrollPane;
	}

	/**
	 * This method initializes destScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getDestScrollPane() {
		if (destScrollPane == null) {
			destScrollPane = new JScrollPane();
			destScrollPane.setViewportView(getDestPanel());
			destScrollPane.setBorder(BorderFactory.createTitledBorder(null, "\u5207\u7247\u540e\uff1a", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
		}
		return destScrollPane;
	}

	/**
	 * This method initializes srcPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getSrcPanel() {
		if (srcPanel == null) {
			srcPanel = new JPanel(){
			    public void paintComponent(Graphics g)
			    {
			        super.paintComponent(g);
			        drawSrc((Graphics2D)g);
			    }

			};
			srcPanel.setLayout(new BorderLayout());
			srcPanel.setEnabled(true);
			srcPanel.setPreferredSize(new Dimension(10, 10));
		}
		return srcPanel;
	}

	/**
	 * This method initializes destPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getDestPanel() {
		if (destPanel == null) {
			destPanel = new JPanel(){
			    public void paintComponent(Graphics g)
			    {
			        super.paintComponent(g);
			        drawDest((Graphics2D)g);
			    }

			};
			destPanel.setLayout(new GridBagLayout());
		}
		return destPanel;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			numLabel = new JLabel();
			numLabel.setBounds(new Rectangle(204, 4, 53, 18));
			numLabel.setText("切片数：");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setPreferredSize(new Dimension(1, 30));
			jPanel.add(gwLabel, null);
			jPanel.add(getGridWidth_tf(), null);
			jPanel.add(ghLabel, null);
			jPanel.add(getGridHeight_tf(), null);
			jPanel.add(getUpdateButton(), null);
			jPanel.add(numLabel, null);
			jPanel.add(getClipNumTextField(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes updateButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getUpdateButton() {
		if (updateButton == null) {
			updateButton = new JButton();
			updateButton.setBounds(new Rectangle(140, 4, 61, 21));
			updateButton.setText("更新");
			updateButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					updateDestImage();
				}
			});
		}
		return updateButton;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			//jPanel1.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
			jPanel1.setPreferredSize(new Dimension(300, 360));
			jPanel1.add(getSrcScrollPane(), BorderLayout.NORTH);
			jPanel1.add(getDestScrollPane(), BorderLayout.CENTER);
			jPanel1.add(getJPanel(), BorderLayout.SOUTH);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jToolBar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getOpenButton());
			jToolBar.add(getSaveButton());
			jToolBar.add(getGridToggleButton());
		}
		return jToolBar;
	}

	/**
	 * This method initializes openButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getOpenButton() {
		if (openButton == null) {
			openButton = new JButton();
			openButton.setText("打开图形");
			openButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					loadImage();
				}
			});
		}
		return openButton;
	}

	/**
	 * This method initializes saveButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getSaveButton() {
		if (saveButton == null) {
			saveButton = new JButton();
			saveButton.setText("保存图形");
			saveButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					saveTileImage();
				}
			});
		}
		return saveButton;
	}

	/**
	 * This method initializes gridToggleButton	
	 * 	
	 * @return javax.swing.JToggleButton	
	 */
	private JToggleButton getGridToggleButton() {
		if (gridToggleButton == null) {
			gridToggleButton = new JToggleButton();
			gridToggleButton.setText("网格");
		}
		return gridToggleButton;
	}

	/**
	 * This method initializes clipNumTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getClipNumTextField() {
		if (clipNumTextField == null) {
			clipNumTextField = new JTextField();
			clipNumTextField.setBounds(new Rectangle(260, 4, 29, 22));
			clipNumTextField.setText("0");
		}
		return clipNumTextField;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
