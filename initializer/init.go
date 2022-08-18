// Copyright (c) 2022, Nadun De Silva. All Rights Reserved.
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//   http://www.apache.org/licenses/LICENSE-2.0
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package main

import (
	"fmt"
	"net"
	"os"
	"time"
)

var (
	keyCloakAddress = os.Getenv("KEYCLOAK_ADDRESS")
)

func main()  {
	fmt.Println("Initializin Pet Store Sample")
	waitForPort(keyCloakAddress)
	fmt.Println("Initializin Pet Store Sample Complete")
}

func waitForPort(address string) {
	waitTime := 2 * time.Second
	for {
		conn, err := net.DialTimeout("tcp", address, time.Second)
		if err != nil {
			fmt.Printf("Waiting %s for %s: error %v\n", waitTime, address, err)
			time.Sleep(waitTime)
		} else {
			defer conn.Close()
			fmt.Printf("Port %s open\n", address)
			break
		}
	}
}
