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

public class LetTest {

    @Test
    public void testWriteToFile() throws IOException {
        
        //Let x = 1, y = 2, z = 3 in x+y+z
        ASTId id1 = new ASTId("x", new ASTInt(1));
        id1.setType(IntType.getInstance());
        ASTId id2 = new ASTId("y", new ASTInt(2));
        id2.setType(IntType.getInstance());
        ASTId id3 = new ASTId("z", new ASTInt(3));
        id3.setType(IntType.getInstance());

        // Create a list of ASTId bindings
        List<Exp> bindings = new ArrayList<>();
        bindings.add(id1);
        bindings.add(id2);
        bindings.add(id3);

        // Create an ASTLet expression
        ASTLet letExpression = new ASTLet();
        letExpression.setBindings(bindings);
        
        // Set body
        ASTId body1 = new ASTId("x", null);
        body1.setType(IntType.getInstance());
        ASTId body2 = new ASTId("y", null);
        body2.setType(IntType.getInstance());
        ASTId body3 = new ASTId("z", null);
        body3.setType(IntType.getInstance());
        ASTAdd body4 = new ASTAdd(body2, body3);
        ASTAdd body = new ASTAdd(body1, body4);
        
        letExpression.setBody(body);

        // Define the output file name
        String filename = "Demo.j";

        // Generate the code and write to file
        CodeGen.writeToFile(letExpression, null, filename);

        // Compile using Jasmin
        Process process = new ProcessBuilder("java", "-jar", "jasmin.jar", "*.j").start();
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify class file is created
        File classFile = new File("Demo.class");
        assertTrue(classFile.exists());

    }
}
