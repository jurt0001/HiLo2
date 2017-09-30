/**
 * HiLo Game - User guesses a number between 1 and 1000.
 *
 * @author Christian Jurt (jurt0001@algonquinlive.com)
 */

package com.algonquincollege.jurt0001.hilo;

//importing

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

//main App class

public class MainActivity extends Activity {

    // Java's final means the value cannot be changes
    public static final int MAX_NUM = 1000; //max number user can guess
    public static final int MIN_NUM = 1; //min number a user can guess
    public int numGuess = 0;  //counts user guesses
    public EditText userGuess; //user Guess input field
    public Button guessButton; //Button user uses to submit guess
    public Button resetButton; //Button user uses to either reset the game or see the answer
    private int theNumber = 0; // The games correct number

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Random rand = new Random(); //creating the random number
        theNumber = rand.nextInt(1001) + 1; //setting the guessing range and the number

        // get a reference to the userGuess
        userGuess = (EditText) findViewById(R.id.editText); //assigning the userguess field to a variable


        guessButton = (Button) findViewById(R.id.button); // assigning the guess button to a var
        resetButton = (Button) findViewById(R.id.button2); //assigning the reset button to a var


        //setting a click listen for the reset button to reset the game and create a new random number and reset the guesses
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                theNumber = rand.nextInt(1001) + 1;
                numGuess = 0;

            }

        });

        //longClickListener on the reset button as option to see the games answer.
        resetButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Toast.makeText(getApplicationContext(),
                        "The correct number is " + theNumber,
                        Toast.LENGTH_SHORT).show();

                return false;
            }

        });


        // register an anonymous inner class as the event handler for guessButton
        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userGuessString = userGuess.getText().toString();

                // Use parseInt() to convert the string value to its (primitive) int value
                // parseInt() is a static method of class Integer
                // Class Integer is a Wrapper Class --- the Java class equivalent of int (primitive)
                // parseInt() throws a NumberFormatException if the User enters a non-integer
                int guess = 0;

                try {
                    guess = Integer.parseInt(userGuessString);
                } catch (NumberFormatException e) {

                    //Throwing an error if the uers enters a non integer
                    userGuess.setError("Please Enter a Number");
                    userGuess.requestFocus();
                    return;
                }

                // Validation Rule: min number against the user guess
                if (guess < MIN_NUM) {
                    userGuess.setError("Please a number bigger or equal to " + MIN_NUM);
                    userGuess.requestFocus();
                    return;
                }

                // Validation Rule: check max number against the user guess
                if (guess > MAX_NUM) {
                    userGuess.setError("Please Enter a number smaller or equal to " + MAX_NUM);
                    userGuess.requestFocus();
                    return;
                }

                //incrementing the amount of user guesses
                numGuess++;

                if (numGuess > 10) {
                    Toast.makeText(getApplicationContext(),

                            //prompts user to reset if max number of guesses is exceeded.
                            "Please Reset!",
                            Toast.LENGTH_SHORT).show();
                    userGuess.setText("");
                    return;
                }

                if (theNumber > guess) {
                    // message to user indicating the guess was too low
                    Toast.makeText(getApplicationContext(),
                            "Guess # " + numGuess + " You are too low. Guess Again",
                            Toast.LENGTH_SHORT).show();

                }

                if (theNumber < guess) {
                    // message to user indicating the guess was too high
                    Toast.makeText(getApplicationContext(),
                            "Guess # " + numGuess + " You are too high. Guess Again",
                            Toast.LENGTH_SHORT).show();

                }

                if (theNumber == guess) {
                    // message to user indicating the guess was correct

                    if (numGuess <= 5) {
                        Toast.makeText(getApplicationContext(),
                                "Superior Win!" + " You only took " + numGuess + " Guesses.",
                                Toast.LENGTH_SHORT).show();

                    }
                    if (numGuess >= 5 && numGuess <= 10) {
                        Toast.makeText(getApplicationContext(),
                                "Excellent Win! You took " + numGuess + " Guesses",
                                Toast.LENGTH_SHORT).show();
                    }

                }

                // re-set text field
                userGuess.setText("");
            }

        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    //adding a menu item to the action bar and setting up the contents of the dialog window.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int res_id = item.getItemId();
        if (res_id == R.id.action_about) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setCancelable(true);
            builder.setTitle("App Author");
            builder.setMessage("Christian Jurt (jurt0001)");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    dialogInterface.cancel();
                }
            });
            builder.show();
        }
        return super.onOptionsItemSelected(item);
    }
}
