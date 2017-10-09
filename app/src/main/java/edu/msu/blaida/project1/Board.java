package edu.msu.blaida.project1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

    /**
     * Completed puzzle bitmap
     */
    private Bitmap boardEmpty;



    public Board(Context context) {
        // Create paint for filling the area the board will
        // be solved in.
        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setColor(0xffcccccc);

        // Load the solved puzzle image
        boardEmpty = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.board);

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

        float scaleFactor = (float)boardSize / (float)boardEmpty.getWidth();

        canvas.save();
        canvas.translate(marginX, marginY);
        canvas.scale(scaleFactor, scaleFactor);
        canvas.drawBitmap(boardEmpty, 0, 0, null);
        canvas.restore();
    }


    /**
     * Call this from the touch events.
     *
     * @param startX    First tap X in terms of grid position
     * @param startY    First tap Y in terms of grid position
     * @param endX      Second tap X in terms of grid position
     * @param endY      Second tap Y in terms of grid position
     * @return  boolean flag true if valid move, false if not
     */
    public boolean executeMove(int startX, int startY, int endX, int endY){
        Piece attacker = getPiece(startX, startY);
        Piece defender = getPiece(endX, endY);
        if(attacker == null){
            return false;
        }
        else if(attacker != null && defender == null){
            return movePiece(attacker, endX, endY);
        }
        else if(attacker != null && defender != null){
            return takePiece(attacker, defender);
        }
        return false;
    }

    public Piece getPiece(int x, int y){
        return this.board[y][x];
    }

    private boolean movePiece(Piece piece, int destinationX, int destinationY){
        if(piece.validMove(destinationX, destinationY)){
            swapPiece(piece, destinationX, destinationY);
            return true;
        }
        return false;
        //chessView.Invalidate() //Invalidate after moving. Do in touch event.
    }

    private boolean takePiece(Piece attacker, Piece defender){
        if(attacker.validTake(defender.getX(),defender.getY())){
            removePiece(defender.getX(), defender.getY());
            swapPiece(attacker,defender.getX(), defender.getY());
            return true;
        }
        return false;
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
