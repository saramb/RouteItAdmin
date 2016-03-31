package bahomaid.sara.routeitadmin;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Login extends AppCompatActivity {

    public static final String ROOT_URL ="http://rawan.16mb.com/tesst/";

    public static String admin="";

    EditText username , pass;
    TextView wrong;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        username =(EditText) findViewById(R.id.username);
        pass =(EditText) findViewById(R.id.pass);
        login = (Button) findViewById(R.id.button);
        wrong=(TextView)findViewById(R.id.textView31);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                wrong.setText("");
                wrong.setError(null);


                if (username.getText().toString().equals("") || pass.getText().toString().equals("")) {
                    if (username.getText().toString().equals("")) {
                        username.setError("Please enter user name");
                    } else
                        username.setError(null);

                    if (pass.getText().toString().equals("")) {
                        pass.setError("Please enter password");
                    } else
                        pass.setError(null);
                } else {
                    username.setError(null);
                    pass.setError(null);
                }
                if (!(username.getText().toString().equals("") || pass.getText().toString().equals("")))
                    login();

            }
        });
    }

    private void login(){
        //Here we will handle the http request to insert user to mysql db
        //Creating a RestAdapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL) //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        routeAPI api = adapter.create(routeAPI.class);

        //Defining the method insertuser of our interface
        api.login(

                //Passing the values by getting it from editTexts
                username.getText().toString(),
                pass.getText().toString(),

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


                            if (output.equals("Successfully logged in")) {
                                Toast.makeText(getApplicationContext(),output+"",Toast.LENGTH_SHORT).show();
                                admin=username.getText().toString();
                                Intent i = new Intent(getApplicationContext(),Menu.class);
                                startActivity(i);
                        }

                            else {
                                wrong.setText("Wrong user name or password  ");
                                wrong.setError("");
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //If any error occured displaying the error as toast
                        Toast.makeText(Login.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

}
