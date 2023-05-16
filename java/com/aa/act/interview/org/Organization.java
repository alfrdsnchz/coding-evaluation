package com.aa.act.interview.org;

import java.util.Optional;
import java.util.LinkedList; 

public abstract class Organization {

	private Position root;
	private int positionIdx; 

	public Organization() {
		root = createOrganization();
		positionIdx = 0; 
	}
	
	protected abstract Position createOrganization();
	
	/**
	 * hire the given person as an employee in the position that has that title
	 * 
	 * @param person
	 * @param title
	 * @return the newly filled position or empty if no position has that title
	 */
	public Optional<Position> hire(Name person, String title) {
		//your code here
		Employee newHire = new Employee(this.positionIdx, person);
		this.positionIdx++; 
		Optional<Position> titledPosition = findPositionByTitle(title); 

		titledPosition.get().setEmployee(Optional.of(newHire));

		return titledPosition;
	}

	@Override
	public String toString() {
		return printOrganization(root, "");
	}
	
	private String printOrganization(Position pos, String prefix) {
		StringBuffer sb = new StringBuffer(prefix + "+-" + pos.toString() + "\n");
		for(Position p : pos.getDirectReports()) {
			sb.append(printOrganization(p, prefix + "\t"));
		}
		return sb.toString();
	}

	private Optional<Position> findPositionByTitle(String title) {
		Position tmp = root; 
		LinkedList<Position> q = new LinkedList<Position>(); 
		q.addLast(tmp); 
		while(!q.isEmpty()) {
			Position boss = q.removeFirst();
			if (boss.getTitle().equals(title)) return Optional.of(boss);

			for (Position under : boss.getDirectReports() ) {
				q.addLast(under);
			}
		}
		return Optional.empty(); 
	}
}
