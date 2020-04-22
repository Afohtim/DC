#ifndef TAPE
#define TAPE

#include "tools.h"


void tape_circuit_multiplication(float const *a, float const *b, float *c, int size, int process_id, int tape_count);

double tape_circuit_multiplication_time(int size, int process_id, int tape_count);


#endif 
