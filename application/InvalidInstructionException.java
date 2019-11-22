package application;

@SuppressWarnings("serial")
public class InvalidInstructionException extends RuntimeException{
  public InvalidInstructionException() {
    this("Input file contains invalid insturction\r"
        + "delete the invalid instruction or replace with a correct instruction");
  }
  public InvalidInstructionException(String errorMessage) {
    super(errorMessage);
  }
}