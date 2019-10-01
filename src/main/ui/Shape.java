package ui;

import java.awt.*;

//shamelessly copied from design-patterns-lecture-starters/Observer-SimpleDrawingPlayerSolution
public class Shape {

    private int coordinateX;
    private int coordinateY;
    private int width;
    private int height;

    // REQUIRES: w, h >= 0
    // EFFECTS: constructs Shape at the given x,coordinateY-coordinates, with width w and height h
    public Shape(int x, int y, int w, int h) {
        this.coordinateX = x;
        this.coordinateY = y;
        width = w;
        height = h;
    }

    // getters
    public int getWidth()  {
        return width;
    }

    public int getXCoord() {
        return coordinateX;
    }

    public int getYCoord() {
        return coordinateY;
    }

    public int getHeight() {
        return height;
    }

    // EFFECTS: return true iff the given x value is within the bounds of the Shape
    public boolean containsX(int x) {
        return (this.coordinateX <= x) && (x <= this.coordinateX + width);
    }

    // EFFECTS: return true iff the given coordinateY value is within the bounds of the Shape
    public boolean containsY(int y) {
        return (this.coordinateY <= y) && (y <= this.coordinateY + height);
    }

    // EFFECTS: return true if the given Point (x,coordinateY) is contained within the bounds of this Shape
    public boolean contains(Point point) {
        int pointX = point.x;
        int pointY = point.y;

        return containsX(pointX) && containsY(pointY);
    }

    // REQUIRES: the x-coordinate must be >= this.x and the coordinateY-coordinate must be >= this.coordinateY
    // MODIFIES: this
    // EFFECTS:  sets the bottom left corner of this Shape to the given Point
    public void setBounds(Point bottomRight) {
        width  = bottomRight.x - coordinateX;
        height = bottomRight.y - coordinateY;
    }

    // EFFECTS: draws this Shape on the MusicAnnotator, if the shape is selected, Shape is filled in
    //          else, Shape is unfilled (white)
    public void draw(Graphics g) {
        Color save = g.getColor();
//        if (selected) {
//            g.setColor(SHADOW_COLOR);
//        } else {
//            g.setColor(Color.white);
//        }
        g.setColor(Color.white);

        g.fillRect(coordinateX, coordinateY, width, height);
        g.setColor(save);
        g.drawRect(coordinateX, coordinateY, width, height);
    }
}

