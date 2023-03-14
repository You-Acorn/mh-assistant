package com.jacksanger.mhassistant.database.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.jacksanger.mhassistant.models.Monster;

/*
 * Interface which extends the jpa repository and connections the java application to the monster table
 */

public interface MonsterRepository extends JpaRepository<Monster, String> {
	//Takes a weapon vulnerability and attribute weakness and returns a list of monsters with those attributes
	List<Monster> findMonstersByweaponVulnerabilityAndAttributeWeakness(String weaponVulnerability, String attributeWeakness);
	//Takes an attribute weakness and returns a list of monsters with that weakness
	List<Monster> findMonstersByAttributeWeakness(String attributeWeakness);
	//Takes a weapon vulnerability and returns a list of monsters with that vulnerability
	List<Monster> findMonstersByWeaponVulnerability(String weaponVulnerability);
	
	//Takes the elements of a monster and updates the monster with the same name with the given values and returns the number of rows modified
	@Modifying
	@Query("UPDATE Monster m SET m.threatLevel = :threatLevel, "
			+ "m.attackAttribute = :attackAttribute, "
			+ "m.weaponVulnerability = :weaponVulnerability, "
			+ "m.attributeWeakness = :attributeWeakness "
			+ "WHERE m.monster = :mon")
	@Transactional
	int modifyMonster(int threatLevel, String attackAttribute, String weaponVulnerability, String attributeWeakness, String mon);
	
	//Takes the elements of a monster and adds the monster to the database
	@Modifying
	@Query(value = "INSERT INTO Monsters (monster, threat_level, attack_attribute, weapon_vulnerability, attribute_weakness) "
			+ "VALUES (:mon, :threatLevel, :attackAttribute, :weaponVulnerability, :attributeWeakness)", 
			countQuery = "select 1", nativeQuery = true)
	@Transactional
	void addMonster(@Param("mon") String mon, @Param("threatLevel") int threatLevel, @Param("attackAttribute") String attackAttribute, 
			@Param("weaponVulnerability") String weaponVulnerability, @Param("attributeWeakness") String attributeWeakness);
	
	//Takes the name of a monster and deletes the relevant monster from the database and returns the number of rows modified
	@Modifying
	@Query("DELETE FROM Monster m WHERE m.monster = :mon")
	@Transactional
	int deleteMonster(String mon);
}
