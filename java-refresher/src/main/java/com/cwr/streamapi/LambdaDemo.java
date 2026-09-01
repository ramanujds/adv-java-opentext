package com.cwr.streamapi;

interface Printable {
    void print(String str);
}

interface Greetable {
    String greet(String name, String greeting);
}


public class LambdaDemo {

    public static void main(String[] args) {
//        Printable printer = str -> {
//            System.out.println("Hello, "+str);
//        };
//        printer.print("Gaurav");

        Greetable greeter = (name, greeting) -> {
            return greeting + " " + name+"!";
        };
        String output = greeter.greet("Gaurav", "Welcome");
        System.out.println(output);

    }

}
