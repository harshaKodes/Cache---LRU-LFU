import java.util.HashMap;

public class LRUCache {

        // Doubly Linked List, Hashmap
        public static class NodeDLL {

            int key; int value;
            NodeDLL prev; NodeDLL next;

            public NodeDLL(int key, int value) {

                this.key = key;
                this.value = value;
            }
        }

        public void removeNode(NodeDLL node) {

            node.next.prev = node.prev;
            node.prev.next = node.next;
        }

        public void addToHead(NodeDLL node) {

            node.prev = head;
            node.next = head.next;

            head.next = node;
            node.next.prev = node;
        }

        private int capacity;
        private static HashMap<Integer, NodeDLL> mapKeyToNode;
        private NodeDLL head;
        private NodeDLL tail;

        // constructor
        public LRUCache(int capacity) {

            this.capacity = capacity;
            mapKeyToNode = new HashMap<>();
            this.head = new NodeDLL(-1, -1);
            this.tail = new NodeDLL(-1, -1);
            this.head.next = this.tail;
            this.tail.next = this.head;
        }

        public int get(int key) {

            if(mapKeyToNode.containsKey(key)) {

                //get node from hashmap, make it MRU and return value
                NodeDLL nodeMRU = mapKeyToNode.get(key);

                removeNode(nodeMRU);

                addToHead(nodeMRU);

                return nodeMRU.value;
            }
            // if given key is not there in cache
            return -1;
        }

        public void put(int key, int value) {

            // If node with given key already exists, update its value, make it MRU
            if (mapKeyToNode.containsKey(key)) {

                NodeDLL nodeUpdate = mapKeyToNode.get(key);

                nodeUpdate.value = value;

                removeNode(nodeUpdate);

                addToHead(nodeUpdate);
            }

            //if new node is given
            else {

                // if capacity is at full, remove an LRU node
                if (capacity == mapKeyToNode.size()) {

                    NodeDLL nodeLRU = tail.prev;

                    removeNode(nodeLRU);

                    mapKeyToNode.remove(nodeLRU.key);
                }

                NodeDLL nodeNew = new NodeDLL(key, value);

                //add new node to head in doubly linked list and hashmap
                addToHead(nodeNew);

                mapKeyToNode.put(key, nodeNew);


            }


        }

        public static void main(String[] args) {

            LRUCache lru = new LRUCache(2);

            lru.put(1, 1); // cache is {1=1}
            System.out.println("LRU cache now after put: ");
            for (int key : mapKeyToNode.keySet()) {
                System.out.println(key + " ");
            }


            lru.put(2, 2); // cache is {1=1, 2=2}
            System.out.println("LRU cache now after put: ");
            for (int key : mapKeyToNode.keySet()) {
                System.out.println(key + " ");
            }

            lru.get(1);    // return 1
            System.out.println("Get value: ");
            System.out.println(lru.get(1));
            System.out.println("LRU cache now after get: ");
            for (int key : mapKeyToNode.keySet()) {
                System.out.println(key + " ");
            }

            lru.put(3, 3); // LRU key was 2, evicts key 2, cache is {1=1, 3=3}
            System.out.println("LRU cache now after put: ");
            for (int key : mapKeyToNode.keySet()) {
                System.out.println(key + " ");
            }

            lru.get(2);    // returns -1 (not found)
            System.out.println("Get value: ");
            System.out.println(lru.get(2));
            System.out.println("LRU cache now after get: ");
            for (int key : mapKeyToNode.keySet()) {
                System.out.println(key + " ");
            }

            lru.put(4, 4); // LRU key was 1, evicts key 1, cache is {4=4, 3=3}
            System.out.println("LRU cache now after put: ");
            for (int key : mapKeyToNode.keySet()) {
                System.out.println(key + " ");
            }

            lru.get(1);    // return -1 (not found)
            System.out.println("Get value: ");
            System.out.println(lru.get(1));
            System.out.println("LRU cache now after get: ");
            for (int key : mapKeyToNode.keySet()) {
                System.out.println(key + " ");
            }

            lru.get(3);    // return 3
            System.out.println("Get value: ");
            System.out.println(lru.get(3));
            System.out.println("LRU cache now after get: ");
            for (int key : mapKeyToNode.keySet()) {
                System.out.println(key + " ");
            }

            lru.get(4);    // return 4
            System.out.println("Get value: ");
            System.out.println(lru.get(4));
            System.out.println("LRU cache now after get: ");
            for (int key : mapKeyToNode.keySet()) {
                System.out.println(key + " ");
            }
        }

}

/*
TIME COMPLEXITY = O(1) - both get, put and all operations done in O(1)
SPACE COMPLEXITY = O(C)
C = capacity given, max space for hashmap and doubly linked list
*/




