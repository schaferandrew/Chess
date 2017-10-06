package edu.msu.blaida.project1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;


public class Board {

    /**
     * Percentage of the display width or height that
     * is occupied by the puzzle.
     */
    final static float SCALE_IN_VIEW = 0.9f;

    /**
     * Paint for filling the area the puzzle is in
     */
    private Paint fillPaint;

    /**
     * Paint for outlining the area the puzzle is in
     */
    private Paint outlinePaint;

    private Piece[][] board = new Piece[8][8];



    public Board(Context context) {
        // Create paint for filling the area the board will
        // be solved in.
        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setColor(0xffcccccc);

    }
    public void draw(Canvas canvas) {
        int wid = canvas.getWidth();
        int hit = canvas.getHeight();

        // Determine the minimum of the two dimensions
        int minDim = wid < hit ? wid : hit;

        int boardSize = (int)(minDim * SCALE_IN_VIEW);

        // Compute the margins so we center the puzzle
        int marginX = (wid - boardSize
        ) / 2;
        int marginY = (hit - boardSize) / 2;

        //
        // Draw the outline of the puzzle
        //

        canvas.drawRect(marginX, marginY,
                marginX + boardSize, marginY + boardSize, fillPaint);
    }

    public void movePiece(Piece piece, int destinationX, int destinationY){
        if(piece.validMove(destinationX, destinationY)){
            swapPiece(piece, destinationX, destinationY);
        }

        //chessView.Invalidate() //Invalidate after moving. Do in touch event.
    }

    public void takePiece(Piece attacker, Piece defender){
        if(attacker.validTake(defender.getX(),defender.getY())){
            removePiece(defender.getX(), defender.getY());
            swapPiece(attacker,defender.getX(), defender.getY());
        }
        //chessView.Invalidate() //Invalidate after taking. Do in touch event.

    }

    /**
     * Helper function for takePiece. Does not perform error checking again, so do not call directly.
     *
     * @param x X coordinate of the piece to remove
     * @param y Y coordinate of the piece to remove
     */
    private void removePiece(int x, int y){
        this.board[y][x] = null;
    }

    /**
     * Helper function for movePiece. Swaps the null space with the piece.
     *
     * @param piece
     * @param newX
     * @param newY
     */
    private void swapPiece(Piece piece, int newX, int newY){
        this.board[newX][newY] = piece;
        removePiece(piece.getX(), piece.getY());
        piece.setX(newX);
        piece.setY(newY);
    }
}
