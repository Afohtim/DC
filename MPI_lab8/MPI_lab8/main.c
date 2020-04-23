#include "consecutive.h"
#include "tape.h"
#include "fox.h"
#include "cannon.h"



int main()
{
	int const size = 5000;
	int const id = CANNON_METHOD_ALGO_ID;

	char const * name = "Undefined";
	if (id == CONSECUTIVE_ALGO_ID)
	{
		name = CONSECUTIVE_ALGO_NAME;
	}
	if (id == TAPE_CIRCUIT_ALGO_ID)
	{
		name = TAPE_CIRCUIT_ALGO_NAME;
	}
	if (id == FOXS_METHOD_ALGO_ID)
	{
		name = FOXS_METHOD_ALGO_NAME;
	}
	if (id == CANNON_METHOD_ALGO_ID)
	{
		name = CANNON_METHOD_ALGO_NAME;
	}

	int process_id;
	int world_size;

	MPI_Init(NULL, NULL);

	MPI_Comm_size(MPI_COMM_WORLD, &world_size);
	MPI_Comm_rank(MPI_COMM_WORLD, &process_id);

	double delta = HUGE_VAL;

	if (id == CONSECUTIVE_ALGO_ID)
	{
		delta = consecutive_multiplication_worktime(size, (int)process_id, (int)world_size);
	}
	if (id == TAPE_CIRCUIT_ALGO_ID)
	{
		delta = tape_circuit_multiplication_time(size, (int)process_id, (int)world_size);
	}
	if (id == FOXS_METHOD_ALGO_ID)
	{
		delta = foxs_method_multiply_time(size, (int)process_id, (int)world_size);
	}
	if (id == CANNON_METHOD_ALGO_ID)
	{
		delta = cannon_method_multiply_time(size, (int)process_id, (int)world_size);
	}

	if (process_id == 0)
	{
		printf("Algo %s, dimention %zd time %f s\n", name, size, delta);
	}

	MPI_Finalize();
	return 0;
}