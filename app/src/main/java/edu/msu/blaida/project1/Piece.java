package edu.msu.blaida.project1;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Abstract class describing a piece
 */
public abstract class Piece {

    // Adam, delete these are from your old system, delete if not used
    private int x;
    private int y;

    // Actual coordinates on the canvas for pieces
    private float X;
    private float Y;

    private int player;

    private Bitmap bitmap;


    public Piece(Context context, float x, float y, int player) {
        this.X = x;
        this.Y = y;
        this.player = player;
    }

    abstract boolean validMove(int startX, int startY, int endX, int endY);

    abstract boolean validTake(int startX, int startY, int endX, int endY);

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    /**
     * Draw the puzzle piece
     * @param canvas Canvas we are drawing on
     * @param marginX Margin x value in pixels
     * @param marginY Margin y value in pixels
     * @param boardSize Size we draw the puzzle in pixels
     * @param scaleFactor Amount we scale the puzzle pieces when we draw them
     */
    public void draw(Canvas canvas, int marginX, int marginY,
                     int boardSize, float scaleFactor) {
        canvas.save();

        // Convert x,y to pixels and add the margin, then draw
        canvas.translate(marginX + X * boardSize, marginY + Y * boardSize);

        // Scale it to the right size
        canvas.scale(scaleFactor/5, scaleFactor/5);

        // This magic code makes the center of the piece at 0, 0
        canvas.translate(-getBitmap().getWidth() / 2, -getBitmap().getHeight() / 2);

        // Draw the bitmap
        canvas.drawBitmap(getBitmap(), 0, 0, null);
        canvas.restore();
    }

}
