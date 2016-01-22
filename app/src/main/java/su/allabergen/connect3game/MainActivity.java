package su.allabergen.connect3game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // 0 - black, 1 - red
    int activePlayer = 0;
    boolean gameIsActive = true;
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void dropIn(View view) {
        ImageView counter = (ImageView) view;

        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if (gameState[tappedCounter] == 2 && gameIsActive) {
            gameState[tappedCounter] = activePlayer;

            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.black);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }

            counter.animate().alpha(0.0f);
            counter.animate().alpha(1.0f).setDuration(500);

            for (int[] winningPos : winningPositions) {
                if (gameState[winningPos[0]] == gameState[winningPos[1]]
                        && gameState[winningPos[1]] == gameState[winningPos[2]]
                        && gameState[winningPos[0]] != 2) {

                    String winner = "Red";
                    if (gameState[winningPos[0]] == 0)
                        winner = "Black";

                    LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
                    TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);

                    winnerMessage.setText(winner + " WIN!");
                    layout.setVisibility(View.VISIBLE);
                    gameIsActive = false;

                    Toast.makeText(getApplicationContext(), "Winner is" + winner, Toast.LENGTH_LONG).show();
                } else {
                    boolean isGameOver = true;

                    for (int counterState : gameState) {
                        if (counterState == 2) isGameOver = false;
                    }

                    if (isGameOver) {
                        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
                        TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);

                        winnerMessage.setText("Draw");
                        layout.setVisibility(View.VISIBLE);
                        gameIsActive = false;
                    }
                }
            }
        }
    }

    public void playAgain(View view) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        layout.setVisibility(View.INVISIBLE);
        gameIsActive = true;

        activePlayer = 0;
        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;
        }

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }
    }
}
