public class BlacksmithEntityVisitor extends AllFalseEntityVisitor {

    @Override
    public Boolean visit(Blacksmith blacksmith) {
        return true;
    }

}