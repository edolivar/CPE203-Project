import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import java.util.Arrays;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Comparator;

import org.junit.Test;

public class TestCases
{
    @Test
    public void testContains() {
        List<Point> openList = new LinkedList<>();
        List<Point> closedList = new LinkedList<>();
        //Point testPoint = new Point(1, 1);
        openList.add(new Point(1, 1));
        closedList.add(openList.get(0));
        openList.remove(0);
        assertTrue(closedList.contains(new Point(1, 1)));
    }

    @Test
    public void testContains2() {
        List<Point> openList = new LinkedList<>();
        List<Point> closedList = new LinkedList<>();
        Point testPoint = new Point(1, 1);
        openList.add(new Point(1, 1));
        openList.add(new Point(2, 1));
        openList.add(new Point(3, 1));
        closedList.add(new Point(1, 1));
        closedList.add(new Point(2, 1));

        List<Point> validPoints = openList.stream()
                .filter(point -> !closedList.contains(point))
                .collect(Collectors.toList());

        assertTrue(validPoints.contains(new Point(3, 1)));
        assertFalse(validPoints.contains(new Point(2, 2)));
    }

    public void testNode() {

        /*
        List<Node> openList = new LinkedList<>();
        List<Node> closedList = new LinkedList<>();
        Point startPos = new Point(1, 1);
        Node start = new Node(null, startPos);
        Node another = new Node(null, new Point(2, 2));
        another.setF(99);

        openList.add(another);
        openList.add(start);
        assertTrue(openList.get(0).equals(new Node(null, new Point(2, 2))));
        assertTrue(openList.get(1).equals(new Node(null, new Point(1, 1))));
        assertTrue(openList.get(0).getF() == 99);
        assertTrue(openList.get(1).getF() == 0);
        openList = openList.stream()
                .sorted(Comparator.comparing(Node::getF))
                .collect(Collectors.toList());

        assertTrue(openList.get(0).getF() == 0);
        assertTrue(openList.get(1).getF() == 99);
        assertTrue(openList.get(1).equals(new Node(null, new Point(2, 2))));
        //assertTrue(openList.contains(new Node(null, new Point(2, 2))));

        */


    }

}
