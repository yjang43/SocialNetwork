.PHONY = compile run jar runjar zip all clean

#TODO: edit with path to your javac (java compiler)
JC =  /usr/lib/jvm/java-11-openjdk-amd64/bin/javac 

#TODO: edit with path to your java (java runtime environment)
#JRE =  /usr/lib/jvm/adoptopenjdk-11-jdk-hotspot/bin/java 
JRE =  /usr/lib/jvm/java-11-openjdk-amd64/bin/java 

#TODO: edit with path to your module-path for javafx
MP = --module-path /home/charasi/eclipse-project/SocialNetwork/javafx-sdk-11.0.2/lib --add-modules javafx.controls,javafx.fxml -Dfile.encoding=UTF-8 

#TODO: edit with your classpath from Eclipse 
CP = -classpath ./:application/*.java:junit-platform-console-standalone-1.5.2.jar:/usr/lib/jvm/java-11-openjdk-amd64/bin/java --module-path /home/charasi/eclipse-project/SocialNetwork/javafx-sdk-11.0.2/lib --add-modules javafx.controls,javafx.fxml -Dfile.encoding=UTF-8 -classpath /home/charasi/eclipse-project/SocialNetwork:/home/charasi/eclipse/javafx-sdk-11.0.2/lib/javafx.media.jar:/home/charasi/eclipse/javafx-sdk-11.0.2/lib/javafx.web.jar:/home/charasi/eclipse/javafx-sdk-11.0.2/lib/javafx.graphics.jar:/home/charasi/eclipse/javafx-sdk-11.0.2/lib/javafx.swing.jar:/home/charasi/eclipse/javafx-sdk-11.0.2/lib/javafx.fxml.jar:/home/charasi/eclipse/javafx-sdk-11.0.2/lib/javafx.controls.jar:/home/charasi/eclipse/javafx-sdk-11.0.2/lib/src.zip:/home/charasi/eclipse/javafx-sdk-11.0.2/lib/javafx.base.jar:/home/charasi/eclipse/javafx-sdk-11.0.2/lib/javafx-swt.jar application.Main

SRC = application/*.java   

APP = application.Main 

ARGS = deb mark sapan  # place your command line args here

compile:
	$(JC) $(CP) $(SRC) 

run:
	$(JRE) $(MP) $(CP) $(APP) $(ARGS)

jar:
	jar -cvmf manifest.txt executable.jar .

runjar:
	$(JRE) $(MP) -jar executable.jar $(ARGS)

# Create zip file for submitting to handin
zip: 
	zip -r ateam.zip .

#Eclipse's "Show Command Line"
all:

# Remove generated files
clean:
	rm -f application/*.class
	rm -f executable.jar

