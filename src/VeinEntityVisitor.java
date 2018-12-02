public class VeinEntityVisitor extends AllFalseEntityVisitor {

    @Override
    public Boolean visit(Vein vein) {
        return true;
    }

}