package ru.kata.spring.boot_security.demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.mapper.UserMapper;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.model.UserDTO;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Slf4j
@Controller
@RequestMapping(path = "/")
@CrossOrigin(origins = "http://localhost:8080")
@RequiredArgsConstructor
public class AdminController {
    private final String adminUrl = "/admin";
    private final UserService userService;
    private final PasswordEncoder encoder;
    private final RoleService roleService;


    @GetMapping("/admin")
    public String index(@AuthenticationPrincipal org.springframework.security.core.userdetails.User authUser, Model model) {
        model.addAttribute("user", userService.userByUserName(authUser.getUsername()));
        model.addAttribute("isAdmin", authUser.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN")));
        return "index";
    }

    @GetMapping("/user")
    public String user(@AuthenticationPrincipal org.springframework.security.core.userdetails.User authUser, Model model) {
        return index(authUser, model);
    }

    @PostMapping(adminUrl + "/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteUser(@ModelAttribute UserDTO userDTO) {
        userService.deleteUserById(userDTO.getId());
        log.info("Deleting user: {}", userDTO);
        return "redirect:" + adminUrl;
    }

    @PostMapping(adminUrl + "/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateUser(@ModelAttribute UserDTO userDTO) {
        userService.updateUser(UserMapper.toUser(userDTO, encoder, roleService.findAllRoles()));
        log.info("Updating user: {}", userDTO);
        return "redirect:" + adminUrl;
    }

    @PostMapping(adminUrl + "/register")
    @PreAuthorize("hasRole('ADMIN')")
    public String processRegistration(@ModelAttribute UserDTO userDTO) {
        userService.createUser(UserMapper.toUser(userDTO, encoder, roleService.findAllRoles()));
        log.info("Registering user: {}", userDTO);
        return "redirect:" + adminUrl;
    }

    @ModelAttribute("users")
    public Iterable<User> users() {
        log.info("Getting all users");
        return userService.getAllUsers();
    }
}
