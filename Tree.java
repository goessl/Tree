/*
 * MIT License
 * 
 * Copyright (c) 2019 Sebastian Gössl
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */



package tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.swing.tree.TreeNode;



/**
 * TreeNode class used to store and generate tree like data structures.
 * 
 * @author Sebastian Gössl
 * @version 1.1 21.2.2020
 * @param <T> data type the TreeNode should store
 */
public class Tree<T> implements TreeNode, Iterable<Tree<T>> {
    
    /**
     * Data of this node.
     */
    private final T data;
    /**
     * Parent node or null if this is the root node.
     */
    private final Tree<T> parent;
    /**
     * Children nodes.
     */
    private final List<Tree<T>> children = new ArrayList<>();
    
    
    
    /**
     * Constructs a new root node with the given data.
     * 
     * @param data data of this node
     */
    public Tree(T data) {
        this(null, data);
    }
    
    /**
     * Constructs a new root node and grows a sub tree with the given function.
     * The function is applied on every node recursively and every returned
     * data object of the iterable is added as a new child node.
     * 
     * @param data data of the this node
     * @param grow growth function
     */
    public Tree(T data, Function<T, Iterable<T>> grow) {
        this(data);
        grow.apply(data).forEach((child) -> add(child, grow));
    }
    
    /**
     * Constructs a new node with the given parent node and data.
     * 
     * @param parent parent node of this node
     * @param data data of this node
     */
    private Tree(Tree<T> parent, T data) {
        this.parent = parent;
        this.data = data;
    }
    
    /**
     * Constructs a new node with the given parent node and grows a sub
     * tree with the given function.
     * The function is applied on every node recursively and every returned
     * data object of the iterable is added as a new child node.
     * 
     * @param parent parent node of this node
     * @param data data of the this node
     * @param grow growth function
     */
    private Tree(Tree<T> parent, T data,
            Function<T, Iterable<T>> grow) {
        
        this(parent, data);
        grow.apply(data).forEach((child) -> add(child, grow));
    }
    
    
    
    /**
     * Returns the data of this node.
     * 
     * @return data of this node
     */
    public T getData() {
        return data;
    }
    
    /**
     * Returns the parent of this node or null if this is a root node.
     * 
     * @return parent of this node or null if this is a root node
     */
    @Override
    public Tree<T> getParent() {
        return parent;
    }
    
    /**
     * Returns the children of this node.
     * 
     * @return children of this node
     */
    public List<Tree<T>> getChildren() {
        return Collections.unmodifiableList(children);
    }
    
    /**
     * Returns if this is a root node.
     * 
     * @return if this is a root node
     */
    public boolean isRoot() {
        return getParent() == null;
    }
    
    /**
     * Returns if this is a leaf node (which means that this node has no
     * children).
     * 
     * @return if this is a leaf node
     */
    @Override
    public boolean isLeaf() {
        return getChildren().isEmpty();
    }
    
    
    /**
     * Adds a new child node with the given data.
     * 
     * @param child data of the child to be added
     * @return the newly added child node
     */
    public Tree<T> add(T child) {
        final Tree<T> node = new Tree<>(this, child);
        children.add(node);
        return node;
    }
    
    /**
     * Adds a new child node with the given data and grows a sub tree from
     * this child with the given function.
     * 
     * @param child data of the child to be added
     * @param grow growth function
     * @return the newly added child node
     */
    public Tree<T> add(T child, Function<T, Iterable<T>> grow) {
        final Tree<T> node = new Tree<>(this, child, grow);
        children.add(node);
        return node;
    }
    
    /**
     * Removes the child with at given index.
     * 
     * @param index index of the child to be removed
     * @return removed child node
     */
    public Tree<T> remove(int index) {
        return children.remove(index);
    }
    
    /**
     * Removes the first child that holds the given data and returns the
     * whole node.
     * 
     * @param child data of the child to be removed
     * @return removed child node
     */
    public Tree<T> remove(T child) {
        for(int i=0; i<children.size(); i++) {
            if(child.equals(children.get(i).getData())) {
                return children.remove(i);
            }
        }
        
        return null;
    }
    
    
    /**
     * Performs the given action for each node in this tree in pre-order.
     * It is first performed for this node and then for its children
     * recursively and therefore for the whole tree.
     * 
     * @param action to be performed for every node in pre-order
     */
    public void preOrder(Consumer<? super Tree<T>> action) {
        action.accept(this);
        getChildren().forEach((child) -> child.preOrder(action));
    }
    
    /**
     * Performs the given action for each node in this tree in post-order.
     * It is first performed for its children recursively and then for this
     * node and therefore for the whole tree.
     * 
     * @param action to be performed for every node in post-order
     */
    public void postOrder(Consumer<? super Tree<T>> action) {
        getChildren().forEach((child) -> child.postOrder(action));
        action.accept(this);
    }
    
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Tree<T> getChildAt(int childIndex) {
        return getChildren().get(childIndex);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int getChildCount() {
        return getChildren().size();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int getIndex(TreeNode node) {
        final List<Tree<T>> children = getChildren();
        
        for(int i=0; i<children.size(); i++) {
            final Tree<T> child = children.get(i);
            if(equals(child)) {
                return i;
            }
        }
        
        return -1;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getAllowsChildren() {
        return true;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Enumeration children() {
        return Collections.enumeration(getChildren());
    }
    
    
    /**
     * Iterator that iterates over this tree in pre-order.
     */
    private class TreeIterator implements Iterator<Tree<T>> {
        /**
         * Iterator for the direct children.
         */
        private Iterator<Tree<T>> children;
        /**
         * Iterator for the grandchildren children.
         */
        private Iterator<Tree<T>> childIterator;
        
        
        
        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasNext() {
            if(children == null) {  //Still at root
                return true;
                
            } else {
                if(childIterator != null) {
                    if(childIterator.hasNext()) { //Still grandchildren left
                        return true;
                        
                    } else {  //No grandchildren but children left
                        return children.hasNext();
                    }
                } else {
                    return false;
                }
            }
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public Tree<T> next() {
            if(children == null) {  //Still at root
                children = getChildren().iterator();
                if(children.hasNext()) {
                    childIterator = children.next().iterator();
                }
                return Tree.this;
                
            } else {
                if(childIterator.hasNext()) {
                    //While there are grandchildren, return them
                    return childIterator.next();
                    
                } else {  //Otherwise go to next child
                    childIterator = children.next().iterator();
                    return childIterator.next();
                }
            }
        }
    }
    
    /**
     * Returns a iterator that traverses this tree in pre-order.
     * 
     * @return iterator that traverses this tree in pre-order
     */
    @Override
    public Iterator<Tree<T>> iterator() {
        /*
        final List<TreeNode<T>> list = new ArrayList<>();
        preOrder(list::add);
        return list.iterator();
        */
        
        return new TreeIterator();
    }
    
    /**
     * Performs the given action for each node in pre-order.
     */
    @Override
    public void forEach(Consumer<? super Tree<T>> action) {
        preOrder(action);
    }
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return data.toString();
    }
    
    
    
    
    public static void main(String[] args) {
        
        /* Generating permutations */
        final int[] superset = new int[]{0, 1, 2, 3};
        
        
        final Tree<List<Integer>> tree =
                new Tree<>(new ArrayList<>(), (node) -> {
                    
                    final List<List<Integer>> children = new ArrayList<>();
                    
                    if(node.size() < superset.length) {
                        for(int element : superset) {
                            if(!node.contains(element)) {
                                
                                final List<Integer> child =
                                        new ArrayList<>(node);
                                child.add(element);
                                
                                children.add(child);
                            }
                        }
                    }
                    
                    return children;
                });
        
        
        
        tree.forEach((node) -> {
            if(node.isLeaf()) {
                System.out.println(node.getData());
            }
        });
        
        /*
        final int MAX_SIZE = 50;
        final Random rand = new Random();
        
        final Tree<Integer> tree = new Tree<>(0, (i) -> {
            final List<Integer> children = new ArrayList<>();
            
            while(rand.nextDouble() <= MAX_SIZE/100.0) {
                children.add(rand.nextInt(MAX_SIZE));
            }
            
            return children;
        });
        
        
        EventQueue.invokeLater(() -> {
            final JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(
                    WindowConstants.DISPOSE_ON_CLOSE);
            frame.getContentPane().add(new JTree(tree),
                    BorderLayout.CENTER);
            frame.pack();
            frame.setVisible(true);
        });*/
    }
}
