package com.EatToday.EatToday.Contoller;

import jakarta.annotation.Resource;
import org.springframework.ui.Model;
import com.EatToday.EatToday.DTO.StoreDto;
import com.EatToday.EatToday.Service.StoreService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller

public class StoreController {
    private final StoreService storeService;

    public StoreController(StoreService storeService){
        this.storeService = storeService;
    }

    @RequestMapping(value ="/home")
    public String home(Model model) throws IOException {
        List<StoreDto> storeDtoList = storeService.getBoardList();

        model.addAttribute("list",storeDtoList);

        return "Store";
    }
}
