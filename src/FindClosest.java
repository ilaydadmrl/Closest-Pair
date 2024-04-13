import java.awt.geom.Point2D;

public class FindClosest {

    private PointPair closestPointPair;
    private final QuickSort quicksort = new QuickSort();

    /** Constructor
     *
     * @param points --> point array
     */
    public FindClosest(Point2D.Double[] points)
    {
        //Sort points by X coordinate
        quicksort.sort(points, 0, points.length - 1, "compareX");
        this.closestPointPair = calculateClosestPointPair(points, 0, points.length - 1);
        //*********************************do nothing***************************************//
    }

    /** Get closest Point Pair
     *
     * @return closestPointPair
     */
    public PointPair getClosestPointPair()
    {
        return this.closestPointPair;
    }

    /** Main method for calculate and return closest point pair
     *
     * @param p --> point array
     * @param startIndex --> First index of p[]
     * @param lastIndex --> last index of p[]
     * @return
     */
    private PointPair calculateClosestPointPair(Point2D.Double[] p, int startIndex, int lastIndex) {

        PointPair pointPair;

        if ((lastIndex - startIndex) < 2) {

            Point2D.Double p1 = new Point2D.Double(Double.MAX_VALUE, Double.MAX_VALUE);
            Point2D.Double p2 = new Point2D.Double(0, 0);
            pointPair = new PointPair(p1, p2);

            for ( int i = 0 ; i < lastIndex ; ++i ) {
                PointPair min = new PointPair(p[i], p[i + 1]);
                if (min.getDistance() < pointPair.getDistance()) {
                    pointPair = min;
                }
            }
            return pointPair;
        }
        else if (lastIndex - startIndex == 2) {
            return getClosestPointPair(p[startIndex], p[startIndex + 1], p[lastIndex]);
        }

        int mid = ((lastIndex + startIndex) / 2);
        PointPair left = calculateClosestPointPair(p, startIndex, mid);
        PointPair right = calculateClosestPointPair(p, mid + 1, lastIndex);
        double distance;

        if (left.getDistance() < right.getDistance()) {
            distance = left.getDistance();
            pointPair = left;
        } else {
            distance = right.getDistance();
            pointPair = right;
        }

        Point2D.Double[] strip = new Point2D.Double[lastIndex + 1];
        int j = 0;

        for ( int i = startIndex ; i <= lastIndex ; i++ ) {
            if (Math.abs(p[i].x - p[mid].x) < distance) {
                strip[j] = p[i];
                j++;
            }
        }
        if (stripClosest(strip, j, pointPair).getDistance() < distance) {
            pointPair = stripClosest(strip, j, pointPair);
        }
        return pointPair;

    }

    /** calculate and return closest point pair from 3 points
     *
     * @param p1 --> point 1
     * @param p2 --> point 2
     * @param p3 --> point 3
     * @return
     */

    private PointPair getClosestPointPair(Point2D.Double p1, Point2D.Double p2, Point2D.Double p3) {

        PointPair pair1;
        pair1 = new PointPair(p1, p2);
        PointPair pair2 = new PointPair(p1, p3);
        PointPair pair3 = new PointPair(p2, p3);

        PointPair closestPair = getClosestPointPair(pair1, pair2);
        closestPair = getClosestPointPair(closestPair, pair3);
        return closestPair;

    }

    private PointPair getClosestPointPair(PointPair p1, PointPair p2){

        if ( p1.getDistance() < p2.getDistance() )
            return p1;
        return p2;

    }

    /**
     * A utility function to find the distance between the closest points of
     * strip of given size. All points in strip[] are sorted according to
     * y coordinate. They all have an upper bound on minimum distance as d.
     * Note that this method seems to be a O(n^2) method, but it's a O(n)
     * method as the inner loop runs at most 6 times
     *
     * @param strip --> point array
     * @param size --> strip array element count
     * @param shortestLine --> shortest line calculated so far
     * @return --> new shortest line
     */
    private PointPair stripClosest(Point2D.Double strip[], int size, PointPair shortestLine) {

        quicksort.sort(strip, 0, size-1, "compareY");

        for ( int i = 0 ; i < size ; ++i ) {
            for ( int j = i + 1 ; j < size && (strip[j].y - strip[i].y) < shortestLine.getDistance() ; ++j ) {
                PointPair shortest = new PointPair(strip[i], strip[j]);
                if ( shortest.getDistance() < shortestLine.getDistance() )
                    shortestLine = shortest;
            }
        }
        return shortestLine;

    }

}