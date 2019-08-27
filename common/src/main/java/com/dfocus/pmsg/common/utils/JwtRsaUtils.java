package com.dfocus.pmsg.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
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
 * @author baozi
 * @Date: 2019/8/5 10:28
 * @Description:
 */
@Slf4j
public class JwtRsaUtils {

	/**
	 * 私钥加密-生成token
	 * @param privateKeyStr 秘钥
	 * @param map 自定义属性
	 * @param expiredTime 过期时间ms
	 * @return
	 * @throws Exception
	 */
	public static String genToken(String privateKeyStr, Map<String, Object> map, long expiredTime) throws Exception {

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
	 * 秘钥/公钥解密-校验
	 * @param publicKeyStr 公钥or秘钥
	 * @param token
	 * @return null: 校验失败
	 */
	public static Map<String, Claim> verify(String publicKeyStr, String token) throws Exception {
		DecodedJWT jwt;
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

}
