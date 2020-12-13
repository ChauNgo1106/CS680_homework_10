package edu.umb.cs680.hw10.apfs;

import static org.junit.jupiter.api.Assertions.*;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


//import edu.umb.cs680.hw09.fat.FAT;

public class APFSTest {
	// setting up object
	private static ApfsDirectory root;
	private static ApfsDirectory home;
	private static ApfsDirectory applications;
	private static ApfsDirectory code;
	private static ApfsFile a;
	private static ApfsFile b;
	private static ApfsFile c;
	private static ApfsFile d;
	private static ApfsFile e;
	private static ApfsFile f;
	private static ApfsLink x;
	private static ApfsLink y;
	private static ApfsLink z;
	private static ApfsCountingVisitor visitor1;
	private static ApfsCountingVisitor visitor2;
	private static ApfsFileCrawlingVisitor visitor3;
	private static ApfsFileCrawlingVisitor visitor4;
	private static ApfsFileSearchVisitor visitor5;
	private static ApfsFileSearchVisitor visitor6;

	@BeforeAll
	public static void setUp() {
		// initialize APFS file system
		APFS.getFileSystem().initFileSystemAPFS("APFS", 10240);

		// assign root in APFS system into root variable
		root = APFS.getFileSystem().getRootDir();

		applications = new ApfsDirectory(root, "applications", 0, "Chau Ngo",
				LocalDateTime.of(LocalDate.of(2019, 9, 15), LocalTime.of(13, 00, 40)),
				LocalDateTime.of(LocalDate.of(2019, 9, 15), LocalTime.of(13, 00, 40)));

		home = new ApfsDirectory(root, "home", 0, "Chau Ngo",
				LocalDateTime.of(LocalDate.of(2019, 10, 15), LocalTime.of(20, 30, 00)),
				LocalDateTime.of(LocalDate.of(2019, 10, 15), LocalTime.of(20, 30, 00)));
		code = new ApfsDirectory(home, "code", 0, "Chau Ngo",
				LocalDateTime.of(LocalDate.of(2019, 11, 15), LocalTime.of(20, 31, 00)),
				LocalDateTime.of(LocalDate.of(2019, 11, 15), LocalTime.of(20, 31, 00)));

		a = new ApfsFile(applications, "a", 2048, "Chau Ngo",
				LocalDateTime.of(LocalDate.of(2019, 9, 15), LocalTime.of(13, 01, 00)),
				LocalDateTime.of(LocalDate.of(2019, 9, 15), LocalTime.of(13, 01, 00)));
		b = new ApfsFile(applications, "b", 1024, "Chau Ngo",
				LocalDateTime.of(LocalDate.of(2019, 9, 15), LocalTime.of(13, 10, 00)),
				LocalDateTime.of(LocalDate.of(2019, 9, 15), LocalTime.of(13, 10, 00)));

		c = new ApfsFile(home, "c", 2048, "Chau Ngo",
				LocalDateTime.of(LocalDate.of(2019, 10, 15), LocalTime.of(21, 32, 45)),
				LocalDateTime.of(LocalDate.of(2019, 10, 15), LocalTime.of(21, 32, 45)));
		d = new ApfsFile(home, "d", 4096, "Chau Ngo",
				LocalDateTime.of(LocalDate.of(2019, 9, 15), LocalTime.of(21, 45, 18)),
				LocalDateTime.of(LocalDate.of(2019, 9, 15), LocalTime.of(21, 45, 18)));

		e = new ApfsFile(code, "e", 1024, "Chau Ngo",
				LocalDateTime.of(LocalDate.of(2019, 11, 15), LocalTime.of(20, 40, 59)),
				LocalDateTime.of(LocalDate.of(2019, 11, 15), LocalTime.of(20, 40, 59)));
		f = new ApfsFile(code, "f", 2048, "Chau Ngo",
				LocalDateTime.of(LocalDate.of(2019, 11, 15), LocalTime.of(20, 45, 51)),
				LocalDateTime.of(LocalDate.of(2019, 11, 15), LocalTime.of(20, 45, 51)));
		// symbolic Link
		x = new ApfsLink(home, "x", 0, "Chau Ngo",
				LocalDateTime.of(LocalDate.of(2019, 11, 17), LocalTime.of(20, 46, 00)),
				LocalDateTime.of(LocalDate.of(2019, 11, 17), LocalTime.of(20, 46, 00)), applications);
		y = new ApfsLink(code, "y", 0, "Chau Ngo",
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

		// Testing visitors
		visitor1 = new ApfsCountingVisitor();
		root.accept(visitor1);

		visitor2 = new ApfsCountingVisitor();
		applications.accept(visitor2);

		visitor3 = new ApfsFileCrawlingVisitor();
		root.accept(visitor3);

		visitor4 = new ApfsFileCrawlingVisitor();
		applications.accept(visitor4);

		// searching file a in the root directory
		visitor5 = new ApfsFileSearchVisitor("a");
		root.accept(visitor4);

		// searching file d in the root directory
		visitor6 = new ApfsFileSearchVisitor("d");
		root.accept(visitor5);

	}

	@Test
	public void checking_type_of_all_visitor_variables() {
		assertTrue(visitor1 instanceof ApfsVisitor);
		assertTrue(visitor2 instanceof ApfsVisitor);
		assertTrue(visitor3 instanceof ApfsVisitor);
		assertTrue(visitor4 instanceof ApfsVisitor);
		assertTrue(visitor5 instanceof ApfsVisitor);
		assertTrue(visitor6 instanceof ApfsVisitor);

		assertTrue(visitor1 instanceof ApfsCountingVisitor);
		assertTrue(visitor2 instanceof ApfsCountingVisitor);
		assertTrue(visitor3 instanceof ApfsFileCrawlingVisitor);
		assertTrue(visitor4 instanceof ApfsFileCrawlingVisitor);
		assertTrue(visitor5 instanceof ApfsFileSearchVisitor);
		assertTrue(visitor6 instanceof ApfsFileSearchVisitor);

	}

	@Test
	public void checking_number_of_directories_and_files_and_links_of_root() {
		int[] expected = { 4, 6, 2 };
		int[] result = { visitor1.getDirNum(), visitor1.getFileNum(), visitor1.getLinkNum() };
		assertArrayEquals(expected, result);

	}

	@Test
	public void checking_number_of_directories_and_files_and_links_of_applications() {
		int[] expected = { 1, 2, 0 };
		int[] result = { visitor2.getDirNum(), visitor2.getFileNum(), visitor2.getLinkNum() };
		assertArrayEquals(expected, result);

	}

	@Test
	public void getting_files_of_root() {
		String[] expected = { "a", "b", "e", "f", "c", "d" };
		String[] result = new String[visitor3.getFiles().size()];
		int i = 0;
		for (ApfsFile apfs : visitor3.getFiles()) {
			result[i++] = apfs.getName();
		}

		assertArrayEquals(expected, result);
		// reset the LinkedList contain Files
	}

	@Test
	public void finding_file_named_a() {
		// searching file a in the root directory
		int expectedSize = 2048;
		String expectedName = "a";
		for (ApfsFile apfs : visitor5.getFoundFiles()) {
			assertEquals(expectedSize, apfs.getSize());
			assertEquals(expectedName, apfs.getName() );
		}

	}

	@Test
	public void finding_file_named_d() {
		// searching file a in the root directory
		int expected = 4096;
		String expectedName = "d";
		for (ApfsFile apfs : visitor6.getFoundFiles()) {
			assertEquals(expected, apfs.getSize());
			assertEquals(expectedName, apfs.getName() );
		}

	}

}
