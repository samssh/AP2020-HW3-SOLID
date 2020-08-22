package model;

import java.util.List;

public class Board {
    private List<PuzzlePiece> puzzlePieces;
    private int missingPiece;

    public Board() {}

    public void setPuzzlePieces(List<PuzzlePiece> puzzlePieces) {
        this.puzzlePieces = puzzlePieces;
    }

    public void setMissingPiece(int missingPiece) {
        this.missingPiece = missingPiece;
    }

    public int getMissingPiece() {
        return missingPiece;
    }

    public List<PuzzlePiece> getPuzzlePieces() {
        return puzzlePieces;
    }
}
