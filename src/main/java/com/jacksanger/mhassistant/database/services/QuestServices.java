package com.jacksanger.mhassistant.database.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jacksanger.mhassistant.database.repositories.QuestRepository;
import com.jacksanger.mhassistant.models.Monster;
import com.jacksanger.mhassistant.models.Quest;

/*
 * Class containing methods to facilitate CRUD operations (currently only contains read) relevant to quests
 */

@Service
public class QuestServices {
	@Autowired
	private QuestRepository questRepository;
	
	//Takes the int id of a quest and returns the quest
	public Optional<Quest> getQuestById(int id) {
		return questRepository.findById(id);
	}
	
	//Takes a set of monsters and returns a list of quests which contain all of said monsters
	//Does not always return the same quests given the same set of monsters
	public List<Quest> getQuestsByMonsters(Set<Monster> monsters) {	
		List<Quest> quests = //questRepository.getQuestsByMonstersIn(monsters);
								new ArrayList<Quest>();

		//Add quests with two+ monsters
		for(Monster mon : monsters) {
			Set<Monster> monster = new HashSet<Monster>();
			monster.add(mon);
			List<Quest> subQuests = questRepository.getQuestsByMonstersIn(monster);
			
			for(Quest subQuest : subQuests) {
				for(Monster otherMon : monsters) {
					if(mon != otherMon) {
						
						if(subQuest.getMonsters().contains(otherMon)) {
							quests.add(subQuest);
						}
					}
				}
			}	
		}
		
		//Remove duplicates
		if(!quests.isEmpty()) {
			Set<Quest> removeDuplicates = new HashSet<Quest>(quests);
			quests.clear();
			quests.addAll(removeDuplicates);
		
			//Remove monsters that have a quest
			Set<Monster> monstersToRemove = new HashSet<Monster>();
			for(Monster mon : monsters) {
				for(Quest subQuest : quests) {
					if (subQuest.getMonsters().contains(mon)) {
						monstersToRemove.add(mon);
					}
				}
			}
			monsters.removeAll(monstersToRemove);
		}
		
		
		//Add monsters that will be in a quest by themselves
		for(Monster mon : monsters) {
			Set<Monster> monster = new HashSet<Monster>();
			monster.add(mon);
			List<Quest> subQuests = questRepository.getQuestsByMonstersIn(monster);
			int randomIndex = (int)Math.floor(Math.random() * (subQuests.size()));
			quests.add(subQuests.get(randomIndex));
		}
		
		return quests;
	}
}