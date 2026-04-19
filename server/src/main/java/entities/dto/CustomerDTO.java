package entities.dto;

import lombok.Data;

@Data
public class CustomerDTO {
    private Long id;

    private String login;

    private String firstName;

    private String fastName;

    private boolean active;
}