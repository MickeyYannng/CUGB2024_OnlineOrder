package com.example.hometownrestaurant.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.easysdk.factory.Factory;
import com.example.hometownrestaurant.pojo.AlipayConfig;
import com.example.hometownrestaurant.pojo.Order;
import com.example.hometownrestaurant.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付宝接口
 */
@Slf4j
@RestController
@RequestMapping("/alipay")
public class AlipayController {

    @Resource
    AlipayConfig alipayConfig;
    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/pay", produces = "text/html")
    @ResponseBody
    public String createOrder(@RequestParam int userid, @RequestParam double totalmoney) throws AlipayApiException {
        // 创建订单对象并设置属性
//        Order order = createOrder();
        log.info("创建订单参数:{}，{}", userid,totalmoney);
        Order order =  orderService.createOrder(userid,totalmoney);
        // 调用支付宝支付模板进行支付
        return alipayConfig.pay(order);
    }


    @PostMapping("/notify")  // 注意这里必须是POST接口
    public String payNotify(HttpServletRequest request) throws Exception {
        // 检查交易状态是否为成功
        if (!"TRADE_SUCCESS".equals(request.getParameter("trade_status"))) {
            log.info("支付失败！");
            return "failure"; // 如果状态不是成功，则返回失败
        }

        System.out.println("=========支付宝异步回调========");

        // 创建一个存储请求参数的Map
        Map<String, String> params = getRequestParams(request);

        // 提取支付信息
        String tradeNo = params.get("out_trade_no");    // 商户订单号
        String gmtPayment = params.get("gmt_payment");  // 付款时间
        String alipayTradeNo = params.get("trade_no");  // 支付宝交易号

        // 验证支付宝返回的签名
        if (verifySignature(params)) {
            logTransactionDetails(params); // 记录交易详情
            // 更新订单状态的逻辑可以在这里添加
        }

        return "success"; // 返回成功响应给支付宝
    }

    /**
     * 提取请求参数的方法
     * @param request
     * @return
     */
    private Map<String, String> getRequestParams(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (String name : requestParams.keySet()) {
            params.put(name, request.getParameter(name));
        }
        return params;
    }

    /**
     * 验证签名的方法
     * @param params
     * @return
     * @throws Exception
     */
    private boolean verifySignature(Map<String, String> params) throws Exception {
        return Factory.Payment.Common().verifyNotify(params);
    }

    /**
     * 记录交易详情的方法
     * @param params
     */
    private void logTransactionDetails(Map<String, String> params) {
        System.out.println("交易名称: " + params.get("subject"));
        System.out.println("交易状态: " + params.get("trade_status"));
        System.out.println("支付宝交易凭证号: " + params.get("trade_no"));
        System.out.println("商户订单号: " + params.get("out_trade_no"));
        System.out.println("交易金额: " + params.get("total_amount"));
        System.out.println("买家在支付宝唯一id: " + params.get("buyer_id"));
        System.out.println("买家付款时间: " + params.get("gmt_payment"));
        System.out.println("买家付款金额: " + params.get("buyer_pay_amount"));
    }
}