package edu.umb.cs680.hw10.apfs;

import java.util.LinkedList;

public class ApfsFileSearchVisitor implements ApfsVisitor {

	protected String fileName;
	protected LinkedList<ApfsFile> foundFiles = new LinkedList<>();
	
	public ApfsFileSearchVisitor() {}
	
	public ApfsFileSearchVisitor(String fileName) {
		this.fileName = fileName;
	}
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
		if(file.getName().equals(this.fileName)) {
			foundFiles.add(file);
		}
		
	}

	protected String getFileName() {
		return fileName;
	}

	protected LinkedList<ApfsFile> getFoundFiles() {
		return foundFiles;
	}


}
