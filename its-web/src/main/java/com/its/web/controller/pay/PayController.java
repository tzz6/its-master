package com.its.web.controller.pay;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.its.web.controller.login.BaseController;
/**
 * 支付管理
 *
 */
@Controller
@RequestMapping("/pay")
public class PayController extends BaseController {

	/** 支付宝 */
	@RequestMapping(value = "/alipay", method = RequestMethod.GET)
	public String alipay() {
		return "/pay/alipay";
	}

	
}
