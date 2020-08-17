import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class p2 {
    static class Client extends Thread {
        ObjectInputStream objectInputStream;
        ObjectOutputStream objectOutputStream;

        Client(Socket socket) throws IOException {
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        }


        @Override
        public void run() {

            try {
                ArrayList<Point> list = new ArrayList<>();
                for (int i = 1; i < 4; i++) {
                    for (Point point : list) point.x = -point.x;
                    list.add(new Point(i, i));
                    objectOutputStream.writeObject(list);
                    synchronized (System.out) {
                        System.out.println(list);
                    }
                }
                objectOutputStream.writeObject(new ArrayList<>());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class Server extends Thread {
        ObjectInputStream objectInputStream;
        ObjectOutputStream objectOutputStream;

        Server(Socket socket) throws IOException {
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
        }
        @SuppressWarnings("unchecked")
        @Override
        public void run() {
            while (true) {
                try {
                    ArrayList<Point> list = (ArrayList<Point>) objectInputStream.readObject();
                    ArrayList<Point> list1= (ArrayList<Point>) objectInputStream.readObject();
                    ArrayList<Point> list2 = (ArrayList<Point>) objectInputStream.readObject();
                    if (list.size() == 0) break;
                    synchronized (System.out) {
                        System.out.println("vd"+list);
                        System.out.println("vd"+list1);
                        System.out.println("vd"+list2);
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Thread t1=new Thread(() -> {
           try{
               System.out.println("here");
               Socket socket = new Socket("localhost",8090);
               new Client(socket).start();
           }catch (IOException e) {
               e.printStackTrace();
           }
        });
        ServerSocket serverSocket = new ServerSocket(8090);
        t1.start();
        Socket socket = serverSocket.accept();
        new Server(socket).start();
    }

}
