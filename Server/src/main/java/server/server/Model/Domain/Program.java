/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server.server.Model.Domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Data;

/**
 *
 * @author Fernando
 */
@Entity
@Table(name="program")
@Data
public class Program implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="IDPROGRAM")
    private long programId; 
    
    @NotEmpty(message = "PRG103") 
    @Column(length=100, name="NAME")
    private String name;
    
    @NotEmpty(message = "PRG104") 
    @Column(length=25, name="code")
    @Pattern(regexp = "^[a-zA-Z0-9]{2,8}", message="PRG107")
    private String code; 
    
    @NotEmpty(message = "PRG105") 
    @Column(length=100, name="location")
    private String location; 
    
    @NotNull(message = "PRG106") 
    @ManyToOne
    @JoinColumn(name="DEPARTMENTID")
    private Department department;
    
    @NotEmpty(message = "PRG109") 
    @Column(length=100, name="color")
    private String color;
}
