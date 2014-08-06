package com.doteyplay.core.resourcebundle.util.convert;

/**
 * ×ª»¯Òì³£
 * @author 
 *
 */
public class ConvertException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6934353380032404520L;
	/**
	 * 
	 */
	
	private Throwable cause;

    public ConvertException(String message) {
        super(message);
    }

    public ConvertException(Throwable t) {
        super(t.getMessage());
        this.cause = t;
    }

    public Throwable getCause() {
        return this.cause;
    }
}
