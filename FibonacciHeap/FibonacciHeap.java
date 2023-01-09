/** * FibonacciHeap * * An implementation of a Fibonacci Heap over integers. */public class FibonacciHeap {    private HeapNode firstNode;    private HeapNode minNode;    private int size;    private int markedCount;    private int treesCount;    static private int linksCount;    static private int cutsCount;   /**    * public boolean isEmpty()    *    * Returns true if and only if the heap is empty.    *       */    public boolean isEmpty()    {    	return size == 0;    }		   /**    * public HeapNode insert(int key)    *    * Creates a node (of type HeapNode) which contains the given key, and inserts it into the heap.    * The added key is assumed not to already belong to the heap.      *     * Returns the newly created node.    */    public HeapNode insert(int key)    {        	HeapNode node = new HeapNode(key, firstNode, firstNode.prev, null, null);        firstNode.prev.next = node;        firstNode.prev = node;        if (minNode.key > key) {            minNode = node;        }        treesCount++;        size++;        return node;    }   /**    * public void deleteMin()    *    * Deletes the node containing the minimum key.    *    */    public void deleteMin()    {     	return; // should be replaced by student code    }   /**    * public HeapNode findMin()    *    * Returns the node of the heap whose key is minimal, or null if the heap is empty.    *    */    public HeapNode findMin()    {    	return minNode;    }        /**    * public void meld (FibonacciHeap heap2)    *    * Melds heap2 with the current heap.    *    */    public void meld (FibonacciHeap heap2)    {    	  return; // should be replaced by student code   		    }   /**    * public int size()    *    * Returns the number of elements in the heap.    *       */    public int size()    {    	return size;    }    	    /**    * public int[] countersRep()    *    * Return an array of counters. The i-th entry contains the number of trees of order i in the heap.    * (Note: The size of the array depends on the maximum order of a tree.)    *     */    public int[] countersRep()    {    	int[] arr = new int[100];        return arr; //	 to be replaced by student code    }	   /**    * public void delete(HeapNode x)    *    * Deletes the node x from the heap.	* It is assumed that x indeed belongs to the heap.    *    */    public void delete(HeapNode x)     {        	return; // should be replaced by student code    }   /**    * public void decreaseKey(HeapNode x, int delta)    *    * Decreases the key of the node x by a non-negative value delta. The structure of the heap should be updated    * to reflect this change (for example, the cascading cuts procedure should be applied if needed).    */    public void decreaseKey(HeapNode x, int delta)    {        	return; // should be replaced by student code    }   /**    * public int nonMarked()     *    * This function returns the current number of non-marked items in the heap    */    public int nonMarked()     {            return size - markedCounter;    }   /**    * public int potential()     *    * This function returns the current potential of the heap, which is:    * Potential = #trees + 2*#marked    *     * In words: The potential equals to the number of trees in the heap    * plus twice the number of marked nodes in the heap.     */    public int potential()     {            return treesCount + 2 * markedCount;    }   /**    * public static int totalLinks()     *    * This static function returns the total number of link operations made during the    * run-time of the program. A link operation is the operation which gets as input two    * trees of the same rank, and generates a tree of rank bigger by one, by hanging the    * tree which has larger value in its root under the other tree.    */    public static int totalLinks()    {        	return linksCount;    }   /**    * public static int totalCuts()     *    * This static function returns the total number of cut operations made during the    * run-time of the program. A cut operation is the operation which disconnects a subtree    * from its parent (during decreaseKey/delete methods).     */    public static int totalCuts()    {        	return cutsCount;    }     /**    * public static int[] kMin(FibonacciHeap H, int k)     *    * This static function returns the k smallest elements in a Fibonacci heap that contains a single tree.    * The function should run in O(k*deg(H)). (deg(H) is the degree of the only tree in H.)    *      * ###CRITICAL### : you are NOT allowed to change H.     */    public static int[] kMin(FibonacciHeap H, int k)    {            int[] arr = new int[100];        return arr; // should be replaced by student code    }       /**    * public class HeapNode    *     * If you wish to implement classes other than FibonacciHeap    * (for example HeapNode), do it in this file, not in another file.     *      */    public static class HeapNode{        public int key;        public HeapNode next;        public HeapNode prev;        public HeapNode parent;        public HeapNode child;        public HeapNode(int ke) {            this.key = ke;        }        public HeapNode(int ke, HeapNode next, HeapNode prev, HeapNode parent, HeapNode child){            this.key = ke;            this.parent = parent;            this.child = child;            this.next = next;            this.prev = prev;        }        public int getKey() {            return this.key;        }        public void setKey(int key) {           this.key = key;        }        public HeapNode getNext() {return  this.next;}        public HeapNode getPrev() {return  this.prev;}        public HeapNode getParent() {return  this.parent;}        public HeapNode getChild() {return  this.child;}        public void setNext(HeapNode next) {           this.next = next;        }       public void setPrev(HeapNode prev) {           this.prev = prev;       }       public void setParent(HeapNode parent) {           this.parent = parent;       }       public void setChild(HeapNode child) {           this.child = child;       }   }}