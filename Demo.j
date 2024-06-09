.class public mypackage/Demo
.super java/lang/Object
.method public <init>()V
   aload_0
   invokenonvirtual java/lang/Object/<init>()V
   return
.end method
.method public static main([Ljava/lang/String;)V
 .limit locals 10
 .limit stack 256
 ; setup local variables:
 ;    1 - the PrintStream object held in java.lang.out
getstatic java/lang/System/out Ljava/io/PrintStream;
new mypackage/frame_0
dup
invokespecial mypackage/frame_0/<init>()V
dup
aload 0
putfield mypackage/frame_0/SL Ljava/lang/Object;
astore 0
aload 0
sipush 2
putfield mypackage/frame_0/loc_0 I
aload 0
sipush 3
putfield mypackage/frame_0/loc_1 I
new mypackage/frame_1
dup
invokespecial mypackage/frame_1/<init>()V
dup
aload 0
putfield mypackage/frame_1/SL Lmypackage/frame_0;
astore 0
aload 0
aload 0
getfield mypackage/frame_1/SL Lmypackage/frame_0;
getfield mypackage/frame_0/loc_0 I
aload 0
getfield mypackage/frame_1/SL Lmypackage/frame_0;
getfield mypackage/frame_0/loc_1 I
iadd
putfield mypackage/frame_1/loc_0 I
aload 0
getfield mypackage/frame_1/SL Lmypackage/frame_0;
getfield mypackage/frame_0/loc_0 I
aload 0
getfield mypackage/frame_1/SL Lmypackage/frame_0;
getfield mypackage/frame_0/loc_1 I
iadd
aload 0
getfield mypackage/frame_1/loc_0 I
iadd
aload 0
checkcast mypackage/frame_1
getfield mypackage/frame_1/SL Lmypackage/frame_0;
astore 0
aload 0
checkcast mypackage/frame_0
getfield mypackage/frame_0/SL Ljava/lang/Object;
astore 0
invokestatic java/lang/String/valueOf(I)Ljava/lang/String;
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
return
.end method
