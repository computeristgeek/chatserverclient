all: echo3.class chat.class

echo3.class: echo3.java
	javac echo3.java --release 8

chat.class: chat.java Client.java
	javac chat.java --release 8

clean:
	rm -rf *.class

run_server: echo3.class
	java echo3

run_client: chat.class
	java chat
