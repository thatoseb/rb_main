package co.za.sbk.service;

import co.za.sbk.service.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {
    
    /**
     * Save User 
     * 
     * @param userDTO 
     * @return the persisted entity 
     */
    UserDTO save(UserDTO userDTO);

    /**
     * Get all the users.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserDTO> findAll(Pageable pageable);


    /**
     * Find one user by id number.
     *
     * @param userDTO.
     * @return the entity.
     */
    Optional<UserDTO> findUserByIdNumber(UserDTO userDTO);
}
