import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.HashMap;

public class Graph {
    public HashMap<String,Vertex> Vertices = new HashMap<>();
    public Graph() {

    }

    public void insertVertex(Vertex vertex) {
        Vertices.put(vertex.getCountry().getCountryName(),vertex);

    }

    public void addAdjacent(String source, String adjacent, double distance) {
        if (Vertices.get(source)==null || Vertices.get(adjacent)==null)
            return;
        Vertices.get(source).getAdjacents().add(new Edge(Vertices.get(adjacent),distance));
    }
    public void findShortestPath(String source, String dis) {
        PriorityQueue queue = new PriorityQueue(Vertices.size());
        for (String i : Vertices.keySet()) {
            Vertices.get(i).setKnown(false);
          Vertices.get(i).setDis(Double.MAX_VALUE);
          Vertices.get(i).setHeapIndex(-1);
          Vertices.get(i).setPrev(null);
        }

        Vertices.get(source).setDis(0.0);
queue.add(Vertices.get(source));
        while(!queue.isEmpty()) {

            Vertex v = queue.poll(); // delete min
            if (v==Vertices.get(dis))
                break;

            if(!v.isKnown()) {
              modifyAdjacents(v,queue);
            }
            v.setKnown(true);

        }

    }
    private void modifyAdjacents(Vertex v, PriorityQueue queue){

        for (int i = 0; i < v.getAdjacents().size(); i++) {
            if (!v.getAdjacents().get(i).getVertex().isKnown()) {
                if (v.getAdjacents().get(i).getDistance() + v.getDis() < v.getAdjacents().get(i).getVertex().getDis()) {
                    v.getAdjacents().get(i).getVertex().setPrev(v);
                    v.getAdjacents().get(i).getVertex().setDis(v.getAdjacents().get(i).getDistance() + v.getDis());
                    if (v.getAdjacents().get(i).getVertex().getHeapIndex()==-1)
                        queue.add(v.getAdjacents().get(i).getVertex());
                    else
                        queue.modifyPosition(v.getAdjacents().get(i).getVertex());


                }
            }
        }
    }



    public void fill(TextArea path,String value) {
        Vertex v = Vertices.get(value);
        if (v.getPrev()==null && !v.isKnown()) {
            path.setText("There is no path between these two countries.");
            return;
        }
        else if (v.getPrev()==null && v.isKnown()) {
            path.setText("0 km\n");
            path.setText(path.getText()+"Same Country Chosen.\n");
            return;
        }

        double dis = (((int)(v.getDis()*100))/100.0);
        path.appendText("Distance: "+dis+"\n");
        fillDP(Vertices.get(value),path);
    }

    private void fillDP(Vertex v, TextArea path) {
        if (v!=null) {
            fillDP(v.getPrev(),path);
            double dis = (((int)(v.getDis()*100))/100.0);

            path.appendText(v.getCountry().getCountryName()+" -> "+dis+"\n");
        }
    }
}
