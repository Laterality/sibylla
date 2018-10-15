package kr.latera.sibylla.authapi

import com.nimbusds.jose.*
import com.nimbusds.jose.crypto.MACSigner
import com.nimbusds.jose.crypto.RSASSASigner
import com.nimbusds.jose.crypto.RSASSAVerifier
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.SignedJWT
import org.junit.Assert
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import sun.security.rsa.RSAPrivateKeyImpl
import sun.security.rsa.RSAPublicKeyImpl
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.nio.file.Files
import java.nio.file.Paths
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.PublicKey
import java.security.SecureRandom
import java.security.interfaces.RSAKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest
class JWTTest {

    @Value("\${private-key-path}")
    private lateinit var privKeyPath: String
    @Value("\${public-key-path}")
    private lateinit var pubKeyPath: String

    @Ignore
    @Test
    fun getKey() {
        System.out.println(privKeyPath)
        System.out.println(pubKeyPath)

        val privKeyBytes = Files.readAllBytes(Paths.get(privKeyPath))
        val pubKeyBytes = Files.readAllBytes(Paths.get(pubKeyPath))

        val kf = KeyFactory.getInstance("RSA")
        val privKey: PrivateKey = kf.generatePrivate(PKCS8EncodedKeySpec(privKeyBytes))
        val pubKey: PublicKey = kf.generatePublic(X509EncodedKeySpec(pubKeyBytes))

        val signer = RSASSASigner(privKey)

        // iss: JWT 발급자
        // sub: JWT 발급 대상
        // aud: JWT 수신자(클라이언트 서비스)
        // exp: JWT 만료 기한
        // jti: JWT ID
        val claims = JWTClaimsSet.Builder()
                .issuer("altair.latera.kr")
                .subject("user/1")
                .audience("sibylla.latera.kr")
                .jwtID("c51fd85f-b21a-4022-99f9-1e54d7bbbf52")
                .build()

        val signedJWT = SignedJWT(JWSHeader.Builder(JWSAlgorithm.RS256).type(JOSEObjectType.JWT).build(),
                claims)

        // 개인키로 서명한 뒤
        signedJWT.sign(signer)

        // 완성된 JWT를 클라이언트에 보냄
//        System.out.println(signedJWT.serialize())

        // 이후 요청 헤더의 JWT를 검사
        val parsedJWT = SignedJWT.parse(signedJWT.serialize())
        val verifier = RSASSAVerifier(RSAPublicKeyImpl(pubKeyBytes))
        // #verify 리턴값이 true이면 유효하므로 각 요청에서 페이로드 해석하여 처리
//        System.out.println(parsedJWT.verify(verifier))
    }



}