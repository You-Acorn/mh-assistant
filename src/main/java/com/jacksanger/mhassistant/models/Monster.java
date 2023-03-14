package com.jacksanger.mhassistant.models;

import javax.persistence.*;

import lombok.*;

/*
 * Object for monster information. Contains getters and setters
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "monsters")
public class Monster {
	@Id
	@Column(name = "monster", length = 50)
	private String monster;
	
	@Column(name = "ThreatLevel")
	private int threatLevel;
	
	@Column(name = "AttackAttribute", length = 20)
	private String attackAttribute;
	
	@Column(name = "WeaponVulnerability", length = 10)
	private String weaponVulnerability;
	
	@Column(name = "AttributeWeakness", length = 20)
	private String attributeWeakness;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((monster == null) ? 0 : monster.hashCode());
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
		Monster other = (Monster) obj;
		if (monster == null) {
			if (other.monster != null)
				return false;
		} else if (!monster.equals(other.monster))
			return false;
		return true;
	}
}
