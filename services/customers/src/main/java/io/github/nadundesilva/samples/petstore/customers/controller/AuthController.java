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
package io.github.nadundesilva.samples.petstore.customers.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.nadundesilva.samples.petstore.customers.dto.NewUser;
import io.github.nadundesilva.samples.petstore.customers.dto.Response;
import io.github.nadundesilva.samples.petstore.customers.dto.Response.Status;
import io.github.nadundesilva.samples.petstore.customers.dto.credential.JwtCredential;
import io.github.nadundesilva.samples.petstore.customers.dto.credential.PasswordCredential;
import io.github.nadundesilva.samples.petstore.customers.exception.AlreadyExistsException;
import io.github.nadundesilva.samples.petstore.customers.exception.UnauthenticatedException;
import io.github.nadundesilva.samples.petstore.customers.service.UserService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

	UserService userService;

    @PostMapping("/register")
	public ResponseEntity<Response<?>> register(@RequestBody NewUser user) throws AlreadyExistsException {
		userService.register(user);
		return ResponseEntity.accepted()
			.body(Response.SUCCESS);
	}

    @PostMapping("/authenticate")
	public ResponseEntity<Response<JwtCredential>> authenticate(@RequestBody PasswordCredential credential) throws UnauthenticatedException {
		Optional<JwtCredential> jwt = userService.authenticate(credential);
        if (jwt.isPresent()) {
			return ResponseEntity.accepted()
				.body(new Response<>(Status.SUCCESS, jwt.get()));
        }
        throw new UnauthenticatedException();
	}
}
