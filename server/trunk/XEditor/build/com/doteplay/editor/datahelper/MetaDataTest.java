package com.doteplay.editor.datahelper;

import java.util.ArrayList;
import java.util.List;

public class MetaDataTest {

    public static void main(String[] args) {
        MetaDataScheme tmpScheme = new MetaDataScheme();
        tmpScheme.loadDataSchemeFile("D:/t4work/XEditor2/conf/basedata.xml");

        List tmpDataList = new ArrayList();
        MetaData tmpData;
        long tmpAllTime = System.currentTimeMillis();
        System.out.println("begin parse");
        int i, tmpPkId, tmpSum;
        String tmpDataId, tmpName, tmpDesc;
        for (int k = 0; k < 100; k++) {
            tmpDataList.clear();
            for (i = 0; i < 2000; i++) {
                tmpData = new MetaData(tmpScheme);
                tmpData.setIntData("pkid", i);
                tmpData.setStrData("dataid", String.valueOf(i));
                tmpData.setStrData("name", String.valueOf(i));
                tmpData.setStrData("description", String.valueOf(i));
                tmpDataList.add(tmpData);
            }
            tmpSum = 0;
            for (i = 0; i < tmpDataList.size(); i++) {
                tmpData = (MetaData) tmpDataList.get(i);
                tmpPkId = tmpData.getIntData("pkid");
                tmpDataId = tmpData.getStrData("dataid");
                tmpName = tmpData.getStrData("name");
                tmpDesc = tmpData.getStrData("description");
                tmpSum += tmpPkId;
                tmpSum += tmpDataId.length();
                tmpSum += tmpName.length();
                tmpSum += tmpDesc.length();
            }
        }
        System.out.println("total time(ms):" + (System.currentTimeMillis() - tmpAllTime));
    }

}
