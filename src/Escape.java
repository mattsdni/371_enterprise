import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by Matt on 10/17/2016.
 */
public class Escape
{
    /**
     * Loads file into graph data structures
     * Runtime: O(n), where n = x * y.  (not counting call to findPath)
     * @param filename file name
     */
    public void initialize(String filename)
    {
        try
        {
            int case_num = 1;
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            int cases = Integer.parseInt(bufferedReader.readLine().trim());
            for (int i = 0; i < cases; i++)
            {
                String[] line = bufferedReader.readLine().trim().split("\\s+");
                int classes = Integer.parseInt(line[0]);
                int width = Integer.parseInt(line[1]);
                int height = Integer.parseInt(line[2]);
                int[][] graph_matrix = new int[width][height];
                int start_x = -1;
                int start_y = -1;
                HashMap<String, Integer> class_duration = new HashMap<>();
                for (int c = 0; c < classes; c++)
                {
                    String[] line2 = bufferedReader.readLine().trim().split("\\s+");
                    class_duration.put(line2[0], Integer.parseInt(line2[1]));
                }

                for (int y = 0; y < height; y++)
                {
                    String[] line3 = bufferedReader.readLine().trim().split("");
                    for (int x = 0; x < width; x++)
                    {
                        if (line3[x].compareTo("E") == 0)
                        {
                            graph_matrix[x][y] = 0;
                            start_x = x;
                            start_y = y;
                        }
                        else
                        {
                            graph_matrix[x][y] = class_duration.get(line3[x]);
                        }
                    }
                }
                case_num++;
                findPath(graph_matrix, new Node(start_x, start_y, 0));
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Computes the fastest path out of the ships
     * Runtime:
     * @param graph a graph of ships
     * @param enterprise the node where the enterprise is
     */
    private void findPath(int[][] graph, Node enterprise)
    {
        PriorityQueue<Node> q = new PriorityQueue<>();
        HashSet<Node> s = new HashSet<>();
        boolean[][] visited = new boolean[graph.length][graph[0].length];

        Node[][] node_graph = new Node[graph.length][graph[0].length];

        for (int y = 0; y < graph[0].length; y++)
            for (int x = 0; x < graph.length; x++)
                node_graph[x][y] = new  Node(x, y, graph[x][y]);


        // add the enterprise
        enterprise.d = 0;
        q.add(enterprise);

        while (!q.isEmpty())
        {
            Node u = q.remove();
            if (u.x == 0 || u.x == graph.length-1 || u.y == 0 || u.y == graph[0].length-1)
            {
                System.out.println(u.d);
                return;
            }
            if (visited[u.x][u.y])
                continue;
            visited[u.x][u.y] = true;
            s.add(u);

            // 'relax' (check neighbors)
            List<Node> adjacentNodes = new LinkedList<>();
            adjacentNodes.add(node_graph[u.x][u.y-1]);
            adjacentNodes.add(node_graph[u.x][u.y+1]);
            adjacentNodes.add(node_graph[u.x+1][u.y]);
            adjacentNodes.add(node_graph[u.x-1][u.y]);

            for (Node v : adjacentNodes)
                if (!s.contains(v))
                    if (v.d > u.d + v.w)
                    {
                        v.d = u.d + v.w;
                        q.add(v);
                    }
        }
    }

    private class Node implements Comparable
    {
        int x,y,w,d;
        Node p;
        Node(int x, int y, int w)
        {
            this.x = x;
            this.y = y;
            this.w = w;
            this.p = null;
            this.d = Integer.MAX_VALUE;
        }

        @Override
        public int compareTo(Object o)
        {
            if (this.d > ((Node)o).d)
                return 1;
            if (this.d < ((Node)o).d)
                return -1;
            return 0;
        }

        @Override
        public boolean equals(Object o)
        {
            Node n = (Node) o;
            return this.x == n.x && this.y == n.y && this.w == n.w;
        }

        public String toString()
        {
            return "x: " + this.x + " y: " + this.y + " distance: " + this.d;
        }
    }
}
