package com.example.hometownrestaurant.pojo;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

//
//@Component
//@ConfigurationProperties(prefix = "alipay")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Configuration
/**
 * 支付宝配置类
 */
public class AlipayConfig {
        @Value("${alipay.appId}")
        private String appId;

        @Value("${alipay.privateKey}")
        public String privateKey;

        @Value("${alipay.publicKey}")
        public String publicKey;

        @Value("${alipay.notifyUrl}")
        public String notifyUrl;

        @Value("${alipay.returnUrl}")
        public String returnUrl;

        @Value("${alipay.signType}")
        private String signType;

        @Value("${alipay.charset}")
        private String charset;

        /**
         * 订单超时时间
         */
        private String timeout = "2m";

        /**
         * 支付宝网关；https://openapi-sandbox.dl.alipaydev.com/gateway.do
         */
        @Value("${alipay.gatewayUrl}")
        public String gatewayUrl;

        public String pay(Order order) throws AlipayApiException {
            System.out.println("APPID:" + appId);
            System.out.println("应用私钥:" + privateKey);
            System.out.println("支付宝公钥:" + publicKey);
            System.out.println("支付宝会悄悄的给我们发送一个请求，告诉我们支付成功的信息:" + notifyUrl);
            System.out.println("同步通知，支付成功，一般跳转到成功页:" + returnUrl);
            System.out.println("签名方式:" + signType);
            System.out.println("字符编码格式:" + charset);
            System.out.println("订单超时时间:" + timeout);
            System.out.println("支付宝网关:" + gatewayUrl);
            // 1. 根据支付宝的配置生成一个支付客户端
            AlipayClient alipayClient = new DefaultAlipayClient(
                    gatewayUrl,             // 支付宝网关地址
                    appId,                  // 应用ID
                    privateKey,     // 商户私钥
                    "json",                 // 请求数据格式
                    charset,                // 字符编码格式
                    publicKey,        // 支付宝公钥
                    signType                // 签名方式
            );

            //2、创建一个支付请求，并设置请求参数
            AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
            alipayRequest.setReturnUrl(returnUrl);// 设置支付成功后的返回地址
            alipayRequest.setNotifyUrl(notifyUrl);// 设置支付结果通知地址

            Long id = order.getOrderid();                            // 获取订单ID
            Long interfaceInfoId = order.getInterfaceinfoid();  // 获取接口信息ID
            double money = order.getTotalmoney();                    // 获取支付金额
            String paymentMethod = order.getPaymentmethod();    // 获取支付方式

            // 设置业务内容，包含必要的支付参数
            alipayRequest.setBizContent(
                    "{" +
                            "\"out_trade_no\":\"" + id + "\"," +
                            "\"total_amount\":\"" + money + "\"," +
                            "\"subject\":\"" + interfaceInfoId + "\"," +
                            "\"body\":\"" + paymentMethod + "\"," +
                            "\"timeout_express\":\"" + timeout + "\"," +
                            "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"" +
                            "}"
            );
            // 执行支付请求并获取支付宝的响应
            String result = alipayClient.pageExecute(alipayRequest).getBody();
            //会收到支付宝的响应，响应的是一个页面，只要浏览器显示这个页面，就会自动来到支付宝的收银台页面
            System.out.println("支付宝的响应：" + result);
            //返回支付宝响应的结果
            return result;
        }

    }