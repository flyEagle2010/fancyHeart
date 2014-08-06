/**
 * 
 */
package com.doteplay.editor.common;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public interface ISavebleData {
	public void init(DataInputStream in) throws Exception;
	public void save(DataOutputStream out) throws Exception;
	public void saveClient(DataOutputStream out) throws Exception;
	public void saveServer(DataOutputStream out) throws Exception;
}
