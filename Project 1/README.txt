## PROJECT DETAILS ##

DataSetGenerator.java -- used to create random values with a cap of 100000 and only positives.

LinearProbing.java - The main runnner file for LinearProbing. I fixed the capacity at 400.
HashFunction : 
private static int getHashCode(int value)
    {
        return ((constA * constB + value) % prime ) % capacity;
    }

Input : random1.txt
Output : output_probe_new.txt


CuckooHashing.java - The main runner file for Cuckoo Hashing. Here capacity is fixed at 200 for each row of the 2-D array.
HashFunction : 
    private static int hash(int table,int value)
    {
        switch(table)
        {
            case 0 : return  ((constA + value) % primeA) % capacity;
            case 1 : return  ((constB + constA * value) % primeB) % capacity;
        }
        return -1;
    }

Input : random1.txt
Output : output_new_final.txt


ReadFile.py - This reads the output files and parses it into lists.

plotter.py - The above lists are used in plotter.py to plot the loglog graph as well as the slope graph.

plotter.py was run on colab Jupyter (online google tool for graph plots)
ReadFile.py was run on terminal