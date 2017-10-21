package edu.msu.blaida.project1;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Point;

/**
 * Created by power on 10/9/2017.
 */

public class Knight extends Piece {
    public Knight(Context context, float x, float y, int player) {
        super(context, x, y, player);
        if(player == 1){
            this.setBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.chess_nlt45));
        }else{
            this.setBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.chess_ndt45));
        }
    }

    @Override
    Point[] getMovePath(Point start, Point end) {
        if((Math.abs(start.x-end.x) == 2 && Math.abs(start.y-end.y) == 1) ||
                (Math.abs(start.y-end.y) == 2 && Math.abs(start.x-end.x) == 1)){
            Point destination = new Point(end.x, end.y);
            Point[] path = new Point[1];
            path[0] = destination;
            return path;
        }
        return null;
    }

    @Override
    Point[] getTakePath(Point start, Point end) {
        return getMovePath(start, end);
    }
}
