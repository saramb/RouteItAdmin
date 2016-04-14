package bahomaid.sara.routeitadmin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
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


public class EnterBusStation extends AppCompatActivity {

    private String ROOT_URL = Login.ROOT_URL;
    Spinner dropdown1 , dropdown2 , dropdown3;
    List<String> spin = new ArrayList<String>();
    List<String> spinMetro = new ArrayList<String>();
    List<String> spinPosition = new ArrayList<String>();
    List<String> spinPosition2 = new ArrayList<String>();
    TextView error,error2,error3, stationStreet ;
    String s,n,c,c2,d1,d2,d3,st,Admin;
    EditText station,coorX ,name,coorY,street;
    Button enter;
    ArrayAdapter<String> adapter;
    boolean done1=false;
    boolean done2=false;
    boolean done3=false;
    boolean done4=false;
    boolean done5=false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_bus_station);


        enter=(Button)findViewById(R.id.button9);
        station=(EditText)findViewById(R.id.editText4);
        station.setHint(Html.fromHtml("<small><small>" + "Please enter station ID" + "</small></small>"));

        name=(EditText)findViewById(R.id.editText5);
        name.setHint(Html.fromHtml("<small><small>" + "Please enter station name" + "</small><small>"));

        coorX=(EditText)findViewById(R.id.editText15);
        coorX.setHint(Html.fromHtml("<small><small>" + "Please enter X Coordinate" + "</small></small>"));

        coorY=(EditText)findViewById(R.id.editText);
        coorY.setHint(Html.fromHtml("<small><small>" + "Please enter Y Coordinate" + "</small></small>"));


        street=(EditText)findViewById(R.id.editText2);
        street.setHint(Html.fromHtml("<small><small>" + "Please enter station street" + "</small></small>"));

        stationStreet=(TextView)findViewById(R.id.StreettextView);
        dropdown1=(Spinner)findViewById(R.id.spinner4);
        dropdown2=(Spinner)findViewById(R.id.spinner5);
        dropdown3=(Spinner)findViewById(R.id.spinner6);
        error=(TextView)findViewById(R.id.textView13);
        error2=(TextView)findViewById(R.id.textView15);
        error3=(TextView)findViewById(R.id.textView33);

        spinPosition.add(" ");
        spinPosition.add("At the begining");
        spinPosition.add("At the end");




        adapter = new ArrayAdapter<String>(EnterBusStation.this,android.R.layout.simple_dropdown_item_1line, spinPosition);
        dropdown3.setAdapter(adapter);

        Retrieve();

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                s = station.getText().toString();
                n = name.getText().toString();
                st= street.getText().toString();
                c = coorX.getText().toString();
                c2 = coorY.getText().toString();
                d1=dropdown1.getSelectedItem().toString();
                d2=dropdown2.getSelectedItem().toString();
                d3=dropdown3.getSelectedItem().toString();

                if (s.equals("") || n.equals("") || c.equals("") || c2.equals("") || d1.equals(" ") ||d3.equals(" ")||d2.equals("")||st.equals("")) {done1=false;} else{done1=true;}


            if (dropdown1.getSelectedItem().toString().equals(" ")) {
                        //error.requestFocus();
                        error.setError("Please select line ID");
                    }else
                        error.setError(null);

                    if (dropdown2.getSelectedItem().toString().equals(" ")) {
                        // error2.requestFocus();
                        error2.setError("Please select the position of the station");
                    }else
                        error2.setError(null);

                    if (dropdown3.getSelectedItem().toString().equals(" ")) {
                        // error2.requestFocus();
                        error3.setError("Please select the position of the station");
                    }else
                        error3.setError(null);

                    if (station.getText().toString().equals("")) {
                        station.setError("Please fill station ID");
                    }
                    else{
                        station.setError(null);
                        if (android.text.TextUtils.isDigitsOnly(station.getText().toString())) {
                            done2 = true;
                            station.setError(null);
                        } else {
                            station.setError("Please enter digits only");
                            done2=false;
                        }
                    }
                    if (name.getText().toString().equals("")) {
                        name.setError("Please fill the name");
                    }
                    if (coorX.getText().toString().equals("")) {
                        coorX.setError("Please fill the X coordination");
                    }else{

                        coorX.setError(null);

                        if (coorX.getText().toString().contains(".") && coorX.getText().toString().length() >= 4 &&
                            coorX.getText().toString().substring(0, coorX.getText().toString().indexOf(".")).equals("24") &&
                            android.text.TextUtils.isDigitsOnly(coorX.getText().toString().substring(coorX.getText().toString().indexOf(".") + 1))) {

                        coorX.setError(null);
                        done4 = true;

                    } else {
                        coorX.setError("Please enter coordinate start with 24.XXX ");
                            done4 = false;

                        }}
                    if (coorY.getText().toString().equals("")) {
                        coorY.setError("Please fill the Y coordination");
                    }else{
                        coorY.setError(null);
                        if (coorY.getText().toString().contains(".") && coorY.getText().toString().length() >= 4 &&
                                coorY.getText().toString().substring(0, coorY.getText().toString().indexOf(".")).equals("46") &&
                                android.text.TextUtils.isDigitsOnly(coorY.getText().toString().substring(coorY.getText().toString().indexOf(".") + 1))) {

                            coorY.setError(null);
                            done5 = true;

                        } else {
                            coorY.setError("Please enter coordinate start with 46.XXX");
                            done5 = false;

                        }
                    }
                    if (street.getText().toString().equals("")) {
                        street.setError("Please fill the station street");
                    }
                    else{
                        street.setError(null);

                        if (android.text.TextUtils.isDigitsOnly(street.getText().toString()) && street.getText().toString().length() <= 2) {
                            street.setError(null);
                            done3 = true;
                        } else {
                            street.setError("Please enter 2 digits only");
                            done3 = false;

                        }

                    }








                if(done1 &&done2 &&done3 &&done4 &&done5){

                    error.setError(null);
                    error2.setError(null);
                    error3.setError(null);
                    coorX.setError(null);
                    coorY.setError(null);
                    station.setError(null);
                    name.setError(null);
                    street.setError(null);


                    done1=false;
                    done2=false;
                    done3=false;
                    done4=false;
                    done5=false;

                    if (dropdown1.getSelectedItem().toString().equals("Green")) {
                        dropdown3.setSelection(1);

                    }
                    new AlertDialog.Builder(EnterBusStation.this)
                            .setMessage("Are you sure you want to continue the  entering process ?")
                            .setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    EnterBusStation();
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

    private void EnterBusStation(){
        //Here we will handle the http request to insert user to mysql db
        //Creating a RestAdapter

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL) //Setting the Root URL
                .build();  //Finally building the adapter

        //Creating object for our interface
        routeAPI api = adapter.create(routeAPI.class);

        String LocationID = "2."+ dropdown1.getSelectedItemPosition()+"."+street.getText().toString()+"."+station.getText().toString();



 //Admin=getIntent().getExtras().getString("AdminID");
        //Defining the method insertuser of our interface
        api.EnterBusStation(


                //Passing the values by getting it from editTexts
                LocationID,
                coorX.getText().toString(),
                coorY.getText().toString(),
                name.getText().toString(),
                dropdown2.getSelectedItem().toString(),
                dropdown1.getSelectedItemPosition(),
                Login.admin,
                dropdown3.getSelectedItemPosition()+"",




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
                            Toast.makeText(EnterBusStation.this, output, Toast.LENGTH_LONG).show();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        //Displaying the output as a toast
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //If any error occured displaying the error as toast
                        Toast.makeText(EnterBusStation.this, error.toString(), Toast.LENGTH_LONG).show();
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
                "4",
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
                                // Toast.makeText(EnterBusStation.this, output, Toast.LENGTH_LONG).show();
                                String color = output.substring(0, output.indexOf("/"));
                                output = output.substring(output.indexOf("/") + 1);
                                if (!spin.contains(color)) {
                                    spin.add(color);
                                }
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(EnterBusStation.this,android.R.layout.simple_dropdown_item_1line, spin);

                            dropdown1.setAdapter(adapter);

                            //Toast.makeText(EnterBusStation.this, output, Toast.LENGTH_LONG).show();


                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        //Displaying the output as a toast
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //If any error occured displaying the error as toast
                        Toast.makeText(EnterBusStation.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );


       /* RestAdapter adapter2 = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL) //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        routeAPI api2 = adapter.create(routeAPI.class);*////تعديلمهم نشوفه

        //Defining the method insertuser of our interface
        api.Retrieve(

                //Passing the values by getting it from editTexts
                "2",
                //Creating an anonymous callback
                new Callback<Response>() {
                    @Override
                    public void success(Response result, Response response) {
                        //On success we will read the server's output using bufferedreader
                        //Creating a bufferedreader object
                        BufferedReader reader2 = null;

                        //An string to store output from the server
                        String output2 = "";

                        try {
                            //Initializing buffered reader
                            reader2 = new BufferedReader(new InputStreamReader(result.getBody().in()));

                            //Reading the output in the string
                            output2 = reader2.readLine();

                            spinMetro.add(" ");

                            while (!output2.equals("")) {
                                // Toast.makeText(EnterBusStation.this, output, Toast.LENGTH_LONG).show();
                                String color2 = output2.substring(0, output2.indexOf("/"));
                                output2 = output2.substring(output2.indexOf("/") + 1);
                                if (!spinMetro.contains(color2)) {
                                    spinMetro.add(color2);
                                }
                            }

                            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(EnterBusStation.this, android.R.layout.simple_dropdown_item_1line, spinMetro);

                            dropdown2.setAdapter(adapter2);

                            //Toast.makeText(EnterBusStation.this, output, Toast.LENGTH_LONG).show();


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //If any error occured displaying the error as toast
                        Toast.makeText(EnterBusStation.this, error.toString(), Toast.LENGTH_LONG).show();
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

            new AlertDialog.Builder(EnterBusStation.this)
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
