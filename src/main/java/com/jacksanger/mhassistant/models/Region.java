package com.jacksanger.mhassistant.models;

import javax.persistence.*;

import lombok.*;

/*
 * Object for region information (primary key and region name). Contains getters and setters
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "regions")
public class Region {
	@Id
	@Column(name = "region_id")
	private int id;
	
	@Column(name = "region")
	private String region;

	@Override
	public String toString() {
		return region;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((region == null) ? 0 : region.hashCode());
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
		Region other = (Region) obj;
		if (region == null) {
			if (other.region != null)
				return false;
		} else if (!region.equals(other.region))
			return false;
		return true;
	}

}
