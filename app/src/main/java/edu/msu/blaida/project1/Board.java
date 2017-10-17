package edu.msu.blaida.project1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


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

    private Piece[][] board = new Piece[8][8];

    /**
     * Completed puzzle bitmap
     */
    private Bitmap boardEmpty;

    /**
     * The size of the puzzle in pixels
     */
    private int boardSize;

    /**
     * How much we scale the puzzle pieces
     */
    private float scaleFactor;

    /**
     * Left margin in pixels
     */
    private int marginX;

    /**
     * Top margin in pixels
     */
    private int marginY;

    /**
     * Selected piece
     */
    private Piece selectedPiece = null;

    public Board(Context context) {
        // Create paint for filling the area the board will
        // be solved in.
        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setColor(0xffcccccc);

        // Load the empty board image
        boardEmpty = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.board);

        // Load the chess pieces
        // Player 2 - Black
        board[0][0] = new Rook(context,.064f,.058f, 2);
        board[0][1] = new Knight(context, .190f, .058f, 2);
        board[0][2] = new Bishop(context, .313f, .065f, 2);
        board[0][3] = new Queen(context, .440f, .060f,2);
        board[0][4] = new King(context, 9/16f, 1/16f, 2); //better to do with fractions
        board[0][5] = new Bishop(context, 11/16f, 1/16f, 2);
        board[0][6] = new Knight(context, 13/16f, 1/16f, 2);
        board[0][7] = new Rook(context, 15/16f, 1/16f, 2);

        board[1][0] = new Pawn(context, 1/16f, 3/16f, 2);
        board[1][1] = new Pawn(context, 3/16f, 3/16f, 2);
        board[1][2] = new Pawn(context, 5/16f, 3/16f, 2);
        board[1][3] = new Pawn(context, 7/16f, 3/16f, 2);
        board[1][4] = new Pawn(context, 9/16f, 3/16f, 2);
        board[1][5] = new Pawn(context, 11/16f, 3/16f, 2);
        board[1][6] = new Pawn(context, 13/16f, 3/16f, 2);
        board[1][7] = new Pawn(context, 15/16f, 3/16f, 2);

        // Player 1 - White
        board[6][0] = new Pawn(context, 1/16f, 13/16f,1);
        board[6][1] = new Pawn(context, 3/16f, 13/16f,1);
        board[6][2] = new Pawn(context, 5/16f, 13/16f,1);
        board[6][3] = new Pawn(context, 7/16f, 13/16f,1);
        board[6][4] = new Pawn(context, 9/16f, 13/16f,1);
        board[6][5] = new Pawn(context, 11/16f, 13/16f,1);
        board[6][6] = new Pawn(context, 13/16f, 13/16f,1);
        board[6][7] = new Pawn(context, 15/16f, 13/16f,1);

        board[7][0] = new Rook(context, 1/16f, 15/16f,1);
        board[7][1] = new Knight(context, 3/16f, 15/16f, 1);
        board[7][2] = new Bishop(context, 5/16f, 15/16f, 1);
        board[7][3] = new Queen(context, 7/16f, 15/16f, 1);
        board[7][4] = new King(context, 9/16f, 15/16f, 1);
        board[7][5] = new Bishop(context, 11/16f, 15/16f,1);
        board[7][6] = new Knight(context, 13/16f, 15/16f, 1);
        board[7][7] = new Rook(context, 15/16f, 15/16f, 1);

    }
    public void draw(Canvas canvas) {
        int wid = canvas.getWidth();
        int hit = canvas.getHeight();

        // Determine the minimum of the two dimensions
        int minDim = wid < hit ? wid : hit;

        boardSize = (int)(minDim * SCALE_IN_VIEW);

        // Compute the margins so we center the puzzle
        marginX = (wid - boardSize
        ) / 2;
        marginY = (hit - boardSize) / 2;

        //
        // Draw the outline of the puzzle
        //

        canvas.drawRect(marginX, marginY,
                marginX + boardSize, marginY + boardSize, fillPaint);

        // Draw the chess board
        scaleFactor = (float)boardSize / (float)boardEmpty.getWidth();

        canvas.save();
        canvas.translate(marginX, marginY);
        canvas.scale(scaleFactor, scaleFactor);
        canvas.drawBitmap(boardEmpty, 0, 0, null);
        canvas.restore();

        // Draw the pieces

        for(Piece[] pieceRow : board) {
            for(Piece piece : pieceRow) {
                if (piece != null) {
                    piece.draw(canvas, marginX, marginY, boardSize, scaleFactor);
                }
            }
        }
    }

    /**
     * Handle a touch event from the view.
     * @param view The view that is the source of the touch
     * @param event The motion event describing the touch
     * @return true if the touch is handled.
     */
    public boolean onTouchEvent(View view, MotionEvent event) {
        //
        // Convert an x,y location to a relative location in the
        // puzzle.
        //

        float relX = (event.getX() - marginX) / boardSize;
        float relY = (event.getY() - marginY) / boardSize;

        return onTouched(relX, relY);
    }

    /**
     * Handle a touch message. This is when we get an initial touch
     * @param x x location for the touch, relative to the puzzle - 0 to 1 over the puzzle
     * @param y y location for the touch, relative to the puzzle - 0 to 1 over the puzzle
     * @return true if the touch is handled
     */
    private boolean onTouched(float x, float y) {

        // Check each piece to see if it has been hit
        // We do this in reverse order so we find the pieces in front

        for(Piece[] pieceRow : board) {
            for(Piece piece : pieceRow) {
                if (piece != null) {
                    if (piece.hit(x, y, boardSize, scaleFactor)) {
                        // We hit a piece
                        //Log.d("HitTest", "Hit Piece");
                        if (selectedPiece == null) {
                            selectedPiece = piece;
                            //might want to signify press with color change of selected piece
                        }
                        else {
                            // make sure selectedPiece is reset to null after move is executed
                            // do what Adam needs it to do
                        }
                        return true;
                    }
                }
            }
        }

        return false;
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
            return movePiece(attacker, startX, startY, endX, endY);
        }
        else if(attacker != null && defender != null){
            return takePiece(attacker, defender, startX, startY, endX, endY);
        }
        return false;
    }

    public Piece getPiece(int x, int y){
        return this.board[y][x];
    }

    private boolean movePiece(Piece piece, int startX, int startY, int destinationX, int destinationY){
        if(piece.validMove(startX, startY, destinationX, destinationY)){
            swapPiece(piece, destinationX, destinationY);
            return true;
        }
        return false;
        //chessView.Invalidate() //Invalidate after moving. Do in touch event.
    }

    private boolean takePiece(Piece attacker, Piece defender, int startX, int startY, int destinationX, int destinationY){
        if(attacker.validTake(startX, startY, destinationX,destinationY)){
            removePiece(destinationX, destinationY);
            swapPiece(attacker,destinationX, destinationY);
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
