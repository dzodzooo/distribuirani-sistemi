#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <mpi.h>
#define BUFF_MAX_SIZE 10*1024*1024

void main(int argc, char** argv){
	MPI_Init(&argc, &argv);
	int rank;
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
	int size;
	MPI_Comm_size(MPI_COMM_WORLD, &size);
	printf("Comm size = %d\n", size);
	MPI_File file;
	MPI_File_open(MPI_COMM_WORLD, "input.dat", MPI_MODE_RDONLY, MPI_INFO_NULL, &file);
	MPI_File output;
	MPI_File_open(MPI_COMM_WORLD, "output.dat", MPI_MODE_WRONLY | MPI_MODE_CREATE , MPI_INFO_NULL, &output);
	MPI_Offset filesize;
	MPI_File_get_size(file, &filesize);
	MPI_File_seek(file, rank*filesize/size, MPI_SEEK_SET);
	int count = filesize/size;
	char *buff = malloc(count+1);
	buff[count]='\0';
	MPI_Status status;
	MPI_File_read_all(file, buff, count, MPI_CHAR, &status);
//	printf("%s", buff);
//	printf("\n--------\n");
	
	int blocklength = 1, stride = size;
	MPI_Datatype oldtype = MPI_CHAR, newtype;
	MPI_Type_vector(count, blocklength, stride, oldtype, &newtype);
	MPI_Type_commit(&newtype);
	MPI_File_set_view(output, rank, MPI_CHAR, newtype, "native", MPI_INFO_NULL);   
	MPI_File_write_all(output, buff, count, MPI_CHAR, &status);
	free(buff);
	MPI_File_close(&file);
	MPI_File_close(&output);
	MPI_Finalize();
}
