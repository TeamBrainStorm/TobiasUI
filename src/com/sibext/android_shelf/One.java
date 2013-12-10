package com.sibext.android_shelf;

import java.text.DecimalFormat;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.d4a.tobias.R;

public class One extends Fragment implements OnClickListener {
	 private TextView calculatorDisplay;
		
	 private static final String DIGITS = "0123456789.";
	 
	 private Boolean userIsInTheMiddleOfTypingANumber = false;
	  
	    DecimalFormat df = new DecimalFormat("@###########");
	  
	    CalculatorBrain brain;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Get the view from fragmenttab3.xml
		View v = inflater.inflate(R.layout.one, container, false);
		brain = new CalculatorBrain();
		  
       calculatorDisplay = (TextView)v. findViewById(R.id.textView1);
 
       df.setMinimumFractionDigits(0);
       df.setMinimumIntegerDigits(1);
       df.setMaximumIntegerDigits(8);
 
       v. findViewById(R.id.button0).setOnClickListener(this);
     
       
       v. findViewById(R.id.chat).setOnClickListener(this);

       v. findViewById(R.id.googleplay).setOnClickListener(this);

       v. findViewById(R.id.youtube).setOnClickListener(this);

       
       v. findViewById(R.id.button4).setOnClickListener(this);
       
       v.  findViewById(R.id.button5).setOnClickListener(this);
       
       v. findViewById(R.id.button6).setOnClickListener(this);
      
       v. findViewById(R.id.button7).setOnClickListener(this);
       
       v.   findViewById(R.id.button8).setOnClickListener(this);
   
       v.   findViewById(R.id.button9).setOnClickListener(this);


       
       v.  findViewById(R.id.buttonAdd).setOnClickListener(this);
       
       
       v.  findViewById(R.id.buttonSubtract).setOnClickListener(this);
       
       
       v.  findViewById(R.id.buttonMultiply).setOnClickListener(this);
       
       
       v.  findViewById(R.id.buttonDivide).setOnClickListener(this);
       
       
       v.  findViewById(R.id.buttonToggleSign).setOnClickListener(this);
       
       
       v.    findViewById(R.id.buttonDecimalPoint).setOnClickListener(this);
       
       
       v.   findViewById(R.id.buttonEquals).setOnClickListener(this);
       
       
       
       v.   findViewById(R.id.buttonClear).setOnClickListener(this);
       
       
       v.  findViewById(R.id.buttonClearMemory).setOnClickListener(this);
       
       
       v.   findViewById(R.id.buttonAddToMemory).setOnClickListener(this);
       
       
       v.   findViewById(R.id.buttonSubtractFromMemory).setOnClickListener(this);
       
    
       v.  findViewById(R.id.buttonRecallMemory).setOnClickListener(this);
	
	
	
	
	
	return v;

}


@Override
public void onClick(View v) {
	// TODO Auto-generated method stub


   String buttonPressed = ((Button)v).getText().toString();
   // String digits = "0123456789.";

   if (DIGITS.contains(buttonPressed)) {
       // digit was pressed
       
   	
   	if (userIsInTheMiddleOfTypingANumber) {
          
       	calculatorDisplay.append(buttonPressed);
      
       
       } 
       
       else 
       
       {
           
       	
       	calculatorDisplay.setText(buttonPressed);
       
           
           userIsInTheMiddleOfTypingANumber = true;
   
       }
   
   } 
   
   else 
   
   
   {
       // operation was pressed
       if (userIsInTheMiddleOfTypingANumber)
       
       {
         
       	brain.setOperand(Double.parseDouble(calculatorDisplay.getText().toString()));
          
       	userIsInTheMiddleOfTypingANumber = false;
       	
       }

            brain.performOperation(buttonPressed);
    
             calculatorDisplay.setText(df.format(brain.getResult()));
    }	
		







}

 @Override
public void onSaveInstanceState(Bundle outState) 
   
   {
       super.onSaveInstanceState(outState);
       // Save variables on screen orientation change
   
       
       outState.putDouble("OPERAND", brain.getResult());
   
       
       outState.putDouble("MEMORY", brain.getMemory());
   
   
   
   
   
   }















}