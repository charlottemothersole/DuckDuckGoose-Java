package com.duckduckgoose.app.controllers;

import com.duckduckgoose.app.models.request.RegistrationRequest;
import com.duckduckgoose.app.services.RegistrationService;
import com.duckduckgoose.app.util.AuthHelper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegistrationController {

    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView getRegistrationPage() {
        if (AuthHelper.isAuthenticated()) {
            return new ModelAndView("redirect:/honks");
        }
        return new ModelAndView("register", "registrationRequest", new RegistrationRequest());
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView onRegistrationSubmit(
            @Valid RegistrationRequest registrationRequest,
            BindingResult bindingResult
    ) {
        if (!registrationRequest.getPassword().equals(registrationRequest.getConfirmPassword())) {
            bindingResult.addError(new FieldError("registrationRequest", "confirmPassword", "Those passwords don't match"));
        }
        if (bindingResult.hasErrors()) {
            return new ModelAndView("register", "registrationRequest", registrationRequest);
        }
        try {
            registrationService.registerMember(registrationRequest);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            bindingResult.addError(new FieldError("registrationRequest", "username", "That username is already taken"));
            return new ModelAndView("register", "registrationRequest", registrationRequest);
        }
        return new ModelAndView("redirect:/login");
    }
}
