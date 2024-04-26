#!/bin/bash

cd parser
ls | grep -xv "Parser.jj" | xargs rm
javacc Parser.jj
cd ../main
javac -cp ".." Console.java
cd ..
java main.Console
