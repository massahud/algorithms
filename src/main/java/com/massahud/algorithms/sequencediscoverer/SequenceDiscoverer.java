package com.massahud.algorithms.sequencediscoverer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * This class discovers if we can discover a full sequence
 * given a list of subsequences.
 */
public class SequenceDiscoverer {

  private static class Node {

    private Set<Node> parents = new HashSet<>();
    private Set<Node> children = new HashSet<>();
    private Integer value;

    private Node(Integer value) {
      this.value = value;
    }
  }

  private Node root = new Node(null);

  private HashMap<Integer, Node> nodes = new HashMap<>();

  public SequenceDiscoverer() {
    nodes.put(null, root);
  }

  /**
   * Add a new subsequence
   * @param subsequence subsequence
   */
  public void addSubsequence(int... subsequence) {
    Node prev = root;
    for (int v : subsequence) {
      Node node = nodes.get(v);
      if (node == null) {
        node = new Node(v);
        nodes.put(v, node);
      }
      node.parents.add(prev);
      prev.children.add(node);
      prev = node;
    }
  }

  /**
   * @return true if a sequence can be discovered.
   */
  public boolean isSequenceDiscoverable() {
    return prune(root, new HashSet<>());
  }

  /**
   * @return the discovered sequence
   * @throws SequenceNotDiscoverableException if a sequence can not be discovered
   */
  public List<Integer> getSequence() throws SequenceNotDiscoverableException {
    if (!isSequenceDiscoverable()) {
      throw new SequenceNotDiscoverableException();
    }

    // already pruned, only need to list
    ArrayList<Integer> sequence = new ArrayList<>();
    Node n = root;
    while (true) {
      if (n.value != null) {
        sequence.add(n.value);
      }
      if (n.children.size() == 0) {
        break;
      }
      n = n.children.iterator().next();
    }
    return sequence;
  }

  /**
   * Walks the graph prunning all edges to children with more than one parent.
   *
   * @param node current node
   * @param pruned set of already pruned nodes (to resolve cycles)
   * @return true if all known nodes where pruned
   */
  private boolean prune(Node node, Set<Node> pruned) {
    pruned.add(node);
    if (node.children.isEmpty()) {
      return pruned.size() == nodes.size();
    }

    // remove edges of childs with more than one parent
    Iterator<Node> childIt = node.children.iterator();
    while (childIt.hasNext()) {
      Node child = childIt.next();
      if (pruned.contains(child)) {
        // cycle
        return false;
      }

      if (child.parents.size() > 1) {
        child.parents.remove(node);
        childIt.remove();
      }
    }

    // more than one child with 1 parent, can't solve
    if (node.children.size() > 1) {
      return false;
    }

    // no children with more than one parent left
    if (node.children.isEmpty()) {
      System.out.println("size = 0. " + node.children);
      return false;
    }

    return prune(node.children.iterator().next(), pruned);
  }
}