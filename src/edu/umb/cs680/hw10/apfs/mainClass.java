package edu.umb.cs680.hw10.apfs;

import java.time.LocalDate;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeAll;

public class mainClass {

	public static void main(String[] args) {

		// init APFS file system
		APFS.getFileSystem().initFileSystemAPFS("root", 10240);

		ApfsDirectory root = new ApfsDirectory(null, "root", 0, "Chau Ngo",
				LocalDateTime.of(LocalDate.of(2019, 8, 15), LocalTime.of(12, 00, 00)),
				LocalDateTime.of(LocalDate.of(2019, 8, 15), LocalTime.of(12, 00, 00)));
		ApfsDirectory applications = new ApfsDirectory(root, "applications", 0, "Chau Ngo",
				LocalDateTime.of(LocalDate.of(2019, 9, 15), LocalTime.of(13, 00, 40)),
				LocalDateTime.of(LocalDate.of(2019, 9, 15), LocalTime.of(13, 00, 40)));

		ApfsDirectory home = new ApfsDirectory(root, "home", 0, "Chau Ngo",
				LocalDateTime.of(LocalDate.of(2019, 10, 15), LocalTime.of(20, 30, 00)),
				LocalDateTime.of(LocalDate.of(2019, 10, 15), LocalTime.of(20, 30, 00)));
		ApfsDirectory code = new ApfsDirectory(home, "code", 0, "Chau Ngo",
				LocalDateTime.of(LocalDate.of(2019, 11, 15), LocalTime.of(20, 31, 00)),
				LocalDateTime.of(LocalDate.of(2019, 11, 15), LocalTime.of(20, 31, 00)));

		ApfsFile a = new ApfsFile(applications, "a", 2048, "Chau Ngo",
				LocalDateTime.of(LocalDate.of(2019, 9, 15), LocalTime.of(13, 01, 00)),
				LocalDateTime.of(LocalDate.of(2019, 9, 15), LocalTime.of(13, 01, 00)));
		ApfsFile b = new ApfsFile(applications, "b", 1024, "Chau Ngo",
				LocalDateTime.of(LocalDate.of(2019, 9, 15), LocalTime.of(13, 10, 00)),
				LocalDateTime.of(LocalDate.of(2019, 9, 15), LocalTime.of(13, 10, 00)));

		ApfsFile c = new ApfsFile(home, "c", 2048, "Chau Ngo",
				LocalDateTime.of(LocalDate.of(2019, 10, 15), LocalTime.of(21, 32, 45)),
				LocalDateTime.of(LocalDate.of(2019, 10, 15), LocalTime.of(21, 32, 45)));
		ApfsFile d = new ApfsFile(home, "d", 4096, "Chau Ngo",
				LocalDateTime.of(LocalDate.of(2019, 9, 15), LocalTime.of(21, 45, 18)),
				LocalDateTime.of(LocalDate.of(2019, 9, 15), LocalTime.of(21, 45, 18)));

		ApfsFile e = new ApfsFile(code, "e", 1024, "Chau Ngo",
				LocalDateTime.of(LocalDate.of(2019, 11, 15), LocalTime.of(20, 40, 59)),
				LocalDateTime.of(LocalDate.of(2019, 11, 15), LocalTime.of(20, 40, 59)));
		ApfsFile f = new ApfsFile(code, "f", 2048, "Chau Ngo",
				LocalDateTime.of(LocalDate.of(2019, 11, 15), LocalTime.of(20, 45, 51)),
				LocalDateTime.of(LocalDate.of(2019, 11, 15), LocalTime.of(20, 45, 51)));
		// symbolic Link
		ApfsLink x = new ApfsLink(home, "x", 0, "Chau Ngo",
				LocalDateTime.of(LocalDate.of(2019, 11, 17), LocalTime.of(20, 46, 00)),
				LocalDateTime.of(LocalDate.of(2019, 11, 17), LocalTime.of(20, 46, 00)), applications);
		ApfsLink y = new ApfsLink(code, "y", 0, "Chau Ngo",
				LocalDateTime.of(LocalDate.of(2019, 9, 16), LocalTime.of(20, 39, 00)),
				LocalDateTime.of(LocalDate.of(2019, 9, 16), LocalTime.of(20, 39, 00)), b);

		applications.appendChild(a);
		applications.appendChild(b);

		root.appendChild(applications);
		root.appendChild(home);

		home.appendChild(code);
		home.appendChild(c);
		home.appendChild(d);
		home.appendChild(x);

		code.appendChild(e);
		code.appendChild(f);
		code.appendChild(y);
		
		//Testing visitor
		ApfsCountingVisitor visitor =  new ApfsCountingVisitor();
		root.accept(visitor);
		System.out.println("Root");
		System.out.println("The numbers of directories are: " + visitor.getDirNum());
		System.out.println("The numbers of files are: " + visitor.getFileNum());
		System.out.println("The numbers of links are: " + visitor.getLinkNum());
		System.out.println();
		System.out.println("Applications");
		ApfsCountingVisitor visitor2 =  new ApfsCountingVisitor();
		applications.accept(visitor2);
		System.out.println("The numbers of directories are: " + visitor2.getDirNum());
		System.out.println("The numbers of files are: " +visitor2.getFileNum());
		System.out.println("The numbers of links are: " + visitor2.getLinkNum());
		
		System.out.println();
		System.out.println("All files of root");
		ApfsFileCrawlingVisitor visitor3 = new ApfsFileCrawlingVisitor(); 
		root.accept( visitor3 );
		//print out all files not including links in the root
		for(ApfsFile apfs : visitor3.getFiles()) {
			System.out.println(apfs.getName());
		}
		
		System.out.println();
		System.out.println("ALl files of applications");
		ApfsFileCrawlingVisitor visitor3a = new ApfsFileCrawlingVisitor(); 
		applications.accept( visitor3a );
		//print out all files not including links in the root
		for(ApfsFile apfs : visitor3a.getFiles()) {
			System.out.println(apfs.getName());
		}
		
		System.out.println();
		System.out.println("Find the size of file a");
		//searching file a in the root directory
		ApfsFileSearchVisitor visitor4 = new ApfsFileSearchVisitor("a"); 
		root.accept( visitor4 ); 
		for(ApfsFile apfs : visitor4.getFoundFiles()) {
			System.out.println(apfs.getSize());
		}
		
		System.out.println();
		System.out.println("Find the size of file d");
		//searching file d in the root directory
		ApfsFileSearchVisitor visitor5 = new ApfsFileSearchVisitor("d"); 
		root.accept( visitor5 ); 
		for(ApfsFile apfs : visitor5.getFoundFiles()) {
			System.out.println(apfs.getSize());
		}
	}

}
