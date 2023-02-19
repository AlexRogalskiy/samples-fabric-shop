/*
 * Copyright (c) 2022, Nadun De Silva. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *   http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.nadundesilva.samples.fabricshop.customers.config;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration
@Getter
public class AuthJwtConfig {
    
    @Value("${auth.jwt.audience}")
    private String audience;

    @Value("${auth.jwt.issuer}")
    private String issuer;

    private PrivateKey signingPrivateKey;
    private PublicKey signingPublicKey;

    public AuthJwtConfig() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        keyPairGenerator.initialize(256, random);
        KeyPair secretKeyPair = keyPairGenerator.generateKeyPair();

        signingPrivateKey = secretKeyPair.getPrivate();
        signingPublicKey = secretKeyPair.getPublic();
    }
}
