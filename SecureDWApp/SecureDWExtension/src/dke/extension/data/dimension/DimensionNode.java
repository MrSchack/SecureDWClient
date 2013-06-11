package dke.extension.data.dimension;

import java.util.List;

public class DimensionNode<T> {
      private T name;
      private List<DimensionNode<T>> children;
      private List<T> attributes;

    public T getName() {
        return name;
    }

    public void setName(T name) {
        this.name = name;
    }

    public List<DimensionNode<T>> getChildren() {
        return children;
    }

    public void setChildren(List<DimensionNode<T>> children) {
        this.children = children;
    }
    
  public void addChildren(DimensionNode<T> child) {
      this.children.add(child);
  }

    public List<T> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<T> attributes) {
        this.attributes = attributes;
    }
    
    public void addAttribute(T attribute) {
        this.attributes.add(attribute);
    }
}
