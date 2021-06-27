all:			Processo.class ReceiveMessage.class \
			Main.class

Processo.class:	Processo.java
			@javac Processo.java

ReceiveMessage.class: ReceiveMessage.java
			@javac ReceiveMessage.java

Main.class:	Main.java
			@javac Main.java


run: all
			@java Main

clean:
			@rm -f *.class *~