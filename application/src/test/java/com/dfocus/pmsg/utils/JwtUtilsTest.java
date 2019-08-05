package com.dfocus.pmsg.utils;

import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.dfocus.pmsg.common.utils.JwtUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @auther: baozi
 * @date: 2019/8/5 15:02
 * @description:
 */
public class JwtUtilsTest {

	/**
	 * 秘钥 dm
	 */
	private static String RSA_PRIVATE_KEY_DM = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANDrROfXcbDeMB40Aa+X/JkRquSj2hXfeMAbkiArzobMEkqwDbdG76/YtSRx6Nem79yqwhUNqNg+lKfMRMprERDm/+/eX1Tv3YDFzpXZay0md5hZxUQKNqWDobCd7U8twKRDfIw1BfIBtPidL3RKmOXlJudoa+KeThIRQlT2DHrFAgMBAAECgYBs1OKEU7sqA9TVJwppyqcPpiB8Es8c7dkdWj94+tkPZ2dv+N5sR0u9MwrJ/XzqOlBhh6KrDP6UB6Ww87wyJiwwy+11R6Oz2XxPSGBFimoZPyc+dgPC/z63PPpHStXN9027IcaS47sjvtUp6HoCjLZZNht/oSGxSLZjLDXPm3JLgQJBAP2MAgP387fF8wa5WHqafaNGKW5KI9srwY7szCbleO0QEL5eLmYVGUns5sjRFJSjl242QWvkmNlp52TgIkfj+M0CQQDS8LnGa5zeWSzUiDkPoiMhaJSVQtlgApfsTAA7Lx+M0TENjJcFGFKhGWDSY8AIE1DzYWIijRbC0vsc+WZZzOnZAkEA1dt3/7zudv2iJPPEq3UPr94IKByk7cKUemdFMzGus9YvKULrQ/Nb5zzI1G12PIFXwwBEYirouclYAYADqjuhqQJAHfgDfNRHKjPjMaLU8IqpkRKJoZcoyQI1UWYO1lnAksIZxQIHZrro6mhvoBR58OvFoX5hceU3qaBN+vTX/MQnKQJBAKX95ga2NNWQ5Y1TfMEN3X0Ki+y9H64HODxaIuHW/h0W7kVssHSdNPvlEzR+S5pBaVy/6nDHCUXtz5Uuq3Wm3CA=";

	/**
	 * 公钥 dm
	 */
	private static String RSA_PUBLIC_KEY_DM = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDQ60Tn13Gw3jAeNAGvl/yZEarko9oV33jAG5IgK86GzBJKsA23Ru+v2LUkcejXpu/cqsIVDajYPpSnzETKaxEQ5v/v3l9U792Axc6V2WstJneYWcVECjalg6Gwne1PLcCkQ3yMNQXyAbT4nS90Spjl5SbnaGvink4SEUJU9gx6xQIDAQAB";

	/**
	 * 秘钥 fm
	 */
	private static String RSA_PRIVATE_KEY_FM = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBANWmAnwEpXRkNMdw00fRsORQNGrFbfB0cV7MnpI7pi6QDSIQesYAZZu4NQVQJZVbh0CdMVx++MmJq/OqEcgEDe40MTB7ffLFeIV2sUbm3cyl0GiqQp5U+iSCtXBqGHJj3ptVpdFzdFg6LNLEnrYuYGdKeaRuBCT0PY77IySWOOSrAgMBAAECgYBx2eGBDUjOWYf2sB0nLItDX6pPK56GgMbZadkmvDq2nXUiLf3+/b8n2GYIysj8aqYMvLfNPqx9WawUURDgsahpyi2mZQQwN6QDAz6e/F//JQTmriNPyVuv/d9LEkMxVu42+z75ZJJKLS8VRugT+wCopvqhxcIa2fb6KeP9bwmYgQJBAPcDFQ7rQbM79ZZmBeB+LxyjWfd0RQDCKb2suRas2UTJeX/njjRuBYEdio8KqZH2J7r3vtVjXMFaQ5CDTG+pmPECQQDdbCVQpALgp0UzlRjGWCRf81s0GKI+s3gLylgFlhY/QDC/nvkCXL2hY3kwlXb+XwVchhWP67E19AGrFccQT/dbAkAxWR9K5LsyeKcezfEcoXj+CrB9nG/wr9mw+CHeItghQvMQF420ccqNuf4lh6bK71eOpdItRXWRd1wKMgECSeqxAkBFs0NTzS0ytrTr9eUkmp5ordxp289G2YvSelGXAJXkNzqhN1O/UJ5I7V060/HvRM4VQAgNk+3mw/a6elJ9ag69AkBgp/vIeetmkeiIgrZ2BK9sW4UUvS5ofZAkpOVZ6IYYUVfkUp94ZN68HUf+aED7XyphKN27G0gF6zCKeH36XFCC";

	/**
	 * 公钥 fm
	 */
	private static String RSA_PUBLIC_KEY_FM = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDVpgJ8BKV0ZDTHcNNH0bDkUDRqxW3wdHFezJ6SO6YukA0iEHrGAGWbuDUFUCWVW4dAnTFcfvjJiavzqhHIBA3uNDEwe33yxXiFdrFG5t3MpdBoqkKeVPokgrVwahhyY96bVaXRc3RYOizSxJ62LmBnSnmkbgQk9D2O+yMkljjkqwIDAQAB";

	@Test
	public void testToken() {
		Map<String, Object> map = new HashMap<>();
		map.put("userName", "shuaige");
		try {
			// 加密
			String token = JwtUtils.getToken(RSA_PRIVATE_KEY_FM, map);
			System.out.println(token);
			Thread.sleep(1000);

			// 解密
			Map<String, Claim> verify = JwtUtils.verify(RSA_PUBLIC_KEY_FM, token);

			// 断言
			String userName = verify.get("userName").asString();
			System.out.println(verify.get("userName").asString());
			Assert.assertTrue(userName.equals("shuaige"));
		}
		catch (InvalidClaimException e) {
			System.out.println("InvalidClaimException");
		}
		catch (SignatureVerificationException e) {
			System.out.println("SignatureVerificationException");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
