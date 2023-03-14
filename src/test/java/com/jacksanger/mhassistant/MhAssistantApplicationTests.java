package com.jacksanger.mhassistant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import com.jacksanger.mhassistant.database.repositories.MonsterRepository;
import com.jacksanger.mhassistant.database.repositories.QuestRepository;
import com.jacksanger.mhassistant.database.services.MonsterServices;
import com.jacksanger.mhassistant.database.services.QuestServices;
import com.jacksanger.mhassistant.models.Monster;
import com.jacksanger.mhassistant.models.Quest;
import com.jacksanger.mhassistant.models.QuestType;
import com.jacksanger.mhassistant.models.Region;
import com.jacksanger.mhassistant.security.database.UserRepository;
import com.jacksanger.mhassistant.security.database.UserServiceImpl;
import com.jacksanger.mhassistant.security.models.User;

/*
 * Testing class using JUnit5 to unit test the repository interfaces and service classes
 */
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class MhAssistantApplicationTests {
	
	private QuestRepository questRepo;
	private MonsterRepository monRepo;
	private QuestServices questSvc;
	private MonsterServices monSvc;
	
	private UserRepository userRepo;
	private UserServiceImpl userSvc;
	
	@Autowired
	public MhAssistantApplicationTests(
			QuestRepository questRepo, 
			MonsterRepository monRepo,
			QuestServices questSvc, 
			MonsterServices monSvc,
			UserRepository userRepo,
			UserServiceImpl userSvc) {
		this.questRepo = questRepo;
		this.monRepo = monRepo;
		this.questSvc = questSvc;
		this.monSvc = monSvc;
		
		this.userRepo = userRepo;
		this.userSvc = userSvc;
	}

	//Context test
	@Test
	@Order(1)
	void questRepositoryContextLoads() {
		assertNotNull(questRepo);
		assertNotNull(monRepo);
		assertNotNull(questSvc);
		assertNotNull(monSvc);
		
		assertNotNull(userRepo);
		assertNotNull(userSvc);
	}
	
	//Quest Repository Tests
	//Test if quest repo retrieves the correct quests given a set of monsters
	@Test
	@Order(2)
	public void testGetQuestsFromMonstersRepo() {
		HashSet<Monster> searchedMonsters = new HashSet<Monster>();
		searchedMonsters.add(new Monster("Great Wroggi", 2, "Poison", "Slash", "Paralysis"));
		
		List<Quest> actualQuests = (ArrayList<Quest>) questRepo.getQuestsByMonstersIn(searchedMonsters);
		
		assertNotNull(actualQuests);
		assertEquals("Learning the Light Bowgun", actualQuests.get(0).getName());
	}
	//Test if quest repo fails to return a quest given monsters with no quest
	@Test
	@Order(13)
	public void testNegativeGetQuestsFromMonstersRepo() {
		HashSet<Monster> searchedMonsters = new HashSet<Monster>();
		searchedMonsters.add(new Monster("Not a real monster", 2, "Poison", "Slash", "Paralysis"));
		
		List<Quest> actualQuests = (ArrayList<Quest>) questRepo.getQuestsByMonstersIn(searchedMonsters);
		
		assertEquals(actualQuests.size(), 0);
	}
	
	//Monster Repository Tests
	//Test finding monsters by their combined weakness
	@Test
	@Order(3)
	public void testFindMonserWithVulnerabilityAndWeaknessFromRepo() {
		List<Monster> foundMonsters = monRepo.findMonstersByweaponVulnerabilityAndAttributeWeakness("Slash", "Blast");
		for(Monster monster : foundMonsters) {
			assertEquals("Slash", monster.getWeaponVulnerability());
			assertEquals("Blast", monster.getAttributeWeakness());
		}
	}
	//Test not finding monsters when combined weakness doesn't exist
	@Test
	@Order(14)
	public void testNegativeFindMonserWithVulnerabilityAndWeaknessFromRepo() {
		List<Monster> foundMonsters = monRepo.findMonstersByweaponVulnerabilityAndAttributeWeakness("Does not exist", "Does not exist");
		assertEquals(foundMonsters.size(), 0);
	}
	//Test finding monsters by their attribute weakness
	@ParameterizedTest
	@ValueSource(strings = {"Poison", "Blast", "Paralysis", "Sleep", "Water", "Fire"})
	@Order(4)
	public void testFindMonserWithWeaknessFromRepo(String weakness) {
		List<Monster> foundMonsters = monRepo.findMonstersByAttributeWeakness(weakness);
		for(Monster monster : foundMonsters) {
			assertEquals(weakness, monster.getAttributeWeakness());
		}
	}
	//Test not finding monsters when their weakness doesn't exist
	@Test
	@Order(15)
	public void testNegativeFindMonserWithWeaknessFromRepo() {
		List<Monster> foundMonsters = monRepo.findMonstersByAttributeWeakness("Does not exist");
		assertEquals(foundMonsters.size(), 0);
	}
	//Test finding monsters by their weapon vulnerability
	@ParameterizedTest
	@ValueSource(strings = {"Blunt", "Slash", "Ammo"})
	@Order(5)
	public void testFindMonserWithVulnerabilityFromRepo(String vulnerability) {
		List<Monster> foundMonsters = monRepo.findMonstersByWeaponVulnerability(vulnerability);
		for(Monster monster : foundMonsters) {
			assertEquals(vulnerability, monster.getWeaponVulnerability());
		}
	}
	//Test not finding monsters when their vulnerability doesn't exist
	@Test
	@Order(16)
	public void testNegativeFindMonserWithVulnerabilityFromRepo() {
		List<Monster> foundMonsters = monRepo.findMonstersByWeaponVulnerability("Does not exist");
		assertEquals(foundMonsters.size(), 0);
	}
	//Test if monster repo adds a monster to the database
	@Test
	@Order(6)
	public void testAddMonsterFromRepo() {
		Optional<Monster> testMon = monRepo.findById("Test Monster");
		assertTrue(testMon.isEmpty());
		
		monRepo.addMonster("Test Monster", 9, "Blast", "Blunt", "Water");
		Optional<Monster> testMonster = monRepo.findById("Test Monster");
		assertFalse(testMonster.isEmpty());
		assertEquals("Test Monster", testMonster.get().getMonster());
		assertEquals(9, testMonster.get().getThreatLevel());
		assertEquals("Blast", testMonster.get().getAttackAttribute());
		assertEquals("Blunt", testMonster.get().getWeaponVulnerability());
		assertEquals("Water", testMonster.get().getAttributeWeakness());
	}
	//Test if monster repo modifies a monster correctly
	@Test
	@Order(7)
	public void testModifyMonsterFromRepo() {
		int rowsModified = monRepo.modifyMonster(1, "", "", "", "Test Monster");
		
		assertEquals(1, rowsModified);
		
		Optional<Monster> testMonster = monRepo.findById("Test Monster");
		assertEquals(1, testMonster.get().getThreatLevel());
	}
	//Test if monster repo deletes monster
	@Test
	@Order(8)
	public void testDeleteMonsterFromRepo() {
		Optional<Monster> testMon = monRepo.findById("Test Monster");
		assertFalse(testMon.isEmpty());
		
		int rowsDeleted = monRepo.deleteMonster("Test Monster");
		
		assertEquals(1, rowsDeleted);
		
		Optional<Monster> testMonster = monRepo.findById("Test Monster");
		assertTrue(testMonster.isEmpty());
	}
	
	//Quest Service Tests
	//Test if quest service gets quests from monster set correctly
	@Test
	@Order(9)
	public void testGetQuestsByMonstersService() {
		Set<Monster> monsters = new HashSet<Monster>();
		monsters.add(monRepo.findById("Great Izuchi").get());
		monsters.add(monRepo.findById("Tetranadon").get());
		
		List<Quest> returnedQuests = questSvc.getQuestsByMonsters(monsters);
		
		boolean greatIzuchiPresent = false;
		boolean tetranadonPresent = false;
		for(Quest quest : returnedQuests) {
			for(Monster monster : quest.getMonsters()) {
				if(monster.getMonster().equals("Great Izuchi")) {
					greatIzuchiPresent = true;
				}
				if(monster.getMonster().equals("Tetranadon")) {
					tetranadonPresent = true;
				}
			}
		}
		assertTrue(greatIzuchiPresent);
		assertTrue(tetranadonPresent);
	}
	//Test if quest service doesn't get quests when not given real monsters
	@Test
	@Order(17)
	public void testNegativeGetQuestsByMonstersService() {
		try {
			Set<Monster> monsters = new HashSet<Monster>();
			monsters.add(new Monster("Not a real monster", 0, "", "", ""));
			
			List<Quest> returnedQuests = questSvc.getQuestsByMonsters(monsters);
			assertEquals(returnedQuests.size(), 0);
		} catch(Exception e) {
			assertTrue(true);
		}
	}
	
	//Monster Service Tests
	//Test if monster service gets monster from monster name correctly
	@Test
	@Order(10)
	public void testGetMonsterByNameService() {
		Optional<Monster> actualMonster = monSvc.findByName("Great Izuchi");
		
		assertFalse(actualMonster.isEmpty());
		assertEquals("Great Izuchi", actualMonster.get().getMonster());
		
	}
	//Test if monster service doesn't get monster from no monster name
	@Test
	@Order(18)
	public void testNegativeGetMonsterByNameService() {
		Optional<Monster> actualMonster = monSvc.findByName("Not a real monster");
		
		assertTrue(actualMonster.isEmpty());
	}
	
	//User Repository Tests
	//Test if user repository retrieves correct user by username
	@Test
	@Order(11)
	public void testGetUserRepository() {
		User foundUser = userRepo.findByUserName("JSanger1");
		
		assertNotNull(foundUser);
		assertEquals("JSanger1", foundUser.getUserName());
	}
	//Test if user repository fails to retrieve user with no username
	@Test
	@Order(19)
	public void testNegativeGetUserRepository() {
		User foundUser = userRepo.findByUserName("Not a username");
		
		assertNull(foundUser);
	}
	
	//User Service Tests
	//Test if user service retrieves correct user by username
	@Test
	@Order(12)
	public void testGetUserService() {
		User foundUser = userSvc.findByUserName("JSanger1");
		
		assertNotNull(foundUser);
		assertEquals("JSanger1", foundUser.getUserName());
	}
	//Test if user service retrieves correct user by username
	@Test
	@Order(20)
	public void testNegativeGetUserService() {
		User foundUser = userSvc.findByUserName("Not a username");
		
		assertNull(foundUser);
	}
}
