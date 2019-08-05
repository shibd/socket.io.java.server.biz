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

	@Test
	public void testToken() {
		Map<String, Object> map = new HashMap<>();
		map.put("userName", "baozi");
		try {
			// 加密
			String token = JwtUtils.getToken(map);
			System.out.println(token);
			Thread.sleep(1000);

			// 解密
			Map<String, Claim> verify = JwtUtils.verify(token);

			// 断言
			String userName = verify.get("userName").asString();
			System.out.println(verify.get("userName").asString());
			Assert.assertTrue(userName.equals("baozi"));
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
