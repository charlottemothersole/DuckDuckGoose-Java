package com.duckduckgoose.app.controllers;

import com.duckduckgoose.app.models.auth.MemberDetails;
import com.duckduckgoose.app.models.database.Honk;
import com.duckduckgoose.app.models.database.Member;
import com.duckduckgoose.app.models.view.MemberViewModel;
import com.duckduckgoose.app.models.view.MembersViewModel;
import com.duckduckgoose.app.services.HonkService;
import com.duckduckgoose.app.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.duckduckgoose.app.constants.PaginationConstants.PAGE_SIZE;

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
            @RequestParam (value = "page", required = false) Integer page,
            RedirectAttributes redirectAttributes
    ) {
        Page<Member> members;
        if (page == null) {
            page = 1;
        }
        Pageable pageRequest = PageRequest.of(page - 1, PAGE_SIZE);
        if (filter != null && filter.equals("followedUsers")) {
            if (SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                    .stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ANONYMOUS"))) {
                redirectAttributes.addFlashAttribute("redirected", true);
                return new ModelAndView("redirect:/login");
            }
            Member followerMember = ((MemberDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).member();
            if (search == null) {
                members = memberService.getFollowedMembers(followerMember, pageRequest);
            } else {
                members = memberService.getFollowedMembersContaining(search, followerMember, pageRequest);
            }
        } else if (search == null) {
            members = memberService.getMembers(pageRequest);
        } else {
            members = memberService.getMembersContaining(search, pageRequest);
        }
        MembersViewModel membersViewModel = new MembersViewModel(members, search, filter);
        return new ModelAndView("members", "model", membersViewModel);
    }

    @RequestMapping(value = "/member/{username}", method = RequestMethod.GET)
    public ModelAndView getMemberPage(
            @PathVariable String username,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam (value = "page", required = false) Integer page
    ) {
        Member member = memberService.getMemberByUsername(username);
        Page<Honk> honks;
        if (page == null) {
            page = 1;
        }
        Pageable pageRequest = PageRequest.of(page - 1, PAGE_SIZE);
        if (search == null) {
            honks = honkService.getMemberHonks(member, pageRequest);
        } else {
            honks = honkService.getMemberHonksContaining(search, member, pageRequest);
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
