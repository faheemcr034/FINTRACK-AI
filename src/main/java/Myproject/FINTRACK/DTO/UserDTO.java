package Myproject.FINTRACK.DTO;

public class UserDTO {
    private Long id;
    private String name;
    private String email;

    public UserDTO() {
    }

    public UserDTO(String username, String email, Long id) {
        this.name = username;
        this.email = email;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String username) {
        this.name = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

  
}
