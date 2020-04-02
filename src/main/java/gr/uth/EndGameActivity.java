package gr.uth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class EndGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);
        TextView finalScore = findViewById(R.id.finalScoreValueTextView);
        Intent intent=getIntent();
        int score = intent.getIntExtra("score",0);
        finalScore.setText(String.valueOf(score));
    }
}
