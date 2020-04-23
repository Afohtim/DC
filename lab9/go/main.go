package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

var size = 5000
var tapes_count = 4
var ticks_per_second = 1000000000.0

var a = make([]float32, size*size)
var b = make([]float32, size*size)
var c = make([]float32, size*size)

func create_matrices() {
	for i := 0; i < size; i++ {
		for j := 0; j < size; j++ {
			a[i*size+j] = (rand.Float32() - 0.5) * 200
		}
	}
	for i := 0; i < size; i++ {
		for j := 0; j < size; j++ {
			b[i*size+j] = (rand.Float32() - 0.5) * 200
		}
	}
	for i := 0; i < size; i++ {
		for j := 0; j < size; j++ {
			c[i*size+j] = 0.0
		}
	}
}

func consecutive_time() float64 {
	start := time.Now()

	for i := 0; i < size; i++ {
		for j := 0; j < size; j++ {
			temp := c[i*size+j]
			for k := 0; k < size; k++ {
				temp += a[i*size+k] * b[k*size+j]
			}
			c[i*size+j] = temp
		}
	}

	end := time.Now()
	diff := end.Sub(start)
	return float64(diff) / ticks_per_second
}

func tape_circuit(rank int, wg *sync.WaitGroup) {
	defer wg.Done()
	current_size := (size / tapes_count)
	begin := current_size * rank
	end := begin + current_size

	for i := begin; i < end; i++ {
		for j := 0; j < size; j++ {
			temp := c[i*size+j]
			for k := 0; k < size; k++ {
				temp += a[i*size+k] * b[k*size+j]
			}
			c[i*size+j] = temp
		}
	}

}

func tape_circuit_time() float64 {
	start_time := time.Now()

	var wg sync.WaitGroup

	for i := 0; i < tapes_count; i++ {
		wg.Add(1)
		go tape_circuit(i, &wg)
	}

	wg.Wait()

	end_time := time.Now()
	diff := end_time.Sub(start_time)

	return float64(diff) / ticks_per_second
}

func main() {
	create_matrices()

	fmt.Println(consecutive_time())
	//fmt.Println(tape_circuit_time())
}
