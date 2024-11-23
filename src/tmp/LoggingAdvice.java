package tmp;

public class LoggingAdvice implements Advice {
    @Override
    public void before() {
        System.out.println("before");
    }

    @Override
    public void after() {
        System.out.println("after");
    }
}
