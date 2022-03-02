import java.util.ArrayList;
import java.util.LinkedList;


public class Vertex implements Comparable<Vertex>{
    private Country country;
    private ArrayList<Edge> Adjacents;
    private boolean enqueued;
    private int heapIndex = -1;

    public int getHeapIndex() {
        return heapIndex;
    }

    public void setHeapIndex(int heapIndex) {
        this.heapIndex = heapIndex;
    }

    public boolean isEnqueued() {
        return enqueued;
    }

    public void setEnqueued(boolean enqueued) {
        this.enqueued = enqueued;
    }

    public boolean isKnown() {
        return known;
    }

    public void setKnown(boolean known) {
        this.known = known;
    }

    public Vertex getPrev() {
        return prev;
    }

    public void setPrev(Vertex prev) {
        this.prev = prev;
    }

    private boolean known;
    private Vertex prev;


    private double dis;

    public double getDis() {
        return dis;
    }

    public void setDis(double dis) {
        this.dis = dis;
    }

    public Vertex(Country country){
        this.country = country;
        Adjacents = new ArrayList<>();
    }

    public void addAdjacent(Vertex vertex, double distance) {
        Adjacents.add(new Edge(vertex,distance));
    }



    public Country getCountry(){return country;}
    public ArrayList<Edge> getAdjacents(){return Adjacents;}



    public void setCountry(Country country) {this.country = country;}
    public void setAdjacents(ArrayList<Edge> Adjacents){this.Adjacents = Adjacents;}


    @Override
    public int compareTo(Vertex o) {
        if (this.dis>o.getDis())
            return 1;
        else if (this.dis==o.getDis())
            return 0;
        else
            return -1;

    }
}