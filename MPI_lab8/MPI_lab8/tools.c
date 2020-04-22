#include "tools.h"

float *generate_matrix(int size, BOOL is_zero_fill)
{
	float * a = (float *)malloc(size * size * sizeof(float));
	for (int i = 0; i < size; ++i)
	{
		for (int j = 0; j < size; ++j)
		{
			a[i * size + j] = 0.f;
			if (is_zero_fill != TRUE)
			{
				a[i * size + j] = ((float)(rand() % 1000) - 500.f) * 0.01f;
			}
		}
	}

	return a;
}

/*void generate_matrices(float *a, float *b, float *c, int size)
{
	a = generate_matrix(size, FALSE);
	b = generate_matrix(size, FALSE);
	c = generate_matrix(size, TRUE);
}*/

void free_matrices(float *a, float *b, float *c)
{
	free(a);
	free(b);
	free(c);
}

double calculate_multiply_time(
	int size,
	int process_id,
	int world_size,
	void(*multiplier)(float const *, float const *, float *, int, int, int)
)
{
	LARGE_INTEGER frequency;
	LARGE_INTEGER start_time;
	LARGE_INTEGER finish_time;

	QueryPerformanceFrequency(&frequency);

	float *a = NULL, *b = NULL, *c = NULL;

	if (process_id == MAIN_PROCESS_ID)
	{
		a = generate_matrix(size, FALSE);
		b = generate_matrix(size, FALSE);
		c = generate_matrix(size, TRUE);
	}

	QueryPerformanceCounter(&start_time);

	multiplier(a, b, c, size, process_id, world_size);

	QueryPerformanceCounter(&finish_time);

	if (process_id == MAIN_PROCESS_ID)
	{
		free_matrices(a, b, c);
	}

	return (double)(finish_time.QuadPart - start_time.QuadPart) / (double)frequency.QuadPart;
}

void set_to_result(
	float const * matrix,
	float * c,
	int size,
	int current_size,
	int row,
	int col
)
{
	for (int i = 0; i < current_size; ++i)
	{
		for (int j = 0; j < current_size; ++j)
		{
			c[(i + row * current_size) * size + (j + col * current_size)] = matrix[i * current_size + j];
		}
	}
}

void destroy_matrix(float * a)
{
	free(a);
}

