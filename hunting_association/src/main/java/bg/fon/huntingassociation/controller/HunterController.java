package bg.fon.huntingassociation.controller;

import bg.fon.huntingassociation.domain.dtos.HunterDto;
import bg.fon.huntingassociation.service.HunterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@Transactional
@RestController
@RequestMapping("api/v1/hunter")
public class HunterController {

    private final HunterService hunterService;
    Logger LOG = LoggerFactory.getLogger(HunterController.class);

    public HunterController(HunterService hunterService) {
        this.hunterService = hunterService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addHunter(@RequestBody HunterDto hunter) {
        try {
            return new ResponseEntity<>(hunterService.addHunter(hunter), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllHunters() {
        return new ResponseEntity<>(hunterService.findAllHunters(), HttpStatus.OK);
    }

    @GetMapping("/all-pageable")
    public ResponseEntity<?> getAllHuntersPageable(@RequestParam("pageNumber") int pageNumber,
                                                   @RequestParam("pageSize") int pageSize,
                                                   @RequestParam(defaultValue = "id") String sortBy,
                                                   @RequestParam(required = false) String filter) {
        return new ResponseEntity<>(hunterService.findAllPageable(pageNumber, pageSize, sortBy, filter), HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> getHunterById(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(hunterService.findHunterById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteHunter(@PathVariable("id") Long id) {
        try {
            hunterService.deleteHunter(id);
            return ResponseEntity.ok("Hunter has been successfully removed!");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("edit")
    public ResponseEntity<?> editHunter(@RequestBody HunterDto hunter) {
        try {
            return new ResponseEntity<>(hunterService.editHunter(hunter), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
