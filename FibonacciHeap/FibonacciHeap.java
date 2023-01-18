/** * FibonacciHeap * * An implementation of a Fibonacci Heap over integers. */public class FibonacciHeap {    private HeapNode firstNode;    private HeapNode minNode;    private int size;    private int markedCount;    private int treesCount;    static private int linksCount = 0;    static private int cutsCount = 0;   /**    * public boolean isEmpty()    *    * Returns true if and only if the heap is empty.    *       */    public boolean isEmpty()    {    	return size == 0;    }		   /**    * public HeapNode insert(int key)    *    * Creates a node (of type HeapNode) which contains the given key, and inserts it into the heap.    * The added key is assumed not to already belong to the heap.      *     * Returns the newly created node.    *    * Time Complexity: O(1)    */    public HeapNode insert(int key) {        HeapNode node;        if (this.size == 0){            node = new HeapNode(key);            node.setPrev(node);            node.setNext(node);            minNode = node;        }else {            node = new HeapNode(key, firstNode, firstNode.prev, null, null);            firstNode.prev.next = node;            firstNode.prev = node;            if (minNode.key > key) {                minNode = node;            }        }        firstNode = node;        treesCount++;        size++;        return node;    }    /**     * public HeapNode insert(int key, HeapNode heapNodePointer)     *     * Creates a node (of type HeapNode) which contains the given key, and inserts it into the heap.     * The added key is assumed not to already belong to the heap.     * Adds a pointer to the pointer field.     * Used only as a helper function for kMin.     *     * Returns the newly created node.     *     * Time Complexity: O(1)     */    private HeapNode insert(int key, HeapNode heapNodePointer) {        HeapNode heapNode = insert(key);        heapNode.heapNodePointer = heapNodePointer;        return heapNode;    }   /**    * public void deleteMin()    *    * Deletes the node containing the minimum key.    * melds his children with the heap    *    * Time Complexity O(n)    */    public void deleteMin()    {     	HeapNode min = this.minNode;        cutsCount += min.rank;        HeapNode prevMin = min.getPrev();        HeapNode nextMin = min.getNext();        if (min == this.firstNode){            this.firstNode = min.getNext() == min ? null : min.getNext();        }        if (min.getChild() != null){            // meld his children with the heap            HeapNode firstChild = min.getChild();            HeapNode lastChild = firstChild.getPrev();            HeapNode p = firstChild;            p.marked = false;            while (p.getNext() != firstChild){                p = p.getNext();                p.marked = false;                p.setParent(null);            }            prevMin.setNext(firstChild);            firstChild.setPrev(prevMin);            nextMin.setPrev(lastChild);            lastChild.setNext(nextMin);            this.treesCount += (min.rank - 1);        } else {            prevMin.setNext(nextMin);            nextMin.setPrev(prevMin);            this.treesCount--;        }        this.size--;        this.successiveLinking();        HeapNode p = this.firstNode;        int minKey = this.firstNode.getKey();        for (int i = 0; i < this.treesCount; i++) {            if (minKey < p.getKey()){                this.minNode = p;                minKey = p.getKey();            }        }        this.minNode = p;    }   /**    * public HeapNode findMin()    *    * Returns the node of the heap whose key is minimal, or null if the heap is empty.    *    * Time Complexity O(1)    */    public HeapNode findMin()    {    	return minNode;    }        /**    * public void meld (FibonacciHeap heap2)    *    * Melds heap2 with the current heap.    *    * Time Complexity O(1)    */    public void meld(FibonacciHeap heap2)    {        if (this.isEmpty() && heap2.isEmpty()) {            if (this.isEmpty()){                this.firstNode = heap2.firstNode;                this.size = heap2.size();                this.minNode = heap2.minNode;                this.treesCount = heap2.treesCount;                this.markedCount = heap2.markedCount;            }        } else{            HeapNode lastNode1 = this.firstNode.prev;            HeapNode lastNode2 = heap2.firstNode.prev;            lastNode1.next = heap2.firstNode;            heap2.firstNode.prev = lastNode1;            this.firstNode.prev = lastNode2;            lastNode2.next = this.firstNode;            this.minNode = this.minNode.key < heap2.minNode.key ? this.minNode : heap2.minNode;            this.size = this.size() + heap2.size();            this.markedCount =+ heap2.markedCount;            this.treesCount =+ heap2.treesCount;        }    }   /**    * public int size()    *    * Returns the number of elements in the heap.    *    *  Time Complexity O(1)    */    public int size()    {    	return size;    }    	    /**    * public int[] countersRep()    *    * Return an array of counters. The i-th entry contains the number of trees of order i in the heap.    * (Note: The size of the array depends on the maximum order of a tree.)    *     * Time Complexity O(n)    */    public int[] countersRep()    {        if (isEmpty()) {            return new int[0];        }        HeapNode node = this.firstNode;        int maxRank = 0;        do { // find the max rank            maxRank = Math.max(maxRank, node.rank);            node = node.next;        } while (node != firstNode);        // node == firstNode        int[] result = new int[maxRank + 1];        do {            result[node.rank]++;            node = node.next;        } while (node != firstNode);        return  result;    }	   /**    * public void delete(HeapNode x)    *    * Deletes the node x from the heap.	* It is assumed that x indeed belongs to the heap.    * Time Complexity O(n)    */    public void delete(HeapNode x)    {    	decreaseKey(x, x.key - minNode.key + 1);        deleteMin();    }   /**    * public void decreaseKey(HeapNode x, int delta)    *    * Decreases the key of the node x by a non-negative value delta. The structure of the heap should be updated    * to reflect this change (for example, the cascading cuts procedure should be applied if needed).    *    * Time Complexity O(n)    */    public void decreaseKey(HeapNode x, int delta)    {        	x.key -= delta;        if (x.parent == null || x.key > x.parent.key) { return; }        // at least one cut is needed        HeapNode node = x;        HeapNode parent = node.parent;        while (parent.marked) { // stops anyway if the parent is a root            cut(node);            node = parent;            parent = node.parent;        }        cut(node);    }    /**     * private void cut(HeapNode node)     *     * Decreases the key of the node x by a non-negative value delta. The structure of the heap should be updated     * to reflect this change (for example, the cascading cuts procedure should be applied if needed).     *     * Time Complexity O(n)     */    private void cut(HeapNode node) {        firstNode.prev.next = node;        firstNode.prev = node;        firstNode = node;        node.unmark();        if (node.parent.rank == 1) {            node.parent.child = null;        } else { // parent.rank > 1            node.next.prev = node.prev;            node.prev.next = node.next;            node.parent.child = node.next;        }        node.parent.rank--;        if (node.parent.parent != null) {            node.parent.mark();        }        node.parent = null;        if (node.key < minNode.key) {            minNode = node;        }        treesCount++;        cutsCount++;    }   /**    * public int nonMarked()     *    * This function returns the current number of non-marked items in the heap    * Time Complexity O(1)    */    public int nonMarked()     {            return size - markedCount;    }   /**    * public int potential()     *    * This function returns the current potential of the heap, which is:    * Potential = #trees + 2*#marked    *     * In words: The potential equals to the number of trees in the heap    * plus twice the number of marked nodes in the heap.    *    * Time Complexity O(1)    */    public int potential()     {            return treesCount + 2 * markedCount;    }   /**    * public static int totalLinks()     *    * This static function returns the total number of link operations made during the    * run-time of the program. A link operation is the operation which gets as input two    * trees of the same rank, and generates a tree of rank bigger by one, by hanging the    * tree which has larger value in its root under the other tree.    *    * Time Complexity O(1)    */    public static int totalLinks()    {        	return linksCount;    }   /**    * public static int totalCuts()     *    * This static function returns the total number of cut operations made during the    * run-time of the program. A cut operation is the operation which disconnects a subtree    * from its parent (during decreaseKey/delete methods).    *    * Time Complexity O(1)    */    public static int totalCuts()    {        	return cutsCount;    }     /**    * public static int[] kMin(FibonacciHeap H, int k)     *    * This static function returns the k smallest elements in a Fibonacci heap that contains a single tree.    * The function should run in O(k*deg(H)). (deg(H) is the degree of the only tree in H.)    *      * ###CRITICAL### : you are NOT allowed to change H.      *      * Time Complexity O(k * H.rank)    */    public static int[] kMin(FibonacciHeap H, int k) {        int[] arr = new int[k];        FibonacciHeap helperHeap = new FibonacciHeap();        helperHeap.insert(H.findMin().key, H.findMin());        for (int i = 0; i < k; i++) {            arr[i] = helperHeap.findMin().key;            HeapNode child = helperHeap.findMin().child;            if (child != null) {                insertMinsChildren(helperHeap, child);            }            helperHeap.deleteMin();        }        return arr;    }    /**     * private static void insertMinsChildren(FibonacciHeap helperHeap, HeapNode child)     *     * This static function inserts child and his siblings to helperHeap     *     * Time Complexity O(child.parent.rank)     */    private static void insertMinsChildren(FibonacciHeap helperHeap, HeapNode child) {        HeapNode temp = child;        do {            helperHeap.insert(temp.key, temp);            temp = temp.next;        }        while (temp != child);    }    /**     * private void successiveLinking()     *     * transforms the heap into an optimal non-lazy heap. Afterwords the heap has one tree.     *     * Time Complexity O(n)     */    private void successiveLinking(){        int ranksSum = this.size();        HeapNode[] bucket = new HeapNode[ranksSum];        HeapNode p = this.firstNode;        int numMerge = 0;        for (int i = 0; i < this.treesCount; i++) {            int j = p.rank;            if (bucket[j] == null){                bucket[j] = p;            } else {                HeapNode rootNode = p;                while(bucket[j] != null){                    HeapNode localNode = bucket[j];                    rootNode = connect(rootNode, localNode);                    bucket[j] = null;                    numMerge++;                    j++;                }                bucket[j] = rootNode;            }            p = p.getNext();        }        this.treesCount -= numMerge;    }    /**     * private HeapNode connect(HeapNode hp1, HeapNode hp2)     *     * Assuming hp1 and hp2 are of the same rank, connects hp1 and hp2 to a new heap from a higher rank. returns the result.     *     * Time Complexity O(1)     */    private HeapNode connect(HeapNode hp1, HeapNode hp2) {        linksCount++;        boolean isFirst = false;        if (this.firstNode == hp1 || this.firstNode == hp2){            isFirst = true;        }        // add one of them as a child of the other        if (hp1.key < hp2.key) {            HeapNode hp2Prev = hp2.getPrev();            HeapNode hp2Next = hp2.getNext();            hp2Prev.setNext(hp2Next);            hp2Next.setPrev(hp2Prev);            if (hp1.rank == 0) {                hp1.setChild(hp2);                hp2.setParent(hp1);                hp2.setNext(hp2);                hp2.setPrev(hp2);            } else {                HeapNode child = hp1.getChild();                HeapNode lastChild = child.prev;                hp2.setNext(child);                child.setPrev(hp2);                hp2.setPrev(lastChild);                lastChild.setNext(hp2);                hp2.setParent(hp1);            }            if (isFirst) {                this.firstNode = hp1;            }            hp1.increaseRank();            return hp1;        }        HeapNode hp1Prev = hp1.getPrev();        HeapNode hp1Next = hp1.getNext();        hp1Prev.setNext(hp1Next);        hp1Next.setPrev(hp1Prev);        if (hp2.rank == 0){            hp2.setChild(hp1);            hp1.setParent(hp2);            hp1.setNext(hp1);            hp1.setPrev(hp1);        }else {            HeapNode child = hp2.getChild();            HeapNode lastChild = child.prev;            hp1.setNext(child);            child.setPrev(hp1);            hp1.setPrev(lastChild);            lastChild.setNext(hp1);            hp1.setParent(hp2);        }        if (isFirst) {            this.firstNode = hp2;        }        hp2.increaseRank();        return  hp2;    }    public HeapNode getFirst() {        return this.firstNode;    }    /**    * public class HeapNode    *     * If you wish to implement classes other than FibonacciHeap    * (for example HeapNode), do it in this file, not in another file.     *      */    public static class HeapNode{        private int key;       private HeapNode next;       private HeapNode prev;       private HeapNode parent;       private HeapNode child;       private boolean marked;       private int rank;       private HeapNode heapNodePointer;        public HeapNode(int ke) {            this.key = ke;            this.rank = 0;            this.marked = false;        }        public HeapNode(int ke, HeapNode next, HeapNode prev, HeapNode parent, HeapNode child, HeapNode heapNodePointer){            this(ke);            this.parent = parent;            this.child = child;            this.next = next;            this.prev = prev;            this.heapNodePointer = heapNodePointer;        }       /**        * public int getKey()        *        * returns this.key        *        * Time Complexity O(1)        */        public int getKey() {            return this.key;        }       /**        * public int setKey()        *        * set this.key to be key        *        * Time Complexity O(1)        */        public void setKey(int key) {           this.key = key;        }       /**        * public int getNext()        *        * returns this.next        *        * Time Complexity O(1)        */        public HeapNode getNext() {return  this.next;}       /**        * public int getPrev()        *        * returns this.prev        *        * Time Complexity O(1)        */        public HeapNode getPrev() {return  this.prev;}       /**        * public int getParent()        *        * returns this.parent        *        * Time Complexity O(1)        */        public HeapNode getParent() {return  this.parent;}       /**        * public int getChild()        *        * returns this.child        *        * Time Complexity O(1)        */        public HeapNode getChild() {return  this.child;}       public int getRank() {return this.rank;}       public boolean getMarked(){return this.marked;}       /**        * public int setNext()        *        * set this.next to be next        *        * Time Complexity O(1)        */        public void setNext(HeapNode next) {           this.next = next;        }       /**        * public int setPrev()        *        * set this.prev to be prev        *        * Time Complexity O(1)        */        public void setPrev(HeapNode prev) {           this.prev = prev;        }       /**        * public int setParent()        *        * set this.parent to be parent        *        * Time Complexity O(1)        */        public void setParent(HeapNode parent) {           this.parent = parent;        }       /**        * public int setChild()        *        * set this.child to be child        *        * Time Complexity O(1)        */        public void setChild(HeapNode child) {           this.child = child;        }       /**        * public int mark()        *        * set this.marked to be true        *        * Time Complexity O(1)        */        public void mark() {            this.marked = true;        }       /**        * public int unmark()        *        * set this.marked to be false        *        * Time Complexity O(1)        */        public void unmark() {            this.marked = false;        }       /**        * public void increaseRank()        *        * this.rank++;        *        * Time Complexity O(1)        */        public void increaseRank() {            this.rank ++;        }    }   public static void main(String[] args){        FibonacciHeap heap = new FibonacciHeap();       System.out.println("");   }}