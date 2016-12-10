package model;

public class Tool extends GameObject {
    private boolean isAlive = true;

    public Tool(Point pt) {
        super(pt);
    }

    @Override
    public char getSymbol() {
        return 'T';
    }

    @Override
    public boolean isAlive() {
        return isAlive;
    }

    @Override
    public void shootReaction(GameObject killer) {
        if (killer.getClass() == Character.class) {
            ((Character)killer).addTool(this);
            isAlive = false;
        }
    }
}
