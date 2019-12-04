.PHONY = compile run jar runjar zip all clean

#TODO: edit with path to your javac (java compiler)
JC =  javac 

#TODO: edit with path to your java (java runtime environment)
JRE =  java 

#TODO: edit with path to your module-path for javafx
MP = --module-path javafx-sdk-11.0.2/lib --add-modules javafx.controls,javafx.fxml -Dfile.encoding=UTF-8 

#TODO: edit with your classpath from Eclipse 
CP =  -classpath javafx-sdk-11.0.2/lib/javafx.media.jar:javafx-sdk-11.0.2/lib/javafx.web.jar:javafx-sdk-11.0.2/lib/javafx.graphics.jar:javafx-sdk-11.0.2/lib/javafx.swing.jar:javafx-sdk-11.0.2/lib/javafx.fxml.jar:javafx-sdk-11.0.2/lib/javafx.controls.jar:javafx-sdk-11.0.2/lib/src.zip:javafx-sdk-11.0.2/lib/javafx.base.jar:javafx-sdk-11.0.2/lib/javafx-swt.jar:. application.Main

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

