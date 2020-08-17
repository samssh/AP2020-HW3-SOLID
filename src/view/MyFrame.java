package view;

import loaders.MyConfigLoader;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {
    private final int fps;

    public MyFrame() {
        super();
        this.setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        fps = MyConfigLoader.getInstance().getProperty(Integer.class, "graphicFPS");
        new Thread(this::run).start();
    }

    private void run() {
        while (true) {
            update();
            sleep();
        }
    }

    private void update() {
        this.repaint();
        this.revalidate();
    }

    private void sleep() {
        try {
            Thread.sleep(1000 / fps);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void setContentPane(Container contentPane) {
        super.setContentPane(contentPane);
        this.repaint();
        this.revalidate();
        this.pack();
    }
}
