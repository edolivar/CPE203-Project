public class Node {

    private Point position;
    private double f, g, h;
    private Node parent;

    public Node(Node parent, double g, double h, Point position) {
        this.parent = parent;
        this.position = position;
        this.g = g;
        this.h = h;
        this.f = g + h;
    }

    public Point getPosition() {
        return this.position;
    }

    public Node getParent() {
        return this.parent;
    }

    public void setParent(Node p) {
        this.parent = p;
    }

    public double getF() {
        return this.f;
    }

    public void setF(double value) {
        this.f = value;
    }

    public double getG() {
        return this.g;
    }

    public void setG(double value) {
        this.g = value;
    }

    public double getH() {
        return this.h;
    }

    public void setH(double value) {
        this.h = value;
    }

    public boolean equals(Node other) {
        if (other == null) {
            return false;
        }
        return this.position.x == other.getPosition().x && this.position.y == other.getPosition().y;
    }

}