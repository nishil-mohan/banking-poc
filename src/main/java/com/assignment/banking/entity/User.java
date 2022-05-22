package com.assignment.banking.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
@Table(name = "tbl_user")

@AllArgsConstructor
@NoArgsConstructor
 public class User {


    @Id
    private Long id;
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;


}
