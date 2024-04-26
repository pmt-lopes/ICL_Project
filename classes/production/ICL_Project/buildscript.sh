#!/bin/bash

cd parser
ls | grep -xv "Parser.jj" | xargs rm
javacc Parser.jj
cd ..
cd Console
java main.Console
