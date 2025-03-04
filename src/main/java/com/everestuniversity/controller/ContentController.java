package com.everestuniversity.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.everestuniversity.dto.ContentDto;
import com.everestuniversity.entity.ContentEntity;
import com.everestuniversity.repository.ContentRepository;

@RestController
@RequestMapping("/api/private/content/")
public class ContentController {

	@Autowired
	ContentRepository contentRepository;

	@PostMapping("/newannouncement")
	public ResponseEntity<?> newAnnouncement(@RequestBody ContentDto contentDto) {
		// AdminDto admin =
		// webBuilder.build().get().uri("http://CORE_SERVICE/api/private/profile/getuser").retrieve()
		// .bodyToMono(AdminDto.class).block();
		// if (admin.getRole().equalsIgnoreCase("admin")) {
		// ContentEntity contentEntity = new ContentEntity();
		// contentEntity.setTitle(contentDto.getTitle());
		// contentEntity.setBody(contentDto.getBody());
		// contentEntity.setAuthor(contentDto.getAuthor());
		// contentRepository.save(contentEntity);
		// // getAllAnnouncement();
		// return ResponseEntity.ok("Content Successfully add.");
		// }
		return ResponseEntity.ok(null);
	}

	@GetMapping("/getAllAnnouncement")
	public ResponseEntity<?> getAllAnnouncement() {
		List<ContentEntity> contents = contentRepository.findAll();
		if (contents.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Right now, No accouncement available.");
		}
		return ResponseEntity.ok(contents);
	}

	// home page content
	@GetMapping("/getannouncementbyid")
	public ResponseEntity<?> getAnnouncementById(@RequestParam UUID contentId) {
		Optional<ContentEntity> optional = contentRepository.findById(contentId);
		ContentEntity content = optional.get();
		if (content.equals(null)) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Announcement not available.");
		}
		return ResponseEntity.ok(content);
	}

	@PutMapping("/updateannouncement")
	public ResponseEntity<?> updateAnnouncement(@RequestParam UUID contentId, @RequestParam UUID adminId,
			@RequestBody ContentDto contentDto) {
		// AdminDto admin =
		// webBuilder.build().get().uri("http://CORE_SERVICE/api/private/profile/getuser").retrieve()
		// .bodyToMono(AdminDto.class).block();

		Optional<ContentEntity> optional = contentRepository.findById(contentId);
		ContentEntity contentEntity = optional.get();
		contentEntity.setTitle(contentDto.getTitle());
		contentEntity.setBody(contentDto.getBody());
		contentEntity.setAuthor(contentDto.getAuthor());

		contentEntity = contentRepository.save(contentEntity);
		return ResponseEntity.ok(contentEntity);

	}

	// @DeleteMapping("/deleteannouncement")
	// public ResponseEntity<?> deleteAnnouncement(@RequestParam UUID contentId) {
	// // AdminDto admin =
	// webBuilder.build().get().uri("http://CORE_SERVICE/api/private/profile/getuser").retrieve()
	// // .bodyToMono(AdminDto.class).block();
	// Optional<ContentEntity> optional = contentRepository.findById(contentId);
	// ContentEntity contentEntity = optional.get();
	// if (admin.getRole().equalsIgnoreCase("admin")) {
	// contentRepository.delete(contentEntity);
	// return ResponseEntity.ok("Content successfully deleted.");
	// }
	// return ResponseEntity.ok("You are not authenticate person to delete this
	// content.");
	// }

}
