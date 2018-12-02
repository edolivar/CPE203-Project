public class QuakeEntityVisitor extends AllFalseEntityVisitor {

    @Override
    public Boolean visit(Quake quake) {
        return true;
    }

}