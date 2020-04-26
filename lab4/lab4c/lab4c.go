package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

//Graph is YOU; Graph is WIN; BABA is DEFEAT
type Graph struct {
	matrix [][]int
	size   int
	rwLock *sync.RWMutex
}

type pair struct {
	v    int
	cost int
}

//INTMAX - prime big int
var INTMAX = 1000000007

var done = make(chan bool, 4)

func min(a int, b int) int {
	if a < b {
		return a
	}
	return b

}

func initGraph(graph *Graph) {
	graph.matrix = [][]int{
		{0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0},
	}
	graph.size = 5
	graph.rwLock = new(sync.RWMutex)

}

func changeTicketPrice(rw *sync.RWMutex, graph *Graph) {
	for {
		rw.Lock()
		i := rand.Intn(graph.size)
		j := rand.Intn(graph.size)
		c := rand.Intn(10)
		if graph.matrix[i][j] != 0 {
			graph.matrix[i][j] = c
			graph.matrix[j][i] = c
		}
		rw.Unlock()
		time.Sleep(200 * time.Millisecond)
	}
	//done <- true

}

func changeRoutes(rw *sync.RWMutex, graph *Graph) {
	for {
		rw.Lock()
		i := rand.Intn(graph.size)
		j := rand.Intn(graph.size)
		c := rand.Intn(10)
		if graph.matrix[i][j] == 0 {
			graph.matrix[i][j] = c
		} else {
			graph.matrix[i][j] = 0
		}
		rw.Unlock()
		time.Sleep(200 * time.Millisecond)
	}

}

func changeCities(rw *sync.RWMutex, graph *Graph) {
	i := 0
	for {
		rw.Lock()
		if i == 0 {
			for j := 0; j < graph.size; j++ {
				graph.matrix[j] = append(graph.matrix[j], 0)
			}
			graph.size++
			graph.matrix = append(graph.matrix, make([]int, graph.size))
		} else {
			graph.size--
			graph.matrix = graph.matrix[:graph.size]
			for j := 0; j < graph.size; j++ {
				graph.matrix[j] = graph.matrix[j][:graph.size]
			}
		}
		i = (i + 1) % 2
		rw.Unlock()
		time.Sleep(200 * time.Millisecond)
	}

}

func findPathBetweenCities(rw *sync.RWMutex, graph *Graph) {
	for {
		rw.RLock()
		i := rand.Intn(graph.size)
		j := rand.Intn(graph.size)
		ans := dfs(i, j, graph, make([]bool, graph.size))
		rw.RUnlock()

		fmt.Println(i, j, ans)
		time.Sleep(1 * time.Second)
	}
}

func dfs(v int, destination int, graph *Graph, used []bool) int {
	if v == destination {
		return 0
	}
	ans := INTMAX
	for i := 0; i < graph.size; i++ {
		if i == v {
			continue
		}
		if !used[i] && graph.matrix[v][i] > 0 {
			used[i] = true
			ans = min(ans, dfs(i, destination, graph, used)+graph.matrix[v][i])
			used[i] = false
		}
	}
	return ans
}

func main() {
	graph := new(Graph)
	initGraph(graph)

	go changeRoutes(graph.rwLock, graph)
	go changeCities(graph.rwLock, graph)
	go changeTicketPrice(graph.rwLock, graph)
	go findPathBetweenCities(graph.rwLock, graph)

	time.Sleep(1000 * time.Second)
}
