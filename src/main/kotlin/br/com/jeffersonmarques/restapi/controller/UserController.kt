package br.com.jeffersonmarques.restapi.controller

import br.com.jeffersonmarques.restapi.dto.UserDTO
import br.com.jeffersonmarques.restapi.model.User
import br.com.jeffersonmarques.restapi.service.UserService
import io.swagger.annotations.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.websocket.server.PathParam
import javax.validation.Valid
import javax.validation.constraints.NotBlank

@Api(value = "Apis responsible for customer control", tags = ["User"])
@RestController
@RequestMapping("/v1/users")
class UserController(private val userService: UserService) {

    @ApiOperation(value = "save a new user")
    @ApiImplicitParam(name = "user",
                      value = "parameters needed to save a new user",
                      required = true,
                      dataType = "UserDto")
    @PostMapping()
    fun save(@RequestBody @Valid userDTO: UserDTO): ResponseEntity<User>
            = ResponseEntity(userService.save(userDTO), HttpStatus.CREATED)

    @ApiOperation(value = "Update user by id")
    @PatchMapping("/{id}")
    fun update(@RequestBody @Valid userDTO: UserDTO, @PathVariable("id") id: Long): ResponseEntity<User>
            = ResponseEntity.ok(userService.update(userDTO, id))

    @ApiOperation(value = "Get user by id")
    @GetMapping("/{id}")
    fun getById( @PathVariable("id") id: Long): ResponseEntity<User>
            = ResponseEntity.status(HttpStatus.FOUND).body(userService.findById(id))

    @ApiOperation(value = "Get list of all users or user by name")
    @GetMapping()
    fun getAll(@RequestParam("name", required= false, defaultValue = "") name: String): ResponseEntity<List<User>>
            = ResponseEntity.ok(userService.findAll().filter { it.name!!.contains(name, true) })

    @ApiOperation(value = "Delete user by id")
    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: Long): ResponseEntity<*>
            = ResponseEntity(userService.delete(id), HttpStatus.NO_CONTENT)

}