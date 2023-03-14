package com.jacksanger.mhassistant.database.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jacksanger.mhassistant.database.repositories.MonsterRepository;
import com.jacksanger.mhassistant.models.Monster;

/*
 * Class containing methods to facilitate CRUD operations relevant to monsters
 */

@Service
public class MonsterServices {
	@Autowired
	private MonsterRepository monsterRepository;
	
	//Takes a weapon vulnerability and attribute weakness and returns a list of monsters with those attributes
	public List<Monster> findByWeakness(String weaponVulnerability, String attributeWeakness) {
		return monsterRepository.findMonstersByweaponVulnerabilityAndAttributeWeakness(weaponVulnerability, attributeWeakness);
	}
	//Takes an attribute weakness and returns a list of monsters with that weakness
	public List<Monster> findByAttribute(String attributeWeakness) {
		return monsterRepository.findMonstersByAttributeWeakness(attributeWeakness);
	}
	//Takes a weapon vulnerability and returns a list of monsters with that vulnerability
	public List<Monster> findByVulnerability(String weaponVulnerability) {
		return monsterRepository.findMonstersByWeaponVulnerability(weaponVulnerability);
	}
	//Returns a list of all monsters
	public List<Monster> getAllMonsters() {
		return monsterRepository.findAll();
	}
	//Takes the name of a monster and returns the relevant monster
	public Optional<Monster> findByName(String name) {
		return monsterRepository.findById(name);
	}
	//Takes a monster and updates the current monster in the database with the values of the input monster
	//If values for the input monster are blank the values of the old monster are not changed
	public boolean updateMonster(Monster monster) {
		Optional<Monster> oldMon = findByName(monster.getMonster());
		if(oldMon.isEmpty()) {
			return false;
		}
		
		if(monster.getThreatLevel() <= 0) {
			monster.setThreatLevel(oldMon.get().getThreatLevel());
		}
		if(monster.getAttackAttribute().isBlank()) {
			monster.setAttackAttribute(oldMon.get().getAttackAttribute());
		}
		if(monster.getWeaponVulnerability().isBlank()) {
			monster.setWeaponVulnerability(oldMon.get().getWeaponVulnerability());
		}
		if(monster.getAttributeWeakness().isBlank()) {
			monster.setAttributeWeakness(oldMon.get().getAttributeWeakness());
		}
		int updatedRows = monsterRepository.modifyMonster(monster.getThreatLevel(), 
														monster.getAttackAttribute(), 
														monster.getWeaponVulnerability(), 
														monster.getAttributeWeakness(), 
														monster.getMonster());
		return updatedRows > 0;
	}
	//Takes a monster and adds it to the database
	public void addMonster(Monster monster) {
		monsterRepository.addMonster(monster.getMonster(), 
				monster.getThreatLevel(), 
				monster.getAttackAttribute(), 
				monster.getWeaponVulnerability(), 
				monster.getAttributeWeakness());
	}
	//Takes a monster and deletes a monster with the same name from the database
	public boolean deleteMonster(Monster monster) {
		return monsterRepository.deleteMonster(monster.getMonster()) > 0;
	}
}
