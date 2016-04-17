package bahomaid.sara.routeitadmin;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {
    Button enter , delete ,post;
    String adminId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);



        enter=(Button)findViewById(R.id.button2);
        delete=(Button)findViewById(R.id.button3);
        post=(Button)findViewById(R.id.button4);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // adminId= getIntent().getExtras().getString("AdminID");
                Intent i =new Intent(getApplicationContext(),Enter.class);
               // i.putExtra("ID", adminId);
                startActivity(i);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Delete.class);
                startActivity(i);
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), NewPost.class);
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
            finish(); }
        if (item.getItemId() == R.id.logout) {

            new AlertDialog.Builder(Menu.this)
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

