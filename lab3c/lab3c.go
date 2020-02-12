package main

import (
	"fmt"
	"math/rand"
	"time"
)

type semaphore chan bool

var currentItemSemaphore = make(semaphore, 1)
var found = make(semaphore, 1)
var finish_work = make(chan int)
var current_item_id = -1

var item_names = map[int]string{
	1: "tobacco",
	2: "paper",
	3: "matches",
}

func smoker(missing_item_id int) {
	for true {
		currentItemSemaphore <- true

		if current_item_id == missing_item_id {
			fmt.Printf("Smoker %d with %s started smoking\n", missing_item_id, item_names[missing_item_id])
			time.Sleep(200 * time.Millisecond)
			found <- true
		}

		<-currentItemSemaphore
	}
}

func intermediator(turns_count int) {
	for i := 0; i < turns_count; i++ {
		currentItemSemaphore <- true
		current_item_id = rand.Intn(3) + 1
		<-currentItemSemaphore
		<-found

	}
	fmt.Printf("intermediator finished work\n")

	finish_work <- 0
}

func main() {
	go smoker(1)
	go smoker(2)
	go smoker(3)

	go intermediator(15)

	<-finish_work
}
