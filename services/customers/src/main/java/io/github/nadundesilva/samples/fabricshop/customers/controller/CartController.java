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
package io.github.nadundesilva.samples.fabricshop.customers.controller;

import java.util.Collection;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datastax.oss.driver.api.core.servererrors.AlreadyExistsException;

import io.github.nadundesilva.samples.fabricshop.customers.dto.CartEntry;
import io.github.nadundesilva.samples.fabricshop.customers.dto.NewCartEntry;
import io.github.nadundesilva.samples.fabricshop.customers.dto.Response;
import io.github.nadundesilva.samples.fabricshop.customers.dto.Response.Status;
import io.github.nadundesilva.samples.fabricshop.customers.service.CartService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/cart")
public class CartController {

    CartService cartService;

    @PostMapping
	public ResponseEntity<Response<?>> addCartEntry(@RequestBody NewCartEntry entry) throws AlreadyExistsException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        cartService.addCartEntry(userDetails.getUsername(), entry);
		return ResponseEntity.accepted()
			.body(Response.SUCCESS);
    }

    @GetMapping
	public ResponseEntity<Response<Collection<CartEntry>>> getCart() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Collection<CartEntry> cartEntries = cartService.getCartEntries(userDetails.getUsername());
        return ResponseEntity.ok()
            .body(new Response<>(Status.SUCCESS, cartEntries));
    }
}
