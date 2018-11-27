package kr.latera.sibylla.authapi.util

import com.nimbusds.jose.JOSEObjectType
import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.crypto.RSASSASigner
import com.nimbusds.jose.crypto.RSASSAVerifier
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.SignedJWT
import sun.security.rsa.RSAPublicKeyImpl
import java.nio.file.Files
import java.nio.file.Paths
import java.security.KeyFactory
import java.security.spec.PKCS8EncodedKeySpec

object AuthUtil {

    fun generateToken(privKeyPath: String, issuer:String, subj: String, audience: String, jwtId: String): String {
        val privKeyBytes = Files.readAllBytes(Paths.get(privKeyPath))

        val kf = KeyFactory.getInstance("RSA")
        val privKey = kf.generatePrivate(PKCS8EncodedKeySpec(privKeyBytes))

        val signer = RSASSASigner(privKey)

        val claims = JWTClaimsSet.Builder()
                .issuer(issuer)
                .subject(subj)
                .audience(audience)
                .jwtID(jwtId)
                .build()

        val signedJWT = SignedJWT(
                JWSHeader.Builder(JWSAlgorithm.RS256).type(JOSEObjectType.JWT).build(),
                claims
        )

        signedJWT.sign(signer)

        return signedJWT.serialize()
    }

    fun verifyToken(pubKeyPath: String, token: String): Boolean {
        val pubKeyBytes = Files.readAllBytes(Paths.get(pubKeyPath))
        val verifier = RSASSAVerifier(RSAPublicKeyImpl(pubKeyBytes))

        return SignedJWT.parse(token).verify(verifier)
    }

}