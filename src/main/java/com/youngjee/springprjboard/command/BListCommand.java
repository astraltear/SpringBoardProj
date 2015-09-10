package com.youngjee.springprjboard.command;

import java.util.ArrayList;

import org.springframework.ui.Model;

import com.youngjee.springprjboard.dao.BDao;
import com.youngjee.springprjboard.dto.BDto;

public class BListCommand implements BCommand {

	@Override
	public void execute(Model model) {
		BDao  dao = new BDao();
		ArrayList<BDto> dtos =  dao.list();
		
		model.addAttribute("list", dtos);
	}

}
