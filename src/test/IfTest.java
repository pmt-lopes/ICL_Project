package test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import ast.*;
import types.*;
import compiler.CodeGen;

public class IfTest {

    @Test
    public void testWriteToFile() throws IOException {
    	
    	ASTEq eq = new ASTEq(new ASTBool(true), new ASTBool(true));
    	ASTAdd ad1 = new ASTAdd(new ASTInt(3),new ASTInt(3));
    	ASTAdd ad2 = new ASTAdd(new ASTInt(5),new ASTInt(5));
    	ASTPrintln p1 = new ASTPrintln(ad1);
    	ASTPrintln p2 = new ASTPrintln(ad2);
    	
    	ASTIf exp = new ASTIf(eq, p1, p2);

        // Define the output file name
        String filename = "Demo.j";

        // Generate the code and write to file
        CodeGen.writeToFile(exp, null, filename);

        // Compile using Jasmin
        Process process = new ProcessBuilder("java", "-jar", "jasmin.jar", "*.j").start();
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify class file is created
        File classFile = new File("mypackage/Demo.class");
        assertTrue(classFile.exists());

    }
}
