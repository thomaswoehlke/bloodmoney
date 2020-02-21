package org.woehlke.bloodmoney.frontend.controller;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.woehlke.bloodmoney.frontend.model.UserSession;
import org.woehlke.bloodmoney.oodm.model.BloodPressureMeasurement;
import org.woehlke.bloodmoney.oodm.services.BloodPressureMeasurementService;
import org.woehlke.bloodmoney.user.services.UserSessionService;

import javax.validation.Valid;


@Log
@Controller
@RequestMapping("/measurement")
@SessionAttributes("userSession")
public class MeasurementController {

    @GetMapping("/all")
    public String getAll(
        @PageableDefault(sort={"date","time"},direction=Sort.Direction.DESC) Pageable pageable,
        @SessionAttribute(name="userSession",required=false) UserSession userSession,
        Model model
    ) {
        model = userSessionService.handleUserSession(userSession, model);
        Page<BloodPressureMeasurement> all = bloodPressureMeasurementService.getAll(pageable);
        model.addAttribute("all", all);
        return "measurement/all";
    }

    @GetMapping("/{id}")
    public String getOne(
        @PathVariable("id") BloodPressureMeasurement one,
        @SessionAttribute(name="userSession",required=false) UserSession userSession,
        Model model
    ) {
        model = userSessionService.handleUserSession(userSession, model);
        model.addAttribute("one", one);
        return "measurement/one";
    }

    @GetMapping("/{id}/edit")
    public String editGet(
        @PathVariable("id") BloodPressureMeasurement one,
        @SessionAttribute(name="userSession",required=false) UserSession userSession,
        Model model
    ) {
        model = userSessionService.handleUserSession(userSession, model);
        model.addAttribute("one", one);
        return "measurement/edit";
    }

    @PostMapping("/{id}/edit")
    public final String editPost(
            @PathVariable("id") Long id,
            @Valid BloodPressureMeasurement one,
            @SessionAttribute(name="userSession",required=false) UserSession userSession,
            BindingResult result, Model model
    ) {
        if(result.hasErrors()){
            return "measurement/edit";
        } else {
            one = bloodPressureMeasurementService.update(one);
            return "redirect:/measurement/all";
        }
    }

    @GetMapping("/{id}/delete")
    public String deleteGet(
        @PathVariable("id") BloodPressureMeasurement one,
        @SessionAttribute(name="userSession",required=false) UserSession userSession,
        Model model
    ) {
        bloodPressureMeasurementService.delete(one);
        return "redirect:/measurement/all";
    }

    @GetMapping("/add")
    public String addGet(
        @SessionAttribute(name="userSession",required=false) UserSession userSession,
        Model model
    ) {
        model = userSessionService.handleUserSession(userSession, model);
        BloodPressureMeasurement one = BloodPressureMeasurement.getInstance();
        model.addAttribute("one", one);
        return "measurement/add";
    }

    @PostMapping("/add")
    public final String addPost(
            @Valid BloodPressureMeasurement one,
            @SessionAttribute(name="userSession", required=false) UserSession userSession,
            BindingResult result, Model model
    ) {
        if(result.hasErrors()){
            return "measurement/edit";
        } else {
            one = bloodPressureMeasurementService.add(one);
            return "redirect:/measurement/all";
        }
    }

    private final BloodPressureMeasurementService bloodPressureMeasurementService;
    private final UserSessionService userSessionService;

    @Autowired
    public MeasurementController(
        BloodPressureMeasurementService bloodPressureMeasurementService,
        UserSessionService userSessionService
    ) {
        this.bloodPressureMeasurementService = bloodPressureMeasurementService;
        this.userSessionService = userSessionService;
    }
}
