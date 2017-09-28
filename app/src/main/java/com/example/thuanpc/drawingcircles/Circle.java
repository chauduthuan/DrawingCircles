package com.example.thuanpc.drawingcircles;

import android.graphics.Paint;

/**
 * Created by thuanPC on 2/18/2017.
 */

public class Circle {
    float x;
    float y;

    float deltaX;
    float deltaY;

    int width;
    int height;

    float radius;
    boolean isSelected;
    Paint paint;

    public Circle(){
        paint = new Paint();
        radius = 0;
    }

    public Circle(float x, float y, int color){
        this.x = x;
        this.y = y;
        paint = new Paint();
        paint.setColor(color);
    }

    public void setCenter(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setSelected(){
        isSelected = true;
    }
    public void setUnselected(){
        isSelected = false;
    }

    public void setVelocity(float deltaX, float deltaY){
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    public void setResolution(int width, int height){
        this.width = width;
        this.height = height;
    }
    public void calculateAndSetRadius(float x1, float y1){
        float distance = calculateDistance(x1,y1);
        radius = getMaxRadius(distance);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getRadius(){
        return radius;
    }

    public Paint getPaint(){
        return paint;
    }

    //need to fix
    private float getMaxRadius(float distance){
        float maxX = Math.min(x,width - x);
        float maxY = Math.min(y, height - y);
        float maxRadius = Math.min(maxX, maxY);
        return Math.min(distance, maxRadius);
    }

    public boolean containsPoint(float x1, float y1){
        float distance = calculateDistance(x1,y1);
        return distance < radius;
    }

    private float calculateDistance(float x1, float y1){
        float distance = (float) Math.sqrt((x1-x)*(x1-x) + (y1-y)*(y1-y));;
        return distance;
    }

    public boolean isSelected(){
        return  isSelected;
    }

    public void updateCenterAndVelocity(){
        float newX = x + deltaX;
        float newY = y + deltaY;
        setCenter(newX, newY);
        changeOnCollision();
    }

    private void changeOnCollision(){
        if(xIsOutOfBounds()) deltaX *= -1;
        if(yIsOutOfBounds()) deltaY *= -1;
    }

    private boolean xIsOutOfBounds(){
        if (x - radius < 0) return true;
        if (x + radius > width) return true;
        return false;
    }

    private boolean yIsOutOfBounds(){
        if (y - radius < 0) return true;
        if (y + radius > height) return true;
        return false;
    }


}
