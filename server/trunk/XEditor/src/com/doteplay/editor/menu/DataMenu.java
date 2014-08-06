/**
 * @package com.doteplay.editor.menu
 * @file DataMenu.java
 */
package com.doteplay.editor.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.doteplay.editor.XEditor;
import com.doteplay.editor.tools.help.HelpDataXmlParser;
import com.doteplay.editor.tools.update.DataUpdateToolDialog;
import com.doteplay.editor.util.Util;

/**
 */
public class DataMenu extends JMenu {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	protected XEditor xEditor;
	
	private JMenuItem dataUpdateMenuItem = null;
	private JMenuItem helpImportDBMenuItem = null;

	private JMenu importDBMenu = null;

	private JMenuItem cangbaogeActivityDescMenuItem = null;
	
	private JMenuItem fengshenBangMenuItem = null;
	
	private JMenuItem huoDongRedeemMenuItem = null;

	public DataMenu(XEditor xEditor) {
		this.xEditor = xEditor;
		this.initialize();
	}
	
	private void initialize() {
		this.setText("数据");
		this.add(getDataUpdateMenuItem());
		this.add(getImportDBMenu());
	}
	
	/**
	 * 打开资源更新设定
	 */
	public void showDataUpdateToolDialog() {
		DataUpdateToolDialog newDialog = new DataUpdateToolDialog(xEditor.getJFrame());
		newDialog.setModal(true);
		newDialog.init();
		Util.showCenteredDialog(newDialog, xEditor.getJFrame());

		newDialog.release();
		newDialog = null;
	}
	
    /**
     * XML文件导入导出
     */
    public void showHelpImportDBDialog(){

		String fileFormat[] = { "xml","XML" };
		JFileChooser chooser = new JFileChooser();
		FileFilter  filter = new FileNameExtensionFilter( "xml文件",fileFormat);
        chooser.setFileFilter(filter);//设置文件后缀过滤器
        int  retval = chooser.showOpenDialog(xEditor.getJFrame());//显示"保存文件"对话框
        File f = null;//
        if(retval == JFileChooser.APPROVE_OPTION) {//若成功打开
            f = chooser.getSelectedFile();//得到选择的文件名
        }
        /**
         *  File f = Util.openSelectFileDialog("Excel文件","xlsx");
         */
		if(f == null){
			return;
		}
		
		HelpDataXmlParser parser =  new HelpDataXmlParser(f);
		if(parser.dealWithSql()){//说明的导入成功
			JOptionPane.showMessageDialog(xEditor.getJFrame(), "导入成功!");
			
		}else{//导入失败
			JOptionPane.showMessageDialog(xEditor.getJFrame(), "导入失败!");
		}
		
		
    }

	/**
	 * This method initializes dataUpdateMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getDataUpdateMenuItem() {
		if (dataUpdateMenuItem == null) {
			dataUpdateMenuItem = new JMenuItem();
			dataUpdateMenuItem.setName("");
			dataUpdateMenuItem.setText("资源更新");
			dataUpdateMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					showDataUpdateToolDialog();
				}
			});
		}
		return dataUpdateMenuItem;
	}
	

	/**
	 * This method initializes XmlMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getHelpImportDBMenuItem() {
		if (helpImportDBMenuItem == null) {
			helpImportDBMenuItem = new JMenuItem();
			helpImportDBMenuItem.setText("帮助");
			helpImportDBMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					showHelpImportDBDialog();
				}
			});
		}
		return helpImportDBMenuItem;
	}

	
	/**
	 * This method initializes importDBMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getImportDBMenu() {
		if (importDBMenu == null) {
			importDBMenu = new JMenu();
			importDBMenu.setText("导入数据库");
			importDBMenu.add(getHelpImportDBMenuItem());
		}
		return importDBMenu;
	}
	
}
