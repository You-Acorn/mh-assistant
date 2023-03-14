package com.jacksanger.mhassistant.models;

import javax.persistence.*;

import lombok.*;

/*
 * Object for quest type information (primary key and quest type). Contains getters and setters
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "quest_type")
public class QuestType {
	@Id
	@Column(name = "quest_type_id")
	private int id;
	
	@Column(name = "quest_type")
	private String type;
	
	@Override
	public String toString() {
		return type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QuestType other = (QuestType) obj;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
}
