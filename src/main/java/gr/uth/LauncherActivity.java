package gr.uth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        ImageButton btn1 = findViewById(R.id.englandButton);
        ImageButton btn2 = findViewById(R.id.franceButton);

        //load questions about England
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                intent.putExtra("value","England");
                startActivity(intent);
            }
        });

        //load questions about France
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                intent.putExtra("value","France");
                startActivity(intent);
            }
        });
    }

    /*public void startQuiz(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("value","England");
        startActivity(intent);
    }*/
}
