package edu.msu.blaida.project1;

import android.content.Context;
import android.graphics.BitmapFactory;

/**
 * Created by power on 10/9/2017.
 */

public class King extends Piece {

    public King(Context context, float x, float y, int player) {
        super(context, x, y, player);
        if(player == 1){
            this.setBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.chess_klt45));
        }else{
            this.setBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.chess_kdt45));
        }
    }

    @Override
    boolean validMove(int startX, int startY, int endX, int endY){
        return (Math.abs(startX - endX) < 2 && Math.abs(startY - endY) < 2);
    }

    @Override
    boolean validTake(int startX, int startY, int endX, int endY) {
        return validMove(startX, startY, endX, endY);
    }
}
