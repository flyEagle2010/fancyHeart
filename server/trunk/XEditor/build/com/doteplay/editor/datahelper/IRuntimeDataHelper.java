package com.doteplay.editor.datahelper;

import java.util.List;

// 将各种数据之间的访问用元数据隔离
// 此处为了使用方便，将日志输出与元数据访问写到一个接口内
public interface IRuntimeDataHelper {

    public void resetCache();// 清空数据查询接口上的临时缓存

    public List<MetaData> getRuntimeData(String datagroup, ParamList params);// 带参数数据查询接口

    public MetaData findRuntimeData(String datagroup, String dataid);// 搜索单个数据

    public void debugInfoOutput(String info, int infotype);// 消息展示

}
