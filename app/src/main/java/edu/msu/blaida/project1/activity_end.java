package edu.msu.blaida.project1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class activity_end extends AppCompatActivity {

    String p1Name;
    String p2Name;

    public String getP1Name() {
        return p1Name;
    }

    public void setP1Name(String p1Name) {
        this.p1Name = p1Name;
    }

    public String getP2Name() {
        return p2Name;
    }

    public void setP2Name(String p2Name) {
        this.p2Name = p2Name;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        int winner = getIntent().getExtras().getInt("winner");
        String p1 = getIntent().getExtras().getString("p1Name");
        String p2 = getIntent().getExtras().getString("p2Name");

        setP1Name(p1);
        setP2Name(p2);
        TextView winnerText = (TextView)findViewById(R.id.winnerText);
        String winnerName;
        if(winner == 1){
            winnerName = p1;
        }else{
            winnerName = p2;
        }
        String winText = winnerName + " is the winner!";
        winnerText.setText(winText.toCharArray(),0,winText.length());
    }

    public void onRestartGame(View view) {
        Intent intent = new Intent(this, ChessActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("p1Name",getP1Name());
        intent.putExtra("p2Name",getP2Name());
        startActivity(intent);
    }
}
