package com.xkc.androidgoogleplay;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.SkuDetails;
import com.android.vending.billing.IInAppBillingService;
import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
import com.xck.tirisfirebasesdk.module.login.utils.logs.GCLogger;
import com.xck.tirisfirebasesdk.module.net.ToastUtil;
import com.xck.tirisfirebasesdk.module.net.interfaces.RequestPayCall;
import com.xck.tirisfirebasesdk.module.pay.bean.RequestPayData;
import com.xck.tirisfirebasesdk.module.pay.utils.GoogleBillingUtil;
import com.xck.tirisfirebasesdk.module.pay.utils.PayUtil;
import com.xck.tirisfirebasesdk.module.ugame.UGameUtil;
import org.json.JSONObject;

import java.util.List;

public class PayActivity extends AppCompatActivity implements GoogleBillingUtil.OnPurchaseFinishedListener,
        GoogleBillingUtil.OnQueryFinishedListener, GoogleBillingUtil.OnStartSetupFinishedListener,
        GoogleBillingUtil.OnConsumeResponseListener  {

    private RecyclerView mProducts;
    private GoogleBillingUtil googleBillingUtil;

    private ProductsAdapter productsAdapter ;
//    IInAppBillingService mService;
//
//    ServiceConnection mServiceConn = new ServiceConnection() {
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//            mService = null;
//        }
//
//        @Override
//        public void onServiceConnected(ComponentName name,
//                                       IBinder service) {
//            GCLogger.debug("ComponentName " + name);
//            mService = IInAppBillingService.Stub.asInterface(service);
//        }
//    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
        setContentView(R.layout.activity_pay);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mProducts = findViewById(R.id.products);
        mProducts.setLayoutManager(layoutManager);

        findViewById(R.id.loadProducts).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                googleBillingUtil.queryInventoryInApp();
//                googleBillingUtil.queryInventorySubs();
//                googleBillingUtil.queryPurchasesInApp();
//                RequestPayData requestPayData =new RequestPayData();
//                requestPayData.commodityId = "get_1_coins"; //商品ID
//                requestPayData.skuType = BillingClient.SkuType.INAPP; //内购
//                googleBillingUtil.requestPayment(PayActivity.this,requestPayData);
            }
        });
        findViewById(R.id.loadProducts1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UGameUtil.getInstance().pay(1,100,1000);
                RequestPayData requestPayData   =new RequestPayData();
                requestPayData.gameOrderId      = "XCK.3307-0630-9005-87571"; //游戏订单号
                requestPayData.payNumber        = 1;      //商品数量
                requestPayData.productPrice     = 2000.0; //商品价格
                requestPayData.productAttr      = "测试商品"; //商品介绍
                requestPayData.productID        = "get_10_coins"; //商品ID
                requestPayData.productName      = "游戏币"; //商品名称
                requestPayData.userName         = "游戏币"; //商品名称
                requestPayData.userName         = "xck玩家1"; //用户角色名
                requestPayData.userArea         = "1服"; //商品名称
                requestPayData.stay_field1      = ""; //保留字段1 可选
                requestPayData.stay_field2      = ""; //保留字段2 可选


                PayUtil.getInstance()
                        .requestPayment(PayActivity.this,requestPayData, new RequestPayCall(){
                    @Override
                    public void call(int code, String massage) {
                        if (code == 0) { //成功

                        }
                        ToastUtil.toast(massage);
                    }
                });
            }
        });
    }

    private void initProductAdapter(List<SkuDetails> skuDetailsList) {
        productsAdapter = new ProductsAdapter(skuDetailsList, new ProductsAdapter.OnCallListener() {
            @Override
            public void call(SkuDetails skuDetails) {
                RequestPayData requestPayData   =new RequestPayData();
                requestPayData.productID        = skuDetails.getSku(); //google商品ID
                requestPayData.gameOrderId      = "XCK.3307-0630-9005-87571"; //游戏订单号
                requestPayData.payNumber        = 1;      //商品数量
                requestPayData.productPrice     = 1000.0; //商品价格
                requestPayData.skuType = BillingClient.SkuType.INAPP;
                requestPayData.skuDetails =skuDetails;
                googleBillingUtil.requestPayment(PayActivity.this,requestPayData);
            }
        });
        mProducts.setAdapter(productsAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        UGameUtil.getInstance().onPageStart(this,PayActivity.class.getName());
    }

    @Override
    protected void onPause() {
        super.onPause();
        UGameUtil.getInstance().onPageEnd(this,PayActivity.class.getName());
    }




    @Override
    public void onQuerySuccess(String skuType, List<SkuDetails> list) {
        Log.e("PayActivity", "onQuerySuccess " + skuType);

        if (list !=null && skuType == BillingClient.SkuType.INAPP) {
            initProductAdapter(list);
        }
    }

    @Override
    public void onQueryFail(int responseCode) {
        Log.e("PayActivity", "onQueryFail " + responseCode);

    }

    @Override
    public void onQueryError() {
        Log.e("PayActivity", "onQueryError ");

    }

    @Override
    public void onPurchaseSuccess(List<Purchase> list) {
        for (int i =0 ; i< list.size();i++){
            Log.e("PayActivity", "onPurchaseSuccess " + list.get(i).getSku() );
            Log.e("PayActivity", "onPurchaseSuccess " + new Gson().toJson(list));
        }

    }


    @Override
    public void onPurchaseFail(int responseCode) {
        Log.e("PayActivity", "onPurchaseFail " + responseCode);
    }

    @Override
    public void onPurchaseError() {
        Log.e("PayActivity", "onPurchaseError " );

    }

    @Override
    public void onSetupSuccess() {
        Log.e("PayActivity", "onSetupSuccess " );

    }

    @Override
    public void onSetupFail(int responseCode) {
        Log.e("PayActivity", "onSetupFail " + responseCode);

    }

    @Override
    public void onSetupError() {
        Log.e("PayActivity", "onSetupError ");

    }

    @Override
    public void onConsumeSuccess(String purchaseToken) {
        Log.e("PayActivity", "onConsumeSuccess " + purchaseToken);

    }

    @Override
    public void onConsumeFail(int responseCode) {

        Log.e("PayActivity", "onConsumeFail " + responseCode);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PayUtil.getInstance().cleanListener();
        PayUtil.getInstance().endConnection();
    }
}
