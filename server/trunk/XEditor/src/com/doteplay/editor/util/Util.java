package com.doteplay.editor.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.IndexColorModel;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Stack;
import java.util.Vector;
import java.util.zip.Deflater;

import javax.imageio.ImageIO;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.doteplay.editor.EditorConfig;
import com.doteplay.editor.XEditor;
import com.doteplay.editor.common.BaseData;
import com.doteplay.editor.common.DataManager;
import com.doteplay.editor.dialog.MultiSelectionBaseDataDialog;
import com.doteplay.editor.tools.SelectObjectDialogList;
import com.doteplay.editor.tools.TileRGBData;

public class Util
{
	public Util()
	{
	}

	public static Random random = new Random(System.currentTimeMillis());

	public static Properties getProperties(String name)
	{
		Properties dbProps = new Properties();
		try
		{
			FileInputStream is = new FileInputStream(name);
			dbProps.load(is);
			is.close();
			return dbProps;
		} catch (Exception e)
		{
			System.err
					.println("不能读取属性文件. " + "请确保" + name + "在CLASSPATH指定的路径中");
		}
		return null;
	}

	public static final int random(int from, int to)
	{
		if (from >= to)
			return from;

		return random.nextInt(to - from) + from;
	}

	public static final Vector<BaseData> selectDatas(JFrame jframe, String gid)
	{
		MultiSelectionBaseDataDialog mbdd = new MultiSelectionBaseDataDialog(
				jframe, gid);
		showCenteredDialog(mbdd, null);
		Vector<BaseData> bds = mbdd.getBaseDatas();
		return bds;
	}

	public static int roundDivide(int i, int j)
	{
		return Math.round((float) i / (float) j);
	}

	public static byte[] loadFile(String path)
	{
		return loadFile(new File(path));
	}

	public static byte[] loadFile(File file)
	{
		// ByteArrayInputStream in=new ByteArrayInputStream();
		byte[] abyte1 = null;
		try
		{
			// File file = new File(path);
			if (!file.exists())
				return null;
			byte abyte0[] = new byte[2048];
			DataInputStream inputstream = new DataInputStream(
					new FileInputStream(file));
			ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
			for (int i1 = 0; (i1 = inputstream.read(abyte0)) != -1;)
				bytearrayoutputstream.write(abyte0, 0, i1);

			inputstream.close();
			inputstream = null;
			abyte0 = null;
			abyte1 = bytearrayoutputstream.toByteArray();
			bytearrayoutputstream.close();
			bytearrayoutputstream = null;
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return abyte1;
	}

	public static Vector<String[]> loadCSVFile(File file)
	{
		Vector<String[]> v = new Vector<String[]>();
		String[] ss = null;
		String s, s1;
		int i, len;
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			// 读取标题
			s = reader.readLine();
			ss = s.split(",", -1);
			v.addElement(ss);
			len = ss.length;
			System.out.println(len);
			while (true)
			{
				s = reader.readLine();
				if (s == null)
					break;
				if (s.equals(""))
					continue;

				System.out.println("-----------------------------" + v.size());
				ss = decodeCSVLine(s, len);
				// for(i=0;i<len;i++)
				// {
				// ss[i]=ss[i].replaceAll("\"\"", "\"");
				// if(i==0)
				// System.out.print(ss[i]);
				// else
				// System.out.print(" | "+ss[i]);
				// }
				v.addElement(ss);
			}

			reader.close();
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return v;
	}

	private static String encodeCSVString(String s)
	{

		String s1 = s.replaceAll("\"", "\"\"");
		if (s1.indexOf(",") >= 0)
			s1 = "\"" + s1 + "\"";
		return s1;
	}

	private static String[] decodeCSVLine(String s, int len)
	{
		String[] ss = new String[len];
		int k = 0;
		int size = s.length();
		String s1;
		char c;
		int start = 0;
		boolean inString = false;
		boolean haveString = false;
		for (int i = 0; i < size; i++)
		{
			c = s.charAt(i);
			if (c == '\"')
			{
				if (i == start)
				{
					inString = true;
					haveString = true;
				} else if (inString)
				{
					if (i < size - 1)
					{
						c = s.charAt(i + 1);
						if (c != '\"')
						{
							inString = false;
						} else
						{
							i++;
						}
					} else
					{
						inString = false;
					}
				}
			} else if (c == ',')
			{
				if (!inString)
				{
					if (haveString)
						s1 = s.substring(start + 1, i - 1);
					else
						s1 = s.substring(start, i);
					haveString = false;
					s1 = s1.replaceAll("\"\"", "\"");
					System.out.println(k + ":" + s1);
					ss[k++] = s1;
					start = i + 1;
				}
			}
		}
		s1 = s.substring(start);
		System.out.println(k + ":" + s1);
		ss[k++] = s1;

		return ss;
	}

	public static boolean saveFile(String path, byte[] data)
	{
		File file = new File(path);

		// System.out.println("saveFile:path=" + path);

		return saveFile(file, data);
	}

	public static boolean saveFile(File file, byte[] data)
	{
		try
		{
			DataOutputStream outputstream = new DataOutputStream(
					new FileOutputStream(file));
			outputstream.write(data);
			outputstream.close();
		} catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean saveBufferedImage(String filePath, BufferedImage image)
	{
		// File saveFile = new File(filePath);
		// if (!saveFile.exists() || !saveFile.isFile()) {
		// return false;
		// }
		return saveBufferedImage(new File(filePath), image);
	}

	public static boolean saveBufferedImage(File file, BufferedImage image)
	{
		return saveBufferedImage(file, image, "png");
	}

	public static boolean saveBufferedImage(File file, BufferedImage image,
			String formatName)
	{
		try
		{
			DataOutputStream outputstream = new DataOutputStream(
					new FileOutputStream(file));
			ImageIO.write(image, formatName, outputstream);
			outputstream.close();
		} catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static byte[] saveBufferedImage(BufferedImage image)
	{
		return saveBufferedImage(image, "png");
	}

	public static byte[] saveBufferedImage(BufferedImage image,
			String formatName)
	{
		try
		{
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(bout);

			ImageIO.write(image, formatName, out);

			out.flush();
			out.close();

			return bout.toByteArray();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static BufferedImage loadBufferedImage(byte[] data)
	{
		try
		{
			BufferedImage img = ImageIO.read(new ByteArrayInputStream(data));
			// System.out.println(img.getWidth()+","+img.getHeight());
			return img;
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public static BufferedImage loadBufferedImage(File file)
	{
		FileInputStream in = null;
		try
		{
			in = new FileInputStream(file);
			BufferedImage img = ImageIO.read(in);// new
			in.close();
			in = null;
			return img;
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		} finally
		{
			if (in != null)
			{
				try
				{
					in.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
				in = null;
			}
		}
	}

	public static BufferedImage loadBufferedImage(String path)
	{
		try
		{
			// byte[] data=loadFile(path);
			// if(data==null)
			// return null;
			FileInputStream in = new FileInputStream(path);
			BufferedImage img = ImageIO.read(in);// new
			in.close();
			in = null;
			// ByteArrayInputStream(data));
			// System.out.println(img.getWidth()+","+img.getHeight());
			return img;
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public static Object loadClassByName(String cn)
	{
		try
		{
			Object b = Class.forName(cn).newInstance();
			return b;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	// function
	public static int getNextBlock(int b)
	{
		int blockSign = b;
		blockSign++;
		if (blockSign >= EditorConfig.BLOCKNUM)
			blockSign = 0;
		return blockSign;
	}

	public static int getPreBlock(int b)
	{
		int blockSign = b;
		blockSign--;
		if (blockSign < 0)
			blockSign = (EditorConfig.BLOCKNUM - 1);
		return blockSign;
	}

	// Swing函数
	public static void setListData(DefaultListModel m, Vector v)
	{
		m.clear();
		for (int i = 0; i < v.size(); i++)
		{
			m.addElement(v.get(i));
		}
	}

	public static void setListData(DefaultListModel m, List v)
	{
		m.clear();
		for (int i = 0; i < v.size(); i++)
		{
			m.addElement(v.get(i));
		}
	}

	public static void setComboBoxData(DefaultComboBoxModel m, Vector v)
	{
		m.removeAllElements();
		for (int i = 0; i < v.size(); i++)
		{
			m.addElement(v.get(i));
		}
	}

	public static void setComboBoxData(JComboBox comboBox, Object[] names,
			Object defaultValue)
	{
		comboBox.addItem(defaultValue);
		for (Object object : names)
		{
			comboBox.addItem(object);
		}
	}

	public static void setComboBoxData(JComboBox comboBox, Object[] names)
	{
		for (Object object : names)
		{
			comboBox.addItem(object);
		}
	}

	public static void showPanelDialog(Component c, JPanel panel)
	{
		JDialog dialog = new JDialog(XEditor.xEditor.getJFrame());
		dialog.setModal(true);
		dialog.setContentPane(panel);
		// dialog.setSize(panel.getSize());
		// dialog.setSize(panel.getPreferredSize());
		dialog.setPreferredSize(panel.getSize());
		Util.showCenteredDialog(dialog, c);
	}

	public static void showCenteredDialog(JDialog dialog, Component c)
	{
		showCenteredDialog(dialog, c, true);
	}

	public static void showCenteredDialog(JDialog dialog, Component c,
			boolean show)
	{
		dialog.pack();
		// newDialog.set
		Dimension d = dialog.getSize();
		if (c == null)
			c = XEditor.xEditor.getJFrame();
		// System.out.println(d);
		Dimension d1 = c.getSize();
		Point loc = c.getLocationOnScreen();

		int dx = (d1.width - d.width) / 2;
		int dy = (d1.height - d.height) / 2;

		if (dx < 10)
		{
			dx = 10;
		}

		if (dy < 10)
		{
			dy = 10;
		}

		// System.out.println(dx+","+dy);

		loc.translate(dx, dy);
		dialog.setLocation(loc);
		// newDialog.setSize(320,240);
		dialog.setVisible(show);
	}

	// 图形处理函数
	public static int TRANS_NONE = 0;
	public static int TRANS_ROT90 = 5;
	public static int TRANS_ROT180 = 3;// v+h 0011
	public static int TRANS_ROT270 = 6;
	public static int TRANS_MIRROR = 2;// h 0010 (2 for j2me)
	public static int TRANS_MIRROR_ROT90 = 7;
	public static int TRANS_MIRROR_ROT180 = 1;// v 0001 (1 for j2me)
	public static int TRANS_MIRROR_ROT270 = 4;
	public static final Color COLOR_SELECT = new Color(0x10FC18);
	public static final Color COLOR_ATTACK = new Color(255, 0, 0, 63);
	public static final Color COLOR_VIEW = new Color(0, 0, 255, 63);
	public static final Color COLOR_SHADOW = new Color(80, 80, 80, 255);
	public static final Color COLOR_BACK = Color.WHITE;
	public static final Color COLOR_FRONT = Color.BLACK;

	private static AffineTransform transform = new AffineTransform();

	public static final Rectangle getRectangle(int select_sx, int select_sy,
			int select_ex, int select_ey)
	{
		int sx = select_ex > select_sx ? select_sx : select_ex;
		int sy = select_ey > select_sy ? select_sy : select_ey;
		int ex = select_ex > select_sx ? select_ex : select_sx;
		int ey = select_ey > select_sy ? select_ey : select_sy;

		return new Rectangle(sx, sy, ex - sx, ey - sy);
	}

	public static final int getTransFlag(boolean h, boolean v)
	{
		int flag = 0;
		if (h)
			flag = flag | TRANS_MIRROR;
		if (v)
			flag = flag | TRANS_MIRROR_ROT180;
		// if(flag==TRANS_ROT180)
		// System.out.println("TRANS_ROT180");
		return flag;
	}

	public static final boolean getMirrorH(int flag)
	{
		return (flag & TRANS_MIRROR) > 0;
	}

	public static final boolean getMirrorV(int flag)
	{
		return (flag & TRANS_MIRROR_ROT180) > 0;
	}

	/**
	 * 水平翻转碰撞
	 * 
	 * @param b
	 * @return
	 */
	public static final int mirrorBlock(int b)
	{
		if (b < 0 || b > EditorConfig.BLOCK_FULL)
			return 0;
		if (b == 0 || b == EditorConfig.BLOCK_FULL)
			return b;
		int block = b;
		if (block < 9)
			block = (b % 2 == 1) ? b + 1 : b - 1;
		else
		{
			if (b == 10)
				block = 12;
			else if (b == 12)
				block = 10;
		}
		return block;
		// if (b % 2 == 1)
		// return b + 1;
		// else
		// return b - 1;
	}

	public static final int mirrorBlock(int b, boolean mh, boolean mv)
	{
		if (b < 0 || b > EditorConfig.BLOCK_FULL)
			return 0;
		if (b == 0 || b == EditorConfig.BLOCK_FULL)
			return b;
		int block = b;
		if (mh)
		{
			if (block < 9)
				block = (b % 2 == 1) ? b + 1 : b - 1;
			else
			{
				if (b == 10)
					block = 12;
				else if (b == 12)
					block = 10;
			}
		}
		if (mv)
		{
			if (block < 5)
				block = 5 - block;
			else if (block < 9)
				block = 13 - block;
			else if (block < 13)
			{
				if (block == 9)
					block = 11;
				else if (block == 11)
					block = 9;
			}
		}
		return block;
	}

	/**
	 * 基础Tile绘制函数
	 * 
	 * @param g
	 * @param img
	 * @param dx
	 * @param dy
	 * @param sgx
	 * @param sgy
	 * @param type
	 * @param alpha
	 */
	public static final void drawImageGrid(Graphics2D g, BufferedImage img,
			int dx, int dy, int sgx, int sgy, int type, boolean alpha)
	{
		drawImage(g, img, dx, dy, sgx / EditorConfig.GW, sgy / EditorConfig.GH,
				EditorConfig.GW, EditorConfig.GH, type, alpha);
	}

	/**
	 * 基础绘制函数
	 * 
	 * @param g
	 * @param img
	 * @param dx
	 * @param dy
	 */
	public static final void drawImage(Graphics2D g, BufferedImage img, int dx,
			int dy, boolean alpha)
	{
		drawImage(g, img, dx, dy, 0, 0, img.getWidth(), img.getHeight(), 0,
				alpha);
	}

	/**
	 * 带翻转参数的基础绘制函数
	 * 
	 * @param g
	 * @param img
	 * @param dx
	 * @param dy
	 * @param sx
	 * @param sy
	 * @param w
	 * @param h
	 * @param mh
	 * @param mv
	 * @param alpha
	 */
	public static final void drawImage(Graphics2D g, BufferedImage img, int dx,
			int dy, int sx, int sy, int w, int h, boolean mh, boolean mv,
			boolean alpha)
	{
		int type = Util.getTransFlag(mh, mv);
		drawImage(g, img, dx, dy, sx, sy, w, h, type, alpha);
	}

	/**
	 * 基础绘制函数
	 * 
	 * @param g
	 * @param img
	 * @param dx
	 * @param dy
	 * @param rect
	 * @param type
	 * @param alpha
	 */
	public static final void drawImage(Graphics2D g, BufferedImage img, int dx,
			int dy, Rectangle rect, int type, boolean alpha)
	{
		drawImage(g, img, dx, dy, rect.x, rect.y, rect.width, rect.height,
				type, alpha);
	}

	/**
	 * 基础绘制函数
	 * 
	 * @param g
	 * @param img
	 * @param dx
	 *            目标x
	 * @param dy
	 *            目标y
	 * @param sx
	 *            源x
	 * @param sy
	 *            源y
	 * @param w
	 *            源宽
	 * @param h
	 *            源高
	 * @param type
	 * @param composite
	 *            composite值
	 */
	public static final void drawImage(Graphics2D g, BufferedImage img, int dx,
			int dy, int sx, int sy, int w, int h, int type, Composite composite)
	{
		Composite oldComposite = g.getComposite();
		g.setComposite(composite);

		if (type == 0)
		{
			g.drawImage(img, dx, dy, dx + w, dy + h, sx, sy, sx + w, sy + h,
					null);
		} else if (type == TRANS_MIRROR)
		{
			g.drawImage(img, dx, dy, dx + w, dy + h, sx + w, sy, sx, sy + h,
					null);
		} else if (type == TRANS_MIRROR_ROT180)
		{
			g.drawImage(img, dx, dy, dx + w, dy + h, sx, sy + h, sx + w, sy,
					null);
		} else if (type == TRANS_ROT180)
		{
			g.drawImage(img, dx, dy, dx + w, dy + h, sx + w, sy + h, sx, sy,
					null);
		} else
		{
			Rectangle r = g.getClipBounds();

			// transform.setToIdentity();
			if (type == TRANS_MIRROR)
			{
				transform.setTransform(-1, 0, 0, 1, dx + sx + w, dy - sy);
				g.setClip(dx, dy, w, h);
			} else if (type == TRANS_MIRROR_ROT180)
			{
				transform.setTransform(1, 0, 0, -1, dx - sx, dy + sy + h);
				g.setClip(dx, dy, w, h);
			} else if (type == TRANS_ROT180)
			{
				transform.setTransform(-1, 0, 0, -1, dx + sx + w, dy + sy + h);
				g.setClip(dx, dy, w, h);
			} else if (type == TRANS_ROT90)
			{
				transform.setTransform(0, 1, -1, 0, dx + sy + h, dy - sx);
				g.setClip(dx, dy, h, w);
			} else if (type == TRANS_ROT270)
			{
				transform.setTransform(0, -1, 1, 0, dx - sy, dy + sx + w);
				g.setClip(dx, dy, h, w);
			} else if (type == TRANS_MIRROR_ROT90)
			{
				transform.setTransform(0, -1, -1, 0, dx + sy + h, dy + sx + w);
				g.setClip(dx, dy, h, w);
			} else if (type == TRANS_MIRROR_ROT270)
			{
				transform.setTransform(0, 1, 1, 0, dx - sy, dy - sx);
				g.setClip(dx, dy, h, w);
			}

			g.drawImage(img, transform, null);
			// if(r!=null)
			// g.setClip(r.x,r.y,r.width,r.height);
			g.setClip(null);
			// g.clipRect(1,1,1,1);
		}
		g.setComposite(oldComposite);
	}

	/**
	 * 基础绘制函数
	 * 
	 * @param g
	 * @param img
	 * @param dx
	 *            目标x
	 * @param dy
	 *            目标y
	 * @param sx
	 *            源x
	 * @param sy
	 *            源y
	 * @param w
	 *            源宽
	 * @param h
	 *            源高
	 * @param type
	 * @param alpha
	 *            alpha值(0-100)
	 */
	public static final void drawImage(Graphics2D g, BufferedImage img, int dx,
			int dy, int sx, int sy, int w, int h, int type, int alpha)
	{
		float a = (float) alpha / 100;
		AlphaComposite newComposite = AlphaComposite.getInstance(
				AlphaComposite.SRC_OVER, a);
		drawImage(g, img, dx, dy, sx, sy, w, h, type, newComposite);
	}

	/**
	 * 基础绘制函数
	 * 
	 * @param g
	 * @param img
	 * @param dx
	 *            目标x
	 * @param dy
	 *            目标y
	 * @param sx
	 *            源x
	 * @param sy
	 *            源y
	 * @param w
	 *            源宽
	 * @param h
	 *            源高
	 * @param type
	 * @param alpha
	 */
	public static final void drawImage(Graphics2D g, BufferedImage img, int dx,
			int dy, int sx, int sy, int w, int h, int type, boolean alpha)
	{
		if (alpha)
		{
			drawImage(g, img, dx, dy, sx, sy, w, h, type, halfAlpha);
		} else
		{
			drawImage(g, img, dx, dy, sx, sy, w, h, type, noAlpha);
		}
	}

	/**
	 * 原点绘制函数，目前无用
	 * 
	 * @param g
	 * @param dx
	 * @param dy
	 */
	public static final void drawOrigin(Graphics2D g, int dx, int dy)
	{
		BufferedImage img = EditorConfig.ORIGINIMG;
		int w = img.getWidth() >> 1;
		int h = img.getHeight() >> 1;
		g.drawImage(img, dx - w, dy - h, null);

	}

	/**
	 * 碰撞区域绘制函数
	 * 
	 * @param graphics
	 * @param block
	 * @param dx
	 * @param dy
	 * @param type
	 */
	public static final void drawBlock(Graphics graphics, int block, int dx,
			int dy, int type)
	{
		int sx = block * EditorConfig.GW;
		int sy = type * EditorConfig.GH;
		// int dx=gx<<EditorConfig.GWB;
		// int dy=gy<<EditorConfig.GHB;
		graphics.drawImage(EditorConfig.BLOCKIMG, dx, dy, dx + EditorConfig.GW,
				dy + EditorConfig.GH, sx, sy, sx + EditorConfig.GW, sy
						+ EditorConfig.GH, null);
	}

	/**
	 * 方块区域绘制函数
	 * 
	 * @param graphics
	 * @param dx
	 * @param dy
	 * @param type
	 */
	public static final void drawGridRect(Graphics graphics, int dx, int dy,
			int type)
	{
		int sx = (EditorConfig.BLOCKNUM - 1) * EditorConfig.GW;
		int sy = type * EditorConfig.GH;
		graphics.drawImage(EditorConfig.BLOCKIMG, dx, dy, dx + EditorConfig.GW,
				dy + EditorConfig.GH, sx, sy, sx + EditorConfig.GW, sy
						+ EditorConfig.GH, null);
	}

	/**
	 * 获取Tile图形
	 * 
	 * @param src
	 * @param gx
	 * @param gy
	 * @param flag
	 * @return
	 */
	public static final BufferedImage getTileImage(BufferedImage src, int gx,
			int gy, int flag)
	{
		BufferedImage img = new BufferedImage(EditorConfig.GW, EditorConfig.GH,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) img.getGraphics();
		drawImageGrid(g, src, 0, 0, gx, gy, flag, false);

		return img;
	}

	public static final AlphaComposite halfAlpha = AlphaComposite.getInstance(
			AlphaComposite.SRC_OVER, 0.5f);
	public static final AlphaComposite noAlpha = AlphaComposite.SrcOver;// .getInstance(AlphaComposite.SRC,

	// 1.0f);

	public static final void drawAlpha(Graphics2D g, BufferedImage src, int x,
			int y)
	{
		g.setComposite(halfAlpha);
		g.drawImage(src, x, y, null);
		g.setComposite(noAlpha);
	}

	/**
	 * 获取Tile图形对象(索引色)
	 * 
	 * @param image
	 * @param tw
	 * @param th
	 * @param type
	 *            0:统一色盘 1:索引色盘
	 * @return
	 */
	public static final Vector<BufferedImage> getTileImageList(
			BufferedImage image, int tw, int th, int type)
	{
		int w = image.getWidth();
		int h = image.getHeight();
		int xnum = (byte) (w / tw);
		int ynum = (byte) (h / th);

		Vector<BufferedImage> v = new Vector<BufferedImage>();

		IndexColorModel cm;
		// cm=(IndexColorModel)image.getColorModel();
		cm = getPalette(image);
		System.out.println("IndexColorModel:" + cm);
		System.out.println("IndexColorModel:" + cm.getMapSize());
		System.out.println("IndexColorModel:" + cm.getTransferType());
		// System.out.println("IndexColorModel:"+cm.getTransparency()+","+cm.getTransparentPixel());

		TileRGBData[][] tileRGBDataList = getTileRGBs(image, tw, th);
		TileRGBData tileRGBData;
		int i, j;
		BufferedImage tileImage;
		for (i = 0; i < ynum; i++)
		{
			for (j = 0; j < xnum; j++)
			{

				tileRGBData = tileRGBDataList[i][j];
				if (isTileRGBEmpty(tileRGBData.rgbArray))
					continue;

				if (type == 1)
					cm = getPalette(tileRGBData.rgbArray);

				System.out.println("getTileImageList: i=" + i + ",j=" + j + ","
						+ cm.getMapSize() + "," + cm.getPixelSize());
				if (cm.getPixelSize() == 8)
					tileImage = new BufferedImage(tw, th,
							BufferedImage.TYPE_BYTE_INDEXED, cm);
				else if (cm.getPixelSize() == 4)
					tileImage = new BufferedImage(tw, th,
							BufferedImage.TYPE_BYTE_BINARY, cm);
				else
					continue;
				tileImage.setRGB(0, 0, tw, th, tileRGBData.rgbArray, 0, tw);
				// Graphics2D g2d=tileImage.createGraphics();
				// g2d.drawImage(image,0,0,tw,th, j*tw, i*th, j*tw+tw, i*th+th,
				// null);
				// // g2d.drawImage(image,j*tw, i*th, tw, th, null);
				// g2d.dispose();
				v.addElement(tileImage);
			}
		}

		return v;
	}

	// /**
	// * Snag the pixels from an image.
	// */
	// private static int[][] getPixels(Image image) throws IOException {
	// int w = image.getWidth(null);
	// int h = image.getHeight(null);
	// int pix[] = new int[w * h];
	// PixelGrabber grabber = new PixelGrabber(image, 0, 0, w, h, pix, 0, w);
	//
	// try {
	// if (grabber.grabPixels() != true) {
	// throw new IOException("Grabber returned false: " +
	// grabber.status());
	// }
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	//
	// int pixels[][] = new int[w][h];
	// for (int x = w; x-- > 0; ) {
	// for (int y = h; y-- > 0; ) {
	// pixels[x][y] = pix[y * w + x];
	// }
	// }
	//
	// return pixels;
	// }

	public static final byte[] getIndexImageData(BufferedImage image)
	{
		BufferedImage img = getIndexImage(image);
		// ShowImageDialog.showDialog(null, image);
		return saveBufferedImage(img);
	}

	public static final BufferedImage getIndexImage(BufferedImage image)
	{
		return getIndexImage(image, 256);
	}

	public static final BufferedImage getIndexImage(BufferedImage image,
			int maxColorNum)
	{
		// if (cm == null)
		// cm = Util.getPalette(image);
		//
		// System.out.println("Util.getIndexImage: cm="+cm);
		//
		// BufferedImage tempImg;
		//
		// if(cm==null)
		// {
		// tempImg = new BufferedImage(image.getWidth(), image.getHeight(),
		// BufferedImage.TYPE_BYTE_INDEXED, null);
		// }
		// else
		// {
		// if (cm.getPixelSize() == 8)
		// tempImg = new BufferedImage(image.getWidth(), image.getHeight(),
		// BufferedImage.TYPE_BYTE_INDEXED, cm);
		// else if (cm.getPixelSize() == 4)
		// tempImg = new BufferedImage(image.getWidth(), image.getHeight(),
		// BufferedImage.TYPE_BYTE_BINARY, cm);
		// else
		// return null;
		// }

		// int maxColorNum=256;

		BufferedImage tempImg;
		int w = image.getWidth();
		int h = image.getHeight();
		int size = w * h;
		int rgb;
		int[] rgbs = new int[size];
		int[] rgbs1 = new int[size];

		image.getRGB(0, 0, w, h, rgbs, 0, w);

		// cm=getPalette(rgbs);
		boolean hasAlpha = false;
		int pixels[][] = new int[w][h];
		for (int x = w; x-- > 0;)
		{
			for (int y = h; y-- > 0;)
			{
				rgb = rgbs[y * w + x];
				if ((rgb >> 24) == 0)
				{
					hasAlpha = true;
					pixels[x][y] = 0;
				}

				pixels[x][y] = rgb;
			}
		}

		// quant
		int palette[] = Quantize.quantizeImage(pixels, maxColorNum - 1);
		int transIndex = 0;
		int palette1[] = palette;
		if (hasAlpha)
		{
			transIndex = palette.length;
			palette1 = new int[palette.length + 1];
			System.arraycopy(palette, 0, palette1, 0, palette.length);
			palette1[transIndex] = 0;
		}
		int colorDepth;
		if (palette1.length <= 16)
			colorDepth = 4;
		else
			colorDepth = 8;
		IndexColorModel cm = new IndexColorModel(colorDepth, palette1.length,
				palette1, 0, hasAlpha, transIndex, DataBuffer.TYPE_BYTE);

		// System.out.println("palette1="+palette1.length+",hasAlpha="+hasAlpha+",transIndex="+transIndex);
		// cm=getPalette(palette);

		// convert to RGB
		for (int x = w; x-- > 0;)
		{
			for (int y = h; y-- > 0;)
			{
				rgb = rgbs[y * w + x];
				if ((rgb >> 24) == 0)
				{
					rgbs1[y * w + x] = palette1[transIndex];
				} else
					rgbs1[y * w + x] = palette1[pixels[x][y]];
			}
		}

		if (cm.getPixelSize() == 8)
			tempImg = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_INDEXED,
					cm);
		else if (cm.getPixelSize() == 4)
			tempImg = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_BINARY,
					cm);
		else
			return null;

		tempImg.setRGB(0, 0, w, h, rgbs1, 0, w);

		// Graphics2D g=tempImg.createGraphics();
		// g.drawImage(image, 0, 0, null);
		// g.dispose();

		return tempImg;
		// return MedianCut.convert(image, 256);
	}

	public static final IndexColorModel getPalette(BufferedImage image)
	{
		int w = image.getWidth();
		int h = image.getHeight();
		int size = w * h;
		int[] rgbs = new int[size];
		image.getRGB(0, 0, w, h, rgbs, 0, w);
		return getPalette(rgbs);
	}

	public static final IndexColorModel getPalette(int[] rgbs)
	{
		int size = rgbs.length;
		int i, num;
		Vector<Integer> colorList = new Vector<Integer>();
		Hashtable<Integer, Integer> colorMap = new Hashtable<Integer, Integer>();
		int rgb, alpha, alphaIndex = 0, alphaColor;
		boolean hasAlpha = false;
		for (i = 0; i < size; i++)
		{
			rgb = rgbs[i];
			if (colorMap.containsKey(rgb))
				continue;
			alpha = rgb >> 24;
			if (alpha == 0 && !hasAlpha)
			{
				// System.out.println("alpha颜色:"+rgb);
				colorList.insertElementAt(rgb, 0);
				hasAlpha = true;
				// rgb=0;
				// alphaColor=rgb;
			} else
				colorList.addElement(rgb);
			colorMap.put(rgb, 1);
		}
		num = colorList.size();
		int[] clist = new int[num];
		for (i = 0; i < num; i++)
		{
			clist[i] = colorList.get(i);
		}
		System.out.println("Util.getPalette: 颜色数=" + num);
		// public IndexColorModel(int bits, int size,
		// int cmap[], int start,
		// boolean hasalpha, int trans, int transferType) {
		IndexColorModel cm = null;
		if (num <= 16)
			cm = new IndexColorModel(4, num, clist, 0, hasAlpha, 0,
					DataBuffer.TYPE_BYTE);
		else if (num <= 256)
			cm = new IndexColorModel(8, num, clist, 0, hasAlpha, 0,
					DataBuffer.TYPE_BYTE);

		// if(!hasAlpha)
		// System.out.println("没有α!!!");
		// IndexColorModel cm=new
		// IndexColorModel(size,num,clist,0,hasAlpha,0,DataBuffer.TYPE_BYTE);
		return cm;
	}

	public static final TileRGBData[][] getTileRGBs(BufferedImage im)
	{
		int gw = EditorConfig.GW;
		int gh = EditorConfig.GH;

		return getTileRGBs(im, gw, gh);
	}

	/**
	 * 获取图形的Tile RGB数组
	 * 
	 * @param im
	 * @return
	 */
	public static final TileRGBData[][] getTileRGBs(BufferedImage im, int tw,
			int th)
	{
		// int gw = EditorConfig.GW;
		// int gh = EditorConfig.GH;
		// int gwb = EditorConfig.GWB;
		// int ghb = EditorConfig.GHB;

		int w = im.getWidth();
		int h = im.getHeight();
		int xnum = (byte) (w / tw);
		int ynum = (byte) (h / th);

		TileRGBData[][] tileRGBs = new TileRGBData[ynum][xnum];
		TileRGBData trd;
		// int[] rgbArray;
		int i, j;
		for (i = 0; i < ynum; i++)
		{
			for (j = 0; j < xnum; j++)
			{
				trd = new TileRGBData(j, i, tw, th);
				// rgbArray=tileRGBs[i][j];
				im.getRGB(j * tw, i * th, tw, th, trd.rgbArray, 0, tw);
				tileRGBs[i][j] = trd;
			}
		}
		return tileRGBs;
	}

	// public static final Vector<TileRGBData> clipImage2TileRGBList(
	// BufferedImage im) {
	// TileRGBData[][] tileRGBs = getTileRGBs(im);
	// int ynum = tileRGBs.length;
	// int xnum = tileRGBs[0].length;
	// // boolean isEmpty = false;
	//
	// Stack<TileRGBData> srcData = new Stack<TileRGBData>();
	// Vector<TileRGBData> destData = new Vector<TileRGBData>();
	// TileRGBData trd, trd1;
	// int i, j;
	// // int[] rgbArray,rgbArray1;
	// for (i = 0; i < ynum; i++) {
	// for (j = 0; j < xnum; j++) {
	// // rgbArray=tileRGBs[i][j];
	// srcData.addElement(tileRGBs[i][j]);
	// }
	// }
	// Enumeration<TileRGBData> enu;
	// while (srcData.size() > 0) {
	// trd = srcData.pop();
	// // isEmpty=isTileRGBEmpty(rgbArray);
	// enu = srcData.elements();
	// while (enu.hasMoreElements()) {
	// trd1 = enu.nextElement();
	// if (compareTileRGB(trd.rgbArray, trd1.rgbArray, true) >= 0) {
	// srcData.remove(trd1);
	// }
	// }
	// if (!isTileRGBEmpty(trd.rgbArray)) {
	// destData.addElement(trd);
	// } else {
	// // System.out.println("Empty!!!");
	// }
	// }
	// return destData;
	// }
	public static final Vector<TileRGBData> clipImage2TileRGBList(
			BufferedImage im)
	{
		TileRGBData[][] tileRGBs = getTileRGBs(im);
		int ynum = tileRGBs.length;
		int xnum = tileRGBs[0].length;
		// boolean isEmpty = false;

		Stack<TileRGBData> srcData = new Stack<TileRGBData>();
		Vector<TileRGBData> destData = new Vector<TileRGBData>();
		TileRGBData trd, trd1;
		int i, j;
		// int[] rgbArray,rgbArray1;
		for (i = 0; i < ynum; i++)
		{
			for (j = 0; j < xnum; j++)
			{
				// rgbArray=tileRGBs[i][j];
				srcData.addElement(tileRGBs[i][j]);
			}
		}
		Enumeration<TileRGBData> enu;
		while (srcData.size() > 0)
		{
			trd = srcData.pop();
			// isEmpty=isTileRGBEmpty(rgbArray);
			// enu = srcData.elements();
			// while (enu.hasMoreElements()) {
			// trd1 = enu.nextElement();
			// if (compareTileRGB(trd.rgbArray, trd1.rgbArray, true) >= 0) {
			// srcData.remove(trd1);
			// }
			// }

			for (i = srcData.size() - 1; i >= 0; i--)
			{
				trd1 = srcData.get(i);
				if (compareTileRGB(trd.rgbArray, trd1.rgbArray, true) >= 0)
				{
					srcData.remove(i);
				}
			}

			if (!isTileRGBEmpty(trd.rgbArray))
			{
				destData.addElement(trd);
			} else
			{
				// System.out.println("Empty!!!");
			}
		}
		return destData;
	}

	public static final BufferedImage createTileImage(Vector<TileRGBData> data,
			int w, int h)
	{
		int gw = EditorConfig.GW;
		int gh = EditorConfig.GH;
		// int gwb = EditorConfig.GWB;
		// int ghb = EditorConfig.GHB;

		int size = data.size();
		if (w * h < size)
			return null;
		BufferedImage img = new BufferedImage(w * EditorConfig.GW, h
				* EditorConfig.GH, BufferedImage.TYPE_INT_ARGB);
		// Graphics g=img.getGraphics();
		// int[] rgbArray;
		TileRGBData trd;
		int i, j, x, y;
		for (i = 0; i < size; i++)
		{
			trd = data.get(i);
			x = i % w;
			y = i / w;
			img.setRGB(x * gw, y * gh, gw, gh, trd.rgbArray, 0, gw);
		}
		return img;
	}

	/**
	 * 判断tile是否纯透明
	 * 
	 * @param a
	 * @return
	 */
	public static final boolean isTileRGBEmpty(int[] a)
	{
		int i, j;
		for (i = 0; i < a.length; i++)
		{
			// System.out.println(a[i]);
			// if (a[i] != 0x00ffffff && a[i] != 0)
			if ((a[i] & 0xff000000) != 0)
				return false;
		}
		return true;
	}

	public static final TileRGBData findTileRGBData(TileRGBData t,
			TileRGBData[][] src)
	{
		int ynum = src.length;
		int xnum = src[0].length;

		TileRGBData trd;
		int i, j, result;
		for (i = 0; i < ynum; i++)
		{
			for (j = 0; j < xnum; j++)
			{
				trd = src[i][j];
				result = compareTileRGB(trd.rgbArray, t.rgbArray, true);
				if (result >= 0)
				{
					System.out.println("findTileRGBData: rsult=" + result);
					trd.mirrorH = (result & 0x00000001) == 0 ? false : true;
					trd.mirrorV = (result & 0x00000002) == 0 ? false : true;
					return trd;
				}
			}
		}
		return null;
	}

	/**
	 * 比较两个Tile RGB数据
	 * 
	 * @param a
	 * @param b
	 * @param trans
	 *            是否翻转比较
	 * @return
	 */
	public static final int compareTileRGB(int[] a, int[] b, boolean trans)
	{
		int gw = EditorConfig.GW;
		int gh = EditorConfig.GH;
		// int gwb = EditorConfig.GWB;
		// int ghb = EditorConfig.GHB;
		int result = 0;
		int i, j;
		for (i = 0; i < a.length; i++)
		{
			if (a[i] != b[i])
			{
				result = -1;
				break;
			}
		}
		if (result != -1)
			return result;
		if (!trans)
			return result;
		int oy1, oy;
		result = 1;
		for (i = 0; i < gh; i++)
		{
			oy = i * gh;
			for (j = 0; j < gw; j++)
			{
				if (a[oy + gw - j - 1] != b[oy + j])
				{
					result = -1;
					break;
				}
			}
		}
		if (result != -1)
			return result;
		result = 2;
		for (i = 0; i < gh; i++)
		{
			oy = i * gh;
			oy1 = (gh - i - 1) * gh;
			for (j = 0; j < gw; j++)
			{
				if (a[oy1 + j] != b[oy + j])
				{
					result = -1;
					break;
				}
			}
		}
		if (result != -1)
			return result;
		result = 3;
		for (i = 0; i < gh; i++)
		{
			oy = i * gh;
			oy1 = (gh - i - 1) * gh;
			for (j = 0; j < gw; j++)
			{
				if (a[oy1 + gw - j - 1] != b[oy + j])
				{
					result = -1;
					break;
				}
			}
		}
		return result;
	}

	/**
	 * 拉伸图片
	 * 
	 * @param image
	 * @param w
	 * @param h
	 * @return
	 */
	public static final BufferedImage scaleImage(BufferedImage image, double d)
	{
		BufferedImage result;
		try
		{
			int w = (int) (image.getWidth() * d);
			int h = (int) (image.getHeight() * d);
			if (w < 0)
				w = 0;
			if (h < 0)
				h = 0;
			result = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

			Graphics2D g = result.createGraphics();
			g.drawImage(image, 0, 0, w, h, null);
			g.dispose();
		} catch (Exception e)
		{
			return null;
		}

		return result;
	}

	// XML function
	public static final boolean writeXMLFile(String fn, Document document)
	{
		try
		{
			// lets write to a file
			OutputFormat format = OutputFormat.createPrettyPrint();
			XMLWriter writer = new XMLWriter(new FileWriter(fn), format);
			writer.write(document);
			writer.close();
			return true;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public static final Document readXMLFile(String fn)
	{
		try
		{
			SAXReader reader = new SAXReader();
			Document document = reader.read(new File(fn));
			return document;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static final int getDirIdByName(String nm)
	{
		String[] ss = EditorConfig.DIR_NAMES_CHINESE;
		for (int i = 0; i < ss.length; i++)
		{
			if (ss[i].equals(nm))
			{
				return i;
			}
		}
		return -1;
	}

	public static final int getDirIndex(int directionType, int dir)
	{
		int[] dirs = EditorConfig.DIRECTION_TYPES[directionType];
		for (int i = 0; i < dirs.length; i++)
		{
			if (dir == dirs[i])
			{
				return i;
			}
		}
		return -1;
	}

	public static final boolean isFloat(String s)
	{
		float n;
		try
		{
			n = Float.parseFloat(s);
			return true;
		} catch (Exception e)
		{

		}
		return false;
	}

	public static final boolean isInteger(String s)
	{
		return isInteger(s, 10);
	}

	public static final boolean isInteger(String s, int aa)
	{
		int n;
		try
		{
			n = Integer.parseInt(s, aa);
			return true;
		} catch (Exception e)
		{

		}
		return false;
	}

	// /**
	// * 通过名字获取精灵动作索引
	// * @param n
	// * @return
	// */
	// public static int getSpriteActionIndexByName(String n)
	// {
	// int k=-1;
	// if(n==null)
	// return -1;
	// for(int i=0;i<EditorConfig.SPRITE_ACTION_NUM;i++)
	// {
	// if(EditorConfig.SPRITE_ACTION_NAMES[i].equals(n))
	// {
	// return i;
	// }
	// }
	// return k;
	// }

	public static int getIndexByNameContains(String n, String[] ns)
	{
		int k = -1;
		if (n == null)
			return -1;
		for (int i = 0; i < ns.length; i++)
		{
			if (ns[i].equals(n))
			{
				return i;
			}
		}
		return k;
	}

	public static int getIndexByNameEquals(String n, String[] ns)
	{
		int k = -1;
		if (n == null)
			return -1;
		for (int i = 0; i < ns.length; i++)
		{
			if (ns[i].equals(n))
			{
				return i;
			}
		}
		return k;
	}

	public static boolean getBooleanByName(String n, String[] ns)
	{
		int i = getIndexByNameContains(n, ns);
		if (i > 0)
			return true;
		return false;
	}

	public static int[] TENS =
	{ 0, 10, 100, 1000, 10000 };
	public static String ZEROS = "000000";

	/**
	 * 获取浮点数字符串
	 * 
	 * @param a
	 *            数据
	 * @param n
	 *            小数点位数
	 * @return
	 */
	public static String getFloatString(int a, int n)
	{
		if (n == 0)
			return String.valueOf(a);
		// int b=TENS[n];
		// int b1=a/b;
		// int b2=a%b;
		// String.valueOf(b1)+"."

		String s = String.valueOf(a);
		int size = s.length();
		if (size > n)
		{
			return s.substring(0, size - n) + "." + s.substring(size - n);
		} else
		{
			return "0." + ZEROS.substring(0, n - size) + s;
		}
	}

	public static final void drawString(Graphics2D g2d, String s, int x, int y,
			int alignx, int aligny)
	{
		drawString(g2d, s, x, y, Color.WHITE, Color.BLACK, alignx, aligny);
	}

	public static final void drawString(Graphics2D g2d, String s, int x, int y)
	{
		drawString(g2d, s, x, y, Color.WHITE, Color.BLACK);
	}

	public static final void drawGreyString(Graphics2D g2d, String s, int x,
			int y)
	{
		drawString(g2d, s, x, y, Color.BLACK, Color.GRAY);
	}

	public static final void drawString(Graphics2D g2d, String s, int x1,
			int y1, Color back, Color front)
	{
		drawString(g2d, s, x1, y1, back, front, SwingConstants.CENTER,
				SwingConstants.CENTER);
	}

	public static final void drawString(Graphics2D g2d, String s, int x1,
			int y1, Color back, Color front, int alignx, int aligny)
	{
		Rectangle2D rect = g2d.getFontMetrics().getStringBounds(s, g2d);
		int x = x1;
		int y = y1;

		if (alignx == SwingConstants.CENTER)
			x = x1 - (int) rect.getWidth() / 2;
		// else if(alignx==SwingConstants.TOP)

		if (aligny == SwingConstants.CENTER)
			y = y1 + (int) rect.getHeight() / 2;
		else if (aligny == SwingConstants.TOP)
			y += rect.getHeight();

		g2d.setColor(back);
		g2d.drawString(s, x - 1, y);
		g2d.drawString(s, x + 1, y);
		g2d.drawString(s, x, y - 1);
		g2d.drawString(s, x, y + 1);

		g2d.setColor(front);
		g2d.drawString(s, x, y);

	}

	public static final void drawError(Graphics2D g2d, String s, int x, int y)
	{
		g2d.setColor(Color.red);
		g2d.drawRect(x, y, 25, 25);
		g2d.drawString(s, x, y);
	}

	public static final Rectangle getErrorBounds()
	{
		return new Rectangle(40, 40);
	}

	public static final BaseData selectData(Component com, String gid,
			Object init)
	{
		return selectData(com, gid, init, -1);
	}

	public static final BaseData selectData(Component com, String gid,
			Object init, int dataType)
	{
		return selectData(com, "选择数据:", gid, init, dataType);
	}

	public static final BaseData selectData(Component com, String message,
			String gid, Object init, int dataType)
	{
		if (gid == null)
			return null;
		Vector<BaseData> v = DataManager.getDataGroup(gid).getDataListByType(
				dataType);
		if (v == null)
			return null;
		BaseData s = (BaseData) SelectObjectDialogList.showInputDialog(v
				.toArray());
		// BaseData s = (BaseData) JOptionPane.showInputDialog(com, message,
		// "选择对话框", JOptionPane.INFORMATION_MESSAGE,
		// null, v.toArray(), init);
		return s;
	}

	public static final int selectIndex(Component com, String message,
			String[] selectionValues, String initialSelectionValue)
	{
		if (selectionValues == null)
			return -1;
		String s = (String) JOptionPane.showInputDialog(com, message, "选择对话框",
				JOptionPane.INFORMATION_MESSAGE, null, selectionValues,
				initialSelectionValue);
		return getIndexByNameContains(s, selectionValues);
	}

	public static final int selectIndex(Component com, String message,
			List selectionValues, String initialSelectionValue)
	{
		if (selectionValues == null)
			return -1;
		Object s = JOptionPane.showInputDialog(com, message, "选择对话框",
				JOptionPane.INFORMATION_MESSAGE, null,
				selectionValues.toArray(), initialSelectionValue);
		return selectionValues.indexOf(s);
	}

	public static final Enum selectIndex(Component com, String message,
			Enum[] selectionValues, Enum initialSelectionValue)
	{
		if (selectionValues == null)
			return null;
		Enum s = (Enum) JOptionPane.showInputDialog(com, message, "选择对话框",
				JOptionPane.INFORMATION_MESSAGE, null, selectionValues,
				initialSelectionValue);
		return s;
	}

	public static final Object selectObject(Component com, String message,
			Collection objList, Object initialSelectionValue)
	{
		Object[] objs = objList.toArray();
		return JOptionPane.showInputDialog(com, message, "选择对话框",
				JOptionPane.INFORMATION_MESSAGE, null, objs,
				initialSelectionValue);
	}

	public static final String VectorToString(Vector v)
	{

		/*
		 * if(v.getClass().toString()==""){ }else{ } return v.toString();
		 */

		String s = "";
		for (int i = 0; i < v.size(); i++)
		{
			if (i != 0)
				s += ",";
			s += v.get(i);
		}
		return s + ";";
		// return v.toString();

	}

	public static final String ListToString(List v)
	{

		String s = "";
		for (int i = 0; i < v.size(); i++)
		{
			if (i != 0)
				s += ",";
			s += v.get(i);
		}
		return s + ";";
	}

	public static final String IntArrayToString(int[] ary)
	{
		String s = "";

		if (ary == null)
		{
			return null;
		}

		for (int i = 0; i < ary.length; i++)
		{
			s += ary[i] + ",";
		}

		return s;
	}

	public static final String StringArrayToString(String[] ary)
	{
		String s = "";

		if (ary == null)
		{
			return null;
		}

		for (int i = 0; i < ary.length; i++)
		{
			s += ary[i] + ",";
		}

		return s;
	}

	public static final String[] StringToStringArray(String s, int charNum)
	{
		String[] ss;

		if (charNum <= 0)
		{
			return null;
		}

		if (charNum >= s.length())
		{
			ss = new String[]
			{ s };
			return ss;
		}

		int num = 0;
		if (s.length() % charNum == 0)
		{
			num = s.length() / charNum;
		} else
		{
			num = s.length() / charNum + 1;
		}

		ss = new String[num];
		for (int i = 0; i < num; i++)
		{
			ss[i] = s.substring(i, i * charNum);
		}

		return ss;
	}

	private Hashtable<JComponent, Hashtable<Component, Boolean>> componentEnableHistoryMap = new Hashtable<JComponent, Hashtable<Component, Boolean>>();

	public static final void setEnabled(JComponent com, boolean enable)
	{
		// Component[] coms=com.getComponents();

	}

	public static final void setEnabled(JComponent com, boolean enable,
			Hashtable<Component, Boolean> his)
	{
		Component[] coms = com.getComponents();
		if (coms == null)
			return;
		Component com1;
		for (int i = 0; i < coms.length; i++)
		{
			com1 = coms[i];
			if (com1 instanceof JComponent)
			{
				setEnabled((JComponent) com1, enable, his);
			}
			if (!enable)
			{
				his.put(com1, com1.isEnabled());
			}
			com1.setEnabled(enable);
		}
	}

	public static final void PrintHashtable(Hashtable ht)
	{
		Enumeration en = ht.keys();
		while (en.hasMoreElements())
		{
			Object key_num = en.nextElement();
			System.out.print(key_num);
			System.out.print(" = ");
			System.out.println(ht.get(key_num));
		}
	}

	/**
	 * 添加一个选择框到指定表格
	 * 
	 * @param table
	 * @param index
	 * @param names
	 */
	public static final void addTableComboBox(JTable table, int index,
			Object[] names)
	{
		addTableComboBox(table, index, names, null);
	}

	public static final void addTableComboBox(JTable table, int index,
			Object[] names, String temp)
	{
		TableColumn typeColumn = table.getColumnModel().getColumn(index);
		JComboBox comboBox = new JComboBox();
		if (temp != null && !temp.equals(""))
			comboBox.addItem(temp);
		for (int i = 0; i < names.length; i++)
			comboBox.addItem(names[i]);
		typeColumn.setCellEditor(new DefaultCellEditor(comboBox));
	}

	/**
	 * 设置表格头宽度
	 * 
	 * @param table
	 * @param widthList
	 */
	public static final void setTableColumnWidth(JTable table, int[] widthList)
	{
		TableColumn typeColumn;
		int size = table.getColumnModel().getColumnCount();
		for (int i = 0; i < size; i++)
		{
			typeColumn = table.getColumnModel().getColumn(i);
			if (widthList[i] != -1)
			{
				typeColumn.setMaxWidth(widthList[i]);
				typeColumn.setPreferredWidth(widthList[i]);
			}
		}

	}

	/**
	 * 清空表格
	 * 
	 * @param tableModel
	 */
	public static final void cleanTableModel(DefaultTableModel tableModel)
	{
		int size = tableModel.getRowCount();
		for (int j = size - 1; j >= 0; j--)
		{
			tableModel.removeRow(j);
		}
	}

	/**
	 * 在JTable进行定位
	 * 
	 * @param table
	 *            指定的JTable
	 * @param row
	 *            要定位的行
	 * @param height
	 *            JTable的高度
	 * */
	public static void setSelectionInterval(JTable table, int row)
	{
		table.setRowSelectionInterval(row, row);
		// 滚动到指定行
		Rectangle rect = table.getCellRect(row, 0, true);

		// int halfTableHeight = height / 2;
		// //根据居中显示的要求，设定显示区域的坐标和高度属性
		// if ( rect.y - halfTableHeight > 0 ) {
		// //纵坐标为当前纵坐标 - JTable一半的高度 + 30像素的修正值
		// rect.y = rect.y - halfTableHeight + 30;
		// //显示区域等于当前高度 + JTable的高度
		// rect.height += height;
		// }

		table.scrollRectToVisible(rect);
	}

	// public static void scrollRectToVisible(JTable table,int row){
	// scrollRectToVisible(table, row, table.getHeight());
	// }
	public static void scrollRectToVisible(JTable table, int row)
	{
		// 滚动到指定行
		Rectangle rect = table.getCellRect(row, 0, true);

		// int halfTableHeight = height / 2;
		// //根据居中显示的要求，设定显示区域的坐标和高度属性
		// if ( rect.y - halfTableHeight > 0 ) {
		// //纵坐标为当前纵坐标 - JTable一半的高度 + 30像素的修正值
		// rect.y = rect.y - halfTableHeight + 30;
		// //显示区域等于当前高度 + JTable的高度
		// rect.height += height;
		// }

		table.scrollRectToVisible(rect);
	}

	public static String byteAry2HexString(byte[] b)
	{
		char[] hexChar =
		{ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
				'E', 'F' };
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++)
		{
			sb.append(hexChar[(b[i] & 0xf0) >>> 4]);
			sb.append(hexChar[b[i] & 0x0f]);
		}
		return sb.toString();
	}

	public static synchronized String MD5(byte[] datas)
	{
		byte[] md;
		try
		{
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(datas);
			md = mdTemp.digest();
			return byteAry2HexString(md);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static void setJTableColumnEditor_ComboBox(JTable table,
			final int colIndex, final Object[] objs, boolean isNeedRender)
	{

		TableColumn column = table.getColumnModel().getColumn(colIndex);
		final JComboBox comboBox = new JComboBox();
		for (int i = 0; i < objs.length; i++)
		{
			comboBox.addItem(objs[i]);
		}
		column.setCellEditor(new DefaultCellEditor(comboBox));

		if (isNeedRender)
		{
			TableCellRenderer render = new TableCellRenderer()
			{
				@Override
				public Component getTableCellRendererComponent(JTable table,
						Object value, boolean isSelected, boolean hasFocus,
						int row, int column)
				{
					if (column == colIndex)
					{
						final JComboBox comboBox = new JComboBox();
						for (int i = 0; i < objs.length; i++)
						{
							comboBox.addItem(objs[i]);
						}
						comboBox.setSelectedItem(value);
						return comboBox;
					}
					return null;
				}
			};
			column.setCellRenderer(render);
		}

	}

	public static int getSign(short a)
	{
		return (a >> 15) & 1;
	}

	/**
	 * 选择文件
	 * 
	 * @return
	 */
	public static File openSelectFileDialog(String desc, String extension)
	{
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setCurrentDirectory(EditorConfig.CURRENT_DIR);
		FileNameExtensionFilter filter = new FileNameExtensionFilter(desc,
				extension);
		fc.setFileFilter(filter);

		int returnVal = fc.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			return fc.getSelectedFile();
		}
		return null;
	}

	public static int String2Int(String s)
	{
		int i = 0;
		try
		{
			i = Integer.parseInt(s.trim());
		} catch (Exception e)
		{
			System.out.println("string2Int error:" + s);
		}
		return i;
	}

	public static long String2Long(String s)
	{
		long i = 0;
		try
		{
			i = Long.parseLong(s.trim());
		} catch (Exception e)
		{
			System.out.println("String2Long error:" + s);
		}
		return i;
	}

	public static boolean IsPointInRect(Point p, Rectangle rect)
	{
		if (rect.contains(p.x, p.y))
		{
			return true;
		}
		return false;
	}

	/**
	 * @param mode
	 *            FILES_ONLY JFileChooser.FILES_ONLY DIRECTORIES_ONLY
	 *            JFileChooser.DIRECTORIES_ONLY FILES_AND_DIRECTORIES
	 *            JFileChooser.FILES_AND_DIRECTORIES
	 * @param fileFormat
	 * @param parent
	 * @return
	 */
	public static File SelectFile(int mode, String[] fileFormat,
			Component parent)
	{
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(mode);// 设置选择模式，既可以选择文件又可以选择文件夹
		if (fileFormat != null)
		{
			FileFilter filter = new FileNameExtensionFilter("文件", fileFormat);
			chooser.setFileFilter(filter);// 设置文件后缀过滤器
		}
		int retval = chooser.showOpenDialog(parent);// 显示"保存文件"对话框
		File f = null;//
		if (retval == JFileChooser.APPROVE_OPTION)
		{// 若成功打开
			f = chooser.getSelectedFile();// 得到选择的文件名
		}
		return f;
	}

	public static File[] SelectFiles(Component parent)
	{
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.setMultiSelectionEnabled(true);
		int retval = chooser.showOpenDialog(parent);
		File[] fs = null;
		if (retval == JFileChooser.APPROVE_OPTION)
		{
			fs = chooser.getSelectedFiles();
		}
		return fs;

	}

	public static void setButtonText(JButton button, Object obj)
	{
		if (obj != null)
		{
			button.setText(obj.toString());
		} else
		{
			button.setText("无");
		}
	}

	public static void moveListItem(List<Object> list, byte direct,
			boolean isToHead)
	{

	}

	public static void WindowsSystemOpenFile(String fileName)
	{
		try
		{
			String cmd = "rundll32 url.dll FileProtocolHandler file://"
					+ fileName;
			Process p = Runtime.getRuntime().exec(cmd);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static byte[] compressBytesByZLib(byte[] input)
	{

		int cachesize = 1024;
		Deflater compresser = new Deflater();

		compresser.reset();
		compresser.setInput(input);
		compresser.finish();

		byte output[] = null;
		ByteArrayOutputStream o = new ByteArrayOutputStream(input.length);
		try
		{
			byte[] buf = new byte[cachesize];
			int got;
			while (!compresser.finished())
			{
				got = compresser.deflate(buf);
				o.write(buf, 0, got);
			}
			output = o.toByteArray();
		} finally
		{
			try
			{
				o.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return output;
	}

	public static int getByteArrayLenth(byte[] input)
	{
		if (input == null)
		{
			return 0;
		}
		return input.length;
	}

	public static String toBitStr(int i)
	{
		String s = Integer.toBinaryString(i);
		int fillZeros = 32 - s.length();
		String sz = "";
		for (int j = 0; j < fillZeros; j++)
		{
			sz += "0";
		}
		return sz + s;
	}

	public static int setBitValue(int data, int num, boolean value)
	{
		if (value)
		{
			return data | (1 << num);
		} else
		{
			return data & (~(1 << num));
		}
	}

	public static int getBitValue(int data, int num)
	{
		return (data >> num) & 1;
	}

	public static boolean getBitBooleanValue(int data, int num)
	{
		int i = getBitValue(data, num);
		return i == 1 ? true : false;
	}

	public static Date formatDateString(String str)
	{
		Date result = new Date(0l);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try
		{
			result = formatter.parse(str);
		} catch (Exception e)
		{
		}
		return result;
	}

	public static void trace(String s, Object... objs)
	{
		String str = s + ": ";
		for (Object o : objs)
		{
			str += o + ",";
		}
		System.out.println(str);
	}

	public static String absolutelyTime2String(long time)
	{
		Date date = new Date(time);
		// String t = date.getYear() + "/" + date.getMonth() + "/" +
		// date.getDay() + " " + date.getHours() + ":"
		// + date.getMinutes() + ":" + date.getSeconds();
		String t = date.toLocaleString().replace("-", "/");
		return t;
	}

	public static long String2AbsolutelyTime(String s)
	{
		long l = -1;
		try
		{
			l = Date.parse(s.trim());
			System.out.println("String2AbsolutelyTime:" + l);
		} catch (Exception e1)
		{
			System.out.println("日期格式错误:" + e1.getMessage());
		}
		return l;
	}

	public static int indexOf(int obj, int[] objs)
	{
		if (objs == null || obj == -1 || objs.length < 1)
		{
			return -1;
		}
		for (int i = 0; i < objs.length; i++)
		{
			if (obj == objs[i])
			{
				return i;
			}
		}

		return -1;
	}
}
