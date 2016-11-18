public abstract class GameObject {
    protected Point pt;

    public GameObject(Point pt) {
        this.pt = pt;
    }

    public abstract char getSymbol();
    public void doTerm(Map map) {}

    public Point getPoint() {
        return pt;
    }

    public int getX() {
        return pt.getX();
    }

    public int getY() {
        return pt.getY();
    }
}
