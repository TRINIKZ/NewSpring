package Cars;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JDMRepository extends JpaRepository<CarModel, UUID>{
    CarModel findByModelo(String modelo);
    public abstract CarModel findByUsername(String username);

}