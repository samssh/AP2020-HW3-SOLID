import controller.Controller;
import loaders.MyConfigLoader;


public class Main {
    public static void main(String[] args) {
        MyConfigLoader.setArgs(args);
        Controller.getInstance().run();
    }
}
