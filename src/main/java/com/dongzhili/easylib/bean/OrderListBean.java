package com.dongzhili.easylib.bean;

import java.util.List;

/**
 * @author wuqiusen
 * @classNote:
 * @date 2018/5/24.
 */
public class OrderListBean {
    /**
     * current : 0
     * list : [{"id":0,"offSiteName":"string","onSiteName":"string","performStatus":0,"travelTime":"string"}]
     * total : 0
     */

    public int current;
    public int total;
    public List<OrderListItemBean> list;

}
