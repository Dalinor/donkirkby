package com.google.code.donkirkby;

import org.junit.Assert;
import org.junit.Test;

public class SquarePathTest {
	@Test
	public void splitEastEast() throws Exception {
		// SETUP
		Path path = new SquarePath(new Cell(0, 0, 4, 4), SquarePath.East, SquarePath.East);
		Path[] expectedChildren = new Path[] {
			new SquarePath(new Cell(0, 0, 2, 2), SquarePath.NorthEast, SquarePath.East),
			new SquarePath(new Cell(2, 0, 4, 2), SquarePath.East, SquarePath.SouthWest),
			new SquarePath(new Cell(0, 2, 2, 4), SquarePath.SouthWest, SquarePath.East),
			new SquarePath(new Cell(2, 2, 4, 4), SquarePath.East, SquarePath.NorthEast),
		};
		
		// EXEC
		Path[] children = path.split();
		
		// VERIFY
		Assert.assertArrayEquals(expectedChildren, children);
	}

	@Test
	public void splitEastNorth() throws Exception {
		// SETUP
		Path path = new SquarePath(new Cell(0, 0, 4, 4), SquarePath.East, SquarePath.North);
		Path[] expectedChildren = new Path[] {
			new SquarePath(new Cell(0, 2, 2, 4), SquarePath.SouthEast, SquarePath.East),
			new SquarePath(new Cell(2, 2, 4, 4), SquarePath.East, SquarePath.North),
			new SquarePath(new Cell(2, 0, 4, 2), SquarePath.North, SquarePath.West),
			new SquarePath(new Cell(0, 0, 2, 2), SquarePath.West, SquarePath.NorthEast),
		};
		
		// EXEC
		Path[] children = path.split();
		
		// VERIFY
		Assert.assertArrayEquals(expectedChildren, children);
	}

	@Test
	public void splitNorthEast() throws Exception {
		// SETUP
		Path path = new SquarePath(new Cell(0, 0, 4, 4), SquarePath.North, SquarePath.East);
		Path[] expectedChildren = new Path[] {
			new SquarePath(new Cell(0, 2, 2, 4), SquarePath.NorthWest, SquarePath.North),
			new SquarePath(new Cell(0, 0, 2, 2), SquarePath.North, SquarePath.East),
			new SquarePath(new Cell(2, 0, 4, 2), SquarePath.East, SquarePath.South),
			new SquarePath(new Cell(2, 2, 4, 4), SquarePath.South, SquarePath.NorthEast),
		};
		
		// EXEC
		Path[] children = path.split();
		
		// VERIFY
		Assert.assertArrayEquals(expectedChildren, children);
	}

	@Test
	public void splitWestNorth() throws Exception {
		// SETUP
		Path path = new SquarePath(new Cell(0, 0, 4, 4), SquarePath.West, SquarePath.North);
		Path[] expectedChildren = new Path[] {
			new SquarePath(new Cell(2, 2, 4, 4), SquarePath.SouthWest, SquarePath.West),
			new SquarePath(new Cell(0, 2, 2, 4), SquarePath.West, SquarePath.North),
			new SquarePath(new Cell(0, 0, 2, 2), SquarePath.North, SquarePath.East),
			new SquarePath(new Cell(2, 0, 4, 2), SquarePath.East, SquarePath.NorthWest),
		};
		
		// EXEC
		Path[] children = path.split();
		
		// VERIFY
		Assert.assertArrayEquals(expectedChildren, children);
	}

	@Test
	public void splitNorthSouthWest() throws Exception {
		// SETUP
		Path path = new SquarePath(new Cell(10, 10, 12, 12), SquarePath.North, SquarePath.SouthWest);
		Path[] expectedChildren = new Path[] {
			new SquarePath(new Cell(11, 11, 12, 12), SquarePath.NorthEast, SquarePath.North),
			new SquarePath(new Cell(11, 10, 12, 11), SquarePath.North, SquarePath.West),
			new SquarePath(new Cell(10, 10, 11, 11), SquarePath.West, SquarePath.South),
			new SquarePath(new Cell(10, 11, 11, 12), SquarePath.South, SquarePath.SouthWest),
		};
		
		// EXEC
		Path[] children = path.split();
		
		// VERIFY
		Assert.assertArrayEquals(expectedChildren, children);
	}

	@Test
	public void splitNorthSouthEast() throws Exception {
		// SETUP
		Path path = new SquarePath(new Cell(10, 10, 12, 12), SquarePath.North, SquarePath.SouthEast);
		Path[] expectedChildren = new Path[] {
			new SquarePath(new Cell(10, 11, 11, 12), SquarePath.NorthWest, SquarePath.North),
			new SquarePath(new Cell(10, 10, 11, 11), SquarePath.North, SquarePath.East),
			new SquarePath(new Cell(11, 10, 12, 11), SquarePath.East, SquarePath.South),
			new SquarePath(new Cell(11, 11, 12, 12), SquarePath.South, SquarePath.SouthEast),
		};
		
		// EXEC
		Path[] children = path.split();
		
		// VERIFY
		Assert.assertArrayEquals(expectedChildren, children);
	}

	@Test
	public void splitNorthEastSouth() throws Exception {
		// SETUP
		Path path = new SquarePath(new Cell(10, 10, 12, 12), SquarePath.NorthEast, SquarePath.South);
		Path[] expectedChildren = new Path[] {
			new SquarePath(new Cell(10, 11, 11, 12), SquarePath.NorthEast, SquarePath.North),
			new SquarePath(new Cell(10, 10, 11, 11), SquarePath.North, SquarePath.East),
			new SquarePath(new Cell(11, 10, 12, 11), SquarePath.East, SquarePath.South),
			new SquarePath(new Cell(11, 11, 12, 12), SquarePath.South, SquarePath.SouthWest),
		};
		
		// EXEC
		Path[] children = path.split();
		
		// VERIFY
		Assert.assertArrayEquals(expectedChildren, children);
	}

	@Test
	public void splitSouthSouthWest() throws Exception {
		// SETUP
		Path path = new SquarePath(new Cell(10, 10, 12, 12), SquarePath.South, SquarePath.SouthWest);
		Path[] expectedChildren = new Path[] {
			new SquarePath(new Cell(11, 10, 12, 11), SquarePath.SouthEast, SquarePath.South),
			new SquarePath(new Cell(11, 11, 12, 12), SquarePath.South, SquarePath.NorthWest),
			new SquarePath(new Cell(10, 10, 11, 11), SquarePath.NorthWest, SquarePath.South),
			new SquarePath(new Cell(10, 11, 11, 12), SquarePath.South, SquarePath.SouthWest),
		};
		
		// EXEC
		Path[] children = path.split();
		
		// VERIFY
		Assert.assertArrayEquals(expectedChildren, children);
	}

	@Test
	public void splitWestEast() throws Exception {
		// SETUP
		Path path = new SquarePath(new Cell(0, 0, 4, 4), SquarePath.West, SquarePath.East);
		
		// EXEC
		String msg = null;
		try
		{
			path.split();
			Assert.fail("should have thrown.");
		}
		catch (IllegalStateException ex)
		{
			msg = ex.getMessage();
		}
		
		// VERIFY
		Assert.assertEquals("message", "Unexpected in/out directions: W, E.", msg);
	}

	@Test
	public void startingChain() throws Exception {
		// SETUP
		Path path = new SquarePath(new Cell(0, 0, 4, 4), SquarePath.West, SquarePath.North);
		
		// EXEC
		Path next = path.getNext();
		Path previous = path.getPrevious();
		
		// VERIFY
		Assert.assertEquals("next", path, next);
		Assert.assertEquals("previous", path, previous);
	}

	@Test
	public void append() throws Exception {
		// SETUP
		Path path1 = new SquarePath(new Cell(0, 0, 4, 4), SquarePath.West, SquarePath.North);
		Path path2 = new SquarePath(new Cell(0, 4, 4, 8), SquarePath.West, SquarePath.North);
		Path path3 = new SquarePath(new Cell(4, 4, 8, 8), SquarePath.West, SquarePath.North);
		
		// EXEC
		path1.append(path2);
		path2.append(path3);
		Path after1 = path1.getNext();
		Path before1 = path1.getPrevious();
		Path after2 = path2.getNext();
		Path before2 = path2.getPrevious();
		
		// VERIFY
		Assert.assertEquals("after1", path2, after1);
		Assert.assertEquals("before1", path3, before1);
		Assert.assertEquals("after2", path3, after2);
		Assert.assertEquals("before2", path1, before2);
	}

	@Test
	public void remove() throws Exception {
		// SETUP
		Path path1 = new SquarePath(new Cell(0, 0, 4, 4), SquarePath.West, SquarePath.North);
		Path path2 = new SquarePath(new Cell(0, 4, 4, 8), SquarePath.West, SquarePath.North);
		Path path3 = new SquarePath(new Cell(4, 4, 8, 8), SquarePath.West, SquarePath.North);
		
		// EXEC
		path1.append(path2);
		path2.append(path3);
		path2.remove();
		Path after1 = path1.getNext();
		Path before3 = path3.getPrevious();
		Path after2 = path2.getNext();
		Path before2 = path2.getPrevious();
		
		// VERIFY
		Assert.assertEquals("after1", path3, after1);
		Assert.assertEquals("before3", path1, before3);
		Assert.assertEquals("after2", path2, after2);
		Assert.assertEquals("before2", path2, before2);
	}

	@Test
	public void alreadyAppended() throws Exception {
		// SETUP
		Path path1 = new SquarePath(new Cell(0, 0, 4, 4), SquarePath.West, SquarePath.North);
		Path path2 = new SquarePath(new Cell(0, 4, 4, 8), SquarePath.West, SquarePath.North);
		Path path3 = new SquarePath(new Cell(4, 4, 8, 8), SquarePath.West, SquarePath.North);
		String msg = null;
		
		// EXEC
		path1.append(path2);
		try
		{
			path3.append(path2);
			Assert.fail("should have thrown.");
		}
		catch (IllegalStateException ex)
		{
			msg = ex.getMessage();
		}
		
		// VERIFY
		Assert.assertEquals(
				"message", 
				"Path cannot be appended if it is already attached.", 
				msg);
	}

	@Test
	public void splitChain() throws Exception {
		// SETUP
		Path path1 = new SquarePath(new Cell(0, 0, 4, 4), SquarePath.West, SquarePath.South);
		Path path2 = new SquarePath(new Cell(0, 4, 4, 8), SquarePath.South, SquarePath.East);
		Path path3 = new SquarePath(new Cell(4, 4, 8, 8), SquarePath.East, SquarePath.North);
		Path path4 = new SquarePath(new Cell(4, 0, 8, 4), SquarePath.North, SquarePath.West);
		
		// EXEC
		path1.append(path2);
		path2.append(path3);
		path3.append(path4);
		Path[] children = path1.split(); //1a, 1b, 1c, 1d
		Path after1d = children[3].getNext();
		Path before1a = children[0].getPrevious();
		Path after4 = path4.getNext();
		Path before2 = path2.getPrevious();
		
		// VERIFY
		Assert.assertEquals("after1d", path2, after1d);
		Assert.assertEquals("before1a", path4, before1a);
		Assert.assertEquals("after4", children[0], after4);
		Assert.assertEquals("before2", children[3], before2);
	}

	@Test
	public void getCoordinates() throws Exception {
		// SETUP
		Path path = new SquarePath(new Cell(0, 0, 4, 4), SquarePath.West, SquarePath.North);
		double [] expectedCoordinates = new double[] {4, 2, 2, 2, 2, 0};
		
		// EXEC
		double [] coordinates = path.getCoordinates();
		
		// VERIFY
		Assert.assertEquals(
				"length", 
				expectedCoordinates.length, 
				coordinates.length);
		for (int i = 0; i < coordinates.length; i++) {
			
			Assert.assertEquals(
					"coordinate " + i, 
					expectedCoordinates[i], 
					coordinates[i],
					0.001);
		}
	}

	@Test
	public void getLength() throws Exception {
		// SETUP
		Path path1 = new SquarePath(new Cell(10, 0, 14, 4), SquarePath.West, SquarePath.North);
		double expectedLength1 = 4;
		Path path2 = new SquarePath(new Cell(10, 0, 12, 2), SquarePath.West, SquarePath.North);
		double expectedLength2 = 2;
		Path path3 = new SquarePath(new Cell(10, 0, 12, 2), SquarePath.West, SquarePath.NorthWest);
		double expectedLength3 = 1 + Math.sqrt(2);
		path1.append(path2);
		path2.append(path3);
		double expectedTotalLength = 
				expectedLength1 + expectedLength2 + expectedLength3;
		
		// EXEC
		double length1 = path1.getLength();
		double length2 = path2.getLength();
		double length3 = path3.getLength();
		double totalLength = path1.getTotalLength();
		
		// VERIFY
		Assert.assertEquals(
				"length1",
				expectedLength1,
				length1, 
				0.0001);
		Assert.assertEquals(
				"length2",
				expectedLength2,
				length2, 
				0.0001);
		Assert.assertEquals(
				"length3",
				expectedLength3,
				length3, 
				0.0001);
		Assert.assertEquals(
				"total length",
				expectedTotalLength,
				totalLength, 
				0.0001);
	}

	@Test
	public void equals() throws Exception {
		// SETUP
		Cell cell1 = new Cell(0, 0, 2, 2);
		Cell cell2 = new Cell(0, 0, 2, 2);
		Cell cell3 = new Cell(0, 0, 3, 3);
		Path path1 = new SquarePath(cell1, SquarePath.North, SquarePath.West);
		Path path2 = new SquarePath(cell2, SquarePath.North, SquarePath.West);
		Path path2b = new SquarePath(cell2, SquarePath.North, SquarePath.East);
		Path path3 = new SquarePath(cell3, SquarePath.North, SquarePath.West);
		
		// EXEC
		boolean isEqualToSame = path1.equals(path2);
		boolean isEqualToDifferentInOut = path1.equals(path2b);
		boolean isEqualToDifferentCell = path1.equals(path3);

		// VERIFY
		Assert.assertEquals(
				"Equals similar path", 
				true,
				isEqualToSame);
		Assert.assertEquals(
				"Equals different in/out", 
				false, 
				isEqualToDifferentInOut);
		Assert.assertEquals(
				"Equals different cell", 
				false, 
				isEqualToDifferentCell);
	}

	@Test
	public void string() throws Exception {
		// SETUP
		Cell cell = new Cell(0, 0, 2, 2);
		Path path1 = new SquarePath(cell, SquarePath.North, SquarePath.West);
		
		// EXEC
		String string = path1.toString();

		// VERIFY
		Assert.assertEquals(
				"String matches", 
				"SquarePath(Cell(0, 0, 2, 2), N, W)", 
				string);
	}
}
