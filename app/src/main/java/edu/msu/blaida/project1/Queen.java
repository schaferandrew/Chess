package edu.msu.blaida.project1;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import java.util.ArrayList;

/**
 * Created by power on 10/9/2017.
 */

public class Queen extends Piece {
    public Queen(Context context, float x, float y, int player) {
        super(context, x, y, player);
        if(player == 1){
            this.setBitmap(R.drawable.chess_qlt45);
        }else{
            this.setBitmap(R.drawable.chess_qdt45);
        }
    }

    @Override
    Point[] getMovePath(Point start, Point end) {
        if(start.x == end.x && start.y == end.y){
            return null;
        }
        if((Math.abs(start.x - end.x) < 2 && Math.abs(start.y - end.y) < 2)){
            //Moving like a king - Code taken from King class
            Point destination = new Point(end.x, end.y);
            Point[] path = new Point[1];
            path[0] = destination;
            return path;
        }else if((start.x == end.x && start.y != end.y)||(start.x != end.x && start.y == end.y)) {
            //Moving like a Rook - Code taken from Rook class
            ArrayList<Point> path = new ArrayList<Point>();
            if (start.x == end.x) {
                //Y is changing
                for (int i = 1; i < Math.abs(start.y - end.y) + 1; i++) {
                    if (start.y > end.y) {
                        //Going down
                        path.add(new Point(start.x, start.y - i));
                    } else {
                        //Goin up
                        path.add(new Point(start.x, start.y + i));
                    }

                }
            } else {
                //X is changing
                for (int i = 1; i < Math.abs(start.x - end.x) + 1; i++) {
                    if (start.x > end.x) {
                        //Going left
                        path.add(new Point(start.x - i, start.y));
                    } else {
                        //Going right
                        path.add(new Point(start.x + i, start.y));
                    }

                }
            }

            Point[] vals = new Point[path.size()];
            path.toArray(vals);
            return vals;

        }else if(Math.abs(end.x- start.x) == Math.abs(end.y-start.y)){
            //Moving like a bishop - Code taken from Bishop class
            ArrayList<Point> path = new ArrayList<Point>();
            for(int i=1;i<Math.abs(end.x-start.x)+1;i++){
                if(start.x > end.x){
                    //Going left
                    if(start.y > end.y){
                        //Going up
                        path.add(new Point(start.x-i,start.y-i));
                    }else{
                        //Going down
                        path.add(new Point(start.x-i,start.y+i));
                    }
                }else{
                    //Going right
                    if(start.y > end.y){
                        //going up
                        path.add(new Point(start.x+i,start.y-i));
                    }else{
                        //going down
                        path.add(new Point(start.x+i,start.y+i));
                    }
                }

                Point[] vals = new Point[path.size()];
                path.toArray(vals);
                return vals;
            }

            Point[] vals = new Point[path.size()];
            path.toArray(vals);
            return vals;

        }
        //Not a valid move
        return null;
    }

    @Override
    Point[] getTakePath(Point start, Point end) {
        return getMovePath(start,end);
    }
}
