# ICL_Project

Pedro Lopes 57514
Francisco Costa 55822

How to run programs:

  run shell script:
    src/buildscript.sh -> will compile and run the interpreter
    
    then, one can type their expressions to test, e.g. : 
        let x=5 in while !x<15 do x:=!x+2 end end
        let x=4 in let y=3 in while !x+!y<100 do x:=!x*!y end end end


  run executables:

    console (add details on how to run it)

    CompTest1 and ComptTest2 are test the compiler with predefined expressions, variable values can be changed in source files (package main)
    CompTest1/CompTest2 =>  java CompTest1 / java CompTest2 
    
Accepted Syntax + Deviations

  Parser/Interpreter

    Everything in the given grammar was implemented except for: functions, explicit type definitions.
    This interpreter is defined expecting explicit dereference using the "!" character before a variable name. If there is no dereference, then I will presume one is mentioning the reference and not the value of the variables.

  Compiler

    Everything except References, whiles and functions

  Deviation between Parser and Compiler

    Compiler expects to receive identifiers in the list of bindings of a ASTLet and was written to accept references as well as identifiers in the bindings (although references are not working)
    
    Let-bind operations using the interpreter allow for the use of references or identifiers as well, if they are well defined in the scope. The "new" function is used to define a reference which points to a null value, which is different from the compiler. 

## Extra Details

  

  While not implemented, the functionalities of the compiler for the syntax of references and whiles were written
