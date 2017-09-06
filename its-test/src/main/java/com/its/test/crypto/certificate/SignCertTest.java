package com.its.test.crypto.certificate;

import org.junit.Test;

import com.its.common.crypto.certificate.SignCert;

public class SignCertTest {


	/** 证书签名*/
	@Test
	public void sign() {
		try {
			SignCert signCert = new SignCert();
			signCert.sign();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}