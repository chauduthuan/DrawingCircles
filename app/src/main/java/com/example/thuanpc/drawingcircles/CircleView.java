package com.example.thuanpc.drawingcircles;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by thuanPC on 2/18/2017.
 */

public class CircleView extends View implements View.OnTouchListener{

    final int TIME_INTERVAL = 20;
    final int DRAW = 0;
    final int MOVING = 1;
    final int DELETE = 2;

    int height;
    int color;
    int mode;
    int width;

    ArrayList<Circle> circles;
    VelocityTracker velocity;

    public CircleView(Context context) {
        super(context);
        setOnTouchListener(this);
        circles = new ArrayList<>();
        mode = DRAW;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Circle circle : circles) {
            if (mode == MOVING){
                circle.updateCenterAndVelocity();
            }
            float x = circle.getX();
            float y = circle.getY();
            float radius = circle.getRadius();
            Paint paint = circle.getPaint();
            canvas.drawCircle(x, y, radius, paint);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(mode == DRAW) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    return handleDrawModeActionDown(event);
                case MotionEvent.ACTION_MOVE:
                    return handleDrawModeActionMoving(event);
                case MotionEvent.ACTION_UP:
                    return handleDrawModeActionUp(event);
            }
        }
        else if (mode == MOVING){
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    return handleMovingModeActionDown(event);
                case MotionEvent.ACTION_MOVE:
                    return handleMovingModeActionMove(event);
                case MotionEvent.ACTION_UP:
                    return handleMovingModeActionUp(event);
            }
        }
        else if (mode == DELETE){
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    return handleDeleteModeActionDown(event);
                case MotionEvent.ACTION_UP:
                    return handleDeleteModeActionUp(event);
            }
        }
        return false;
    }
    private boolean handleDrawModeActionDown(MotionEvent event){
        float x = event.getX();
        float y = event.getY();
        addNewCircle(x,y, color);
        return true;
    }
    private boolean handleDrawModeActionMoving(MotionEvent event){
        float x = event.getX();
        float y = event.getY();
        modifyRadiusOfNewCircle(x,y);
        return true;
    }
    private boolean handleDrawModeActionUp(MotionEvent event){
        float x = event.getX();
        float y = event.getY();
        modifyRadiusOfNewCircle(x,y);
        return true;
    }

    private boolean handleMovingModeActionDown(MotionEvent event){
        velocity = VelocityTracker.obtain();
        velocity.addMovement(event);
        float x = event.getX();
        float y = event.getY();
        for(Circle circle : circles){
            if(circle.containsPoint(x,y)){
                circle.setSelected();
            }
        }
        return true;
    }
    private boolean handleMovingModeActionMove(MotionEvent event){
        velocity.addMovement(event);
        return true;
    }
    private boolean handleMovingModeActionUp(MotionEvent event){
        velocity.computeCurrentVelocity(TIME_INTERVAL);
        float deltaX = velocity.getXVelocity();
        float deltaY = velocity.getYVelocity();
        for(Circle circle : circles){
            if (circle.isSelected()){
                circle.setVelocity(deltaX, deltaY);
                circle.setUnselected();
            }
        }
        velocity.recycle();
        velocity = null;
        return true;
    }

    private boolean handleDeleteModeActionDown(MotionEvent event){
        float x = event.getX();
        float y = event.getY();
        for(Circle circle : circles){
            if(circle.containsPoint(x,y)){
                circle.setSelected();
            }
        }
        return true;
    }
    private boolean handleDeleteModeActionUp(MotionEvent event){
        float x = event.getX();
        float y = event.getY();
        deleteCircle(x,y);
        for(Circle circle : circles){
            circle.setUnselected();
        }
        return true;
    }


    public void addNewCircle(float x, float y, int color){
        Circle newCircle = new Circle(x,y, color);
        newCircle.setResolution(width,height);
        circles.add(newCircle);
    }

    public void modifyRadiusOfNewCircle(float x, float y){
        Circle currentCircle = circles.get(circles.size() - 1);
        currentCircle.calculateAndSetRadius(x,y);
        invalidate();
    }

    public void deleteCircle(float x, float y){
        Circle currentCircle;
        for(int i = circles.size() -1; i >= 0; i--){
            currentCircle = circles.get(i);
            if(currentCircle.isSelected() && currentCircle.containsPoint(x,y)){
                circles.remove(i);
            }
        }
        invalidate();
    }

    public void setColor(int color){
        this.color = color;
    }

    public void setResolution(int width, int height){
        this.width = width;
        this.height = height;
    }

    public void setDrawMode(){
        mode = DRAW;
    }
    public void setMovingMode(){
        if (mode != MOVING){
            mode = MOVING;
            move();
        }
    }
    public void setDeleteMode(){
        mode = DELETE;
    }


    public void move(){
        invalidate();
        if (mode == MOVING){
            postDelayed(new Mover(), TIME_INTERVAL);
        }
    }

    class Mover implements Runnable{
        @Override
        public void run() {
            move();
        }
    }
}
