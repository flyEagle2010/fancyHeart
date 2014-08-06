package com.doteplay.editor.component;

import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * @author tao.li Create on 2011-2-9 上午10:30:01
 * @version 1.0
 */
public class NumberField extends JTextField {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	private NumberDocument numberDocument = null; // @jve:decl-index=0:

	public NumberField() {
		super();
		initialize();
	}

	/**
	 * 限制输入长度
	 */
	public final void setLengthLimit(int limit) {
		getNumberDocument().setLengthLimit(limit);
	}

	private final void initialize() {
		this.setSize(new Dimension(45, 21));
		this.setDocument(getNumberDocument());
	}

	private final NumberDocument getNumberDocument() {
		if (numberDocument == null) {
			numberDocument = new NumberDocument();
		}
		return numberDocument;
	}

	@SuppressWarnings("serial")
	private static final class NumberDocument extends PlainDocument {
		private int lengthLimit = Integer.MAX_VALUE;

		public NumberDocument() {
			super();
		}

		private final void setLengthLimit(int limit) {
			lengthLimit = limit;
		}

		@Override
		public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
			if (str == null) {
				return;
			}

			if ((getLength() + str.length()) <= lengthLimit) {
				char[] upper = str.toCharArray();
				int length = 0;
				for (int i = 0; i < upper.length; i++) {
					if (upper[i] >= '0' && upper[i] <= '9' && upper[i] == '-') {
						upper[length++] = upper[i];
					}
				}
				super.insertString(offset, new String(upper, 0, length), attr);
			}
		}
	}
} // @jve:decl-index=0:visual-constraint="10,10"
