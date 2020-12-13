package edu.umb.cs680.hw10.apfs;

public class ApfsCountingVisitor implements ApfsVisitor {

	private int dirNum = 0;
	private int fileNum = 0;
	private int linkNum = 0;
	
	public ApfsCountingVisitor() {
		
	}
	@Override
	public void visit(ApfsLink link) {
		linkNum++;
	}

	@Override
	public void visit(ApfsDirectory dir) {
		dirNum++;
		
	}

	@Override
	public void visit(ApfsFile file) {
		fileNum++;
		
	}

	public int getDirNum() {
		return dirNum;
	}

	public int getFileNum() {
		return fileNum;
	}

	public int getLinkNum() {
		return linkNum;
	}
	

}
