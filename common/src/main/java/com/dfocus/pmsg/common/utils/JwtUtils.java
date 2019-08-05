package com.dfocus.pmsg.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
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
	 * 私钥加密-生成token
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static String getToken(String privateKeyStr, Map<String, Object> map) throws Exception {

		// 生成签名私钥
		byte[] keyBytes = (new BASE64Decoder()).decodeBuffer(privateKeyStr);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

		// 生成token
		Map<String, Object> mapHeader = new HashMap<>(2);
		mapHeader.put("alg", "RSA256");
		mapHeader.put("typ", "JWT");
		long iat = System.currentTimeMillis();
		long exp = iat + expiredTime * 1000L;
		JWTCreator.Builder jwtBuilder = JWT.create();
		map.forEach((key, value) -> jwtBuilder.withClaim(key, String.valueOf(value)));
		String token = jwtBuilder.withHeader(mapHeader).withIssuedAt(new Date(iat)).withExpiresAt(new Date(exp))
				.sign(Algorithm.RSA256((RSAKey) privateKey));
		return token;
	}

	/**
	 * 公钥解密-校验
	 * @param token
	 * @return
	 */
	public static Map<String, Claim> verify(String publicKeyStr, String token) {
		DecodedJWT jwt;
		try {
			// 生成签名公钥
			byte[] keyBytes = (new BASE64Decoder()).decodeBuffer(publicKeyStr);
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey publicKey = keyFactory.generatePublic(keySpec);

			// 校验
			JWTVerifier verifier = JWT.require(Algorithm.RSA256((RSAKey) publicKey)).build();
			jwt = verifier.verify(token);
			return jwt.getClaims();
		}
		catch (Exception ex) {
			logger.error("verify jwt token failed, token={}", token, ex);
			return null;
		}
	}

}
