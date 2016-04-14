package bahomaid.sara.routeitadmin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class Enter extends AppCompatActivity {

    Button enter_bus_station , enter_metro_station;
    String adminId ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);






        enter_bus_station=(Button)findViewById(R.id.button6);
        enter_metro_station=(Button)findViewById(R.id.button7);



        enter_bus_station.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // adminId = getIntent().getExtras().getString("AdminID");
                Intent i = new Intent(getApplicationContext(), EnterBusStation.class);
                //i.putExtra("AdminID", adminId);
                startActivity(i);
            }
        });

        enter_metro_station.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   adminId = getIntent().getExtras().getString("AdminID");
                Intent i = new Intent(getApplicationContext(), EnterMetroStation.class);
             //   i.putExtra("AdminID", adminId);
                startActivity(i);
            }
        });


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
            startActivity(intent);
            finish();
        }
        if (item.getItemId() == R.id.logout) {

            new AlertDialog.Builder(Enter.this)
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
