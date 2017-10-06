package edu.msu.blaida.project1;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;

/**
 * Abstract class describing a piece
 */
public abstract class Piece {

    private int x;

    private int y;

    private int player;

    private Bitmap bitmap;


    public Piece(Context context, int x, int y, int player) {
        this.x = x;
        this.y = y;
        this.player = player;
    }

    abstract boolean validMove(int x, int y);

    abstract boolean validTake(int x, int y);

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
}
