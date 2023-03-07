package com.duckduckgoose.app.controllers;

import com.duckduckgoose.app.models.auth.MemberDetails;
import com.duckduckgoose.app.models.database.Honk;
import com.duckduckgoose.app.models.database.Member;
import com.duckduckgoose.app.models.view.MemberViewModel;
import com.duckduckgoose.app.models.view.MembersViewModel;
import com.duckduckgoose.app.services.HonkService;
import com.duckduckgoose.app.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class MemberController {

    private final MemberService memberService;

    private final HonkService honkService;

    @Autowired
    public MemberController(MemberService memberService, HonkService honkService) {
        this.memberService = memberService;
        this.honkService = honkService;
    }

    @RequestMapping(value = "/members", method = RequestMethod.GET)
    public ModelAndView getMembersPage(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "filter", required = false) String filter,
            RedirectAttributes redirectAttributes
    ) {
        List<Member> members;
        if (filter != null && filter.equals("followedUsers")) {
            if (SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                    .stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ANONYMOUS"))) {
                redirectAttributes.addFlashAttribute("redirected", true);
                return new ModelAndView("redirect:/login");
            }
            Member followerMember = ((MemberDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).member();
            if (search == null) {
                members = memberService.getFollowedMembers(followerMember);
            } else {
                members = memberService.getFollowedMembersContaining(search, followerMember);
            }
        } else if (search == null) {
            members = memberService.getMembers();
        } else {
            members = memberService.getMembersContaining(search);
        }
        MembersViewModel membersViewModel = new MembersViewModel(members, search, filter);
        return new ModelAndView("members", "model", membersViewModel);
    }

    @RequestMapping(value = "/member/{username}", method = RequestMethod.GET)
    public ModelAndView getMemberPage(
            @PathVariable String username,
            @RequestParam(value = "search", required = false) String search
    ) {
        Member member = memberService.getMemberByUsername(username);
        List<Honk> honks;
        if (search == null) {
            honks = honkService.getMemberHonks(member);
        } else {
            honks = honkService.getMemberHonksContaining(search, member);
        }
        MemberViewModel memberViewModel = new MemberViewModel(member, honks, search);
        return new ModelAndView("member", "model", memberViewModel);
    }

    @RequestMapping(value = "/member/{username}/follow", method = RequestMethod.POST)
    public ModelAndView followMember(@PathVariable String username) {
        Member followerMember = ((MemberDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).member();
        Member followedMember = memberService.getMemberByUsername(username);
        memberService.addFollower(followerMember, followedMember);
        return new ModelAndView("redirect:/member/" + username);
    }

    @RequestMapping(value = "/member/{username}/unfollow", method = RequestMethod.POST)
    public ModelAndView unfollowMember(@PathVariable String username) {
        Member followerMember = ((MemberDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).member();
        Member followedMember = memberService.getMemberByUsername(username);
        memberService.removeFollower(followerMember, followedMember);
        return new ModelAndView("redirect:/member/" + username);
    }
}
