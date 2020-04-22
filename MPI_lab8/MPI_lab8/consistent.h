#ifndef CONSISTENT
#define CONSISTENT

#include "tools.h"

void consistent_multiplication(float const *a, float const *b, float *c, int size);

double consistent_multiplication_worktime(int size, int process_id, int world_size);


#endif 
