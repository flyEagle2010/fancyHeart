package com.doteplay.editor.user;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.doteplay.editor.EditorConfig;
import com.doteplay.editor.database.BaseConnectionPool;

public class UserCheckDialog extends JDialog{
	
	private UserCheckDialog instance;
	private UserData userData;
	
	private JPanel jPanel = null;
	private JLabel jLabelUser = null;
	private JLabel jLabelPassword = null;
	private JTextField jTextFieldUser = null;
	private JTextField jTextFieldPassword = null;
	private JButton jButtonOK = null;
	private JButton jButtonCancel = null;
	private JLabel jLabelMsg = null;
	private JCheckBox jCheckBoxRemember = null;
	
	private boolean isRememberUser=false;
	private String userFileName = EditorConfig.confPath + "user.txt";
	
	
	public UserCheckDialog(){
		super();
		instance=this;
		initialize();
	}
	
	public UserData getUserData(){
		return userData;
	}
	
	public boolean isUserOK(String name,String password)
    {
		
		boolean isOk=false;
		
		Connection conn=BaseConnectionPool.getConnection();
		if(conn==null)
			return false;
		
    	PreparedStatement pstat;
    	ResultSet rs;
    	String sql="select * from t_editor_user where user='"+name+"'";
    	
    	try {
    		
			pstat=conn.prepareStatement(sql);
			rs=pstat.executeQuery();
			
			while(rs.next()){
				String data_password=rs.getString("password");
				if(data_password.equals(password)){
					
					userData.id=rs.getInt("userId");
					userData.groupId=rs.getInt("groupId");
					userData.name=name;
					userData.password=password;
					
					isOk=true;					
					break;
				}
				
			}
			
			rs.close();
			pstat.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally
		{
	    	try
			{
				conn.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
    	return isOk;
    }
	
	
	private void initialize() {
		
		this.setSize(new Dimension(302, 205));
		this.setModal(true);
		this.setContentPane(getJPanel());
		
		try {
			
			File file = new File(userFileName);
			
			if(file.exists()){
				FileReader fr=new FileReader(file);
				char[] buf=new char[255];
				fr.read(buf);
				String s=new String(buf);
				String[] ss = s.split("\r\n");
				
				jTextFieldUser.setText(ss[0]);
				jTextFieldPassword.setText(ss[1]);
				
				fr.close();
				fr=null;
			}
			
			file=null;
			
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabelMsg = new JLabel();
			jLabelMsg.setBounds(new Rectangle(16, 82, 264, 24));
			jLabelMsg.setHorizontalAlignment(SwingConstants.CENTER);
			jLabelMsg.setText("");
			jLabelUser = new JLabel();
			jLabelUser.setBounds(new Rectangle(15, 15, 60, 24));
			jLabelUser.setHorizontalAlignment(SwingConstants.CENTER);
			jLabelUser.setText("用户名：");
			
			jLabelPassword = new JLabel();
			jLabelPassword.setHorizontalAlignment(SwingConstants.CENTER);
			jLabelPassword.setBounds(new Rectangle(15, 50, 60, 24));
			jLabelPassword.setText("密码：");
			
			
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabelUser, null);
			jPanel.add(jLabelPassword, null);
			jPanel.add(getJTextFieldUser(), null);
			jPanel.add(getJTextFieldPassword(), null);
			jPanel.add(getJButtonOK(), null);
			jPanel.add(getJButtonCancel(), null);
			jPanel.add(jLabelMsg, null);
			jPanel.add(getJCheckBoxRemember(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jTextFieldUser	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldUser() {
		if (jTextFieldUser == null) {
			jTextFieldUser = new JTextField();
			jTextFieldUser.setBounds(new Rectangle(80, 15, 200, 24));
			//jTextFieldUser.setText("");
			jTextFieldUser.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusGained(java.awt.event.FocusEvent e) {
					jLabelMsg.setText("");
				}
			});
		}
		return jTextFieldUser;
	}

	/**
	 * This method initializes jTextFieldPassword	
	 * 	
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextFieldPassword() {
		if (jTextFieldPassword == null) {
			jTextFieldPassword = new JPasswordField();
			jTextFieldPassword.setBounds(new Rectangle(80, 50, 200, 24));
			jTextFieldPassword.setText("");
			jTextFieldPassword.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusGained(java.awt.event.FocusEvent e) {
					jLabelMsg.setText("");
				}
			});
		}
		return jTextFieldPassword;
	}

	/**
	 * This method initializes jButtonOK	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonOK() {
		if (jButtonOK == null) {
			jButtonOK = new JButton();
			jButtonOK.setBounds(new Rectangle(39, 145, 80, 25));
			jButtonOK.setText("确认");
			jButtonOK.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					
					String name=jTextFieldUser.getText().trim();
					String password=jTextFieldPassword.getText().trim();
					
					userData=new UserData();
					
					if(isUserOK(name,password)){
						
						jLabelMsg.setText("OK!");
						
						instance.dispose();
						
						if (isRememberUser) {
							
							try {
								
								File file = new File(userFileName);
								file.createNewFile();
								FileWriter fw=new FileWriter(file);
								fw.write(userData.name+"\r\n"+userData.password+"\r\n");
								
								fw.flush();
								
								fw.close();
								fw=null;
								file=null;
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							
						}
						
					}else{
						userData=null;
						jLabelMsg.setForeground(Color.RED);
						jLabelMsg.setText("用户名或密码错误");
					}
					
				}
			});
		}
		return jButtonOK;
	}

	/**
	 * This method initializes jButtonCancel	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonCancel() {
		if (jButtonCancel == null) {
			jButtonCancel = new JButton();
			jButtonCancel.setBounds(new Rectangle(160, 145, 80, 25));
			jButtonCancel.setText("取消");
			jButtonCancel.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					instance.dispose();
				}
			});
		}
		return jButtonCancel;
	}

	/**
	 * This method initializes jCheckBoxRemember	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBoxRemember() {
		if (jCheckBoxRemember == null) {
			jCheckBoxRemember = new JCheckBox();
			jCheckBoxRemember.setBounds(new Rectangle(46, 115, 201, 23));
			jCheckBoxRemember.setText("保存用户名和密码");
			jCheckBoxRemember.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					isRememberUser=!isRememberUser;
					//System.out.println("actionPerformed()"+isRememberUser); // TODO Auto-generated Event stub actionPerformed()
				}
			});
		}
		return jCheckBoxRemember;
	}
	
	
	
}  //  @jve:decl-index=0:visual-constraint="10,10"
