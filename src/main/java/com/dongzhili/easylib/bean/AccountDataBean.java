package com.dongzhili.easylib.bean;

import java.util.List;

public class AccountDataBean {
    /**
     * current : 0
     * list : [{"code":"string","customerName":"string","id":0,"pageNo":0,"pageSize":0,"sortName":"string","sortType":"string","startRow":0,"tradeAccount":"string","tradeMoney":0,"tradeStatus":0,"tradeTime":"string","tradeType":0,"tradeWay":0}]
     * total : 0
     */

    public int current;
    public int total;
    public List<ListBean> list;

    public static class ListBean {

        public String code;
        public String customerName;
        public int id;
        public int pageNo;
        public int pageSize;
        public String sortName;
        public String sortType;
        public int startRow;
        public String tradeAccount;
        public String tradeMoney;
        public int tradeStatus;
        public String tradeTime;
        public int tradeType;
        public int tradeWay;
        public String offSiteName;
        public String onSiteName;
    }
}
