public class OreEntityVisitor extends AllFalseEntityVisitor {

    @Override
    public Boolean visit(Ore ore) {
        return true;
    }

}