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
package io.github.nadundesilva.samples.petstore.customers.service;

import java.security.Key;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.nadundesilva.samples.petstore.customers.config.AuthJwtConfig;
import io.github.nadundesilva.samples.petstore.customers.dto.credential.JwtCredential;
import io.github.nadundesilva.samples.petstore.customers.dto.credential.PasswordCredential;
import io.github.nadundesilva.samples.petstore.customers.exception.AlreadyExistsException;
import io.github.nadundesilva.samples.petstore.customers.model.User;
import io.github.nadundesilva.samples.petstore.customers.dto.NewUser;
import io.github.nadundesilva.samples.petstore.customers.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class UserService {

    private final UserRepository repository;
    private final Key key;
    private final AuthJwtConfig authJwtConfig;

    public UserService(UserRepository userRepository, AuthJwtConfig authJwtConfig) {
        this.repository = userRepository;
        this.authJwtConfig = authJwtConfig;
        this.key = authJwtConfig.getSigningPrivateKey();
    }

    @Transactional
    public void register(final NewUser user) throws AlreadyExistsException {
        Optional<User> existingUser = repository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new AlreadyExistsException();
        }
        repository.save(new User(user.getEmail(), UUID.randomUUID().toString(), user.getPassword(),
            user.getFirstName(), user.getLastName()));
    }

    public Optional<JwtCredential> authenticate(final PasswordCredential credential) {
        Optional<User> userEntity = repository.findByEmail(credential.getEmail());
        if (userEntity.isPresent() && Objects.equals(credential.getPassword(), userEntity.get().getPassword())) {
            Instant now = Instant.now();
            String jwtToken = Jwts.builder()
                .setSubject(userEntity.get().getId())
                .claim("email", userEntity.get().getEmail())
                .claim("first_name", userEntity.get().getFirstName())
                .claim("last_name", userEntity.get().getLastName())
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(1, ChronoUnit.HOURS)))
                .setAudience(authJwtConfig.getAudience())
                .setIssuer(authJwtConfig.getIssuer())
                .signWith(key, SignatureAlgorithm.ES256)
                .compact();
            return Optional.of(new JwtCredential(jwtToken));
        }
        return Optional.empty();
    }
}
