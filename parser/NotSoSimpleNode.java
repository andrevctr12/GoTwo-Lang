package parser;

public class NotSoSimpleNode extends SimpleNode {

  public NotSoSimpleNode(int i) {
    super(i);
  }

  public NotSoSimpleNode(LangGoTwo p, int i) {
    super(p, i);
  }


  @Override
  public void dump(String prefix) {
    print(prefix, true);
  }

  @Override
  public String toString() {
    if (value != null) {
      return LangGoTwoTreeConstants.jjtNodeName[id] + "[" + value.toString() + "]";
    }
    return LangGoTwoTreeConstants.jjtNodeName[id];
  }

  private void print(String prefix, boolean isTail) {
    System.out.println(prefix + (isTail ? "└── " : "├── ") + toString());
    if(children != null) {
      for (int i = 0; i < children.length - 1; ++i) {
        NotSoSimpleNode n = (NotSoSimpleNode) children[i];
        if (n != null) {
          n.print(prefix + (isTail ? "    " : "│   "), false);
        }
      }
      if (children.length > 0) {
        NotSoSimpleNode n = (NotSoSimpleNode) children[children.length - 1];
        n.print(prefix + (isTail ? "    " : "│   "), true);
      }
    }
  }

}