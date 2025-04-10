package Cars;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;


import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping("/jdm")
public class JDMController {

    @Autowired
    private CarRepository jdmRepository;

    @PostMapping("/jdmnovo") //21/03/25
    public ResponseEntity<Object> createJDM(@RequestBody CarModel jdmModel, HttpServletRequest request) {
        // Check if modelo already exists
        var existente = this.jdmRepository.findByModelo(jdmModel.getModelo());
    
        // If the model already exists, return a BAD_REQUEST
        if (existente != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("cadastro existente");
        } else {
            // Ensure that senha is not null before proceeding with hashing
            if (jdmModel.getSenha() == null || jdmModel.getSenha().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Senha não pode ser nula ou vazia");
            }
    
            // Hash the senha using BCrypt if it's not null
            var senhaHash = BCrypt.withDefaults()
                .hashToString(12, jdmModel.getSenha().toCharArray());
            
            // Set the hashed password back to the model
            jdmModel.setSenha(senhaHash);
    
            // Save the new JDMModel to the repository
            var criado = this.jdmRepository.save(jdmModel);
            
            // Return the created model with a CREATED status
            return ResponseEntity.status(HttpStatus.CREATED).body(criado);
        }
    }
    
@GetMapping("/jdmusers") // 13/03/25
public List<CarModel> listarCarros(){
    List<CarModel> carrocad = jdmRepository.findAll();
    return carrocad;
}

@PutMapping("/atualiza")
public ResponseEntity atualizaUser(@RequestBody CarModel jdmModel){
    var criado = this.jdmRepository.save(jdmModel);
    return ResponseEntity.status(HttpStatus.CREATED).body(criado);
}

@DeleteMapping("/deletauser/{id}")
public void deletaUser(@PathVariable UUID id){
    jdmRepository.deleteById(id);
}
}
