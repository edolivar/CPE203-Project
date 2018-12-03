import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.LinkedList;
import java.util.Comparator;

public class AstarPathingStrategy implements PathingStrategy {

    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors) {


        List<Node> openList = new LinkedList<>();
        List<Node> closedList = new LinkedList<>();
        List<Point> path = new LinkedList<>();

        Node currentNode = new Node(null, 0, start.distanceSquared(end), start);
        openList.add(currentNode);

        while (!withinReach.test(currentNode.getPosition(), end)) {

            List<Point> adjacentPositions = potentialNeighbors.apply(currentNode.getPosition())
                    .filter(canPassThrough)
                    .collect(Collectors.toList());

            List<Node> children = new LinkedList<>();

            for (Point pos : adjacentPositions) {
                Node newChildNode = new Node(currentNode, currentNode.getG() + 1, pos.distanceSquared(end), pos);
                boolean inClosedList = false;
                for (Node closedNode : closedList) {
                    if (closedNode.equals(newChildNode)) {
                        inClosedList = true;
                    }
                }
                if (!inClosedList) {
                    children.add(newChildNode);
                }
            }

            for (Node child : children) {
                boolean inOpenList = false;
                for (Node openNode : openList) {
                    if (openNode.equals(child)) {
                        inOpenList = true;
                    }
                }
                if (!inOpenList) {
                    openList.add(child);
                }
                for (Node openNode : openList) {
                    if (openNode.getG() > child.getG() && openNode.getPosition().equals(child.getPosition())) {
                        openNode.setG(child.getG());
                        openNode.setH(child.getH());
                        openNode.setF(child.getG() + child.getH());
                        openNode.setParent(child.getParent());
                    }
                }
            }
            closedList.add(currentNode);
            openList.remove(currentNode);

            if (!openList.isEmpty()) {
                openList = openList.stream()
                        //f = g + h, where g is distance between current node and start node and
                        //h is the distance from current node to end node
                        //Comparator.comparingDouble((point) -> point.distanceSquared(start) + point.distanceSquared(end))
                        .sorted(Comparator.comparing(Node::getF))
                        .collect(Collectors.toList());

                currentNode = openList.get(0);
            }
            else {
                return path;
            }
        }
        while (currentNode.getParent() != null) {
            path.add(0, currentNode.getPosition());
            currentNode = currentNode.getParent();
        }

        path = path.stream()
                .limit(1)
                .collect(Collectors.toList());
        return path;
    }
}


//            /*
//            openList = openList.stream()
//                    //f = g + h, where g is distance between current node and start node and
//                    //h is the distance from current node to end node
//                    //Comparator.comparingDouble((point) -> point.distanceSquared(start) + point.distanceSquared(end))
//                    .sorted(Comparator.comparing(Node::getF))
//                    .collect(Collectors.toList());
//                    */
//
////            for (Node n : openList) {
////                double d = n.getPosition().distanceSquared(end);
////                double somethinElse = d;
////            }
//
//            //currentNode = openList.get(0);
//            double distance = lowestFCost.getPosition().distanceSquared(end);
//            closedList.add(lowestFCost);
//            openList.remove(0);
//
//            if (withinReach.test(lowestFCost.getPosition(), end)) {
//                Node current = lowestFCost;
//                while (current != null) {
//                    path.add(current.getPosition());
//                    current = current.getParent();
//                }
//                long count = path.stream().count();
//                path = path.stream()
//                        .skip(count - 2)
//                        .limit(1)
//                        .collect(Collectors.toList());
//                return path;
//
//            }
//            else {
//                List<Point> adjacentPositions = potentialNeighbors.apply(lowestFCost.getPosition())
//                        .filter(canPassThrough)
//                        /*
//                        .filter(pt ->
//                                !pt.equals(start)
//                                        && !pt.equals(end)
//                                        && Math.abs(end.x - pt.x) <= Math.abs(end.x - start.x)
//                                        && Math.abs(end.y - pt.y) <= Math.abs(end.y - start.y))
//                                        */
//                        .collect(Collectors.toList());
//
//                List<Node> children = new LinkedList<>();
//
//                for (Point pos : adjacentPositions) {
//                    children.add(new Node(lowestFCost, pos));
//                }
//
//                for (Node child : children) {
//                    boolean inClosedList = false;
//
//                    for (Node closedChild : closedList) {
//                        if (child.equals(closedChild)) {
//                            inClosedList = true;
//                        }
//                    }
//
//                    if (!inClosedList) {
//                        double distanceToEnd = child.getPosition().distanceSquared(end);
//                        double gCost = lowestFCost.getG() + 1;
//                        child.setG(lowestFCost.getG() + 1);
//                        child.setH(distanceToEnd);
//                        child.setF(child.getG() + child.getH());
//
//                        for (Node openNode : openList) {
//                            if (child.equals(openNode) && child.getG() > openNode.getG()) {
//                                continue;
//                            }
//                        }
//
//                        openList.add(child);
//                    }
//                }
//            }
//
//        }
//
//        List<Point> emptyList = new LinkedList<>();
//        return emptyList;
//    }

//}
