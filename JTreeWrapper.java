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

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.WindowConstants;



/**
 * Wrapper to pass a tree.TreeNode to a JTree to be displayed.
 * Delegates all methods of the javax.swing.tree.TreeNode straight to the
 * wrapped TreeNode.
 * 
 * @author Sebastian Gössl
 * @version 1.0 17.9.2019
 */
public class JTreeWrapper<T> implements javax.swing.tree.TreeNode {
    
    /**
     * Tree to be wrapped.
     */
    private final TreeNode<T> node;
    
    
    
    /**
     * Constructs a new JTreeWrapper with the given TreeNode.
     * 
     * @param treeNode tree node to wrap
     */
    public JTreeWrapper(TreeNode treeNode) {
        this.node = treeNode;
    }
    
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public javax.swing.tree.TreeNode getChildAt(int childIndex) {
        return new JTreeWrapper(node.getChildren().get(childIndex));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int getChildCount() {
        return node.getChildren().size();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public javax.swing.tree.TreeNode getParent() {
        return new JTreeWrapper(node.getParent());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int getIndex(javax.swing.tree.TreeNode node) {
        final List<TreeNode<T>> children = this.node.getChildren();
        
        for(int i=0; i<children.size(); i++) {
            final TreeNode<T> child = children.get(i);
            if(node.equals(new JTreeWrapper<>(child))) {
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
    public boolean isLeaf() {
        return node.isLeaf();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Enumeration children() {
        return Collections.enumeration(node.getChildren());
    }
    
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return node.getData().toString();
    }
    
    
    
    
    public static void main(String[] args) {
        
        final int MAX_SIZE = 50;
        final Random rand = new Random();
        
        final TreeNode<Integer> tree = new TreeNode<>(0, (i) -> {
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
            frame.getContentPane().add(new JTree(new JTreeWrapper(tree)),
                    BorderLayout.CENTER);
            frame.pack();
            frame.setVisible(true);
        });
    }
}
