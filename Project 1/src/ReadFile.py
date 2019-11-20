import sys
import os

def readFile():

    x =[]
    y =[]

    filepath = '/Users/thomasparayil/Desktop/Data Structures Project/Project 1/outputFiles/output_probe_new.txt'
    with open(filepath) as fp:
        line = fp.readline()
        while line:
            line = fp.readline()
            #print line
            if len(line) != 0:
                elem = line.split(',')
            #print elem[0] + "  " + elem[1]
                if(len(elem) != 0 and elem and elem != ''):
                    #print elem
                    x.append(float(elem[0]))
                    stripper = elem[1].strip('\n')
                    y.append(float(stripper))
        fp.close()

    print x
    print "   "
    print y

if __name__ == "__main__":
    print "Start"
    readFile()
    print "Done"