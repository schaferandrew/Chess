package edu.msu.blaida.project1;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


/**
 * Custom view class for our Puzzle.
 */
public class ChessView extends View {
    /**
     * The actual puzzle
     */
    private Board board;
    private Activity activity;

    public void setActivity(Activity a) {
        activity = a;
    }
    public ChessView(Context context) {
        super(context);
        init(null, 0);

    }

    public ChessView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public ChessView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);

    }
    /**
     * Save the puzzle to a bundle
     * @param bundle The bundle we save to
     */
    public void saveInstanceState(Bundle bundle) {
        board.saveInstanceState(bundle);
    }
    /**
     * Load the puzzle from a bundle
     * @param bundle The bundle we save to
     */
    public void loadInstanceState(Bundle bundle) {
        board.loadInstanceState(bundle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        board = new edu.msu.blaida.project1.Board(getContext());
        setWillNotDraw(false);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        board.setPlayerIndicator((TextView)activity.findViewById(R.id.playerIndicator));
        board.draw(canvas, activity);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        touchResults result = board.onTouchEvent(this,event);
        if(result == touchResults.VALIDMOVE){
            invalidate();
            return true;
        }else if(result == touchResults.INVALIDMOVE){
            CharSequence text = "Invalid move.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(getContext(),text,duration);
            toast.show();
        }
        return super.onTouchEvent(event);
    }
}