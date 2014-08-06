/******************************************************************************* 
* Copyright 2006-2008, YDON. All Rights Reserved.
* Confidential and Proprietary Information of YDON.
* %A%
******************************************************************************/ 

package com.doteyplay.utils;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class PopupAuthenticator extends Authenticator {

	private String username;
	private String passwrod;

	public PopupAuthenticator() {
	};

	public PopupAuthenticator(String username, String password) {
		this.username = username;
		this.passwrod = password;
	}

	public PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(username, passwrod);
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.passwrod = password;
	}
}
