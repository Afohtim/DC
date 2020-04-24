using System;
using System.Diagnostics;
using System.Threading.Tasks;

namespace lab9
{
    public class MatrixMultiplication
    {
        private const int size = 100;
        private const int tapeCount = 4;

        private float[] a = new float[size * size];
        private float[] b = new float[size * size];
        private float[] c = new float[size * size];

        private Random random = new Random();

        private void matrixFill(float[] mtx, int size, bool fillZeroes)
        {
            for (int i = 0; i < size; ++i)
            {
                for (int j = 0; j < size; ++j)
                {
                    if(fillZeroes)
                    {
                        mtx[i * size + j] = 0;

                    }
                    else
                    {
                        mtx[i * size + j] = (((float)random.NextDouble()) - 0.5f) * 200.0f;

                    }
                }
            }
        }

        public MatrixMultiplication()
        {
            matrixFill(a, size, false);
            matrixFill(b, size, false);
            matrixFill(c, size, true);
            
        }

        private void consecutiveMultiplication()
        {
            for (int i = 0; i < size; ++i)
            {
                for (int j = 0; j < size; ++j)
                {
                    float temp = 0.0f;
                    for (int k = 0; k < size; ++k)
                    {
                        temp += a[i * size + k] * b[k * size + j];
                    }
                    c[i * size + j] = temp;
                }
            }
        }

        public float consecutiveMultiplicationTime()
        {
            Stopwatch stopwatch = Stopwatch.StartNew();
            consecutiveMultiplication();
            stopwatch.Stop();

            return (float)stopwatch.ElapsedMilliseconds / 1000.0f;
        }

        private void tapeCircuitMultiplication()
        {
            Parallel.For(0, size, new ParallelOptions { MaxDegreeOfParallelism = tapeCount }, i =>
            {
                for (int j = 0; j < size; j++)
                {
                    float temp = 0;
                    for (int k = 0; k < size; k++)
                    {
                        temp += a[i * size + k] * b[k * size + j];
                    }
                    c[i * size + j] = temp;
                }
            });
        }


        public float tapeCircuitMultiplicationTime()
        {
            Stopwatch stopwatch = Stopwatch.StartNew();

            tapeCircuitMultiplication();

            stopwatch.Stop();

            return (float)stopwatch.ElapsedMilliseconds / 1000.0f;
        }
    }



    class Program
    {
        static void Main(string[] args)
        {
            MatrixMultiplication matrixMultiplication = new MatrixMultiplication();

            Console.WriteLine(matrixMultiplication.consecutiveMultiplicationTime());
            //Console.WriteLine(matrixMultiplication.tapeCircuitMultiplicationTime());
            Console.ReadLine();
        }
    }
}
