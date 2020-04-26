package main

import "fmt"

func get_winner(response chan<- int, left int, right int, data []int) {
	if right - left == 1 {
		ans := 0
		if data[left] > data[right] {
			ans = left
		} else {
			ans = right
		} 
		response <- ans
	} else if left == right {
		response <- left
	} else {
		current_response := make(chan int, 2)

		mid := (left + right)/2
		go get_winner(current_response, left, mid, data)
		go get_winner(current_response, mid + 1, right, data)

		ans_1 := <-current_response
		ans_2 := <-current_response

		ans := 0
		if data[ans_1] >= data[ans_2] {
			ans = ans_1
		} else {
			ans = ans_2
		}
		response <- ans


	}
	
}

func main() {
	data := []int{ 12, 3, 5, 13, 4, 1, 13, 20, 22, 3, 5, 17, 1, 1, 13, 20}
	size := len(data)
	response := make(chan int, 2)
	go get_winner(response, 0, size - 1, data)

	result := <-response

	fmt.Printf("Result = %d\n", data[result])
}