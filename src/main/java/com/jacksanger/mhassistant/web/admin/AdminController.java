package com.jacksanger.mhassistant.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jacksanger.mhassistant.database.services.MonsterServices;
import com.jacksanger.mhassistant.models.Monster;
import com.jacksanger.mhassistant.models.Quest;

/*
 * Controller for admin pages. Navigates an admin between pages only admins can access
 */

@Controller
public class AdminController {
	@Autowired
	protected MonsterServices monsterServices;
	
	//Navigates the admin to the admin home page
	@RequestMapping("/admin")
	public String adminHome() {
		return "admin_home";
	}
	
	//Navigates the admin to the admin quest page [DEPRECATED]
	@RequestMapping("/admin/quest")
	public String adminQuest(Model model) {
		model.addAttribute("questToModify", new Quest());
		return "admin_quest";
	}
	
	//Navigates the admin to the admin monster page and creates an object to store details about the monster to modify later
	@RequestMapping("/admin/monster")
	public String adminMonster(Model model) {
		model.addAttribute("monsterToModify", new Monster());
		return "admin_monster";
	}
	
		//Updates the given monster and returns a success or failure
		@PutMapping("/admin/monster/update")
		public String adminMonsterUpdate(@ModelAttribute("monsterToModify") Monster monsterToModify) {
			if (monsterServices.updateMonster(monsterToModify)) {
				return "redirect:/admin/monster?success";
			}
			return "redirect:/admin/monster?fail";
			
		}
		
		//Adds the given monster and returns a success
		@PostMapping("/admin/monster/add")
		public String adminMonsterAdd(@ModelAttribute("monsterToModify") Monster monsterToModify) {
			monsterServices.addMonster(monsterToModify);
			return "redirect:/admin/monster?success";
		}
		
		//Deletes the given monster and returns a success or failure
		@DeleteMapping("/admin/monster/delete")
		public String adminMonsterDelete(@ModelAttribute("monsterToModify") Monster monsterToModify) {
			if (monsterServices.deleteMonster(monsterToModify)) {
				return "redirect:/admin/monster?success";
			}
			return "redirect:/admin/monster?fail";
		}
}