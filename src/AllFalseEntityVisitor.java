public class AllFalseEntityVisitor implements EntityVisitor<Boolean> {

    public Boolean visit(Blacksmith blacksmith) {
        return false;
    }

    public Boolean visit(MinerFull minerFull) {
        return false;
    }

    public Boolean visit(MinerNotFull minerNotFull) {
        return false;
    }

    public Boolean visit(Obstacle obstacle) {
        return false;
    }

    public Boolean visit(Ore ore) {
        return false;
    }

    public Boolean visit(OreBlob oreBlob) {
        return false;
    }

    public Boolean visit(Quake quake) {
        return false;
    }

    public Boolean visit(Vein vein) {
        return false;
    }

    public Boolean visit(ShadowMage shadowMage) { return false; }

    public Boolean visit(ShadowMiner shadowMiner) { return false;
    }

}
