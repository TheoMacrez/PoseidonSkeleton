package com.nnk.springboot.controllers;

import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller class for handling login and user-related operations.
 */
@Controller
@RequestMapping("app")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    /**
     * Handles the login request.
     *
     * @param expired a parameter indicating if the session has expired.
     * @return the ModelAndView object for the login page.
     */
    @GetMapping("login")
    public ModelAndView login(@RequestParam(value = "expired", required = false) String expired) {
        ModelAndView mav = new ModelAndView();
        if (expired != null && expired.equals("true")) {
            mav.addObject("errorMsg", "Votre session a expiré. Veuillez vous reconnecter.");
        }
        mav.setViewName("login");
        return mav;
    }

    /**
     * Retrieves all user articles.
     *
     * @return the ModelAndView object containing the list of users.
     */
    @GetMapping("secure/article-details")
    public ModelAndView getAllUserArticles() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("users", userRepository.findAll());
        mav.setViewName("user/list");
        return mav;
    }

    /**
     * Handles the error page request.
     *
     * @return the ModelAndView object for the error page.
     */
    @GetMapping("error")
    public ModelAndView error() {
        ModelAndView mav = new ModelAndView();
        String errorMessage = "You are not authorized for the requested data.";
        mav.addObject("errorMsg", errorMessage);
        mav.setViewName("403");
        return mav;
    }

    /**
     * Handles the logout request.
     *
     * @param model the Model object to add attributes.
     * @return the view name for the login page.
     */
    @PostMapping("/logout")
    public String logoutPage(Model model) {
        model.addAttribute("message", "You are disconnected.");
        return "login";
    }
}
