package controller;

import loaders.MyConfigLoader;
import model.Board;
import model.PuzzlePiece;
import view.*;

import javax.swing.*;
import java.util.*;
import java.util.List;

public class Controller {
    private boolean gameFinished = false;
    private final JFrame frame;
    private final MyPanel panel;
    private final Board board;
    private final List<Request> requests;
    private String gameState = "#";
    private final int fps;
    private static final Controller instance = new Controller();

    public static Controller getInstance() {
        return instance;
    }

    private Controller() {
        requests = new ArrayList<>();
        panel = new MyPanel();
        frame = new MyFrame();
        board = new Board();
        frame.addKeyListener(new MyKeyListener());
        frame.setContentPane(panel);
        MyConfigLoader configLoader = MyConfigLoader.getInstance();
        List<Integer> piecesRandomOrder = configLoader.getPropertyList(Integer.class, "piecesRandomOrder");
        List<PuzzlePiece> puzzlePieces = new ArrayList<>();
        board.setMissingPiece(configLoader.getProperty(Integer.class, "missingPiece"));
        for (int i = 0; i < 9; i++) {
            if (board.getMissingPiece() != i) {
                puzzlePieces.add(new PuzzlePiece(piecesRandomOrder.get(i) + 1 + "",
                        piecesRandomOrder.get(i)));
            } else {
                puzzlePieces.add(new PuzzlePiece("missing", piecesRandomOrder.get(i)));
            }
        }
        board.setPuzzlePieces(puzzlePieces);
        updateGraphic();
        if (!solvable(board.getMissingPiece(),
                configLoader.getPropertyList(Integer.class, "piecesRandomOrder"))) {
            JOptionPane.showMessageDialog(frame,
                    "this puzzle is not solvable, change your config and try again",
                    "Puzzle not solvable", JOptionPane.WARNING_MESSAGE);
            gameFinished = true;
        }
        fps = configLoader.getProperty(Integer.class, "controllerFPS");

    }

    public void run() {
        while (true) {
            sleep();
            executeAll();
            if (gameFinished) {
                break;
            }
            if (gameState.equals("finished")) {
                JOptionPane.showMessageDialog(frame, "You finished the game, congratulation"
                        , "game finished", JOptionPane.INFORMATION_MESSAGE);
                gameFinished = true;
            }
        }
    }

    private void sleep() {
        try {
            Thread.sleep(1000 / fps);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void executeAll() {
        synchronized (requests) {
            requests.forEach(Request::execute);
            requests.clear();
        }
    }

    void up() {
        int missingPieceIndex = board.getMissingPiece();
        if (missingPieceIndex <= 2) {
            return;
        }
        swapPieces(missingPieceIndex, missingPieceIndex - 3);
        board.setMissingPiece(missingPieceIndex - 3);
        updateGraphic();
    }

    void down() {
        int missingPieceIndex = board.getMissingPiece();
        if (missingPieceIndex >= 6) {
            return;
        }
        swapPieces(missingPieceIndex, missingPieceIndex + 3);
        board.setMissingPiece(missingPieceIndex + 3);
        updateGraphic();
    }

    void right() {
        int missingPieceIndex = board.getMissingPiece();
        if (missingPieceIndex % 3 == 2) {
            return;
        }
        swapPieces(missingPieceIndex, missingPieceIndex + 1);
        board.setMissingPiece(missingPieceIndex + 1);
        updateGraphic();
    }

    void left() {
        int missingPieceIndex = board.getMissingPiece();
        if (missingPieceIndex % 3 == 0) {
            return;
        }
        swapPieces(missingPieceIndex, missingPieceIndex - 1);
        board.setMissingPiece(missingPieceIndex - 1);
        updateGraphic();
    }

    private void swapPieces(int i, int j) {
        PuzzlePiece copy = this.board.getPuzzlePieces().get(i).getClone();
        board.getPuzzlePieces().get(i).setImageName(board.getPuzzlePieces().get(j).getImageName());
        board.getPuzzlePieces().get(i).setPieceNumber(board.getPuzzlePieces().get(j).getPieceNumber());
        board.getPuzzlePieces().get(j).setImageName(copy.getImageName());
        board.getPuzzlePieces().get(j).setPieceNumber(copy.getPieceNumber());
        if (gameFinished()) {
            gameState = "finished";
        }
    }

    private void updateGraphic() {
        List<Drawable> drawables = new ArrayList<>();
        List<PuzzlePiece> puzzlePieces = board.getPuzzlePieces();
        for (int i = 0; i < puzzlePieces.size(); i++) {
            PuzzlePiece p = puzzlePieces.get(i);
            int a = i % 3, b = i / 3;
            PuzzlePieceOverview o = new PuzzlePieceOverview(p.getImageName(), a, b);
            drawables.add(o);
        }
        panel.setDrawables(drawables);
    }

    private boolean gameFinished() {
        for (int i = 0; i < 9; i++) {
            int pieceIdentifier = board.getPuzzlePieces().get(i).getPieceNumber();
            if (pieceIdentifier == 8) {
                continue;
            }
            if (pieceIdentifier != i) {
                return false;
            }
        }
        return true;
    }

    private boolean solvable(int missingPiece, List<Integer> piecesOrder) {
        int inversionCount = 0;
        for (int i = 0; i < piecesOrder.size(); i++) {
            for (int j = i + 1; j < piecesOrder.size(); j++) {
                if (piecesOrder.get(i) > piecesOrder.get(j)) {
                    inversionCount += 1;
                }
            }
        }
        int parity = inversionCount % 2;
        int distanceOfMissingPiece = (2 - (missingPiece % 3)) + (2 - (missingPiece / 3));
        parity ^= (distanceOfMissingPiece % 2);
        return parity == 0;
    }

    public void addRequest(Request request) {
        if (request != null) {
            synchronized (requests) {
                requests.add(request);
            }
        }
    }
}
