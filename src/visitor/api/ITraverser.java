package visitor.api;

import visitor.impl.OutputVisitor;

public interface ITraverser {
	public void accept(OutputVisitor v);
}
