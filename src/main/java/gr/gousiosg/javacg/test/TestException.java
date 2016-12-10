package gr.gousiosg.javacg.test;

/**
 * Created by caipeichao on 16/12/10.
 */
public class TestException {

    public static void main(String[] argv) {
        new TestException().run();
    }

    public void run() {
        System.out.println("Hello world");
        foo();
        bar();
    }

    public void foo() {
        System.out.println("Foo");
        throw new RuntimeException("Bad times");
    }

    public void bar() {
        System.out.println("Bar");
    }
}
