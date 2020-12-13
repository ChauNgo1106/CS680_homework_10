package edu.umb.cs680.hw10.apfs;

import java.util.LinkedList;

public class ApfsFileCrawlingVisitor implements ApfsVisitor {

	private LinkedList<ApfsFile> files = new LinkedList<>();
	
	
	public ApfsFileCrawlingVisitor() {}
	@Override
	public void visit(ApfsLink link) {
		return;
		
	}

	@Override
	public void visit(ApfsDirectory dir) {
		return;
		
	}

	@Override
	public void visit(ApfsFile file) {
		files.add(file);
		
	}

	public LinkedList<ApfsFile> getFiles() {
		return files;
	}
	
	public void reset() {
		this.files.clear();
	}

}
