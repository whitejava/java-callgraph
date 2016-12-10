package gr.gousiosg.javacg.test;

/**
 * Created by caipeichao on 16/12/10.
 */
public class TestOneThread {

    public static void main(String[] argv) {
        new TestOneThread().run();
    }

    public void run() {
        System.out.println("Hello world");
        foo();
        bar();
    }

    public void foo() {
        System.out.println("Foo");
    }

    public void bar() {
        System.out.println("Bar");
    }
}
