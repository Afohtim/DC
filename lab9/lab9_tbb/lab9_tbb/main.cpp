#include "tbb\tbb.h"

#include <stdio.h>
#include <memory>
#include <windows.h>


constexpr int size = 5000;
int tape_count = 4;

std::vector<std::vector<float> > a;
std::vector<std::vector<float> > b;
std::vector<std::vector<float> > c;


void init_matrix(std::vector<std::vector<float> > & a, bool is_set_to_zero = false)
{
	a = std::vector<std::vector<float> >(size, std::vector<float>(size, 0.0f));
	if (!is_set_to_zero)
	{
		for (size_t i = 0; i < size; ++i)
		{
			for (size_t j = 0; j < size; ++j)
			{
				a[i][j] = (static_cast<float>(rand() % 10000) / 10000.0f - 0.5f) * 200.0f;
			}
		}
	}

}

float multiplication_time(void(*multiplier)())
{
	LARGE_INTEGER frequency;
	LARGE_INTEGER t1;
	LARGE_INTEGER t2;

	QueryPerformanceFrequency(&frequency);

	QueryPerformanceCounter(&t1);

	multiplier();

	QueryPerformanceCounter(&t2);

	return static_cast<float>(t2.QuadPart - t1.QuadPart) / static_cast<float>(frequency.QuadPart);
}

void consecutive()
{
	for (size_t i = 0; i < size; ++i)
	{
		for (size_t j = 0; j < size; ++j)
		{
			float temp = 0.0f;
			for (size_t k = 0; k < size; ++k)
			{
				temp += a[i][k] * b[k][j];
			}
			c[i][j] = temp;
		}
	}
}

void tape_circuit()
{
	tbb::task_scheduler_init init(tape_count);

	tbb::parallel_for(tbb::blocked_range<size_t>(0, size),
		[&](tbb::blocked_range<size_t> r)
	{
		for (size_t i = r.begin(); i < r.end(); ++i)
		{
			for (size_t j = 0; j < size; ++j)
			{
				float temp = 0.f;
				for (size_t k = 0; k < size; ++k)
				{
					temp += a[i][k] * b[k][j];
				}
				c[i][j] = temp;
			}
		}
	});
}


int main(int argc, char * argv[])
{
	init_matrix(a);
	init_matrix(b);
	init_matrix(c, true);

	printf("%f\n", multiplication_time(consecutive));
	tape_count = 4;
	printf("%f\n", multiplication_time(tape_circuit));
	system("pause");
}