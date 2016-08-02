package guru.tour.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import guru.tour.entity.HotelEntity;
import guru.tour.entity.HotelEntity;
import guru.tour.service.HotelEntityManager;

@RestController
public class HotelRestController {
	
	@Autowired
	HotelEntityManager hotelmanager;
	
	@RequestMapping(value = "/hoteldata/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HotelEntity> gethotel(@PathVariable("id") int id) {
		System.out.println("Fetching hotel with id " + id);
		HotelEntity hotel = hotelmanager.findById(id);
		if (hotel == null) {
			System.out.println("hotel with id " + id + " not found");
			return new ResponseEntity<HotelEntity>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<HotelEntity>(hotel, HttpStatus.OK);
	}

	@RequestMapping(value = "/hoteldata/new", method = RequestMethod.POST)
	public ResponseEntity<Void> createhotel(@RequestBody HotelEntity hotel,
			UriComponentsBuilder ucBuilder) {
		System.out.println("Creating hotel " + hotel.getName());

		if (hotelmanager.isHotelEntity(hotel)) {
			System.out.println("A hotelEntity with name " + hotel.getName()
					+ " already exist");
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}

		hotelmanager.saveHotelEntity(hotel);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/hoteldata/{id}")
				.buildAndExpand(hotel.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/hoteldata/all", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<HotelEntity>> getallhotel() {
		List<HotelEntity> hotel = hotelmanager.getAllHotel();
		if (hotel == null) {
			return new ResponseEntity<List<HotelEntity>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<HotelEntity>>(hotel, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/hoteldata/updatehotel/{id}", method = RequestMethod.PUT)
	public ResponseEntity<HotelEntity> updatehotels(
			@PathVariable("id") int id, @RequestBody HotelEntity hotel) {
		
		System.out.println("Updating hotel " + id);
		HotelEntity currenthotel = hotelmanager.findById(id);

		if (currenthotel == null) {
			System.out.println("hotel with id " + id + " not found");
			return new ResponseEntity<HotelEntity>(HttpStatus.NOT_FOUND);
		}

		currenthotel.setName(hotel.getName());
		currenthotel.setDescription(hotel.getDescription());
		currenthotel.setImages(hotel.getImages());

		hotelmanager.updatehotel(currenthotel);
		return new ResponseEntity<HotelEntity>(currenthotel, HttpStatus.OK);
	}


	@RequestMapping(value = "/hoteldata/deletehotel/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<HotelEntity> deletehotel(@PathVariable("id") int id) {
		System.out.println("Fetching & Deleting hotel with id " + id);

		HotelEntity user = hotelmanager.findById(id);
		if (user == null) {
			System.out.println("Unable to delete. hotel with id " + id
					+ " not found");
			return new ResponseEntity<HotelEntity>(HttpStatus.NOT_FOUND);
		}

		hotelmanager.deletehotelById(id);
		return new ResponseEntity<HotelEntity>(HttpStatus.NO_CONTENT);
	}
}