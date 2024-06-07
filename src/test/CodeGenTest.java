package test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import ast.ASTAdd;
import ast.ASTInt;
import compiler.CodeGen;

public class CodeGenTest {

    @Test
    public void testWriteToFile() throws IOException {
        // Constructing an expression (e.g., 10 + 32)
        ASTInt left = new ASTInt(10);
        ASTInt right = new ASTInt(32);
        ASTAdd expression = new ASTAdd(left, right);

        // Define the output file name
        String filename = "Demo.j";

        // Generate the code and write to file
        CodeGen.writeToFile(expression, null, filename);

        // Compile using Jasmin
        Process process = new ProcessBuilder("java", "-jar", "jasmin.jar", filename).start();
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
