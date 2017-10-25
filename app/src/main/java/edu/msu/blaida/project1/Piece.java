package edu.msu.blaida.project1;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Parcelable;

import java.io.Serializable;

import java.io.Serializable;

/**
 * Abstract class describing a piece
 */
public abstract class Piece implements Serializable {

    // Adam, delete these are from your old system, delete if not used
    private int x;
    private int y;

    // Actual coordinates on the canvas for pieces
    //private float X;
    //private float Y;

    private int player;

    private int bitmap;

    private boolean firstMove = true;


    public Piece(Context context, float x, float y, int player) {
        //this.X = x;
        //this.Y = y;
        this.player = player;
    }

    abstract Point[] getMovePath(Point start, Point end);

    abstract Point[] getTakePath(Point start, Point end);

    public boolean isPromotable() { return false;}

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public boolean isFirstMove() {
        return firstMove;
    }

    public void setFirstMove(boolean firstMove) {
        this.firstMove = firstMove;
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

    public int getBitmap() {
        return bitmap;
    }

    public void setBitmap(int bitmap) {
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
    public void draw(Context context, Canvas canvas, int marginX, int marginY,
                     int boardSize, float scaleFactor,
                     float posX, float posY) {
        canvas.save();
        Bitmap b = BitmapFactory.decodeResource(context.getResources(), getBitmap());

        // Convert x,y to pixels and add the margin, then draw
        canvas.translate(marginX + ((posX*2+1)/16) * boardSize, marginY + ((posY*2+1)/16) * boardSize);

        // Scale it to the right size
        canvas.scale(scaleFactor/5, scaleFactor/5);

        // This magic code makes the center of the piece at 0, 0
        canvas.translate(-b.getWidth() / 2, -b.getHeight() / 2);

        // Draw the bitmap
        canvas.drawBitmap(b, 0, 0, null);
        canvas.restore();
    }


    /**
     * Test to see if we have touched a puzzle piece
     * @param testX X location as a normalized coordinate (0 to 1)
     * @param testY Y location as a normalized coordinate (0 to 1)
     * @param boardSize the size of the puzzle in pixels
     * @param scaleFactor the amount to scale a piece by
     * @return true if we hit the piece
     */
    /*
    public boolean hit(float testX, float testY,
                       int boardSize, float scaleFactor) {

        // Make relative to the location and size to the piece size
        int pX = (int)((testX - X) * boardSize / scaleFactor) +
                bitmap.getWidth() / 2;
        int pY = (int)((testY - Y) * boardSize / scaleFactor) +
                bitmap.getHeight() / 2;

        if(pX < 0 || pX >= bitmap.getWidth() ||
                pY < 0 || pY >= bitmap.getHeight()) {
            return false;
        }

        // We are within the rectangle of the piece.
        // Are we touching actual picture?
        return true;
    }
    */

}
