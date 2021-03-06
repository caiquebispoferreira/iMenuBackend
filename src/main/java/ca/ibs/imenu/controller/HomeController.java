package ca.ibs.imenu.controller;

import ca.ibs.imenu.entity.*;
import ca.ibs.imenu.service.OrderService;
import ca.ibs.imenu.service.ProductService;
import ca.ibs.imenu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * HomeController - this class handles the home routes
 * Date 2021-02-04
 *
 * @author Irisi
 * @version 0.0.1
 */
@Controller
public class HomeController {
	@Autowired
	private UserService userService;
	@Autowired
	private ProductService productService;
	@Autowired
	private OrderService orderService;

	@RequestMapping(value = { "/index" }, method = RequestMethod.GET)
	public String index(Model model, Authentication authentication) {
		boolean result = authentication != null && authentication.isAuthenticated();
		model.addAttribute("body", "index.jsp");
		return result ? "redirect:welcome" : "customerTemplate";
	}

	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public String homepage(Model model, Authentication authentication) {
		boolean result = authentication != null && authentication.isAuthenticated();
		return result ? "redirect:welcome" : "homepage";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(@ModelAttribute("userForm") User userForm, Model model, String error, String logout) {
		checkUser();
		if (error != null)
			model.addAttribute("error", "Your username and password is invalid.");

		if (logout != null)
			model.addAttribute("message", "You have been logged out successfully.");
		return "login";
	}

	@RequestMapping(value = { "/welcome" }, method = RequestMethod.GET)
	public String welcome(Model model, Authentication authentication) {
		org.springframework.security.core.userdetails.User p = (org.springframework.security.core.userdetails.User) authentication
				.getPrincipal();
		User user = userService.findByUsername(p.getUsername());

		return user.getRole().equals(Role.ADMINISTRATOR) ? "redirect:adminPanel" : "redirect:staffPanel";
	}

	@RequestMapping(value = "/adminPanel", method = RequestMethod.GET)
	public String adminPanel(Model model, Authentication authentication) {
		if (authentication != null && authentication.isAuthenticated()) {
			model.addAttribute("currentUser",
					userService.findByUsername(
							((org.springframework.security.core.userdetails.User) authentication.getPrincipal())
									.getUsername()));
		}
		model.addAttribute("body", "adminPanel.jsp");
		model.addAttribute("title", "Panel of Administrator");
		return "adminTemplate";
	}

	@RequestMapping(value = "/staffPanel", method = RequestMethod.GET)
	public String staffPanel(Model model, Authentication authentication) {
		if (authentication != null && authentication.isAuthenticated()) {
			model.addAttribute("currentUser",
					userService.findByUsername(
							((org.springframework.security.core.userdetails.User) authentication.getPrincipal())
									.getUsername()));
		}
		model.addAttribute("body", "staffPanel.jsp");
		model.addAttribute("title", "Panel of Staff");
		return "adminTemplate";
	}

	private void checkUser() {
		User user = userService.findByUsername("admin");
		if (user == null) {
			user = new User();
			user.setName("Administrator");
			user.setRole(Role.ADMINISTRATOR);
			user.setPassword("admin");
			user.setUsername("admin");
			userService.save(user);
		}
	}
}
