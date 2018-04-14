Language Used: Java 
Version: java version "1.8.0_161"
IDE compiled: Eclipse Oxygen Release (4.7.0)

STEPS TO CHECK THE CORRECTNESS OF THE PROGRAM

1. Setup the file path before executing. Program would not run correctly if all the file paths are not set up.
	For Simplicity filepath needs to be set up in only one function getFileData.  
	In my case the file path is 'C:\\Users\\Niraj\\eclipse-workspace\\pow_m12511318\\data\\'. Replace this with the desired location on 
	your computing system.
2. Once the file locations have been set-up. You are ready to run your program. 
3. Before executing if there are any data changes that are required to made to the above file please do it. Names of the files and functions are mentioned below.

Program has comments related to the details of the functions. On running the program, please provide the difficulty as input and generate the solution for given input text.
Changes to input text can be done in file input.txt.

Further to check the validation is correct or not, put a breakpoint before running the validation function. Change solution in solution.txt. Should give output as Solution is incorrect.
As you have made changes to the solution, the desired output is what was expected.

=========================== FILE DESCRIPTION ========================================================================
input.txt         :- input a text message that needs to be hashed.
target.txt        :- contains target value for given difficulty input. Contains data in binary.
solution.txt      :- contains the solution for H(m||s) <= t. Where m = message from input.txt, s = solution generated, t = target stored in target.txt
======================================================================================================================