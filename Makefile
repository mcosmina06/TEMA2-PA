build:
	javac -g P1.java
	javac -g P2.java
	javac -g P3.java	
run-p1:
	java -Xmx128m -Xss128m P1
run-p2:
	java P2
run-p3:
	java P3
run-p4:

clean: 
	rm -rf *.class