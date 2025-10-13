#include <stdlib.h>
#include <stdio.h>
#include <mpi.h>

#define MAGIC_NUMBER 105

void generate_numbers(int rank, int* numbers){
	srand(rank);
	for(int i=0; i<MAGIC_NUMBER;i++)
		numbers[i] = 48+rand()%10;
}

MPI_Datatype generate_type(){
	int acc=1;
	int *block_lengths=calloc(MAGIC_NUMBER, sizeof(int));
	int *displacements = calloc(MAGIC_NUMBER, sizeof(MPI_Aint));
	int count=0;
	for(int i=1; acc<MAGIC_NUMBER; i++, count++){
		printf("%d\n",acc);
		block_lengths[count]=i;
		displacements[count]+=3*i;
		displacements[i]=displacements[count];
		acc+=i;
	}
	block_lengths = realloc(block_lengths, count*sizeof(int));
	displacements=realloc(displacements, count*sizeof(int));
	MPI_Datatype newtype;
	MPI_Type_indexed(count,  block_lengths, displacements, MPI_CHAR, &newtype); 
	MPI_Type_commit(&newtype);
	return newtype;
}

void main(int argc, char** argv){
	MPI_Init(&argc, &argv);
	int rank;
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
	int size;
	MPI_Comm_size(MPI_COMM_WORLD, &size);
	
	MPI_File file1;
	MPI_File_open(MPI_COMM_WORLD, "file1.dat", MPI_MODE_WRONLY|MPI_MODE_CREATE, MPI_INFO_NULL, &file1);
	int* numbers = malloc(MAGIC_NUMBER * sizeof(int));
	generate_numbers(rank, numbers);	
	MPI_Offset disp = (size-rank) * MAGIC_NUMBER;
	MPI_Datatype etype = MPI_INT, filetype=MPI_INT;
	MPI_File_set_view(file1, disp, etype, filetype, "native", MPI_INFO_NULL);
	MPI_Status status;
	int count = MAGIC_NUMBER;
	MPI_File_write_all(file1, numbers, count, etype, &status);
	MPI_File_close(&file1);

	MPI_File_open(MPI_COMM_WORLD, "file1.dat", MPI_MODE_RDONLY, MPI_INFO_NULL, &file1);
	int* buff = malloc(MAGIC_NUMBER * sizeof(int));
	MPI_File_read_at(file1, rank*MAGIC_NUMBER*sizeof(int), buff, MAGIC_NUMBER, etype, &status);
	printf("%d\n", buff[0]);
	MPI_File_close(&file1);

	MPI_Datatype newtype = generate_type();
	MPI_File file2;
	MPI_File_open(MPI_COMM_WORLD, "file2.dat", MPI_MODE_WRONLY|MPI_MODE_CREATE, MPI_INFO_NULL, &file2);
	MPI_File_write_all(file2, buff, 1, newtype, &status);
	MPI_File_close(&file2);

	free(numbers);
	free(buff);
	MPI_Finalize();
}

