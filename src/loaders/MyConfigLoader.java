package loaders;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MyConfigLoader extends Properties {
    private static String[] args;
    private static final String DEFAULT_ADDRESS = "./src/MainConfig.properties";
    private static MyConfigLoader instance;

    public static MyConfigLoader getInstance() {
        if (instance == null) {
            String address;
            if (args.length > 0) address = args[0];
            else address = DEFAULT_ADDRESS;
            instance = new MyConfigLoader(address);
        }
        return instance;
    }

    public static void setArgs(String[] args) {
        MyConfigLoader.args = args;
    }

    private MyConfigLoader(String address) {
        super();
        try {
            Reader fileReader = new FileReader(address);
            this.load(fileReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <E> E getProperty(Class<E> c, String propertyName) {
        return getObject(c, getProperty(propertyName));
    }

    public <E> List<E> getPropertyList(Class<E> c, String propertyName) {
        List<E> list = new ArrayList<>();
        String[] values = getProperty(propertyName).split(",");
        for (String value : values) {
            list.add(getObject(c, value));
        }
        return list;
    }

    private <E> E getObject(Class<E> c, String value) {
        E e = null;
        try {
            Constructor<E> constructor = c.getConstructor(String.class);
            e = constructor.newInstance(value);
        } catch (ReflectiveOperationException exception) {
            exception.printStackTrace();
        }
        return e;
    }
}
