package com.examinator.exam.subject;

import com.examinator.security.domain.user.User;
import com.examinator.security.domain.user.UserService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequestMapping("/api")
@RestController
public class SubjectController {

    private final SubjectService subjectService;
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    public SubjectController(SubjectService subjectService, UserDetailsService userDetailsService, UserService userService) {
        this.subjectService = subjectService;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @RequestMapping(value = "/subject/{id}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PostAuthorize("returnObject.owner.username == authentication.name || hasAuthority('ADMIN')")
    public Subject getSubjectById(@PathVariable Long id) {
        return subjectService.findSubjectById(id);
    }

    @RequestMapping(value = "/subject", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PostAuthorize("returnObject.owner.username == authentication.name || hasAuthority('ADMIN')")
    public Subject saveSubject(@RequestBody Subject subject) {
        User user = userService.getUserByUsername(getCurrentUser().getUsername());
        subject.setOwner(user);
        return subjectService.saveSubject(subject);
    }

    @RequestMapping(value = "/subject/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PostAuthorize("returnObject.owner.username == authentication.name || hasAuthority('ADMIN')")
    public void deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubject(id);
    }

    @RequestMapping(value = "/subject", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @ResponseBody
    public Set<Subject> getUserSubjects() {
        return subjectService.getAllSubjectsByOwnerUsername(getCurrentUser().getUsername());
    }

    private UserDetails getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return userDetailsService.loadUserByUsername(authentication.getName());
        } else {
            throw new IllegalStateException("User is not authenticated");
        }
    }
}
