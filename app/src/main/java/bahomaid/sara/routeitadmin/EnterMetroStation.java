package bahomaid.sara.routeitadmin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class EnterMetroStation extends AppCompatActivity {
    String s,n,c,c2,c3;
    private String ROOT_URL = Login.ROOT_URL;
    EditText station,name,coor,coor2;
    Spinner MetroLine;
    List<String> spin = new ArrayList<String>();
    List<String> spinPosition = new ArrayList<String>();
    Spinner Position;
    Button enter;
    TextView error , error2;

    boolean done1=false;
    boolean done3=false;
    boolean done4=false;
    boolean done5=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_metro_station);



        enter=(Button)findViewById(R.id.button18);
        MetroLine=(Spinner)findViewById(R.id.MetroLine);
        station =(EditText) findViewById(R.id.metroId);
        name =(EditText) findViewById(R.id.metroName);
        coor =(EditText) findViewById(R.id.metroCoordinates);
        coor2 =(EditText) findViewById(R.id.metroCoordinates2);
        final TextView error=(TextView)findViewById(R.id.textView2);
        final TextView error2=(TextView)findViewById(R.id.textView36);
        Position=(Spinner)findViewById(R.id.spinner7);

        spinPosition.add(" ");
        spinPosition.add("At the begining");
        spinPosition.add("At the end");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(EnterMetroStation.this,android.R.layout.simple_dropdown_item_1line, spinPosition);
        Position.setAdapter(adapter);

        Retrieve();

        enter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                s = station.getText().toString();
                n = name.getText().toString();
                c = coor.getText().toString();
                c2=coor2.getText().toString();


                if (s.equals("") || n.equals("") || c.equals("")|| Position.getSelectedItem().toString().equals(" ") || MetroLine.getSelectedItem().toString().equals(" ")) {done1=false;} else{done1=true;}

                    if(MetroLine.getSelectedItem().toString().equals(" ")){
                        error.requestFocus();
                        error.setError("Please select line ID");
                    }else
                        error.setError(null);

                    if (Position.getSelectedItem().toString().equals(" ")) {
                        // error2.requestFocus();
                        error2.setError("Please select the position of the station");
                    }else
                        error2.setError(null);

                    if (station.getText().toString().equals("")) {
                        station.setError("Please fill station ID");
                    }else{

                        station.setError(null);
                        if (android.text.TextUtils.isDigitsOnly(station.getText().toString())) {
                            done3 = true;
                            station.setError(null);
                        } else {
                            station.setError("Please enter digits only");
                            done3=false;
                        }
                    }

                    if (name.getText().toString().equals("")) {
                        name.setError("Please fill the name");
                    }
                    if (coor.getText().toString().equals("")) {
                        coor.setError("Please fill the coordination");
                    }
                else{
                        coor.setError(null);

                        if (coor.getText().toString().contains(".") && coor.getText().toString().length() >= 3 &&
                                coor.getText().toString().substring(0, coor.getText().toString().indexOf(".")).equals("24") &&
                                android.text.TextUtils.isDigitsOnly(coor.getText().toString().substring(coor.getText().toString().indexOf(".") + 1))) {

                            coor.setError(null);
                            done4 = true;

                        } else {
                            coor.setError("Please enter correct coordination ");
                            done4 = false;

                        }


                    }

                    if (coor2.getText().toString().equals("")) {
                        coor2.setError("Please fill the coordination");
                    }                else{
                        coor2.setError(null);

                        if (coor2.getText().toString().contains(".") && coor2.getText().toString().length() >= 3 &&
                                coor2.getText().toString().substring(0, coor2.getText().toString().indexOf(".")).equals("46") &&
                                android.text.TextUtils.isDigitsOnly(coor2.getText().toString().substring(coor2.getText().toString().indexOf(".") + 1))) {

                            coor2.setError(null);
                            done5 = true;

                        } else {
                            coor2.setError("Please enter correct coordination ");
                            done5 = false;

                        }


                    }




                if(done1&&done3 &&done4 &&done5){
                    coor.setError(null);
                    coor2.setError(null);
                    station.setError(null);
                    name.setError(null);
                    error2.setError(null);
                    error.setError(null);

                    new AlertDialog.Builder(EnterMetroStation.this)
                            .setMessage("are you sure you want to continue the  entering process ?")
                            .setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    EnterMetroStation();
                                    finish();

                                }
                            })

                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .show();
                }
            }
        });

    }

    private void EnterMetroStation(){
        //Here we will handle the http request to insert user to mysql db
        //Creating a RestAdapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL) //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        routeAPI api = adapter.create(routeAPI.class);

        String LocationID = "1."+ MetroLine.getSelectedItemPosition()+".0."+station.getText().toString();

        //Defining the method insertuser of our interface
        api.EnterMetroStation(

                //Passing the values by getting it from editTexts
                LocationID,
                coor.getText().toString(),
                coor2.getText().toString(),
                name.getText().toString(),
                MetroLine.getSelectedItemPosition(),
               Login.admin,
                Position.getSelectedItemPosition()+"",


                //Creating an anonymous callback
                new Callback<Response>() {
                    @Override
                    public void success(Response result, Response response) {
                        //On success we will read the server's output using bufferedreader
                        //Creating a bufferedreader object
                        BufferedReader reader = null;

                        //An string to store output from the server
                        String output = "";

                        try {
                            //Initializing buffered reader
                            reader = new BufferedReader(new InputStreamReader(result.getBody().in()));

                            //Reading the output in the string
                            output = reader.readLine();
                            Toast.makeText(EnterMetroStation.this, output, Toast.LENGTH_LONG).show();


                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        //Displaying the output as a toast
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //If any error occured displaying the error as toast
                        Toast.makeText(EnterMetroStation.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    private void Retrieve(){
        //Here we will handle the http request to insert user to mysql db
        //Creating a RestAdapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL) //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        routeAPI api = adapter.create(routeAPI.class);
        //Defining the method insertuser of our interface
        api.Retrieve(

                //Passing the values by getting it from editTexts
                "1",
                //Creating an anonymous callback
                new Callback<Response>() {
                    @Override
                    public void success(Response result, Response response) {
                        //On success we will read the server's output using bufferedreader
                        //Creating a bufferedreader object
                        BufferedReader reader = null;

                        //An string to store output from the server
                        String output = "";

                        try {
                            //Initializing buffered reader
                            reader = new BufferedReader(new InputStreamReader(result.getBody().in()));

                            //Reading the output in the string
                            output = reader.readLine();

                            spin.add(" ");

                            while(!output.equals("")) {
                                //Toast.makeText(EnterMetroStation.this, output, Toast.LENGTH_LONG).show();
                                String color = output.substring(0, output.indexOf("/"));
                                output = output.substring(output.indexOf("/") + 1);
                                if (!spin.contains(color)) {
                                    spin.add(color);
                                }
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(EnterMetroStation.this,android.R.layout.simple_dropdown_item_1line, spin);

                            MetroLine.setAdapter(adapter);

                            //Toast.makeText(EnterMetroStation.this, output, Toast.LENGTH_LONG).show();


                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        //Displaying the output as a toast
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //If any error occured displaying the error as toast
                        Toast.makeText(EnterMetroStation.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu); //to connect the menu with the activity
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    //to connect the menu with action listner when it is selected, we override this method
    public boolean onOptionsItemSelected(MenuItem item) {
        //item detects any clicked button in menu
        if (item.getItemId() == R.id.return_) {
            Intent intent = new Intent (getApplicationContext(),Menu.class);
            startActivity(intent); }
        if (item.getItemId() == R.id.logout) {

            new AlertDialog.Builder(EnterMetroStation.this)
                    .setMessage("Are you sure you want to log out ?")
                    .setPositiveButton("Log Out", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                                    /*Intent intent = new Intent();
                                    PendingIntent pIntent = PendingIntent.getActivity(NewPost.this, 0, intent, 0);*///مهم نشوفها
                            Login.admin="";
                            Intent intent = new Intent (getApplicationContext(),Login.class);
                            startActivity(intent);
                            finish();
                        }
                    })

                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();

        }

        //we can do multiple menus for the same activity, and switch between them depending on the condition I want

        return super.onOptionsItemSelected(item);
    }
}
