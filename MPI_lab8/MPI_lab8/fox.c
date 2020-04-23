#include "fox.h"

void fox_method(float const * a, float const * b, float * c, int size, int process_id, int world_size)
{
	assert(world_size == 4);

	MPI_Status status;
	int const tag = 1;
	int const dimension = 2;
	int const current_size = size / dimension;

	int const current_row = process_id / dimension;
	int const current_col = process_id % dimension;

	int const current_row_begin = current_row * current_size;
	int const current_row_end = current_row_begin + current_size;
	int const current_col_begin = current_col * current_size;
	int const current_col_end = current_col_begin + current_size;

	float * native_a_matrix = (float *)malloc(current_size * current_size * sizeof(float));
	float * additional_a_matrix = (float *)malloc(current_size * current_size * sizeof(float));
	float * a_matrix = (float *)malloc(current_size * current_size * sizeof(float));
	float * b_matrix = (float *)malloc(current_size * current_size * sizeof(float));
	float * c_matrix = generate_matrix(current_size, TRUE);

	if (process_id == MAIN_PROCESS_ID)
	{
		for (int j = 0; j < current_size; ++j)
		{
			memcpy(&a_matrix[j * current_size], &a[j * size], current_size * sizeof(float));
		}
		for (int j = 0; j < current_size; ++j)
		{
			memcpy(&b_matrix[j * current_size], &b[j * size], current_size * sizeof(float));
		}

		for (int i = 1; i < world_size; ++i)
		{
			int const row = i / dimension;
			int const col = i % dimension;
			for (int j = 0; j < current_size; ++j)
			{
				MPI_Send(&a[j * size + row * current_size * size + col * current_size], (int)current_size, MPI_FLOAT, (int)i, tag, MPI_COMM_WORLD);
			}
			for (int j = 0; j < current_size; ++j)
			{
				MPI_Send(&b[j * size + row * current_size * size + col * current_size], (int)current_size, MPI_FLOAT, (int)i, tag, MPI_COMM_WORLD);
			}
		}
	}

	if (process_id != MAIN_PROCESS_ID)
	{
		for (int j = 0; j < current_size; ++j)
		{
			MPI_Recv(&a_matrix[j * current_size], (int)current_size, MPI_FLOAT, MAIN_PROCESS_ID, tag, MPI_COMM_WORLD, &status);
		}
		for (int j = 0; j < current_size; ++j)
		{
			MPI_Recv(&b_matrix[j * current_size], (int)current_size, MPI_FLOAT, MAIN_PROCESS_ID, tag, MPI_COMM_WORLD, &status);
		}
	}

	memcpy(&native_a_matrix[0], &a_matrix[0], current_size * current_size * sizeof(float));

	int const iterations = dimension;
	int const a_row = current_row;
	int const a_col = current_col;
	int const b_row = current_row;
	int const b_col = current_col;

	for (int l = 0; l < iterations; ++l)
	{
		memcpy(&a_matrix[0], &native_a_matrix[0], current_size * current_size * sizeof(float));

		int const j = (a_row + l) % dimension;

		if (current_col == j)
		{
			for (int i = 0; i < dimension; ++i)
			{
				if (i != current_col)
				{
					MPI_Send(&a_matrix[0], (int)(current_size * current_size), MPI_FLOAT, (int)(a_row * dimension + i), tag, MPI_COMM_WORLD);
				}
			}
		}
		if (current_col != j)
		{
			MPI_Recv(&a_matrix[0], (int)(current_size * current_size), MPI_FLOAT, (int)(a_row * dimension + j), tag, MPI_COMM_WORLD, &status);
		}

		consecutive_multiplication(a_matrix, b_matrix, c_matrix, current_size);

		if (l + 1 != iterations)
		{
			int const b_next_row = (b_row + 1) % dimension;
			int const b_previous_row = (b_row + dimension - 1) % dimension;
			int const send_to = (int)(b_next_row * dimension + current_col);
			int const recv_from = (int)(b_previous_row * dimension + current_col);
			int const current_rank = (int)process_id;

			if (current_rank < recv_from)
			{
				MPI_Recv(&additional_a_matrix[0], (int)(current_size * current_size), MPI_FLOAT, recv_from, tag, MPI_COMM_WORLD, &status);
				MPI_Send(&b_matrix[0], (int)(current_size * current_size), MPI_FLOAT, send_to, tag, MPI_COMM_WORLD);
				memcpy(&b_matrix[0], &additional_a_matrix[0], current_size * current_size * sizeof(float));
			}
			if (current_rank > recv_from)
			{
				MPI_Send(&b_matrix[0], (int)(current_size * current_size), MPI_FLOAT, send_to, tag, MPI_COMM_WORLD);
				MPI_Recv(&b_matrix[0], (int)(current_size * current_size), MPI_FLOAT, recv_from, tag, MPI_COMM_WORLD, &status);
			}
		}
	}


	if (process_id == MAIN_PROCESS_ID)
	{
		set_to_result(c_matrix, c, size, current_size, 0, 0);

		for (int i = 1; i < (int)world_size; ++i)
		{
			int const row = i / (int)dimension;
			int const col = i % (int)dimension;

			MPI_Recv(&c_matrix[0], (int)(current_size * current_size), MPI_FLOAT, i, tag, MPI_COMM_WORLD, &status);

			set_to_result(c_matrix, c, size, current_size, row, col);
		}
	}

	if (process_id != MAIN_PROCESS_ID)
	{
		MPI_Send(&c_matrix[0], (int)(current_size * current_size), MPI_FLOAT, 0, tag, MPI_COMM_WORLD);
	}

	free(a_matrix);
	free(b_matrix);
	free(c_matrix);
	free(native_a_matrix);
	free(additional_a_matrix);
}

double foxs_method_multiply_time(int size, int process_id, int world_size)
{
	return calculate_multiply_time(size, process_id, world_size, fox_method);
}