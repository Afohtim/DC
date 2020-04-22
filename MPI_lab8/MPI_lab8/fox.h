#ifndef FOX
#define FOX

#include "tools.h"
#include "consistent.h"

void fox_method(float const * a, float const * b, float * c, int size, int process_id, int world_size);

double foxs_method_multiply_time(int size, int process_id, int world_size);


#endif 
