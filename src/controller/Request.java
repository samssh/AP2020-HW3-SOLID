package controller;

public enum Request {
    UP {
        @Override
        void execute() {
            Controller.getInstance().up();
        }
    }, DOWN {
        @Override
        void execute() {
            Controller.getInstance().down();
        }
    }, RIGHT {
        @Override
        void execute() {
            Controller.getInstance().right();
        }
    }, LEFT {
        @Override
        void execute() {
            Controller.getInstance().left();
        }
    };

    abstract void execute();
}