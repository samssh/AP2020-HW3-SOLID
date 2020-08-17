package model;

public class PuzzlePiece {
    private String imageName;
    private int pieceNumber;

    public PuzzlePiece(String img, int pieceIdentifier) {
        this.imageName = img;
        this.pieceNumber = pieceIdentifier;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public int getPieceNumber() {
        return pieceNumber;
    }

    public void setPieceNumber(int pieceNumber) {
        this.pieceNumber = pieceNumber;
    }

    public PuzzlePiece getClone() {
        return new PuzzlePiece(imageName, pieceNumber);
    }
}
