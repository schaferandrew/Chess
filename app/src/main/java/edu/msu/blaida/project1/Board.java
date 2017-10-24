package edu.msu.blaida.project1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;

import static edu.msu.blaida.project1.R.id.contentPanel;
import static edu.msu.blaida.project1.R.id.textView;
import static java.lang.Math.floor;




public class Board {

    public enum EndOfTurnKingStatus{
        SAFE, CHECK, CHECKMATE, TAKEN
    }

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
    private float[] selectedPiece = null;

    /**
     * Turn Counter 1 is white
     */
    private int playerTurn = 1;


    private Context context = null;
    private TextView playerIndicator;
    private ChessView view;
    private Activity activity;


    /**
     * 0 is player 1, 1 is player 2
     */
    private EndOfTurnKingStatus[] playerStatus = new EndOfTurnKingStatus[2];

    public Piece[][] getBoard() {
        return board;
    }
    public void setBoard(Piece[][] board) {
        this.board = board;
    }

    public void setPlayerIndicator(TextView playerIndicator) {
        this.playerIndicator = playerIndicator;
    }
    public void setActivity(Activity activity) {
        this.activity = activity;
    }
    public void setView(ChessView view) {
        this.view = view;
    }

    /**
     * Save the board to a bundle
     * @param bundle The bundle we save to
     */
    public void saveInstanceState(Bundle bundle) {
        bundle.putSerializable("BOARD", board);
    }
    /**
     * Read the puzzle from a bundle
     * @param bundle The bundle we save to
     */
    public void loadInstanceState(Bundle bundle) {
        board = (Piece[][]) bundle.getSerializable("BOARD");

    }

    public Board(Context context) {
        // Create paint for filling the area the board will
        // be solved in.

        this.context = context;

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
    public void resign() {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(context);

        // Parameterize the builder
        builder.setTitle("Resign");
        builder.setMessage("Player " + playerTurn + " has Resigned")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(activity, activity_end.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        activity.startActivity(intent);
                    }
                });
        builder.show();

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
        int i =0,j =0;

        for(Piece[] pieceRow : board) {
            for(Piece piece : pieceRow) {
                if (piece != null) {
                    piece.draw(canvas, marginX, marginY, boardSize, scaleFactor, j, i);
                }
                j++;
            }
            i++;
            j=0;
        }

        if (playerTurn == 1) {
            playerIndicator.setText(context.getResources().getString(R.string.player_one_turn));
        } else {
            playerIndicator.setText(context.getResources().getString(R.string.player_two_turn));
        }
    }

    /**
     * Handle a touch event from the view.
     * @param view The view that is the source of the touch
     * @param event The motion event describing the touch
     * @return true if the touch is handled.
     */
    public touchResults onTouchEvent(View view, MotionEvent event) {
        //
        // Convert an x,y location to a relative location in the
        // puzzle.
        //

        float relX = (event.getX() - marginX) / boardSize;
        float relY = (event.getY() - marginY) / boardSize;


        return onTouched(relX, relY);
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(int playerTurn) {
        this.playerTurn = playerTurn;
    }

    /**
     * Handle a touch message. This is when we get an initial touch
     * @param x x location for the touch, relative to the puzzle - 0 to 1 over the puzzle
     * @param y y location for the touch, relative to the puzzle - 0 to 1 over the puzzle
     * @return true if the touch is handled
     */
    private touchResults onTouched(float x, float y) {

        // Check each piece to see if it has been hit
        // We do this in reverse order so we find the pieces in front
        if (selectedPiece == null) {
            Point checkGridPosition = toGridPosition(x, y);
            if(checkGridPosition == null){
                //Tap outside board
                return touchResults.INVALIDTAP;
            }
            Piece checkSelectedPiece = getPiece(checkGridPosition.x, checkGridPosition.y);
            if (checkSelectedPiece != null && checkSelectedPiece.getPlayer() == playerTurn) {
                selectedPiece = new float[]{ x, y};
                return touchResults.FIRSTTAP;
            } else {
                return touchResults.INVALIDTAP;
            }
        } else {
            Point startGridPosition = toGridPosition(selectedPiece[0], selectedPiece[1]);
            Point endGridPosition = toGridPosition(x,y);
            if (executeMove(startGridPosition,endGridPosition)) {
                if (playerTurn == 1) {
                    playerTurn = 2;

                } else {
                    playerTurn = 1;

                }
                //Find check/checkmate status of the player who's turn it now is
                EndOfTurnKingStatus status = checkKing(playerTurn);
                if(status == EndOfTurnKingStatus.CHECKMATE || status == EndOfTurnKingStatus.TAKEN){
                    //Game over, current player loses.
                    int opponent;
                    if(playerTurn == 1){
                        opponent = 2;
                    }
                    else{
                        opponent = 1;
                    }
                    ((ChessActivity)activity).startEndActivity(opponent);
                }
                playerStatus[playerTurn-1] = status;
                selectedPiece = null;
                return touchResults.VALIDMOVE;
            }
            selectedPiece = null;
            return touchResults.INVALIDMOVE;

        }
    }

    public boolean executeMove(Point start, Point end){
        Piece attacker = getPiece(start.x, start.y);
        Piece defender = getPiece(end.x, end.y);
        if(playerStatus[playerTurn-1] == EndOfTurnKingStatus.CHECK){
            //If youre in Check, you have to be moving a king
            if(!(attacker instanceof King)){
                CharSequence text = "You are in check, you must move your king!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context,text,duration);
                toast.show();
                return false;
            }
            int opponent;
            if(playerTurn == 1){
                opponent = 2;
            }else{
                opponent = 1;
            }
            if(someoneCanAttackSpace(end, opponent)){
                CharSequence text = "That move would put you in checkmate!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context,text,duration);
                toast.show();
                //Cant move to a place that will put you in checkmate
                return false;
            }

        }
        if(attacker == null){
            return false;
        }
        else if(attacker != null && defender == null){
            return movePiece(attacker, start, end);
        }
        else if(attacker != null && defender != null){
            if(attacker.getPlayer() == defender.getPlayer())
                return false;
            return takePiece(attacker, start, end);
        }
        return false;
    }

    public Piece getPiece(int x, int y){
        return this.board[y][x];
    }
    public void getPromotion(final Piece piece, final Point end) {

        final CharSequence pieceType[] = new CharSequence[] {"Queen", "Rook", "Knight", "Bishop"};

        // The puzzle is done
        // Instantiate a dialog box builder
        AlertDialog.Builder builder =
                new AlertDialog.Builder(context);

        // Parameterize the builder
        builder.setTitle("Promotion");
        builder.setItems(pieceType, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (pieceType[which] == "Queen") {
                    board[end.y][end.x] = new Queen(context, 7/16f, 15/16f, piece.getPlayer());

                }
                else if (pieceType[which] == "Rook") {
                    board[end.y][end.x] = new Rook(context, 7/16f, 15/16f, piece.getPlayer());

                }
                else if (pieceType[which] == "Knight") {
                    board[end.y][end.x] = new Knight(context, 7/16f, 15/16f, piece.getPlayer());

                } else {
                    board[end.y][end.x] = new Bishop(context, 7/16f, 15/16f, piece.getPlayer());
                }
                view.invalidate();
            }
        });

        // Create the dialog box and show it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public void checkPromotion(Piece piece, Point end) {
        // White
        if (piece.getPlayer() == 1 && end.y == 0) {
            getPromotion(piece, end);
        } else if (piece.getPlayer() == 2 && end.y == 7){
            getPromotion(piece, end);

        }

    }

    private boolean movePiece(Piece piece, Point start, Point end){
        Point[] path = piece.getMovePath(start, end);
        if(path == null){
            return false;
        }
        if(!piecesBlockingPath(path)){
            board[start.y][start.x] = null;
            board[end.y][end.x] = piece;
            piece.setFirstMove(false);
            if (piece.isPromotable()) {
                checkPromotion(piece, end);
            }
            return true;
        }
        return false;
    }

    private boolean takePiece(Piece attacker, Point start, Point end){
        Point[] path = attacker.getTakePath(start, end);
        if(path == null){
            return false;
        }
        path = Arrays.copyOf(path, path.length-1);

        if(!piecesBlockingPath(path)){
            board[start.y][start.x] = null;
            board[end.y][end.x] = attacker;
            attacker.setFirstMove(false);
            if (attacker.isPromotable()) {
                checkPromotion(attacker, end);
            }
            return true;
        }
        return false;
        //chessView.Invalidate() //Invalidate after moving. Do in touch event.
    }


    private Point toGridPosition(float x, float y) {
        int gridX = (int) floor(x * 8);
        int gridY = (int) floor(y * 8);

        if(gridX >= 8 || gridY >=8 || gridX < 0 || gridY < 0){
            return null;
        }

        return new Point(gridX,gridY);
    }

    private boolean piecesBlockingPath(Point[] path){
        for(Point position:path){
            Piece piece = this.getPiece(position.x,position.y);
            if(piece != null){
                return true;
            }
        }
        return false;
    }


    private Point findKing(int player){
        //Look for the right king
        for(int i=0;i<8;i++) {
            for(int j=0;j<8;j++) {
                //Search the board for the king
                Piece piece = board[j][i];
                if (piece != null && piece instanceof King){
                    //Now that we've found the king, make sure its the right player
                    if(piece.getPlayer() == player){

                        /*
                        //Then get the 3x3 square around the king

                        */
                        return new Point(i,j);
                    }
                }
            }
        }

        return null;
    }


    private boolean someoneCanAttackSpace(Point target, int attackingPlayer){
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                Piece piece = board[j][i];

                if(piece == null)
                    continue;
                if(piece.getPlayer() == attackingPlayer){
                    Point[] path =piece.getTakePath(new Point(i,j), target);
                    if(path == null){
                        continue;
                    }
                    path = Arrays.copyOf(path, path.length-1);
                    if(!piecesBlockingPath(path)){
                        int test=0;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public EndOfTurnKingStatus checkKing(int player){
        int opponent;
        if(player == 1){
            opponent = 2;
        }else{
            opponent = 1;
        }

        EndOfTurnKingStatus status = EndOfTurnKingStatus.SAFE;
        Point KingSpace = findKing(player);

        if(KingSpace == null){
            return EndOfTurnKingStatus.TAKEN;
        }

        if(someoneCanAttackSpace(KingSpace, opponent)){
            status = EndOfTurnKingStatus.CHECK;
        }

        if(status == EndOfTurnKingStatus.CHECK){
            for(int i=-1;i<2;i++){
                for(int j=-1;j<2;j++){
                    int x = KingSpace.x+i;
                    int y = KingSpace.y+j;
                    if(x >0 && x<8 && y>0 && y <8){
                        //If the space is empty and nobody can attack the space, return status as in Check
                        if(getPiece(x,y) == null && !someoneCanAttackSpace(new Point(x, y), opponent))
                            return status;
                    }
                }
            }
            //If we've gotten through, then all spaces king can move to are able to be attacked. Checkmate.
            status = EndOfTurnKingStatus.CHECKMATE;
            return status;
        }

        return EndOfTurnKingStatus.SAFE;
    }
}
