package com.doteyplay.messageconstants.constants;

import com.doteyplay.core.resourcebundle.WordResource;
import com.doteyplay.messageconstants.MessageUtil;
import com.doteyplay.messageconstants.MsgPolymer;

/**
 * 系统常量: 通用内容
 */
public enum SystemMsgConstants implements IMsgConstants {

    SYS_BUSY("系统繁忙，请稍候重试"),

    DAY_TIME_MONTH("月"),
    DAY_TIME_DAY("日"),
    DAY_TIME_YEAR("年"),
    DAY_TIME_TIAN("天"),
    DAY_TIME_HOUR("小时"),
    DAY_TIME_MINUTE("分"),
    DAY_TIME_FZ("分钟"),
    DAY_TIME_SECOND("秒"),
    DAY_TIME_FOREVER("永久"),
    DAY_TIME_MINUTE_SECOND("mm'分'ss'秒'"),
    DAY_TIME_MONTY_DAY("MM月dd日"),
    DAY_TIME_FORMAT_1("yyyy年MM月dd日 HH:mm"),
    DAY_TIME_FORMAT_2(" MM月dd日HH点mm分"),
    
 
    
	;
	private final String message;
    private SystemMsgConstants(String message) {
        this.message = message;
    }

    /**
     * 通过索引获取相应的枚举对象
     * 
     * @param index
     *            必须为有效的索引
     * @return
     */
    public static SystemMsgConstants valueOf(int index) {
        return values()[index];
    }

    @Override
    public String getMessage() {
        return WordResource.get("SystemMsgConstants." + name(), message);
    }

    @Override
    public String getMessage(String... param) {
        return MessageUtil.getMessage(WordResource.get("SystemMsgConstants." + name(), message), param);
    }

    @Override
    public MsgPolymer getMsgPolymer(String... param) {
        return new MsgPolymer(this, param);
    }

    public String toString() {
        return message;
    }
}
