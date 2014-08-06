package com.doteplay.editor.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.PixelGrabber;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

/**
 * TestEncoderB creates a PNG file that shows an analog clock
 * displaying the current time of day, optionally with an
 * alpha channel, and with the specified filter. It uses
 * a BufferedImage as its source.
 *
 * The file name is in the format:
 * 
 *     clockHHMM_fNCD
 *     alphaclockDHHMM_fNCD
 *
 *
 * HH = hours (24-hour clock), MM = minutes,
 *
 * N = filter level (0, 1, or 2)
 *
 * C = compression level (0-9)
 *
 * D is "i" for indexed (256 color) and "d" for
 * direct color (24-bit RGB)
 *
 * "alpha" is prefixed to the name if alpha encoding
 * was used.
 *
 * This test program was written in a burning hurry, so
 * it is not a model of efficient, or even good, code.
 * Comments and bug fixes should be directed to:
 *
 *   david@catcode.com
 *
 * @author J. David Eisenberg
 * @version 1.3, 6 Apr 2000
 */
public class TestEncoderB extends Frame
{
	String			message;
	String			timeStr;
	BufferedImage	clockImage = null;
	int				hour, minute;
	boolean			encodeAlpha;
	int				filter;
	int				compressionLevel;
	int				pixelDepth;
	String			filename;

	public TestEncoderB(String s)
	{
		super(s);
		setSize(200,200);
	}
	
	private void constructImage()
	{
		clockImage = new BufferedImage(100, 100, 
			(pixelDepth == 8) ? BufferedImage.TYPE_BYTE_INDEXED :
			BufferedImage.TYPE_4BYTE_ABGR );
	}

	public void drawClockImage(int hour, int minute)
	{
		// variables used for drawing hands of clock
		Graphics g;
		Font smallFont = new Font("Helvetica", Font.PLAIN, 9 );
		FontMetrics fm;

		int x0, y0, x1, y1;
		double angle;
		
		g = clockImage.getGraphics();
		g.setFont( smallFont );
		fm = g.getFontMetrics();
		g.setColor( Color.white );
		g.fillRect( 0, 0, 100, 100 );

		// draw the clock face; yellow for AM, blue for PM
		if (hour < 12)
		{
			g.setColor( new Color( 255, 255, 204) );
		}
		else
		{
			g.setColor( new Color( 204, 204, 255) );
		}
		g.fillOval(10, 10, 80, 80);
		g.setColor( Color.black );
		g.drawOval(10, 10, 80, 80);
		g.drawOval( 48, 48, 4, 4);
		
		/* draw the 12 / 3 / 6/ 9 numerals */
		g.setFont( smallFont );
		g.drawString( "12", 50-fm.stringWidth("12")/2, 11+fm.getAscent() );
		g.drawString( "3", 88-fm.stringWidth("3"), 50+fm.getAscent()/2 );
		g.drawString( "6", 50-fm.stringWidth("6")/2, 88 );
		g.drawString( "9", 12, 50+fm.getAscent()/2 );
		
		x0 = 50;
		y0 = 50;

		/* draw the hour hand */
		hour %= 12;
		angle = -(hour * 30 + minute/2) + 90;
		angle = angle * Math.PI / 180.0;
		x1 = (int) (x0 + 28 * (Math.cos( angle ) ));
		y1 = (int) (y0 - 28 * (Math.sin( angle ) ));
		g.drawLine( x0, y0, x1, y1 );
		
		/* and the minute hand */
		angle = -(minute * 6) + 90;
		angle = angle * Math.PI / 180.0;
		x1 = (int) (x0 + 35 * (Math.cos( angle ) ));
		y1 = (int) (y0 - 35 * (Math.sin( angle ) )); 
		g.drawLine( x0, y0, x1, y1 );
	}

	public void addAlphaToImage()
	{
		int width=100;
		int height=100;
		int	alphaMask = 0;
       	int[] pixels = new int[width * height];

       	PixelGrabber pg = new PixelGrabber(clockImage, 0, 0,
			width, height, pixels, 0, width);
       	try {
           	pg.grabPixels();
       	}
		catch (InterruptedException e) {
           	System.err.println("interrupted waiting for pixels!");
			return;
		}
       	if ((pg.getStatus() & ImageObserver.ABORT) != 0) {
           	System.err.println("image fetch aborted or errored");
			return;
		}
		for (int i=0; i<width*height; i++)
		{
			if ((i % width) == 0)
			{
				alphaMask = (alphaMask >> 24) & 0xff;
				alphaMask += 2;
				if (alphaMask > 255)
				{
					alphaMask = 255;
				}
				alphaMask = (alphaMask << 24) & 0xff000000;
			}
			pixels[i] = (pixels[i] & 0xffffff) | alphaMask;
		}
		clockImage.setRGB( 0, 0, width, height, pixels, 0, width );
	}

	public void paint( Graphics g )
	{
		if (clockImage != null)
		{
			g.drawImage( clockImage, 50, 20, null );
		}
		if (message != null)
		{
			g.drawString( message, 10, 140 );
		}
	}

	protected static void usage()
	{
		System.out.print("Usage: TestEncoderB -alpha -filter n -compress c");
		System.out.println(" -depth d");
		System.out.println("-alpha means to use alpha encoding (default none)");
		System.out.println("n is filter number 0=none (default), 1=sub, 2=up");
		System.out.println("c is compression factor (0-9); 1 default");
		System.out.println("d is pixel depth (8 or 24 bit); 24 default");
		System.exit(0);
	}

	public static void main(String[] args)
	{
		int	i;

		TestEncoderB te = new TestEncoderB("Test PNG Alpha/Filter Encoder");
		i = 0;
		te.encodeAlpha = false;
		te.filter = 0;
		te.pixelDepth = 24;
		te.compressionLevel = 1;
		while (i < args.length)
		{
			if (args[i].equals("-alpha"))
			{
				te.encodeAlpha = true;
				i++;
			}
			else if (args[i].equals("-filter"))
			{
				if (i != args.length-1)
				{
					try
					{
						te.filter = Integer.parseInt(args[i+1]);
					}
					catch (Exception e)
					{
						usage();
						break;
					}
				}
				i += 2;
			}
			else if (args[i].equals("-compress"))
			{
				if (i != args.length-1)
				{
					try
					{
						te.compressionLevel = Integer.parseInt(args[i+1]);
					}
					catch (Exception e)
					{
						usage();
						break;
					}
				}
				i += 2;
			}
			else if (args[i].equals("-depth"))
			{
				if (i != args.length-1)
				{
					try
					{
						te.pixelDepth = Integer.parseInt(args[i+1]);
						if (te.pixelDepth != 8 && te.pixelDepth != 24)
						{
							usage();
							break;
						}
					}
					catch (Exception e)
					{
						usage();
						break;
					}
				}
				i += 2;
			}
			else
			{
				usage();
				break;
			}
		}
		if (te.pixelDepth == 8)
		{
			te.encodeAlpha = false;
		}
		te.constructImage();
		te.do_your_thing();
	}
	
	public void do_your_thing()
	{
		Image	img;

		// The resultant PNG data will go into this array...
		
		Calendar cal = Calendar.getInstance();	

		hour = cal.get(Calendar.HOUR);
		if (cal.get(Calendar.AM_PM) == 1)
		{
			hour += 12;
		}
		hour %= 24;
		minute = cal.get(Calendar.MINUTE);

		/*
		 * format the time to a string of the form
		 *    hhmm
		 * for use in the filename
		 */
		
		timeStr = Integer.toString( minute );
		if (minute < 10)
		{
			timeStr = "0" + timeStr;
		}
		timeStr = Integer.toString( hour ) + timeStr;
		if (hour < 10)
		{
			timeStr = "0" + timeStr;
		}

		filename = (encodeAlpha) ? "alphaclock" : "clock";
		filename += timeStr + "_f" + filter + 
		   compressionLevel + 
		   ((pixelDepth==8) ? "i" : "d") + ".png";

		message =  "File: " + filename;

		drawClockImage(hour, minute);
		if (encodeAlpha) { addAlphaToImage(); }

		saveClockImage();

		WindowListener l = new WindowAdapter() {
                        public void windowClosing(WindowEvent e) {
                        System.exit(0);
                       }
        };
        addWindowListener(l);
		show();
	}
	
	public void saveClockImage()
	{
		byte[] pngbytes;
		PngEncoderB png =  new PngEncoderB( clockImage,
			(encodeAlpha) ? PngEncoder.ENCODE_ALPHA : PngEncoder.NO_ALPHA,
			filter, compressionLevel );

		try
		{
			FileOutputStream outfile = new FileOutputStream( filename );
			pngbytes = png.pngEncode();
			if (pngbytes == null)
			{
				System.out.println("Null image");
			}
			else
			{
				outfile.write( pngbytes );
			}
			outfile.flush();
			outfile.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}		

}

