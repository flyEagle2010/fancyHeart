package com.doteplay.editor.tools.absolutetime;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author 作者 LC
 * @version 创建时间：2010-11-9 下午01:31:07 类说明 日期时间选择对话框，模仿Windows日期和时间属性对话框
 */
public class JDateChooser extends JPanel implements ChangeListener,
		ItemListener {

	private JComboBox month = new JComboBox(new String[] { "一月", "二月", "三月",
			"四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月" });
	private JSpinner year = new JSpinner(new SpinnerNumberModel(1, 1, 9999, 1));
	private DateGrid dateGrid = new DateGrid();
	private JSpinner time = new JSpinner(new SpinnerDateModel());

	public JDateChooser(Calendar initialDate) {
		this.setBackground(Color.white);
		this.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		this.setLayout(new GridLayout(1, 2, 5, 10));
		JPanel p1 = new JPanel();
		p1.setBackground(Color.white);
		p1.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createTitledBorder("日期"), BorderFactory.createEmptyBorder(5,
				5, 10, 5)));
		p1.setLayout(new BorderLayout(10, 10));
		JPanel p11 = new JPanel(new GridLayout(1, 2, 10, 10));
		p11.setBackground(Color.white);
		p11.add(month);
		month.addItemListener(this);
		month.setPreferredSize(new Dimension(0, 20));
		p11.add(year);
		year.setEditor(new JSpinner.NumberEditor(year, "0"));
		year.addChangeListener(this);
		year.setPreferredSize(new Dimension(0, 20));
		p1.add(p11, BorderLayout.NORTH);
		p1.add(dateGrid);
		this.add(p1);
		JPanel p2 = new JPanel(new BorderLayout(10, 10));
		p2.setBackground(Color.white);
		p2.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createTitledBorder("时间"), BorderFactory.createEmptyBorder(0,
				10, 10, 10)));
		time.setPreferredSize(new Dimension(0, 20));
		JSpinner.DateEditor editor = new JSpinner.DateEditor(time, "HH:mm:ss");
		editor.getTextField().setHorizontalAlignment(JTextField.RIGHT);
		time.setEditor(editor);
		JPanel p = new JPanel();
		p.setBackground(Color.white);
		time.setPreferredSize(new Dimension(100, 20));
		p.add(time);
		p2.add(p, BorderLayout.SOUTH);
		p2.add(new Clock(time));
		this.add(p2);
		setCalendar(initialDate);
		setPreferredSize(new Dimension(369, 200));
	}

	/**
	 * 设置日期和时间
	 * 
	 * @param calendar
	 *            日期和时间
	 */
	public void setCalendar(Calendar calendar) {
		if (calendar == null)
			calendar = new GregorianCalendar();
		month.setSelectedIndex(calendar.get(Calendar.MONTH));
		year.setValue(calendar.get(Calendar.YEAR));
		int year = (Integer) this.year.getValue();
		int month = this.month.getSelectedIndex() + 1;
		dateGrid.set(year, month);
		dateGrid.setDay(calendar.get(Calendar.DAY_OF_MONTH));
		time.setValue(calendar.getTime());
	}

	/**
	 * 获得日期和时间
	 * 
	 * @return 日期和时间
	 */
	public Calendar getCalendar() {
		int year = (Integer) this.year.getValue();
		int month = this.month.getSelectedIndex();
		int day = dateGrid.getDay();
		Calendar c = Calendar.getInstance();
		Date date = (Date) time.getValue();
		c.setTime(date);
		c.set(year, month, day);
		return c;
	}

	/**
	 * 显示对话框以选择日期，使用方法同JColorChooser.showDialog
	 * 
	 * @param c
	 *            父级组件
	 * @param title
	 *            对话框标题
	 * @param initialDate
	 *            初始化日期和时间
	 * @return 选择的日期，null为取消选择
	 */
	public static Calendar showDialog(Component c, String title,
			Calendar initialDate) {
		Window window = c == null ? JOptionPane.getRootFrame() : SwingUtilities
				.windowForComponent(c);
		JDialog dlg;
		if (window instanceof Frame)
			dlg = new JDialog((Frame) window, title, true);
		else
			dlg = new JDialog((Dialog) window, title, true);
		JDateChooser dc = new JDateChooser(initialDate);
		ActionHandler actionHandler = new ActionHandler(dc);
		JPanel p = new JPanel(new BorderLayout());
		p.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createEmptyBorder(6, 6, 0, 6), BorderFactory
				.createEtchedBorder()));
		p.add(dc);
		dlg.add(p);
		p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		dlg.add(p, BorderLayout.SOUTH);
		JButton btOk = new JButton("确定");
		btOk.addActionListener(actionHandler);
		dlg.getRootPane().setDefaultButton(btOk);
		p.add(btOk);
		JButton btCancel = new JButton("取消");
		btCancel.addActionListener(actionHandler);
		p.add(btCancel);
		dlg.pack();
		dlg.setLocationRelativeTo(window);
		dlg.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dlg.addWindowListener(actionHandler);
		dlg.setVisible(true);
		return actionHandler.getCalendar();
	}

	public void stateChanged(ChangeEvent e) {
		dateGrid.set((Integer) year.getValue(), month.getSelectedIndex() + 1);
	}

	public void itemStateChanged(ItemEvent e) {
		dateGrid.set((Integer) year.getValue(), month.getSelectedIndex() + 1);
	}

	private static class ActionHandler extends WindowAdapter implements
			ActionListener {

		private JDateChooser dc;

		public ActionHandler(JDateChooser dc) {
			this.dc = dc;
		}

		public void windowClosing(WindowEvent e) {
			actionPerformed(new ActionEvent(this, 0, "取消"));
		}

		public void actionPerformed(ActionEvent e) {
			JDialog dlg = (JDialog) SwingUtilities.windowForComponent(dc);
			dlg.dispose();
			if ("取消".equals(e.getActionCommand())) {
//				dc = null;
			}
		}

		public Calendar getCalendar() {
			return dc == null ? null : dc.getCalendar();
		}
	}

	private static class DateGrid extends JPanel implements MouseListener {

		JLabel[][] lbs = new JLabel[7][7];

		int year = 1, month = 1, day = 1;

		public DateGrid() {
			super(new GridLayout(7, 7));
			this.setBackground(Color.white);
			Color blue = new Color(140, 150, 255);
			Color white = new Color(220, 220, 255);
			for (int i = 0; i < 7; i++) {
				for (int j = 0; j < 7; j++) {
					this.add(lbs[i][j] = new JLabel((String) null));
					lbs[i][j].setOpaque(true);
					lbs[i][j].setHorizontalAlignment(JLabel.CENTER);
					lbs[i][j].setBackground(Color.white);
					if (i > 0) {
						lbs[i][j].addMouseListener(this);
					} else {
						lbs[0][j].setBackground(blue);
						lbs[0][j].setForeground(white);
					}
				}
			}
			lbs[0][0].setText("日");
			lbs[0][1].setText("一");
			lbs[0][2].setText("二");
			lbs[0][3].setText("三");
			lbs[0][4].setText("四");
			lbs[0][5].setText("五");
			lbs[0][6].setText("六");
			this.setBorder(BorderFactory.createLoweredBevelBorder());
		}

		public void setDay(int day) {
			if (day < 1)
				day = 1;
			int days = maxDay(year, month);
			if (day > days)
				day = days;
			this.day = day;
			int d = 0;
			for (int i = 1; i < 7; i++) {
				for (int j = 0; j < 7; j++) {
					if (lbs[i][j].getText() != null) {
						if (++d == day) {
							lbs[i][j].setBackground(Color.blue);
							lbs[i][j].setForeground(Color.white);
							continue;
						}
					}
					lbs[i][j].setBackground(Color.white);
					lbs[i][j].setForeground(Color.black);
				}
			}
			repaint();
		}

		public int getDay() {
			return day;
		}

		public void set(int year, int month) {
			if (year < 1)
				year = 1;
			else if (year > 9999)
				year = 9999;
			if (month < 1)
				month = 1;
			else if (month > 12)
				month = 12;
			int pastdays = (year - 1) * 365 + (year - 1) / 4 - (year - 1) / 100;
			pastdays += (month - 1) * 31;
			pastdays -= (month - 1) / 2;
			if (month >= 9 && month % 2 == 1)
				pastdays += 1;
			if (month > 2) {
				pastdays -= 2;
				if ((year % 4 == 0) && (year % 400 != 0))
					pastdays += 1;
			}
			int dayInWeak = pastdays % 7 - 1;
			if (dayInWeak == -1)
				dayInWeak = 6;
			// ~
			for (int i = 1; i < 7; i++) {
				for (int j = 0; j < 7; j++) {
					lbs[i][j].setText(null);
				}
			}
			int days = maxDay(year, month);
			for (int i = 1; i <= days; i++) {
				lbs[1 + (dayInWeak + i - 1) / 7][(dayInWeak + i - 1) % 7]
						.setText(String.valueOf(i));
			}
			this.year = year;
			this.month = month;
			setDay(day);
		}

		private int maxDay(int year, int month) {
			int days = (month % 2 == (month > 7 ? 0 : 1)) ? 31 : 30;
			if (month == 2)
				days = (year % 4 == 0 && year % 400 != 0) ? 29 : 28;
			return days;
		}

		public void mouseClicked(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {
			Object o = e.getSource();
			JLabel lb = null;
			for (int i = 1; i < 7; i++) {
				for (int j = 0; j < 7; j++) {
					JLabel b = lbs[i][j];
					if (b == o && b.getText() != null) {
						lb = b;
						break;
					}
				}
			}
			if (lb == null)
				return;
			int d = 0;
			for (int i = 1; i < 7; i++) {
				for (int j = 0; j < 7; j++) {
					if (lbs[i][j].getText() != null) {
						d++;
						if (lbs[i][j] == lb) {
							setDay(d);
							return;
						}
					}
				}
			}
		}

		public void mouseReleased(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}
	}

	private static class Clock extends JComponent implements ChangeListener {

		Calendar c;
		int[] xs = new int[4];
		int[] ys = new int[4];
		static final Color handColor = new Color(24, 116, 109);

		public Clock(JSpinner time) {
			time.addChangeListener(this);
			stateChanged(new ChangeEvent(time));
		}

		public void stateChanged(ChangeEvent e) {
			JSpinner sp = (JSpinner) e.getSource();
			Date date = (Date) sp.getValue();
			if (c == null)
				c = Calendar.getInstance();
			c.setTime(date);
			repaint();
		}

		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			float ox = getWidth() / 2f;
			float oy = getHeight() / 2f;
			final float R = 60f;
			Ellipse2D.Float oval = new Ellipse2D.Float();
			for (int i = 0; i < 360; i += 360 / 12 / 5) {
				float a = i / 360f * (float) Math.PI * 2;
				float dx = (float) Math.sin(a) * R;
				float dy = (float) Math.cos(a) * R;
				g2
						.setColor(i % (360 / 12) == 0 ? Color.black
								: Color.lightGray);
				oval.setFrame(ox - dx - 1f, oy - dy - 1f, 2, 2);
				g2.fill(oval);
				if (i % (360 / 12) == 0) {
					g2.setColor(Color.cyan);
					oval.setFrame(ox + dx - 2f, oy + dy - 1f, 2, 2);
					g2.fill(oval);
				}
			}
			int hour = c.get(Calendar.HOUR);
			int minute = c.get(Calendar.MINUTE);
			int second = c.get(Calendar.SECOND);
			float ha = (float) Math.PI * 2 / 12 * (hour + (float) minute / 60);
			float ma = (float) Math.PI * 2 / 60
					* (minute + (float) second / 60);
			float sa = (float) Math.PI * 2 / 60 * second;
			g2.setColor(Color.lightGray);
			g2.translate(1, 1);
			drawHand(g2, ox, oy, ha, 40);
			g2.translate(1, 1);
			drawHand(g2, ox, oy, ma, 48);
			g2.translate(1, 1);
			drawHand(g2, ox, oy, sa, 50, false);
			g2.translate(-3, -3);
			g2.setColor(handColor);
			drawHand(g2, ox, oy, ha, 40);
			drawHand(g2, ox, oy, ma, 48);
			g2.setColor(Color.black);
			drawHand(g2, ox, oy, sa, 50, false);
		}

		private void drawHand(Graphics2D g2, float ox, float oy, float a,
				float len, boolean w) {
			xs[2] = (int) (ox + (float) Math.sin(a) * len);
			ys[2] = (int) (oy - (float) Math.cos(a) * len);
			xs[0] = (int) (ox + (float) Math.sin(a + Math.PI) * 10);
			ys[0] = (int) (oy - (float) Math.cos(a + Math.PI) * 10);
			if (w) {
				xs[1] = (int) (ox + (float) Math.sin(a + Math.PI / 2) * 3);
				ys[1] = (int) (oy - (float) Math.cos(a + Math.PI / 2) * 3);
				xs[3] = (int) (ox + (float) Math.sin(a + Math.PI * 3 / 2) * 3);
				ys[3] = (int) (oy - (float) Math.cos(a + Math.PI * 3 / 2) * 3);
				g2.fillPolygon(xs, ys, 4);
			} else {
				Line2D.Float line = new Line2D.Float(xs[0], ys[0], xs[2], ys[2]);
				g2.draw(line);
			}
		}

		private void drawHand(Graphics2D g2, float ox, float oy, float a,
				float len) {
			this.drawHand(g2, ox, oy, a, len, true);
		}
	}
}
