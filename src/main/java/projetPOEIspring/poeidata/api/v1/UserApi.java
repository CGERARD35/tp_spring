package projetPOEIspring.poeidata.api.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.hibernate.engine.spi.ManagedEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import projetPOEIspring.poeidata.api.dto.UserDto;
import projetPOEIspring.poeidata.exceptions.NotAllowedToDeleteManagerException;
import projetPOEIspring.poeidata.exceptions.UnknownResourceException;
import projetPOEIspring.poeidata.mappers.UserMapper;
import projetPOEIspring.poeidata.models.User;
import projetPOEIspring.poeidata.services.UserService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserApi {

    Logger log = LoggerFactory.getLogger(UserApi.class);

    private final UserMapper userMapper;

    private final UserService userService;

    public UserApi(UserMapper userMapper, UserService userService) {
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Return the list of all users")
    public ResponseEntity<List<UserDto>> getAll() {
        log.info("Retrieving users.");
        return ResponseEntity.ok(
                this.userService.getAll().stream().map(this.userMapper::mapUserToUserDto).toList()
        );
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Trying to retrieve an user from the given ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Return the user found the given ID"),
            @ApiResponse(responseCode = "404", description = "No user found the given ID")
    })
    public ResponseEntity<UserDto> findById(@PathVariable final Integer id) {
        try {
            return ResponseEntity.ok(this.userMapper.mapUserToUserDto(this.userService.getById(id)));
        } catch (UnknownResourceException ure) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ure.getMessage());
        }
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    @Operation(summary = "create user")
    @ApiResponse(responseCode = "201", description = "Created")
    public ResponseEntity<UserDto> createUser(@RequestBody final UserDto userDto) {
        log.debug("Attempting to create customer with username {}", userDto.getUsername());

        UserDto userDtoResponse =
                this.userMapper.mapUserToUserDto(
                        this.userService.createUser(
                                this.userMapper.mapUserDtoToUser(userDto)
                        )
                );
        return ResponseEntity.created(URI.create("/v1/users/" + userDtoResponse.getId())).body(userDtoResponse);
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete an user for the given id")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "403", description = "Cannot delete the user for the given ID"),
            @ApiResponse(responseCode = "404", description = "No user found the given ID")
    })
    public ResponseEntity<Void> deleteUser(@PathVariable final Integer id) {
        log.debug("Attempting to delete a customer with id {}", id);
        try {
            this.userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (UnknownResourceException ure) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ure.getMessage());
        } catch (NotAllowedToDeleteManagerException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }

    @PutMapping(path = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    @Operation(summary = "Update an user")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No content"),
    })
    public ResponseEntity<UserDto> updateUser(@PathVariable final Integer id,
                                              @RequestBody UserDto userDto) {
        try {
            log.debug("Updating user {}", userDto.getId());
            userDto.setId(id);
            UserDto updateUser = userMapper.mapUserToUserDto(userService.updateUser(userMapper.mapUserDtoToUser(userDto)));
            log.debug("Successfully updated user {}", userDto.getId());
            return ResponseEntity.ok(updateUser);
        } catch (UnknownResourceException ure) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ure.getMessage());
        }
    }

    @GetMapping(path = "/login", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    @Operation(summary = "Return a user by its username and password")
    public ResponseEntity<UserDto> login(@RequestParam String username, @RequestParam String password) {
        try {
            return ResponseEntity.ok(
                    userMapper.mapUserToUserDto(userService.getByUsernameAndPassword(username, password))
            );
        } catch (UnknownResourceException ure) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ure.getMessage());
        }
    }
}
