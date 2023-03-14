package com.jacksanger.mhassistant.models;

import java.util.Set;

import javax.persistence.*;

import lombok.*;

/*
 * Object for quests. Contains getters and setters
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "quests")
public class Quest {
	@Id
	@Column(name = "QuestNumber")
	private int id;
	
	@Column(name = "Name", length = 50)
	private String name;
	
	@Column(name = "quest_rank")
	private int questRank;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "quest_type_id")
    private QuestType questType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "region_id")
    private Region region;
	
	@Column(name = "Reward")
	private int reward;
	
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "quest_details",
            joinColumns = @JoinColumn(name = "quest_number"),
            inverseJoinColumns = @JoinColumn(name = "monster")
    )
    private Set<Monster> monsters;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((monsters == null) ? 0 : monsters.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + questRank;
		result = prime * result + ((questType == null) ? 0 : questType.hashCode());
		result = prime * result + ((region == null) ? 0 : region.hashCode());
		result = prime * result + reward;
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
		Quest other = (Quest) obj;
		if (id != other.id)
			return false;
		if (monsters == null) {
			if (other.monsters != null)
				return false;
		} else if (!monsters.equals(other.monsters))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (questRank != other.questRank)
			return false;
		if (questType == null) {
			if (other.questType != null)
				return false;
		} else if (!questType.equals(other.questType))
			return false;
		if (region == null) {
			if (other.region != null)
				return false;
		} else if (!region.equals(other.region))
			return false;
		if (reward != other.reward)
			return false;
		return true;
	}

}
