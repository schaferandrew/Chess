package edu.msu.blaida.project1;

import android.content.Context;
import android.graphics.BitmapFactory;

/**
 * Created by power on 10/9/2017.
 */

public class Queen extends Piece {
    public Queen(Context context, float x, float y, int player) {
        super(context, x, y, player);
        if(player == 1){
            this.setBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.chess_qlt45));
        }else{
            this.setBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.chess_qdt45));
        }
    }

    @Override
    boolean validMove(int startX, int startY, int endX, int endY) {
        return false;
    }

    @Override
    boolean validTake(int startX, int startY, int endX, int endY) {
        return false;
    }
}
