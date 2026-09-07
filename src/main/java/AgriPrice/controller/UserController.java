package AgriPrice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import AgriPrice.entity.User;
import AgriPrice.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ================= REGISTER =================

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {

        return userRepository.save(user);
    }


    // ================= LOGIN =================

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(
            @RequestBody User user,
            HttpSession session) {

        User existingUser =
                userRepository.findByEmail(user.getEmail());

        if (existingUser != null
                && existingUser.getPassword()
                        .equals(user.getPassword())) {

            // Save logged-in user's name in session
            session.setAttribute(
                    "userName",
                    existingUser.getFullName()
            );

            return ResponseEntity.ok("Login Successful");
        }

        return ResponseEntity
                .badRequest()
                .body("Invalid email or password");
    }


    // ================= CURRENT USER =================

    @GetMapping("/current")
    public ResponseEntity<?> getCurrentUser(
            HttpSession session) {

        String userName =
                (String) session.getAttribute("userName");

        if (userName == null) {

            return ResponseEntity
                    .badRequest()
                    .body("No user logged in");
        }

        return ResponseEntity.ok(userName);
    }


    // ================= LOGOUT =================

    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            HttpSession session) {

        session.invalidate();

        return ResponseEntity.ok("Logout Successful");
    }
}