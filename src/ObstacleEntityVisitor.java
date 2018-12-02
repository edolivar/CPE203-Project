public class ObstacleEntityVisitor extends AllFalseEntityVisitor {

    @Override
    public Boolean visit(Obstacle obstacle) {
        return true;
    }

}