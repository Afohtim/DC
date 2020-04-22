#ifndef TOOLS
#define TOOLS

#include <mpi.h>
#include <stdio.h>
#include <math.h>
#include <assert.h>
#include <windows.h>

#define CONSISTENT_ALGO_NAME "consistent_multiplication"
#define TAPE_CIRCUIT_ALGO_NAME "tape circuit"
#define FOXS_METHOD_ALGO_NAME "fox's method"
#define CANNON_METHOD_ALGO_NAME "cannon method"

#define CONSISTENT_ALGO_ID 0 
#define TAPE_CIRCUIT_ALGO_ID 1
#define FOXS_METHOD_ALGO_ID 2
#define CANNON_METHOD_ALGO_ID 3

#define MAIN_PROCESS_ID 0


float *generate_matrix(int size, BOOL is_zero_fill);

void generate_matrices(float *a, float *b, float *c, int size);

void free_matrices(float *a, float *b, float *c);

double calculate_multiply_time(
	int size,
	int process_id,
	int world_size,
	void(*multiplier)(float const *, float const *, float *, int, int, int)
);

void set_to_result(
	float const * matrix,
	float * c,
	int size,
	int current_size,
	int row,
	int col
);

void destroy_matrix(float *a);


#endif
