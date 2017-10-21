package edu.msu.blaida.project1;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Point;

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
    Point[] getMovePath(Point start, Point end) {
        return null;
    }

    @Override
    Point[] getTakePath(Point start, Point end) {
        return getMovePath(start,end);
    }
}
