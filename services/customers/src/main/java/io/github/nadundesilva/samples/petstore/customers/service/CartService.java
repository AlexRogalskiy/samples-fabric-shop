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

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import org.springframework.stereotype.Service;

import io.github.nadundesilva.samples.petstore.customers.dto.CartEntry;
import io.github.nadundesilva.samples.petstore.customers.dto.NewCartEntry;
import io.github.nadundesilva.samples.petstore.customers.model.CartItem;
import io.github.nadundesilva.samples.petstore.customers.model.CartItemKey;
import io.github.nadundesilva.samples.petstore.customers.repository.CartItemRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CartService {

    private CartItemRepository cartItemRepository;

    public void addCartEntry(String userId, NewCartEntry entry) {
        CartItem item = new CartItem(new CartItemKey(userId, UUID.randomUUID().toString()),
            entry.getPrice(), entry.getCount());
        cartItemRepository.save(item);
    }

    public Collection<CartEntry> getCartEntries(String userId) {
        Collection<CartItem> items = cartItemRepository.findByKeyUserId(userId);
        Collection<CartEntry> entries = new ArrayList<>();
        for (CartItem item : items) {
            CartEntry entry = new CartEntry(item.getKey().getItemId(), item.getPrice(),
                item.getCount());
            entries.add(entry);
        }
        return entries;
    }
}
