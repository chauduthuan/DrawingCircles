package com.example.thuanpc.drawingcircles;

import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity{

    final int DEFAULT_COLOR = Color.BLACK;
    final int BLACK = Color.BLACK;
    final int RED = Color.RED;
    final int GREEN = Color.GREEN;
    final int BLUE = Color.BLUE;
    final int ACTION_BAR_HEIGHT =150;

    CircleView circleView;
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Display display = getWindowManager().getDefaultDisplay();
        Point screenSize = new Point();
        display.getSize(screenSize);
        int screenHeight = screenSize.y;
        int screenWidth = screenSize.x;

        circleView = new CircleView(this);
        circleView.setDrawMode();
        circleView.setColor(DEFAULT_COLOR);
        circleView.setResolution(screenWidth, screenHeight - ACTION_BAR_HEIGHT);
        setContentView(circleView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_mode,menu);
        this.menu = menu;
        menu.findItem(R.id.menu_draw).setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_draw:
                circleView.setDrawMode();
                circleView.setColor(DEFAULT_COLOR);
                break;

            case R.id.menu_draw_black:
                circleView.setColor(BLACK);
                break;
            case R.id.menu_draw_red:
                circleView.setColor(RED);
                break;
            case R.id.menu_draw_green:
                circleView.setColor(GREEN);
                break;
            case R.id.menu_draw_blue:
                circleView.setColor(BLUE);
                break;

            case R.id.menu_moving:
                circleView.setMovingMode();
                break;

            case R.id.menu_delete:
                circleView.setDeleteMode();
                break;
        }
        displayMenuItem(item);
        return true;
    }

    private void displayMenuItem(MenuItem item){
        menu.findItem(R.id.menu_draw).setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_NEVER);
        menu.findItem(R.id.menu_moving).setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_NEVER);
        menu.findItem(R.id.menu_delete).setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_NEVER);
        switch (item.getItemId()){
            case R.id.menu_draw:
            case R.id.menu_draw_black:
            case R.id.menu_draw_red:
            case R.id.menu_draw_green:
            case R.id.menu_draw_blue:
                menu.findItem(R.id.menu_draw).setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
                break;
            case R.id.menu_moving:
            case R.id.menu_delete:
                item.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
                break;
        }
    }

}
