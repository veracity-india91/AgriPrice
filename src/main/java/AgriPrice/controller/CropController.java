package AgriPrice.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import AgriPrice.entity.Crop;
import AgriPrice.repository.CropRepository;

@RestController
@RequestMapping("/api/crops")
public class CropController {

    private final CropRepository cropRepository;

    public CropController(CropRepository cropRepository) {
        this.cropRepository = cropRepository;
    }

    @PostMapping
    public Crop addCrop(@RequestBody Crop crop) {
        return cropRepository.save(crop);
    }

    @GetMapping
    public List<Crop> getAllCrops() {
        return cropRepository.findAll();
    }
}