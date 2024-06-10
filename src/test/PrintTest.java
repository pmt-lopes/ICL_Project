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

public class PrintTest {

    @Test
    public void testWriteToFile() throws IOException {
        
    	ASTPrintln i = new ASTPrintln(new ASTInt(2));
    	ASTPrintln i2 = new ASTPrintln(new ASTInt(4));
    	ASTSeq exp = new ASTSeq(i, i2);

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
