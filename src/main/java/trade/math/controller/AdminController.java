package trade.math.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import trade.math.model.dto.TradeBoardGameDTO;
import trade.math.service.TradeBoardGameService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private TradeBoardGameService tradeBoardGameService;

    @RequestMapping(value = "/games", method = RequestMethod.POST)
    public ResponseEntity uploadGameList(@RequestBody List<TradeBoardGameDTO> tradeBoardGameDTOs) {
        tradeBoardGameService.save(tradeBoardGameDTOs);
        return new ResponseEntity(HttpStatus.OK);
    }

}
