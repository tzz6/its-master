package com.its.web.common.springframework.http.converter;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

import com.its.common.utils.bean.ReflectUtil;
import com.its.common.utils.json.JacksonUtil;
import com.its.model.mybatis.dao.domain.SysUser;

/**
 * 自定义消息转换器
 *
 */
public class SysUserHttpMessageConverter extends AbstractHttpMessageConverter<SysUser> {
	/** 自定义application类型 */
	public SysUserHttpMessageConverter() {
		super(new MediaType("application", "x-user", Charset.forName("UTF-8")));
	}

	/**只对SysUser有效*/
	@Override
	protected boolean supports(Class<?> clazz) {
		boolean flag = SysUser.class.isAssignableFrom(clazz);
		logger.info(flag);
		return flag;
	}

	/** Request */
	@Override
	protected SysUser readInternal(Class<? extends SysUser> clazz, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {
		String tmp = StreamUtils.copyToString(inputMessage.getBody(), Charset.forName("UTF-8"));
		if (null == tmp || tmp.trim().length() == 0) {
			logger.info("===============input http message is null or empty.==================");
			return null;
		}
		SysUser sysUser = new SysUser();
		String[] tempArr = tmp.split("&");
		for (String str : tempArr) {
			String[] strArr = str.split("=");
			ReflectUtil.setFieldValue(sysUser, strArr[0], strArr[1]);
		}
		return sysUser;
	}

	/** Response */
	@Override
	protected void writeInternal(SysUser sysUser, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		String json = JacksonUtil.nonEmptyMapper().toJson(sysUser);
		json = StringEscapeUtils.unescapeHtml(json);//将转义字符转换为html(&lt;&gt; --> <>)
		logger.info(json);
		outputMessage.getBody().write(json.getBytes());
	}

}
