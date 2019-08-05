package com.dfocus.pmsg.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: qfwang Date: 2018-03-15 下午2:19
 */
public class JwtUtils {

	private final static Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	/**
	 * 到期时间一个月
	 */
	private static long expiredTime = 60 * 60 * 24 * 30;

	/**
	 * 秘钥
	 */
	private static String RSA_PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANDrROfXcbDeMB40Aa+X/JkRquSj2hXfeMAbkiArzobMEkqwDbdG76/YtSRx6Nem79yqwhUNqNg+lKfMRMprERDm/+/eX1Tv3YDFzpXZay0md5hZxUQKNqWDobCd7U8twKRDfIw1BfIBtPidL3RKmOXlJudoa+KeThIRQlT2DHrFAgMBAAECgYBs1OKEU7sqA9TVJwppyqcPpiB8Es8c7dkdWj94+tkPZ2dv+N5sR0u9MwrJ/XzqOlBhh6KrDP6UB6Ww87wyJiwwy+11R6Oz2XxPSGBFimoZPyc+dgPC/z63PPpHStXN9027IcaS47sjvtUp6HoCjLZZNht/oSGxSLZjLDXPm3JLgQJBAP2MAgP387fF8wa5WHqafaNGKW5KI9srwY7szCbleO0QEL5eLmYVGUns5sjRFJSjl242QWvkmNlp52TgIkfj+M0CQQDS8LnGa5zeWSzUiDkPoiMhaJSVQtlgApfsTAA7Lx+M0TENjJcFGFKhGWDSY8AIE1DzYWIijRbC0vsc+WZZzOnZAkEA1dt3/7zudv2iJPPEq3UPr94IKByk7cKUemdFMzGus9YvKULrQ/Nb5zzI1G12PIFXwwBEYirouclYAYADqjuhqQJAHfgDfNRHKjPjMaLU8IqpkRKJoZcoyQI1UWYO1lnAksIZxQIHZrro6mhvoBR58OvFoX5hceU3qaBN+vTX/MQnKQJBAKX95ga2NNWQ5Y1TfMEN3X0Ki+y9H64HODxaIuHW/h0W7kVssHSdNPvlEzR+S5pBaVy/6nDHCUXtz5Uuq3Wm3CA=";

	/**
	 * 公钥
	 */
	private static String RSA_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDQ60Tn13Gw3jAeNAGvl/yZEarko9oV33jAG5IgK86GzBJKsA23Ru+v2LUkcejXpu/cqsIVDajYPpSnzETKaxEQ5v/v3l9U792Axc6V2WstJneYWcVECjalg6Gwne1PLcCkQ3yMNQXyAbT4nS90Spjl5SbnaGvink4SEUJU9gx6xQIDAQAB";

	/**
	 * 私钥加密-生成token
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static String getToken(Map<String, Object> map) throws Exception {
		String username = (String) map.get("userName");
		String userId = (String) map.get("userId");
		String account = (String) map.get("account");
		Map<String, Object> mapHeader = new HashMap<>(2);
		mapHeader.put("alg", "HS256");
		mapHeader.put("typ", "JWT");
		long iat = System.currentTimeMillis();
		long exp = iat + expiredTime * 1000L;

		// 生成签名私钥
		byte[] keyBytes = (new BASE64Decoder()).decodeBuffer(RSA_PRIVATE_KEY);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

		String token = JWT.create().withHeader(map).withIssuedAt(new Date(iat)).withExpiresAt(new Date(exp))
				.withClaim("userName", username).withClaim("userId", userId).withClaim("account", account)
				.sign(Algorithm.RSA256((RSAKey) privateKey));
		return token;
	}

	/**
	 * 公钥解密-鉴权
	 * @param token
	 * @return
	 */
	public static Map<String, Claim> verify(String token) {
		DecodedJWT jwt;
		try {
			// 生成签名公钥
			byte[] keyBytes = (new BASE64Decoder()).decodeBuffer(RSA_PUBLIC_KEY);
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey publicKey = keyFactory.generatePublic(keySpec);

			JWTVerifier verifier = JWT.require(Algorithm.RSA256((RSAKey) publicKey)).build();
			jwt = verifier.verify(token);
			Map<String, Claim> claims = jwt.getClaims();
			System.out.println("====用户名====" + claims.get("userName").asString());
			return jwt.getClaims();
		}
		catch (Exception ex) {
			logger.error("verify jwt token failed, token={}", token, ex);
			return null;
		}

	}

}
