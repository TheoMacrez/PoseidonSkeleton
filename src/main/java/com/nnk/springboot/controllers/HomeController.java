package com.nnk.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController
{


	/**
	 * Handles the request to display the redirect home page for admin.
	 *
	 * @param model the Model object to add attributes.
	 *
	 * @return the view name for the BidList list page.
	 */
	@RequestMapping("/admin/home")
	public String adminHome(Model model)
	{
		return "redirect:/bidList/list";
	}


}
