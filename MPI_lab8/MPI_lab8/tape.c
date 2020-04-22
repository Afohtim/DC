#include "tape.h"

void tape_circuit_multiplication(float const *a, float const *b, float *c, int size, int process_id, int tape_count)
{
	size_t const current_size = (size / tape_count);
	size_t const begin = current_size * process_id;
	size_t const end = begin + current_size;

	MPI_Status status;
	int const tag = 1;

	float * a_matrix = NULL;
	float * b_matrix = NULL;
	float * c_matrix = NULL;

	if (process_id == MAIN_PROCESS_ID)
	{
		for (int i = 1; i < (int)tape_count; ++i)
		{
			MPI_Send(&b[0], (int)(size * size), MPI_FLOAT, i, tag, MPI_COMM_WORLD);
			MPI_Send(&a[size * current_size * (size_t)i], (int)(size * current_size), MPI_FLOAT, i, tag, MPI_COMM_WORLD);
		}
	}

	if (process_id != MAIN_PROCESS_ID)
	{
		a_matrix = (float *)malloc(size * current_size * sizeof(float));
		b_matrix = (float *)malloc(size * size * sizeof(float));
		c_matrix = (float *)malloc(size * current_size * sizeof(float));

		MPI_Recv(&b_matrix[0], (int)(size * size), MPI_FLOAT, MAIN_PROCESS_ID, tag, MPI_COMM_WORLD, &status);
		MPI_Recv(&a_matrix[0], (int)(size * current_size), MPI_FLOAT, MAIN_PROCESS_ID, tag, MPI_COMM_WORLD, &status);
	}

	if (process_id == MAIN_PROCESS_ID)
	{
		for (size_t i = begin; i < end; ++i)
		{
			for (size_t j = 0; j < size; ++j)
			{
				float temp = 0.f;
				for (size_t k = 0; k < size; ++k)
				{
					temp += a[i * size + k] * b[k * size + j];
				}
				c[i * size + j] = temp;
			}
		}
	}
	if (process_id != MAIN_PROCESS_ID)
	{
		for (size_t i = 0; i < current_size; ++i)
		{
			for (size_t j = 0; j < size; ++j)
			{
				float temp = 0.f;
				for (size_t k = 0; k < size; ++k)
				{
					temp += a_matrix[i * size + k] * b_matrix[k * size + j];
				}
				c_matrix[i * size + j] = temp;
			}
		}
	}

	if (process_id == MAIN_PROCESS_ID)
	{
		for (int i = 1; i < (int)tape_count; ++i)
		{
			MPI_Recv(&c[size * current_size * (size_t)i], (int)(current_size * size), MPI_FLOAT, i, tag, MPI_COMM_WORLD, &status);
		}
	}

	if (process_id != MAIN_PROCESS_ID)
	{
		MPI_Send(&c_matrix[0], (int)(current_size * size), MPI_FLOAT, MAIN_PROCESS_ID, tag, MPI_COMM_WORLD);
	}

	if (process_id != MAIN_PROCESS_ID)
	{
		destroy_matrix(a_matrix);
		destroy_matrix(b_matrix);
		destroy_matrix(c_matrix);
	}
}

double tape_circuit_multiplication_time(int size, int process_id, int tape_count)
{
	return calculate_multiply_time(size, process_id, tape_count, tape_circuit_multiplication);
}