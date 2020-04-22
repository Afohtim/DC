#include "consistent.h"

void consistent_multiplication(float const *a, float const *b, float *c, int size)
{
	for (int i = 0; i < size; ++i)
	{
		for (int j = 0; j < size; ++j)
		{
			float new_c_value = c[i*size + j];
			for (int k = 0; k < size; ++k)
			{
				new_c_value += a[i*size + k] * b[k*size + j];
			}
			c[i*size + j] = new_c_value;
		}
	}
}

double consistent_multiplication_worktime(int size, int process_id, int world_size)
{
	if (process_id != 0)
	{
		return HUGE_VAL;
	}

	LARGE_INTEGER frequency;
	LARGE_INTEGER start_time;
	LARGE_INTEGER finish_time;

	QueryPerformanceFrequency(&frequency);

	float *a = NULL, *b = NULL, *c = NULL;
	a = generate_matrix(size, FALSE);
	b = generate_matrix(size, FALSE);
	c = generate_matrix(size, TRUE);

	QueryPerformanceCounter(&start_time);

	consistent_multiplication(a, b, c, size);

	QueryPerformanceCounter(&finish_time);

	free_matrices(a, b, c);

	return (double)(finish_time.QuadPart - start_time.QuadPart) / (double)frequency.QuadPart;
}