package com.doteyplay.core.server;




public enum ServerStatus {
	EMPTY, 
	NORMAL, 
	BUSY, 
	FULL, 
	CLOSED;
	// 状态对应的玩家数量上限
	private int players;

	private ServerStatus() {
		players = 0;
	}
	
	/**
	 * 初始化枚举类型的所有属性
	 */
	public static void initStatus(int maxPlayers) {
		for (ServerStatus ss : ServerStatus.values()) {
			int index = ss.ordinal();
			int avg = maxPlayers / 3;
			if (index < 2)
				ss.players = avg * (index + 1);
			else
				ss.players = maxPlayers;
		}
	}

	/**
	 * 根据玩家人数获取对应的服务器状态
	 * 
	 * @param count
	 * @return
	 */
	public static ServerStatus getByPlayerCount(int count) {
		for (ServerStatus ss : ServerStatus.values()) {
			if (count < ss.players)
				return ss;
		}
		return FULL;
	}

	@Override
	public String toString() {
		return this.name() + "(" + players + ")";
	}
}
