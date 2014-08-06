package com.doteyplay.core.resourcebundle.util.convert;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;

/**
 * ∑÷∏Ó¿‡
 * 
 * @author 
 * 
 */
public class Tokener {

	private int character;
	private boolean eof;
	private int index;
	private int line;
	private char previous;
	private Reader reader;
	private boolean usePrevious;

	public Tokener(Reader reader) {
		this.reader = reader.markSupported() ? reader : new BufferedReader(
				reader);
		this.eof = false;
		this.usePrevious = false;
		this.previous = 0;
		this.index = 0;
		this.character = 1;
		this.line = 1;
	}

	public Tokener(String s) {
		this(new StringReader(s));
	}

	public boolean end() {
		return eof && !usePrevious;
	}
	
    public boolean more() throws ConvertException {
        next();
        if (end()) {
            return false;
        } 
        back();
        return true;
    }

    public void back() throws ConvertException {
        if (usePrevious || index <= 0) {
            throw new ConvertException("ªÿÕÀ¥ÌŒÛ"+index);
        }
        this.index -= 1;
        this.character -= 1;
        this.usePrevious = true;
        this.eof = false;
    }
    
	public char next() throws ConvertException {
		int c;
		if (this.usePrevious) {
			this.usePrevious = false;
			c = this.previous;
		} else {
			try{
				c = this.reader.read();
			}catch(Exception e){
				throw new ConvertException("∂¡»°¥ÌŒÛ"+e.getMessage()); 
			}

			if (c <= 0) { // End of stream
				this.eof = true;
				c = 0;
			}
		}
		this.index += 1;
		if (this.previous == '\r') {
			this.line += 1;
			this.character = c == '\n' ? 0 : 1;
		} else if (c == '\n') {
			this.line += 1;
			this.character = 0;
		} else {
			this.character += 1;
		}
		this.previous = (char) c;
		return this.previous;
	}
}
