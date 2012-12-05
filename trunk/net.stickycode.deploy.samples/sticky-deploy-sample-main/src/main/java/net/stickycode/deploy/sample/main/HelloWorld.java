package net.stickycode.deploy.sample.main;

public class HelloWorld {

  public static void main(String[] args) {
    if (args.length == 0)
      System.out.println("Hello World!");

    for (int i = 0; i < args.length; i++)
      try {
        for (int j = 0; j < Integer.parseInt(args[i]); j++) {
          System.out.println("Hello world (" + i + "," + j + ")!");
          Thread.sleep(i * 1000);
        }
      }
      catch (NumberFormatException e) {
        System.err.println("This is not a number " + args[i]);
      }
      catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
  }
}
