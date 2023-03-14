package com.jacksanger.mhassistant.database.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jacksanger.mhassistant.models.Monster;
import com.jacksanger.mhassistant.models.Quest;

/*
 * Interface which extends the jpa repository and connections the java application to the quests table
 */

public interface QuestRepository extends JpaRepository<Quest, Integer> {
	//Takes a set of monsters and returns a list of quests which contain at least one of the given monsters
	List<Quest> getQuestsByMonstersIn(Set<Monster> monsters);
}
