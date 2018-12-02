public class OreBlobEntityVisitor extends AllFalseEntityVisitor {

    @Override
    public Boolean visit(OreBlob oreBlob) {
        return true;
    }

}