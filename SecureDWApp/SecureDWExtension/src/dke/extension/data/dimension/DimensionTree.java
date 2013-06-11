package dke.extension.data.dimension;

import java.util.LinkedList;

/**
 * This Class is a Tree which can represent relations between Dimensions
 * @param <T>
 */
public class DimensionTree<T> {
  private DimensionNode<T> root;

    public DimensionTree(T name) {
        setRoot(new DimensionNode<T>());
        getRoot().setName(name);
        getRoot().setChildren(new LinkedList<DimensionNode<T>>());
      }

    public DimensionNode<T> getRoot() {
        return root;
    }

    public void setRoot(DimensionNode<T> root) {
        this.root = root;
    }
}
