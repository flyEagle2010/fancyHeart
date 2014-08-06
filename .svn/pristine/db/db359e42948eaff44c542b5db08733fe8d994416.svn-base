package com.doteplay.editor.common;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class DateChooserDialog extends JDialog implements ActionListener,
		ChangeListener
{
	/** serialVersionUID */
	private static final long serialVersionUID = 806923175893728540L;

	private DateChooserDialog instance;

	private static int startYear = 1980; // 默认【最小】显示年份
	private static int lastYear = 2050; // 默认【最大】显示年份
	private static int width = 240; // 界面宽度
	private static int height = 200; // 界面高度

	private static Color backGroundColor = Color.gray; // 底色
	// 月历表格配色----------------//
	private static Color palletTableColor = Color.white; // 日历表底色
	private static Color todayBackColor = Color.orange; // 今天背景色
	private static Color weekFontColor = Color.blue; // 星期文字色
	private static Color dateFontColor = Color.black; // 日期文字色
	private static Color weekendFontColor = Color.red; // 周末文字色

	// 控制条配色------------------//
	private static Color controlLineColor = Color.pink; // 控制条底色
	private static Color controlTextColor = Color.white; // 控制条标签文字色

	private static Color rbFontColor = Color.white; // RoundBox文字色
	private static Color rbBorderColor = Color.red; // RoundBox边框色
	private static Color rbButtonColor = Color.pink; // RoundBox按钮色
	private static Color rbBtFontColor = Color.red; // RoundBox按钮文字色

	private JSpinner yearSpin;
	private JSpinner monthSpin;
	private JSpinner hourSpin;
	private JSpinner minuteSpin;
	private JSpinner secondSpin;
	private JButton[][] daysButton = new JButton[6][7];

	private Date date;
	private final Date old_date;
	private JPanel jContentPane = null;

	private JPanel jPanel1 = null;
	private JButton okButton = null;
	private JButton cancelButton = null;

	private int result = 0;

	public final static int BUTTON_OK = 0;
	public final static int BUTTON_CANCEL = 1;

	private DateChooserDialog(Frame owner, Date date)
	{
		super(owner);
		this.date = date;
		this.old_date = date;
		instance = this;
		initialize();
		flushWeekAndDay();
	}

	/**
	 * 传进来一个显示位置点
	 */
	public static DateChooserDialog showWhitLocation(Frame owner, Date date,
			Point point)
	{
		DateChooserDialog dialog = new DateChooserDialog(owner, date);
		dialog.setSize(width, height);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.pack();
		Dimension d = dialog.getSize();
		point.translate(0, -d.height / 3);
		dialog.setLocation(point);
		dialog.setVisible(true);
		return dialog;
	}

	private void initialize()
	{
		this.setSize(width, height);
		this.setTitle("日期时间选择");
		this.setModal(true);
		this.setResizable(false);
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter()
		{
			public void windowClosing(java.awt.event.WindowEvent e)
			{
				pressButton(BUTTON_CANCEL);
			}
		});
	}

	private void pressButton(int button)
	{
		switch (button)
		{
		case BUTTON_OK:
			instance.dispose();
			result = BUTTON_OK;
			break;
		case BUTTON_CANCEL:
			date = old_date;
			instance.dispose();
			result = BUTTON_CANCEL;
			break;
		default:
			break;
		}
	}

	private JPanel getJContentPane()
	{
		if (jContentPane == null)
		{
			jContentPane = new JPanel();

			jContentPane.setLayout(new BorderLayout());
			jContentPane.setBorder(new LineBorder(backGroundColor, 2));
			jContentPane.setBackground(backGroundColor);

			jContentPane.add(getYearAndMonthPanal(), BorderLayout.NORTH);
			jContentPane.add(getWeekAndDayPanal(), BorderLayout.CENTER);
			jContentPane.add(getJPanel1(), BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	private JPanel getJPanel1()
	{
		if (jPanel1 == null)
		{
			jPanel1 = new JPanel();
			jContentPane.add(jPanel1, BorderLayout.SOUTH);
			jPanel1.setPreferredSize(new Dimension(0, 20));
			jPanel1.setLayout(new BorderLayout());

			okButton = new JButton();
			okButton.setText("确定");
			okButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					pressButton(BUTTON_OK);
				}
			});
			cancelButton = new JButton();
			cancelButton.setText("取消");
			cancelButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					pressButton(BUTTON_CANCEL);
				}
			});

			jPanel1.add(okButton, BorderLayout.WEST);
			jPanel1.add(cancelButton, BorderLayout.EAST);
		}

		return jPanel1;
	}

	public static DateChooserDialog show(Frame owner, Date date)
	{
		DateChooserDialog dialog = new DateChooserDialog(owner, date);
		dialog.setSize(width, height);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.pack();
		Point p = owner.getLocationOnScreen();
		p.y = p.y + 30;
		dialog.setLocation(getAppropriateLocation(owner, p));
		dialog.setVisible(true);
		return dialog;
	}

	private static Point getAppropriateLocation(Frame owner, Point position)
	{
		Point result = new Point(position);
		Point p = owner.getLocation();
		int offsetX = (position.x + width) - (p.x + owner.getWidth());
		int offsetY = (position.y + height) - (p.y + owner.getHeight());

		if (offsetX > 0)
		{
			result.x -= offsetX;
		}

		if (offsetY > 0)
		{
			result.y -= offsetY;
		}
		return result;
	}

	private JPanel getYearAndMonthPanal()
	{
		Calendar c = getDateCalendar();
		int currentYear = c.get(Calendar.YEAR);
		int currentMonth = c.get(Calendar.MONTH) + 1;
		int currentHour = c.get(Calendar.HOUR_OF_DAY);
		int currentMinute = c.get(Calendar.MINUTE);
		int currentSecond = c.get(Calendar.SECOND);

		JPanel result = new JPanel();
		result.setLayout(new FlowLayout());
		result.setBackground(controlLineColor);

		yearSpin = new JSpinner(new SpinnerNumberModel(currentYear, startYear,
				lastYear, 1));
		yearSpin.setPreferredSize(new Dimension(48, 20));
		yearSpin.setName("Year");
		yearSpin.setEditor(new JSpinner.NumberEditor(yearSpin, "####"));
		yearSpin.addChangeListener(this);
		result.add(yearSpin);
		JLabel yearLabel = new JLabel("年");
		yearLabel.setForeground(controlTextColor);
		result.add(yearLabel);

		monthSpin = new JSpinner(new SpinnerNumberModel(currentMonth, 1, 12, 1));
		monthSpin.setPreferredSize(new Dimension(35, 20));
		monthSpin.setName("Month");
		monthSpin.addChangeListener(this);
		result.add(monthSpin);
		JLabel monthLabel = new JLabel("月");
		monthLabel.setForeground(controlTextColor);
		result.add(monthLabel);

		hourSpin = new JSpinner(new SpinnerNumberModel(currentHour, 0, 23, 1));
		hourSpin.setPreferredSize(new Dimension(35, 20));
		hourSpin.setName("Hour");
		hourSpin.addChangeListener(this);
		result.add(hourSpin);
		JLabel hourLabel = new JLabel("时");
		hourLabel.setForeground(controlTextColor);
		result.add(hourLabel);

		minuteSpin = new JSpinner(new SpinnerNumberModel(currentMinute, 0, 59,
				1));
		minuteSpin.setPreferredSize(new Dimension(35, 20));
		minuteSpin.setName("Minute");
		minuteSpin.addChangeListener(this);
		result.add(minuteSpin);
		JLabel minuteLabel = new JLabel("分");
		hourLabel.setForeground(controlTextColor);
		result.add(minuteLabel);

		secondSpin = new JSpinner(new SpinnerNumberModel(currentSecond, 0, 59,
				1));
		secondSpin.setPreferredSize(new Dimension(35, 20));
		secondSpin.setName("Second");
		secondSpin.addChangeListener(this);
		result.add(secondSpin);
		JLabel secondLabel = new JLabel("秒");
		secondLabel.setForeground(controlTextColor);
		result.add(secondLabel);

		return result;
	}

	private JPanel getWeekAndDayPanal()
	{
		String colname[] =
		{ "日", "一", "二", "三", "四", "五", "六" };
		JPanel result = new JPanel();
		// 设置固定字体，以免调用环境改变影响界面美观
		result.setFont(new Font("宋体", Font.PLAIN, 12));
		result.setLayout(new GridLayout(7, 7));
		result.setBackground(Color.white);
		JLabel cell;

		for (int i = 0; i < 7; i++)
		{
			cell = new JLabel(colname[i]);
			cell.setHorizontalAlignment(JLabel.RIGHT);
			if (i == 0 || i == 6)
				cell.setForeground(weekendFontColor);
			else
				cell.setForeground(weekFontColor);
			result.add(cell);
		}

		int actionCommandId = 0;
		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 7; j++)
			{
				JButton numberButton = new JButton();
				numberButton.setBorder(null);
				numberButton.setHorizontalAlignment(SwingConstants.RIGHT);
				numberButton.setActionCommand(String.valueOf(actionCommandId));
				numberButton.addActionListener(this);
				numberButton.setBackground(palletTableColor);
				numberButton.setForeground(dateFontColor);
				if (j == 0 || j == 6)
					numberButton.setForeground(weekendFontColor);
				else
					numberButton.setForeground(dateFontColor);
				daysButton[i][j] = numberButton;
				result.add(numberButton);
				actionCommandId++;
			}

		return result;
	}

	private Calendar getDateCalendar()
	{
		Calendar result = Calendar.getInstance();
		result.setTime(this.date);
		return result;
	}

	private int getSelectedYear()
	{
		return ((Integer) yearSpin.getValue()).intValue();
	}

	private int getSelectedMonth()
	{
		return ((Integer) monthSpin.getValue()).intValue();
	}

	private int getSelectedHour()
	{
		return ((Integer) hourSpin.getValue()).intValue();
	}

	private int getSelectedMinute()
	{
		return ((Integer) minuteSpin.getValue()).intValue();
	}

	private int getSelectedSecond()
	{
		return ((Integer) secondSpin.getValue()).intValue();
	}

	private void dayColorUpdate(boolean isOldDay)
	{
		Calendar c = this.getDateCalendar();
		int day = c.get(Calendar.DAY_OF_MONTH);
		c.set(Calendar.DAY_OF_MONTH, 1);
		int actionCommandId = day - 2 + c.get(Calendar.DAY_OF_WEEK);
		int i = actionCommandId / 7;
		int j = actionCommandId % 7;
		if (isOldDay)
			daysButton[i][j].setForeground(dateFontColor);
		else
			daysButton[i][j].setForeground(todayBackColor);
	}

	private void flushWeekAndDay()
	{
		Calendar c = this.getDateCalendar();
		c.set(Calendar.DAY_OF_MONTH, 1);
		int maxDayNo = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		int dayNo = 2 - c.get(Calendar.DAY_OF_WEEK);
		for (int i = 0; i < 6; i++)
		{
			for (int j = 0; j < 7; j++)
			{
				String s = "";
				if (dayNo >= 1 && dayNo <= maxDayNo)
					s = String.valueOf(dayNo);
				daysButton[i][j].setText(s);
				dayNo++;
			}
		}
		dayColorUpdate(false);
	}

	public void stateChanged(ChangeEvent e)
	{
		JSpinner source = (JSpinner) e.getSource();
		Calendar c = this.getDateCalendar();
		if (source.getName().equals("Hour"))
		{
			c.set(Calendar.HOUR_OF_DAY, getSelectedHour());
			this.date = c.getTime();
			return;
		} else if (source.getName().equals("Minute"))
		{
			c.set(Calendar.MINUTE, getSelectedMinute());
			this.date = c.getTime();
			return;
		} else if (source.getName().equals("Second"))
		{
			c.set(Calendar.SECOND, getSelectedSecond());
			this.date = c.getTime();
			return;
		}
		dayColorUpdate(true);

		if (source.getName().equals("Year"))
		{
			c.set(Calendar.YEAR, getSelectedYear());
		} else
		{
			c.set(Calendar.MONTH, getSelectedMonth() - 1);
		}
		this.date = c.getTime();
		flushWeekAndDay();
	}

	public void actionPerformed(ActionEvent e)
	{
		JButton source = (JButton) e.getSource();
		if (source.getText().length() == 0)
			return;
		dayColorUpdate(true);
		source.setForeground(todayBackColor);
		int newDay = Integer.parseInt(source.getText());
		Calendar c = getDateCalendar();
		c.set(Calendar.DAY_OF_MONTH, newDay);
		this.date = c.getTime();
	}

	public Date getDate()
	{
		return this.date;
	}

	public static void main(String[] args)
	{
		final Date d = new Date();
		final JFrame jFrame = new JFrame();
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setSize(800, 600);
		jFrame.setTitle("Hotoshop");

		JPanel p = new JPanel();
		JButton b = new JButton();
		b.setText("日期");
		b.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent e)
			{
				System.out.println(d);
				DateChooserDialog rel = DateChooserDialog.show(jFrame, d);
				System.out.println(rel.getDate());
			}
		});

		p.add(b);
		jFrame.add(p);
		jFrame.pack();

		jFrame.setVisible(true);

	}

	public int getResult()
	{
		return this.result;
	}

	public void setResult(int result)
	{
		this.result = result;
	}

}
