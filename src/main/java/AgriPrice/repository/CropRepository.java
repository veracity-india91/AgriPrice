package AgriPrice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import AgriPrice.entity.Crop;

public interface CropRepository extends JpaRepository<Crop, Long> {

}
