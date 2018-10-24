package engine;

import java.io.Serializable;

public class Direction implements Serializable{
    private final int goDown = 1;
    private final int goUp = -1;
    private final int goLeft = -1;
    private final int goRight = 1;
    private final int stayCool = 0;

    protected int firstX;
    protected int firstY;
    protected int secondX;
    protected int secondY;

    public void setFirstX(int firstX) {
        this.firstX = firstX;
    }

    public void setFirstY(int firstY) {
        this.firstY = firstY;
    }

    public void setSecondX(int secondX) {
        this.secondX = secondX;
    }

    public void setSecondY(int secondY) {
        this.secondY = secondY;
    }

    public int getFirstX() {
        return firstX;
    }

    public int getFirstY() {
        return firstY;
    }

    public int getSecondX() {
        return secondX;
    }

    public int getSecondY() {
        return secondY;
    }

    public class Horizontal extends Direction{
        public Horizontal()
        {
            this.firstX = stayCool;
            this.firstY = goLeft;
            this.secondX = stayCool;
            this.secondY = goRight;

        }
    }
    public class Vertical extends Direction {
        public Vertical() {
            firstX = goDown;
            firstY = stayCool;
            secondX = goUp;
            secondY = stayCool;
        }
    }
    public class FirstObliqueLineStartDownLeft extends Direction {
        public FirstObliqueLineStartDownLeft(){
            firstX = goDown;
            firstY = goLeft;
            secondX = goUp;
            secondY = goRight;
        }
    }
    public class SecondObliqueLineStartDownRight extends Direction{
        public SecondObliqueLineStartDownRight(){
            firstX = goDown;
            firstY = goRight;
            secondX = goUp;
            secondY = goLeft;

        }
    }

}

