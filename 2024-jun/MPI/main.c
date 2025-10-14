#include <mpi.h>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#define MAGIC_NUMBER 6 

void generate_numbers(int rank, int *numbers) {
  srand(rank * time(NULL));
  for (int i = 0; i < MAGIC_NUMBER; i++)
  {
	numbers[i] = 16*rank+rand()%10;//48 + rand() % 10;
    	printf("%d ", numbers[i]);
  }
  printf("\n");
}

MPI_Datatype generate_type(int Comm_size, int Comm_rank) {
  MPI_Datatype newtype;
  int *block_lengths = calloc(MAGIC_NUMBER, sizeof(int));
  int *displacements = calloc(MAGIC_NUMBER, sizeof(int));
  
  int cnt = 0, acc=1, block_len;
  for (block_len = 1; acc <= MAGIC_NUMBER; block_len++, cnt++, acc+=block_len) {
    block_lengths[cnt] = block_len;
    displacements[cnt] += Comm_rank + Comm_size * cnt;
    displacements[cnt+1] = displacements[cnt];
  }

  if(acc-block_len!=MAGIC_NUMBER)
  {
    block_lengths[cnt] = MAGIC_NUMBER-(acc-block_len);
    displacements[cnt] +=  -Comm_rank *(block_len-block_lengths[cnt])+ Comm_rank + Comm_size * cnt;
    cnt++;
    printf("BLOCK LEN %d %d %d\n", block_lengths[0], block_lengths[1], block_lengths[2]);
    printf("DIS %d %d %d\n", displacements[0], displacements[1], displacements[2]);
  }

  block_lengths = realloc(block_lengths, cnt * sizeof(int));
  displacements = realloc(displacements, cnt * sizeof(int));
  
  if(Comm_size==1)
  	MPI_Type_contiguous(MAGIC_NUMBER, MPI_INT, &newtype);
  else
	MPI_Type_indexed(cnt, block_lengths, displacements, MPI_INT, &newtype);
  MPI_Type_commit(&newtype);
  return newtype;
}

void main(int argc, char **argv) {
  MPI_Init(&argc, &argv);

  int rank;
  MPI_Comm_rank(MPI_COMM_WORLD, &rank);
  int size;
  MPI_Comm_size(MPI_COMM_WORLD, &size);

  MPI_File file1;
  MPI_File_open(MPI_COMM_WORLD, "file1.dat", MPI_MODE_WRONLY | MPI_MODE_CREATE,
                MPI_INFO_NULL, &file1);
  int *numbers = malloc(MAGIC_NUMBER * sizeof(int));
  generate_numbers(rank, numbers);
  MPI_Offset disp = (size - rank - 1) * MAGIC_NUMBER * sizeof(int);
  MPI_Datatype etype = MPI_INT;
  MPI_File_set_view(file1, disp, etype, etype, "native", MPI_INFO_NULL);
  MPI_Status status;
  int count = MAGIC_NUMBER;
  MPI_File_write_all(file1, numbers, count, etype, &status);
  MPI_File_close(&file1);
  free(numbers);
  
  MPI_File_open(MPI_COMM_WORLD, "file1.dat", MPI_MODE_RDONLY, MPI_INFO_NULL,
                &file1);
  int *buff = malloc(MAGIC_NUMBER * sizeof(int));
  MPI_File_read_at(file1, rank * MAGIC_NUMBER * sizeof(int), buff, MAGIC_NUMBER,
                   etype, &status);
  printf("%d\n", buff[0]);
  MPI_File_close(&file1);

  MPI_Datatype newtype = generate_type(size, rank);
  MPI_File file2;
  MPI_File_open(MPI_COMM_WORLD, "file2.dat", MPI_MODE_WRONLY | MPI_MODE_CREATE,
                MPI_INFO_NULL, &file2);
  MPI_File_set_view(file2, 0,  MPI_INT, newtype, "native", MPI_INFO_NULL); 
  MPI_File_write_all(file2, buff, MAGIC_NUMBER, etype, &status);
  MPI_File_close(&file2);
  free(buff);
  
  MPI_Finalize();
}
