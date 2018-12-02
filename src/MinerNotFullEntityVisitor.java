public class MinerNotFullEntityVisitor extends AllFalseEntityVisitor {

    @Override
    public Boolean visit(MinerNotFull minerNotFull) {
        return true;
    }

}
