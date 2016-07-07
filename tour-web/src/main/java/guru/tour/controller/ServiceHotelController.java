package guru.tour.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value="/")
public class ServiceHotelController {
	
	@RequestMapping(value="/hotel")
	public String searchHotel(){
		return "searchhotel";
	}
	
	@RequestMapping(value="/detailhotel")
	public String detailhotel(){
		return "detailhotel";
	}
}
