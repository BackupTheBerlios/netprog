mkdir    ..\classes

idlj -pkgPrefix adder     	uebung02.a1_2  -td ..\classes\uebung02\a1_2 -fall -td ..\src ..\libs\a1_2\adder.idl
idlj -pkgPrefix repstring 	uebung02.a3    -td ..\classes\uebung02\a3   -fall -td ..\src ..\libs\a3\repstring.idl
idlj -pkgPrefix adressbuch 	uebung02.a4    -td ..\classes\uebung02\a4   -fall -td ..\src ..\libs\a4\adressbuch.idl

javac -d ..\classes -sourcepath ..\src ..\src\uebung02\*.java
javac -d ..\classes -sourcepath ..\src ..\src\uebung02\a1_2\*.java
javac -d ..\classes -sourcepath ..\src ..\src\uebung02\a3\*.java
javac -d ..\classes -sourcepath ..\src ..\src\uebung02\a4\*.java