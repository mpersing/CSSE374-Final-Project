package visitor.api;

public interface IOutputStrategy {
	public void preVisit(StringBuffer sb);
	
	public void postVisit(StringBuffer sb);
}
