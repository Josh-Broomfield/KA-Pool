package com.b.m.josh.keithdrewpool;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableLayout.LayoutParams;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int playerCount;
    private static final String plus = "+";
    private static final String minus = "-";
    private static final String newPlayer = "+ Player";
    private static final String newBall = "New Ball";
    private static final String txtReset = "Reset";
    private final KALogic game = new KALogic();

    public void setPlayerCount(int playerCount)
    {
        this.playerCount = playerCount;
    }
    public int getPlayerCount()
    {
        return this.playerCount;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAddPlayer = (Button) findViewById((R.id.btnAddPlayer));
        btnAddPlayer.setOnClickListener(this);
        setPlayerCount(0);

        Button btnReset = (Button) findViewById((R.id.btnReset));
        btnReset.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if(view instanceof Button) {
            Button btn = (Button)view;//can't use id cause programmatically added ui elements don't
                                      // have an id the first time you click them
            String btnText = btn.getText().toString();

            //getting parent then child at index https://stackoverflow.com/a/5004133
            switch(btnText) {
                case plus:
                    plus(btn);
                    break;
                case minus:
                    minus(btn);
                    break;
                case newPlayer:
                    addPlayerRow();
                    break;
                case newBall:
                    newBall(btn);
                    break;
                case txtReset:
                    reset();
                    break;
                default:
                    break;
            }
        }
    }

    public void reset() {
        TableLayout tl = (TableLayout) findViewById((R.id.tblPlayers));
        int players = getPlayerCount();

        TableRow tr = null;
        //remove rows from the end until getting to the first
        for(int i = tl.getChildCount() - 1; i >= 1; --i) {
            tr = (TableRow) tl.getChildAt(i);
            tl.removeView(tr);
        }

        game.resetGame();

        for(int i = 0; i < players; ++i) {
            addPlayerRow();
        }
    }

    private int getRowId(TableRow tr) {
        TextView txtId = (TextView) tr.getChildAt(5);

        return Integer.parseInt(txtId.getText().toString());
    }

    private int getRowId(Button btn) {
        return getRowId((TableRow) btn.getParent());
    }

    private void newBall(Button btn) {
        int id = getRowId(btn);
        TableRow tr = (TableRow) btn.getParent();

        try {
            game.getNewBall(id);
            updateRow(tr);
        } catch(Exception e) {}
    }

    private void minus(Button btn) {
        changeScore(btn, -1);
    }

    private void plus(Button btn) {
        changeScore(btn, 1);
    }

    //updates a player's score in the game logic, then makes a call to update ui
    private void changeScore(Button btn, int increment) {
        TableRow tr = (TableRow) btn.getParent();

        int id = getRowId(tr);

        Player p = game.findPlayer(id);
        p.setScore(p.getScore() + increment);
        game.updatePlayer(p);
        updateRow(tr, p);
    }

    //Updates a row's values in the ui
    private void updateRow(TableRow tr, Player p) {
        TextView ball = (TextView) tr.getChildAt(0);
        TextView score = (TextView) tr.getChildAt(1);

        ball.setText(String.valueOf(p.getCurrentBall()));
        score.setText(String.valueOf(p.getScore()));
    }

    private void updateRow(TableRow tr) {
        int id = getRowId(tr);

        Player p = game.findPlayer(id);

        updateRow(tr, p);
    }

    public void addPlayerRow() {
        setPlayerCount(getPlayerCount() + 1);
        TableLayout tl = (TableLayout) findViewById((R.id.tblPlayers));

        Player p = new Player(10, -1, getPlayerCount());

        TableRow tr = new TableRow(this);
        TextView txtId = new TextView(this);
        TextView txtBall = new TextView(this);
        TextView txtScore = new TextView(this);

        Button btnMinus = new Button(this);
        Button btnPlus = new Button(this);
        Button btnNewBall = new Button(this);


        tr.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT,
                1
        ));

        txtBall.setGravity(Gravity.CENTER);
        txtBall.setLayoutParams(new TableRow.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT,
                1
        ));

        txtScore.setGravity(Gravity.CENTER);
        txtScore.setLayoutParams(new TableRow.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT,
                1
        ));

        try {
            p.setCurrentBall(game.getNewBall());

            txtId.setText(String.valueOf(getPlayerCount()));
            txtBall.setText(String.valueOf(p.getCurrentBall()));
            txtScore.setText(String.valueOf(p.getScore()));
            btnMinus.setText(minus);
            btnPlus.setText(plus);
            btnNewBall.setText(newBall);

            btnMinus.setOnClickListener(this);
            btnPlus.setOnClickListener(this);
            btnNewBall.setOnClickListener(this);


            tr.addView(txtBall);
            tr.addView(txtScore);
            tr.addView(btnMinus);
            tr.addView(btnPlus);
            tr.addView(btnNewBall);
            tr.addView(txtId);
            tl.addView(tr);

            txtId.setVisibility(View.GONE);

            game.addPlayer(p);
        } catch(Exception e) {
            setPlayerCount(getPlayerCount() - 1);
        }
    }
}
