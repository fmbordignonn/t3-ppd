all:		ReceiveMessage.class Processo.class \
			Main.class

ReceiveMessage.class: ReceiveMessage.java
			@javac ReceiveMessage.java

Processo.class:	Processo.java
			@javac Processo.java

Main.class:	Main.java
			@javac Main.java


run: all
			@java Main

clean:
			@rm -f *.class *~