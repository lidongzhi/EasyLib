package com.dongzhili.easylib.net;

import com.dongzhili.easylib.base.BaseBean;
import com.dongzhili.easylib.bean.LoginBean;
import com.dongzhili.easylib.bean.VehicleBean;
import com.dongzhili.easylib.bean.VersionBean;
import com.dongzhili.easylib.bean.AccountDataBean;
import com.dongzhili.easylib.bean.OrderListBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface RetrofitService {

    /**
     * 获取版本信息
     */
    public interface CheckVersion {

        @GET("latest/app/version")
        Observable<BaseBean<VersionBean>> checkVersion();

        /**
         * 下载安装包
         */
        @GET
        Call<ResponseBody> getFile(@Url String url);
    }

    public interface Login {
        // 登录
        @POST("login")
        Observable<BaseBean<LoginBean>> login(@Body RequestBody requestBody);
    }

    public interface GetCode {
        // 获取验证码
        @POST("sms/code")
        Observable<BaseBean> getCode(@Body RequestBody requestBody);
    }

    public interface Personal {
        // 用户查看乘客须知 1乘车须知 2服务协议 3扣款授权确认书
//        @GET("customer/must/know/{type}")
//        Observable<BaseBean<MustKnowBean>> mustKnow(@Path("type") int type);
//
//        // 上传头像
//        @Multipart
//        @POST("phone/customer/upload")
//        Observable<BaseBean<PortraitBean>> uploadPortrait(@Part MultipartBody.Part part);

        // 上传头像
        @POST("phone/customer/edit")
        Observable<BaseBean> changeNick(@Body RequestBody requestBody);


    }

    public interface Inform {

        //未读通知总数
        @POST("phone/customer/notice/count")
        Observable<BaseBean<Long>> getInformNoRead(@Body RequestBody requestBody);

    }

    public interface Order {
        // 用户查看个人所有订单
        @POST("phone/customer/order")
        Observable<BaseBean<OrderListBean>> getOrderList(@Body RequestBody requestBody);

        // 获取订单二维码字符串
        @POST("phone/order/qrcode/{orderId}")
        Observable<BaseBean<String>> getQrCodeData(@Path("orderId")String orderId);

        //取消订单
        @POST("phone/cancel/demand/{demandId}")
        Observable<BaseBean> cancelDemand(@Path("demandId") String demandId);
        //取消订单
        @POST("phone/kill/demand/{demandId}")
        Observable<BaseBean> killProcess(@Path("demandId") String demandId);

    }

    public interface Wallet {

        // 消费总数
        @POST("phone/customer/trade/flow/count")
        Observable<BaseBean<String>> getConsumeCount(@Body RequestBody requestBody);

        // 消费列表
        @POST("phone/customer/trade/flow/{type}")
        Observable<BaseBean<AccountDataBean>> getAccountList(@Path("type") int type, @Body RequestBody requestBody);

        //修改交易密码
        @POST("phone/edit/trade/password")
        Observable<BaseBean> editTradePassword(@Body RequestBody requestBody);

        //设置密码
        @POST("phone/set/trade/password")
        Observable<BaseBean> setTradePassword(@Body RequestBody requestBody);

        //支付
        @POST("phone/pay/demand")
        Observable<BaseBean<String>> payDemand(@Body RequestBody requestBody);

        //申请退款
        @POST("phone/refund/order")
        Observable<BaseBean> refundOrder(@Body RequestBody requestBody);

        //开启、关闭免密支付
        @POST("phone/customer/wallet/open/no/secret/pay")
        Observable<BaseBean> setNoSecretPay(@Body RequestBody requestBody);

        @POST("phone/ailipay/search/{tradeCode}")
        Observable<BaseBean> getAliPayResult(@Path("tradeCode") String tradeCode, @Body RequestBody requestBody);

        @POST("phone/weixin/search/{tradeCode}")
        Observable<BaseBean> getWXPayResult(@Path("tradeCode") String tradeCode, @Body RequestBody requestBody);
    }

    public interface Ride{
        // 下单
        @POST("phone/init/demand")
        Observable<BaseBean<Integer>> initDemand(@Body RequestBody requestBody);
    }

    interface UpdateVersion {
        /**
         * 下载安装包
         */
        @Streaming
        @GET
        Call<ResponseBody> getFile(@Url String url);
    }

    /**
     * 获取版本信息
     */
    @POST("app/api/other/zxVersion/vehicleMobileMaxVersion")
    Observable<BaseBean<VersionBean>> checkVersion();

    /**
     * @return
     */
    @POST("app/api/driver/doLogin")
    Observable<BaseBean<LoginBean>> login(@Body RequestBody empty);

    /**
     * 开始任务
     *
     * @param empty
     * @return
     */
    @POST("app/api/vehicleTerminal/begin")
    Observable<BaseBean<String>> begin(@Body RequestBody empty);

    /**
     * 结束任务
     *
     * @param empty
     * @return
     */
    @POST("app/api/vehicleTerminal/end")
    Observable<BaseBean<String>> end(@Body RequestBody empty);

    //获取车牌号信息
    @POST("app/api/zxVehicleMonitor/getByImeiNum")
    Observable<BaseBean<VehicleBean>> getByImeiNum(@QueryMap Map<String, String> map);

}
