package main

//CyclicBarrier - CyclicBarrier with run
type CyclicBarrier struct {
	semaphore1      chan bool
	semaphore2      chan bool
	numberOfThreads int
}

func (barrier CyclicBarrier) work(run func()) {
	for {
		for i := 0; i < barrier.numberOfThreads; i++ {
			<-barrier.semaphore1
		}
		run()
		for i := 0; i < numberOfThreads; i++ {
			barrier.semaphore2 <- true
		}
	}
}

func (barrier CyclicBarrier) run(check func()) {
	go barrier.work(check)
}

func (barrier CyclicBarrier) await() {
	barrier.semaphore1 <- true
	<-barrier.semaphore2
}
