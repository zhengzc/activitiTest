package com.zzc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.portlet.ModelAndView;

@Controller
@RequestMapping(value="/")
public class Main {
	
	@RequestMapping(value="main")
	public ModelAndView main(WebRequest req){
		ModelAndView  mav = new ModelAndView();
		
		
		
		return mav;
	}
}
