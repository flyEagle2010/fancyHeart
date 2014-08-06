/**
 * 
 */
package com.doteplay.editor.component.ImageList;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

/**
 * @author dell
 *
 */
public class ImageList extends JList {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8753645301120820316L;
	
	private double scaleNum = 1;
	
	/**
	 * This method initializes 
	 * 
	 */
	public ImageList() {
		super();
		
		ListCellRenderer cellRenderer=new DefaultListCellRenderer(){

			private static final long serialVersionUID = -3773059797617307351L;
			
			private IImageListItem imageListItem;
			private Rectangle rect;
			@Override
			public Component getListCellRendererComponent(JList arg0,
					Object arg1, int arg2, boolean arg3, boolean arg4) {
				// TODO Auto-generated method stub
				imageListItem=(IImageListItem)arg1;
				
				try
				{
				
				super.getListCellRendererComponent(arg0, arg1, arg2, arg3, arg4);
				this.setHorizontalAlignment(SwingConstants.CENTER);
				this.setVerticalAlignment(SwingConstants.BOTTOM);
//				this.setVerticalTextPosition(JLabel.BOTTOM);
//				this.setHorizontalTextPosition(JLabel.CENTER);
				this.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
				
				rect=imageListItem.getBounds();
//				System.out.println("IamgeList.getListCellRendererComponent: imageListItem="+imageListItem + ", rect="+rect);
				rect.width *= getScaleNum();
				rect.height *= getScaleNum();
				int w=rect.width;
				int h=rect.height;
				
				int max= (int)(imageListItem.getImageItemMaxBounds() * getScaleNum());
				if(w<max)
				{
					rect.x+=(w-max)/2;
					w=max;
				}
				if(h<max)
				{
					rect.y+=(h-max)/2;
					h=max;
				}
				this.setPreferredSize(new Dimension(w, h+20));
				this.setSize(w, h+20);
//				BufferedImage image=animFrameData.getImage(0, 0, -1, -1);
//				if(image!=null)
//					this.setIcon(new ImageIcon(image));
				}
				catch(Exception e)
				{
					System.out.println("IamgeList.getListCellRendererComponent: imageListItem="+imageListItem + ", rect="+rect);
					e.printStackTrace();
				}
				return this;
			}

			@Override
			protected void paintComponent(Graphics g) {
				// TODO Auto-generated method stub
				super.paintComponent(g);
				
				if(imageListItem!=null)
				{
					Graphics2D g2d=(Graphics2D)g;
					if (getScaleNum() == 1) {
//						imageListItem.draw(g2d, -rect.x, -rect.y, false);
						
						imageListItem.draw(g2d, rect.width/2, rect.height*4/5, false);
					}
					else {
						int x2 = (this.getWidth() - rect.width) / 2;
						int y2 = (this.getHeight() - rect.height) / 2;
						imageListItem.draw(g2d, x2, y2, false, getScaleNum());
					}
					
//					String s=imageListItem.getImageItemInfo();
//					if(s!=null && !s.equals(""))
//					{
//						Util.drawString(g2d, s, 0, 0,SwingConstants.LEFT,SwingConstants.TOP);
//					}
				}
				
//				System.out.println(rect);
			}
			
		};
		setCellRenderer(cellRenderer);
	}

	public double getScaleNum() {
		return scaleNum;
	}

	public void setScaleNum(double scaleNum) {
		this.scaleNum = scaleNum;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
