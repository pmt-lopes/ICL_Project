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
        
        //Let 
        ASTId id1 = new ASTId("x", new ASTInt(2));
        id1.setType(IntType.getInstance());
        ASTId id2 = new ASTId("y", new ASTInt(3));
        id2.setType(IntType.getInstance());

        // Create a list of ASTId bindings
        List<Exp> bindings = new ArrayList<>();
        bindings.add(id1);
        bindings.add(id2);

        // Create an ASTLet expression
        ASTLet letExpression = new ASTLet();
        letExpression.setBindings(bindings);
        
        ASTLet second = new ASTLet();
        
        ASTId id3 = new ASTId("k", new ASTAdd(id1, id2));
        id3.setType(IntType.getInstance());
        
        List<Exp> bindings2 = new ArrayList<>();
        bindings2.add(id3);
        
        second.setBindings(bindings2);
        
        ASTAdd secondBody = new ASTAdd(new ASTAdd(id1, id2), id3);
        
        second.setBody(secondBody);
        
        letExpression.setBody(second);

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
        File classFile = new File("mypackage/Demo.class");
        assertTrue(classFile.exists());

    }
}
