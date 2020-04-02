package gr.uth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int sec;
    private int score;
    private boolean counting; //true if time is still running
    private boolean addBonus; //true if bonus should be added

    //QnA arrays
    private String[][] array= {
            {"Ποια είναι η πρωτεύουσα της Γαλλίας;","Παρίσι", "Λιόν", "Γκρενόμπλ", "Νίκαια"},
            {"Ποια ΔΕΝ είναι Γαλλική ομάδα ποδοσφαίρου;", "Παρί Σεν Ζερμέν", "Τουλούζ", "Μπαρτσελόνα", "Μονακό"},
            {"Ποιος είναι ο πρόεδρος της Γαλλίας;", "Σαρκοζί", "Ρουαγιάλ", "Λαγκάρντ", "Ολάντ"},
            {"Πότε ξεκίνησε η Γαλλική Επανάσταση;", "1789","1799","1804","1821"}
    };

    private String[][] array2= {
            {"Ποια είναι η πρωτεύουσα της Αγγλίας;","Μάντσεστερ", "Λονδίνο", "Μπράιτον", "Μπέρμιγχαμ"},
            {"Ποια ΔΕΝ είναι Αγγλική ομάδα ποδοσφαίρου;", "Τσέλσι", "Λίβερπουλ", "Λέστερ", "Ρέιντζερς"},
            {"Πόσα παιδιά έχει η βασίλισσα Ελισάβετ;", "1", "2", "3", "4"},
            {"Ποιό είναι το επίσημο νόμισμα της Αγγλίας;", "Λίρα","Ευρώ","Δολάριο","Κορώνα"}
    };

    private int[] correctAnswers={1,3,4,1}; //correct Answers index Array
    private int[] correctAnswers2={2,4,4,1};
    private int currentQuestion; //index of current Question


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState!=null){
            sec = savedInstanceState.getInt("sec");
            score =savedInstanceState.getInt("score");
            counting = savedInstanceState.getBoolean("counting");
            addBonus = savedInstanceState.getBoolean("addBonus");
            currentQuestion =savedInstanceState.getInt("currentQuestion");
        }else {
            sec=0;
            score=0;
            counting=true;
            addBonus=true;
            currentQuestion=0;

        }
        TextView scoreView = findViewById(R.id.scoreValueTextView);
        scoreView.setText(String.valueOf(score));
        nextQuestion();
        showTime();
    }

    private void showTime() {
        final TextView timerView = findViewById(R.id.TextViewTimeRemaining);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(sec<120 && counting){
                    int minutes = sec / 60;
                    int seconds = sec % 60;
                    String time = String.format("%02d:%02d",minutes,seconds);
                    timerView.setText(time);
                    sec++;
                    handler.postDelayed(this,1000);
                }else {
                    String noBonusMessage = getString(R.string.noBonus);
                    timerView.setText(noBonusMessage);
                    addBonus=false;
                }
            }
        });
    }

    private void nextQuestion() {
        Intent intent=getIntent();
        String str = intent.getStringExtra("value");
        if(str.equals("France")) {
            if (currentQuestion < array.length) {
                TextView question = findViewById(R.id.textViewQuestionText);
                question.setText(array[currentQuestion][0]);
                RadioButton rb1 = findViewById(R.id.rb1);
                rb1.setText(array[currentQuestion][1]);
                RadioButton rb2 = findViewById(R.id.rb2);
                rb2.setText(array[currentQuestion][2]);
                RadioButton rb3 = findViewById(R.id.rb3);
                rb3.setText(array[currentQuestion][3]);
                RadioButton rb4 = findViewById(R.id.rb4);
                rb4.setText(array[currentQuestion][4]);

            }
        }else if(str.equals("England")){
            if(currentQuestion < array2.length) {
                TextView question = findViewById(R.id.textViewQuestionText);
                question.setText(array2[currentQuestion][0]);
                RadioButton rb1 = findViewById(R.id.rb1);
                rb1.setText(array2[currentQuestion][1]);
                RadioButton rb2 = findViewById(R.id.rb2);
                rb2.setText(array2[currentQuestion][2]);
                RadioButton rb3 = findViewById(R.id.rb3);
                rb3.setText(array2[currentQuestion][3]);
                RadioButton rb4 = findViewById(R.id.rb4);
                rb4.setText(array2[currentQuestion][4]);
            }
        }
    }

    public void onClickAnswer(View view) {
        Intent intentChoice=getIntent();
        String str = intentChoice.getStringExtra("value");
        int correctAnswer=0;
        if(str.equals("France")){
             correctAnswer = correctAnswers[currentQuestion];
        }else if(str.equals("England")) {
             correctAnswer = correctAnswers2[currentQuestion];
        }

        int selectedAnswer = getSelectedAnswer();
        RadioGroup radioButtonGroup = findViewById(R.id.radioGroup);
        radioButtonGroup.clearCheck();
        ScrollView sv = findViewById(R.id.scrv);
        sv.scrollTo(0, sv.getTop());

        //Update Score with bonus or not
        if(selectedAnswer==correctAnswer){
            if(addBonus){
                score+=10;
            }
            else{
                score+=5;
            }
        }

        //Display new score
        TextView scoreText = findViewById(R.id.scoreValueTextView);
        scoreText.setText(String.valueOf(score));
        currentQuestion++;

        //if no more questions
        if(currentQuestion==4){
            //stop counting
            counting=false;

            //Start EndGameActivity, send score to EndGameActivity
            Intent intent = new Intent(this,EndGameActivity.class);
            intent.putExtra("score",score);
            startActivity(intent);
        }
        else{
            //go to next Question and start counting from 0
            nextQuestion();
            sec=0;
            counting=true;
            addBonus=true;
            showTime();
        }
    }

    private int getSelectedAnswer() {
        RadioGroup radioButtonGroup = findViewById(R.id.radioGroup);
        int radioButtonID = radioButtonGroup.getCheckedRadioButtonId();
        View selectedRadioButton = radioButtonGroup.findViewById(radioButtonID);
        int index = radioButtonGroup.indexOfChild(selectedRadioButton);
        return index+1;
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("sec",sec);
        savedInstanceState.putInt("score",score);
        savedInstanceState.putBoolean("counting",counting);
        savedInstanceState.putBoolean("addBonus", addBonus);
        savedInstanceState.putInt("currentQuestion", currentQuestion);
    }
}
