import java.util.*;

public class Dijkstra {
    public int distance(Node[] nodes, 
                                int s, 
                                int t,
                                BinaryNodeHeap h) {
        while(h.size > 0){
            Node u = h.getMin();
            for(int i=0; i<u.adj.size();i++){
                Node endNode = nodes[u.adj.get(i)];
                if(endNode.dist>u.dist + u.cost.get(i)){
                    endNode.dist = u.dist + u.cost.get(i);
                    endNode.prev = u.index;
                    h.siftUp(endNode.index);
                    h.add(endNode);
                    h.siftDown(h.size);
                }
            }
        }
        if(nodes[t].dist<Integer.MAX_VALUE)
            return nodes[t].dist;
        else    
            return -1;
    }

    private int distanceNaive(Node[] nodes, int s, int t){
        boolean distChanged;
        do{
            distChanged = false;
            for(Node u:nodes){
                if(u.dist<Integer.MAX_VALUE){
                    for(int i=0; i<u.adj.size();i++){
                        Node endNode = nodes[u.adj.get(i)];
                        if(endNode.dist>u.dist + u.cost.get(i)){
                            endNode.dist = u.dist + u.cost.get(i);
                            endNode.prev = u.index;
                            distChanged = true;
                        }
                    }
                }
            }
        } while(distChanged);
        if(nodes[t].dist<Integer.MAX_VALUE)
            return nodes[t].dist;
        else    
            return -1;
    }
    
    public static void main(String[] args) {
        //testBinaryHeap();
        naiveRun();
        //normalRun();
    }
    
    private static void naiveRun(){
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        Node[] nodes = new Node[n];
        for(int nd = 0; nd < n; nd++)
            nodes[nd] = new Node(nd);
        for (int i = 0; i < m; i++) {
            int x, y, w;
            x = scanner.nextInt();
            y = scanner.nextInt();
            w = scanner.nextInt();
            nodes[x - 1].adj.add(y - 1);
            nodes[x - 1].cost.add(w);
        }
        int x = scanner.nextInt() - 1;
        int y = scanner.nextInt() - 1;
        nodes[x].dist = 0;
        System.out.println(new Dijkstra().distanceNaive(nodes, x, y));


    }
    
    private static void normalRun(){
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        Node[] nodes = new Node[n];
        for(int nd = 0; nd < n; nd++)
            nodes[nd] = new Node(nd);
        for (int i = 0; i < m; i++) {
            int x, y, w;
            x = scanner.nextInt();
            y = scanner.nextInt();
            w = scanner.nextInt();
            nodes[x - 1].adj.add(y - 1);
            nodes[x - 1].cost.add(w);
        }
        int x = scanner.nextInt() - 1;
        int y = scanner.nextInt() - 1;
        nodes[x].dist = 0;
        BinaryNodeHeap h = new BinaryNodeHeap(n);
        h.add(nodes[x]);
        System.out.println(new Dijkstra().distance(nodes, x, y, h));

    }
    
    
    private static class Node{
        ArrayList<Integer> adj;
        ArrayList<Integer> cost;
        int dist = Integer.MAX_VALUE;
        Integer prev = null;
        int index;
        public Node(int index){
            this.index = index;
            adj = new ArrayList<>();
            cost = new ArrayList<>();
        }
    }
    
    private static class BinaryNodeHeap{
        private Node[] heap;
        public int size = 0;
        public BinaryNodeHeap(int maxSize){
            heap = new Node[maxSize + 1];
            for(int i=0; i<maxSize+1;i++)
                heap[i] = new Node(0);
        }
        
        public void add(Node i){
            heap[size] = i;
            siftUp(size);
            size++;
        }
        
        public Node getMin(){
            Node min = heap[0];
            heap[0] = heap[size];
            heap[size] = new Node(0);
            siftDown(0);
            size--;
            return min;
        }
        
        public Node peek(){
            return heap[0];
        }
        
        private int getParentIndex(int i){
            if(i==0)
                return i;
            return (i+1)/2 - 1;
        }
        
        private int[] getChildrenIndices(int index){
            int[] indices = new int[2];
            for(int i=0; i< indices.length; i++){
                indices[i] = (index + 1) * 2 + (-1 + i);
                if(indices[i]>=heap.length)
                    indices[i] = -1;
            }
            return indices;
        } 
        
        private void swapIndices(int a, int b){
            Node temp = heap[a];
            heap[a] = heap[b];
            heap[b] = temp;
        }
        
        public void siftUp(int index){
            
            int parent = getParentIndex(index);
            while(index>0 && heap[parent].dist>heap[index].dist){
                swapIndices(parent, index);
                index = parent;
                parent = getParentIndex(index);
            }
        }
        
        public void siftDown(int index){
            int maxIndex = index;
            int[] children = getChildrenIndices(index);
            for(int child:children){
                try{
                if(child!=-1 && heap[child].dist<heap[maxIndex].dist)
                    maxIndex = child;
                } catch (IndexOutOfBoundsException err){
                    System.out.println();
                }
            }
            if(index!=maxIndex){
                swapIndices(index, maxIndex);
                siftDown(maxIndex);
            }
        }
    }
    
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
    private static class BinaryHeap{
        private int[] heap;
        public int size = 0;
        public BinaryHeap(int maxSize){
            heap = new int[maxSize + 1];
            for(int n = 0; n<maxSize + 1; n++)
                heap[n]=Integer.MAX_VALUE;
        }
        
        public void add(int i){
            heap[size] = i;
            siftUp(size);
            size++;
        }
        
        public int getMin(){
            int min = heap[0];
            heap[0] = heap[size];
            heap[size] = Integer.MAX_VALUE;
            siftDown(0);
            size--;
            return min;
        }
        
        public int peek(){
            return heap[0];
        }
        
        private int getParentIndex(int i){
            if(i==0)
                return i;
            return (i+1)/2 - 1;
        }
        
        private int[] getChildrenIndices(int index){
            int[] indices = new int[2];
            for(int i=0; i< indices.length; i++){
                indices[i] = (index + 1) * 2 + (-1 + i);
                if(indices[i]>=heap.length)
                    indices[i] = -1;
            }
            return indices;
        } 
        
        private void swapIndices(int a, int b){
            int temp = heap[a];
            heap[a] = heap[b];
            heap[b] = temp;
        }
        
        private void siftUp(int index){
            int parent = getParentIndex(index);
            while(index>0 && heap[parent]>heap[index]){
                swapIndices(parent, index);
                index = parent;
                parent = getParentIndex(index);
            }
        }
        
        private void siftDown(int index){
            int maxIndex = index;
            int[] children = getChildrenIndices(index);
            for(int child:children){
                try{
                if(child!=-1 && heap[child]<heap[maxIndex])
                    maxIndex = child;
                } catch (IndexOutOfBoundsException err){
                    System.out.println();
                }
            }
            if(index!=maxIndex){
                swapIndices(index, maxIndex);
                siftDown(maxIndex);
            }
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /*
        private static void testBinaryHeap(){
        Random rnd = new Random();
        ArrayList<Integer> naive;
        
        BinaryHeap heap;
        int trials = 1000;
        for(int i=0;i<trials;i++){
            System.out.println("Trial " + i);
            int size = rnd.nextInt(1000) + 2;
            heap = new BinaryHeap(size);
            naive = new ArrayList<>();
            for(int j=0; j<size; j++){
                int num = rnd.nextInt(Integer.MAX_VALUE - 1);
                naive.add(num);
                heap.add(num);
                if(Collections.min(naive)!=heap.peek()){
                    System.out.printf("Error adding on line %d", i);
                }
                int x = 0;
            }
            ArrayList<Integer> dup = new ArrayList<>(naive);
            int[] naiveReturn = new int[size];
            int[] heapReturn = new int[size];
            for(int j=0; j<size; j++){
                naiveReturn[j] = Collections.min(naive);
                int naiveLoc = naive.indexOf(naiveReturn[j]);
                naive.remove(naiveLoc);
                heapReturn[j] = heap.getMin();
                if(naiveReturn[j] != heapReturn[j]){
                    System.out.println();
                    System.out.printf("Didn't get minimum value. Error in list ad index %d:", j);
                    for(int k = 0; k < size; k++){
                        System.out.printf("%d ", dup.get(k));
                    }
                    System.out.println();
                    System.out.printf("Min value was %d returned %d", naiveReturn[j], heapReturn[j]);
                    System.out.println();
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
        */
        
    }



