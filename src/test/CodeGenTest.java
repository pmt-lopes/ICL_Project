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

public class CodeGenTest {

    @Test
    public void testWriteToFile() throws IOException {
    	
        // Add
        ASTInt left = new ASTInt(10);
        ASTInt right = new ASTInt(32);
        ASTAdd Addexpression = new ASTAdd(left, right);

        //Sub
        ASTInt leftInt2 = new ASTInt(5);
        ASTInt rightInt2 = new ASTInt(3);
        ASTSub subExpression = new ASTSub(leftInt2, rightInt2);

        //Mult
        ASTInt leftInt3 = new ASTInt(8);
        ASTInt rightInt3 = new ASTInt(4);
        ASTMult multExpression = new ASTMult(leftInt3, rightInt3);

        //Div
        ASTInt leftInt4 = new ASTInt(16);
        ASTInt rightInt4 = new ASTInt(4);
        ASTDiv divExpression = new ASTDiv(leftInt4, rightInt4);

        //And
        ASTBool leftBool = new ASTBool(true);
        ASTBool rightBool = new ASTBool(true);
        ASTAnd andExpression = new ASTAnd(leftBool, rightBool);

        //Or
        ASTBool leftBool2 = new ASTBool(false);
        ASTBool rightBool2 = new ASTBool(true);
        ASTOr orExpression = new ASTOr(leftBool2, rightBool2);

        //Eq
        ASTBool leftBool3 = new ASTBool(true);
        ASTBool rightBool3 = new ASTBool(true);
        ASTEq eqExpression = new ASTEq(leftBool3, rightBool3);

        //Gr
        ASTInt leftInt5 = new ASTInt(10);
        ASTInt rightInt5 = new ASTInt(50);
        ASTGr grExpression = new ASTGr(leftInt5, rightInt5);

        //GrE
        ASTInt leftInt6 = new ASTInt(9);
        ASTInt rightInt6 = new ASTInt(8);
        ASTGrE greExpression = new ASTGrE(leftInt6, rightInt6);

        //NEq
        ASTBool leftBool5 = new ASTBool(false);
        ASTBool rightBool5 = new ASTBool(false);
        ASTNEq neqExpression = new ASTNEq(leftBool5, rightBool5);

        //Neg
        ASTBool leftBool7 = new ASTBool(false);
        ASTNeg negExpression = new ASTNeg(leftBool7);
        
        // Define the output file name
        String filename = "Demo.j";

        // Generate the code and write to file
        CodeGen.writeToFile(negExpression, null, filename);

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
