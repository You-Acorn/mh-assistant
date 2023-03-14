package com.jacksanger.mhassistant.web.user;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jacksanger.mhassistant.database.services.MonsterServices;
import com.jacksanger.mhassistant.database.services.QuestServices;
import com.jacksanger.mhassistant.models.Monster;
import com.jacksanger.mhassistant.models.Quest;

/*
 * Controller for user pages. Navigates a user between pages a user has access to
 */

@Controller
public class UserController {
	@Autowired
	protected QuestServices questServices;
	
	@Autowired
	protected MonsterServices monsterServices;
	
	Set<Monster> monsters;
	Set<Quest> quests;
	
	//Navigates the user to the home page
	@RequestMapping("/")
	public String home() {
		return "index";
	}
	
	//Navigates the user to the find quest home page and creates a set of monsters to use in the search
	@GetMapping("/findQuests")
	public String findQuest() {
		monsters = new HashSet<Monster>();
		return "find_quest_home";
	}
	
		//Navigates the user to the find quest by id page which creates an object to submit the id to
		@GetMapping("/findQuestsById")
		public String findQuestByIdHome(Model model) {
			model.addAttribute("questId", new Quest());
			return "find_quest_by_id";
		}
		
			//Find a quest given the id in the previous page
			//Then navigates the user to the results of the find quest by id page or the no results found page if no result is found
			@GetMapping("/findQuestByIdSearch")
			public String indQuestByIdSearch(Model model, Quest questId) {
				int id = questId.getId();
				
				Optional<Quest> quest = questServices.getQuestById(id);
				if(quest.isPresent()) {
					model.addAttribute("quests", quest.get());
					return "find_quest_results";
				}
					return "error";
			}
	
		//Navigates the user to the find quests by monsters monster selection page, finds all the monsters to be displayed, and creates an object to store the chosen monster
		@GetMapping("/findQuestsByMonster")
		public String findQuestsHome(Model model) {
			model.addAttribute("allMonsters", monsterServices.getAllMonsters());
			model.addAttribute("chosenMonster", new Monster());
			return "find_quest_by_monster";
		}
	
			//Navigates the user to the results of their chosen monster which prompts them with the choice to add more monsters or go to results
			@GetMapping("/findQuestsByMonster/addmonster")
			public String findQuests(@ModelAttribute("chosenMonster") Monster chosenMonster, Model model) {
				chosenMonster = monsterServices.findByName(chosenMonster.getMonster()).get();
				monsters.add(chosenMonster);
				model.addAttribute("monsters", monsters);
				
				return "find_quest_by_monster_confirm";
			}
			
			//Finds all the relevant quests and navigates the user to the find quests from monsters results page
			@GetMapping("/findQuestsByMonster/results")
			public String foundQuests(Model model) {
				quests = new HashSet<Quest>();
				quests.addAll(questServices.getQuestsByMonsters(monsters));
				model.addAttribute("quests", quests);
				return "find_quest_results";
			}
			
		//Navigates the user to the find monster page and creates an object to store the search criteria
		@GetMapping("/findmonster")
		public String findMonster(Model model) {
			model.addAttribute("weakness", new Monster());
			return "find_monster";
		}
		
		//Finds the relevant monsters and navigates the user to the results of the find monster page or the no results page
		@GetMapping("/findmonster/results")
		public String findMonsterResults(@ModelAttribute("weakness") Monster weakness, Model model) {
			monsters = new HashSet<Monster>();
			if(weakness.getWeaponVulnerability() != "" && weakness.getAttributeWeakness() != "") {
				monsters.addAll(monsterServices.findByWeakness(weakness.getWeaponVulnerability(), weakness.getAttributeWeakness()));
			} else if (weakness.getAttributeWeakness() != "") {
				monsters.addAll(monsterServices.findByAttribute(weakness.getAttributeWeakness()));
			} else if (weakness.getWeaponVulnerability() != "") {
				monsters.addAll(monsterServices.findByVulnerability(weakness.getWeaponVulnerability()));
			} else {
				return "error";
			}
			model.addAttribute("monsters", monsters);
			
			
			return "find_monster_results";
		}
		
		//Navigates the user to a page of details about the path variable monster
		@GetMapping("/monsterDetails/{monster}")
		public String getMonsterDetails(Model model, @PathVariable("monster") String monsterName) {
			Monster monster = monsterServices.findByName(monsterName).get();
			model.addAttribute("monster", monster);
			return "monster_details";
		}
		
	//Navigates the user to the about page
	@GetMapping("/about")
	public String about() {
		return "about";
	}
	
	//Navigates the user to the login page
	@GetMapping("/login")
    public String login(Model model) {
        return "login";
    }
}