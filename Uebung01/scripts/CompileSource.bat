mkdir    ..\classes
javac -d ..\classes -sourcepath ..\src      ..\src\uebung01\*.java

javac -d ..\classes -sourcepath ..\src      ..\src\uebung01\a1\*.java

javac -d ..\classes -sourcepath ..\src      ..\src\uebung01\a2\*.java
rmic  -d ..\classes -classpath  ..\classes  uebung01.a2.GruppenlisteServer

javac -d ..\classes -sourcepath ..\src      ..\src\uebung01\a3\*.java
rmic  -d ..\classes -classpath  ..\classes  uebung01.a3.ReplicatedStringImpl

javac -d ..\classes -sourcepath ..\src      ..\src\uebung01\a4\*.java
rmic  -d ..\classes -classpath  ..\classes  uebung01.a4.TeilVektorImpl
rmic  -d ..\classes -classpath  ..\classes  uebung01.a4.GesamtVektorImpl

javac -d ..\classes -sourcepath ..\src      ..\src\uebung01\a5\*.java