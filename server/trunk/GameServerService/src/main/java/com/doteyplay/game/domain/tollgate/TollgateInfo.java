package com.doteyplay.game.domain.tollgate;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collection;

import com.doteyplay.game.config.template.TollgateDataManager;
import com.doteyplay.game.config.template.TollgateNodeDataTemplate;

/**
 * @className:RoleTollgate.java
 * @classDescription:用于存贮角色相关的关卡信息.
 * @author:Tom.Zheng
 * @createTime:2014年6月23日 上午11:49:56
 */
public class TollgateInfo  {
	/**
	 * 玩家Id
	 */
	private long roleId;

	private int tollgateId;

	private byte isOpen;

	private String otherInfo;
	
	

	private NodeArray<Integer, NodeInfo> array;

	public TollgateInfo(long roleId) {
		this(roleId,0);
	}

	public TollgateInfo(long roleId, int tollgateId) {
		super();
		this.roleId = roleId;
		this.tollgateId = tollgateId;
		initlize();
	}

	private void initlize() {
		if (array == null) {
			array = new NodeArray<Integer, NodeInfo>();
		}

	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public int getTollgateId() {
		return tollgateId;
	}

	public void setTollgateId(int tollgateId) {
		this.tollgateId = tollgateId;
	}

	public byte getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(byte isOpen) {
		this.isOpen = isOpen;
	}

	public String getOtherInfo() {
		return otherInfo;
	}

	public void setOtherInfo(String otherInfo) {
		this.otherInfo = otherInfo;
	}


	/**
	 * 
	 * @param nodeId
	 * @return boolean true :添加成功。false：里边已经有了。
	 */
	public boolean addNode(int nodeId) {

		if (array.containKey(nodeId)) {
			return false;
		}
		// 添加节点.
		NodeInfo node = new NodeInfo(roleId, tollgateId, nodeId);
		node.setIsOpen((byte) 1);
		//通过原型数据,查询结点的星级.
		byte star = TollgateDataManager.getInstance().getTollgateNodeInitStar(tollgateId, nodeId);
		node.setStarResult(star);
		this.array.addValue(nodeId, node);
		return true;
	}

	public void parseData(DataInputStream in, int ver) {
		// TODO Auto-generated method stub
		try {
			tollgateId = in.readInt();
			isOpen = in.readByte();
			otherInfo = in.readUTF();

			int readSize = in.readInt();

			NodeInfo bean = null;

			for (int i = 0; i < readSize; i++) {

				bean = new NodeInfo(roleId, tollgateId);
				bean.parseData(in, ver);
				this.array.addValue(bean.getNodeId(), bean);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void buildData(DataOutputStream out) {
		// TODO Auto-generated method stub

		try {
			out.writeInt(tollgateId);
			out.writeByte(isOpen);
			out.writeUTF(otherInfo==null?"":otherInfo);
			int size =  array.getNodeList().size();
			out.writeInt(size);
			if (size > 0) {
				for (NodeInfo bean : array.getNodeList()) {
					bean.buildData(out);
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public byte[] getBuildData(){
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(byteArrayOutputStream);
		
		buildData(out);
		
		return byteArrayOutputStream.toByteArray();
	}

	public void release() {
		// TODO Auto-generated method stub
		this.roleId = 0L;

		this.tollgateId = 0;

		this.isOpen = 0;
		this.otherInfo = null;
		if (this.array != null) {
			this.array.clear();
			this.array = null;
		}
	}

	public boolean isOpenNode(int nodeId) {
		// TODO Auto-generated method stub
		return this.array.containKey(nodeId);
	}
	
	public Collection<NodeInfo> getValues(){
		return this.array.getNodeList();
	}

	public byte acceptBattleResult(int nodeId, int star) {
		// TODO Auto-generated method stub
		//只要星级>0,便可以开启下一节点.
		//只要星级>原有的星级,就得进行更新DB操作.
		
		NodeInfo nodeInfo = this.array.get(nodeId);
		if(nodeInfo!=null){
			if(star>nodeInfo.getStarResult()){
				//需要更新
				nodeInfo.setStarResult((byte) star);
				return 1;
			}else{
				//不需要更新
				return 2;
			}
		}
		
		return 0;
		
	}
	

}
