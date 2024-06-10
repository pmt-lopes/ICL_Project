package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ast.*;
import types.*;
import compiler.CodeGen;

public class CompTest2 {

    public static void main(String[] args) throws IOException {
    	
			/*
			 * let x = 4, y = 6 in
			    if (x + y < 10) then
			        print(x + y)
			    else			    
				    if (x <= y) then
				        println(x);
				        println(y)
				    else
				        print(false)
			
			 * 
			 * 
			 */
    	
    	ASTId idX = new ASTId("x", new ASTInt(4));  // VALUE OF X   <--- CHANGE THIS
        idX.setType(IntType.getInstance());
        ASTId idY = new ASTId("y", new ASTInt(5)); // VALUE OF Y   <--- CHANGE THIS
        idY.setType(IntType.getInstance());

        List<Exp> bindings1 = new ArrayList<>();
        bindings1.add(idX);
        bindings1.add(idY);

        // Define the first if expression
        ASTAdd addXY = new ASTAdd(idX, idY);
        ASTGr ltXY10 = new ASTGr(new ASTInt(10), addXY);
        ASTPrint printAddXY = new ASTPrint(addXY);

        // Define the second if expression
        ASTGrE leqXY = new ASTGrE(idY, idX);
        ASTPrintln printX = new ASTPrintln(idX);
        ASTPrintln printY = new ASTPrintln(idY);
        ASTSeq s = new ASTSeq(printX, printY);
        ASTPrint printFalse = new ASTPrint(new ASTBool(false));
        ASTIf ifLeqXY = new ASTIf(leqXY, s, printFalse);
        

        ASTIf ifXY = new ASTIf(ltXY10, printAddXY, ifLeqXY);

        // Define the first let expression
        ASTLet letXY = new ASTLet();
        letXY.setBindings(bindings1);
        letXY.setBody(ifXY);

        // Define the output file name
        String filename = "Demo.j";

        // Generate the code and write to file
        CodeGen.writeToFile(letXY, null, filename);

        // Compile using Jasmin
        Process process = new ProcessBuilder("java", "-jar", "jasmin.jar", "*.j").start();
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        Process process2 = new ProcessBuilder("java", "mypackage/Demo").start();
        try {
            process2.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
