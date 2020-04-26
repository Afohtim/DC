#ifndef CANNON
#define CANNON

#include "tools.h"

void cannon_method(float const * a, float const * b, float * c, int size, int process_id, int world_size);

double cannon_method_multiply_time(int size, int process_id, int world_size);

#endif 
