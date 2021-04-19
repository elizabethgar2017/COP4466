 package com.example.hw6calculator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;


public class MainActivity extends AppCompatActivity implements OnItemSelectedListener {

    EditText num1, num2;
    Button button1;
    TextView txt;
    Spinner spin;
    String[] ops={"Operation:","Add","Subtract","Multiply","Divide"};
    String opera;
    double a, b, c;

    private OnClickListener myClickListener = new OnClickListener()
    {
        public void onClick(View v) {
            a=Double.parseDouble(num1.getText().toString());
            b=Double.parseDouble(num2.getText().toString());
            if (opera=="Add") {
                c=a+b; txt.setText(Double.toString(c));
            }
            else if (opera=="Subtract"){
                c=a-b; txt.setText(Double.toString(c));
            }
            else if (opera=="Multiply"){
                c=a*b; txt.setText(Double.toString(c));
            }
            else if (opera=="Divide"){
                c=a/b; txt.setText(Double.toString(c));}
            else {
                txt.setText("Answer");
            }



        }
    };



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt = (TextView)findViewById(R.id.textView);

        button1 = (Button)findViewById(R.id.button);
        button1.setText("=");
        button1.setOnClickListener(myClickListener);

        num1 = (EditText)findViewById(R.id.eNum1);
        num1.setText("");
        num2 = (EditText)findViewById(R.id.eNum2);
        num2.setText("");


        spin = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, ops);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(this);

    }
    public void onItemSelected (AdapterView<?> p,View v,int position,long id) {
        opera=ops[position];

    }
    public void onNothingSelected(AdapterView<?> p) {
        txt.setText("Please select ops");
    }
}