package edu.umb.cs680.hw10.apfs;
 
import java.time.LocalDateTime;

import edu.umb.cs680.hw10.apfs.ApfsDirectory;

public class ApfsFile extends ApfsElement {
	
	public ApfsFile(ApfsDirectory parent, String name, int size, String owner, LocalDateTime creationTime, LocalDateTime lastModified ) {
		super(parent, name, size, owner, creationTime, lastModified);
	}
	// this is file not directory
	@Override
	public boolean isDirectory() {
		return false;
	}
	@Override
	void accept(ApfsVisitor v) {
		v.visit(this);
		
	}
}
